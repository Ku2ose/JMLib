package com.kurose.jmlib.gui.main.find;

import com.kurose.jmlib.util.Refs;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class CoverPanel extends Stage {

    private String imm;

    private BorderPane root;
    private ImageView imageView;
    private Image image;
    private Label l;
    private ProgressIndicator pi;

    CoverPanel(String s) {
        imm = s;
        image = new Image(imm, true);

        setStage();

        setScene(constructScene());

        show();
    }


    private void setStage() {
        initStyle(StageStyle.UNDECORATED);
        getIcons().add(new Image(Refs.APP_LOGO));
        setTitle("Cover front image");
    }

    private Scene constructScene() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #101010");

        // canc button
        Button canc = new Button("Back");
        canc.setMinWidth(200d);
        root.setBottom(canc);
        BorderPane.setMargin(canc, new Insets(10d));
        BorderPane.setAlignment(canc, Pos.CENTER);
        canc.setOnAction(this::cancAction);

        // the info top label
        l = new Label();
        l.setTextFill(Paint.valueOf("#EEEEEE"));
        l.setText("Computing image...");
        root.setTop(l);
        BorderPane.setAlignment(l, Pos.CENTER);

        // central stackpane
        StackPane sp = new StackPane();

        pi = new ProgressIndicator();
        //pi.lookup(".percentage").setStyle("-fx-fill: #EEEEEE");
        pi.progressProperty().bind(image.progressProperty());
        pi.setMaxSize(50d, 50d);

        pi.progressProperty().addListener(piChangelistener());

        imageView = new ImageView();
        imageView.setVisible(false);
        imageView.setFitWidth(500d);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        sp.getChildren().addAll(pi, imageView);
        StackPane.setAlignment(pi, Pos.CENTER);
        StackPane.setAlignment(imageView, Pos.CENTER);

        root.setCenter(sp);

        imageView.setImage(image);

        return new Scene(root, 500d, 565d);

    }

    private ChangeListener<? super Number> piChangelistener() {
        return (observable, oldValue, newValue) -> {
            if (newValue.intValue() == 1) {
                imageView.setImage(image);
                imageView.setVisible(true);
                pi.setVisible(false);
                l.setText(imageInfoLabel(image));
            }
        };
    }


    private void cancAction(ActionEvent event) {
        this.hide();
    }

    private String imageInfoLabel(Image imm) {
        String type = this.imm.substring(this.imm.lastIndexOf("."));
        String aux = String.format("Original Cover sizes: %.0fx%.0f, format:%s",
                imm.getWidth(), imm.getHeight(), type);

        return aux;
    }
}
