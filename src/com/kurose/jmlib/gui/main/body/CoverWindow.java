package com.kurose.jmlib.gui.main.body;


import com.kurose.jmlib.util.Refs;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CoverWindow extends Alert{

    private ImageView imageView;
    protected static final ButtonType delCover = new ButtonType("Delete");

    protected CoverWindow(AlertType alertType) {
        super(alertType);

        this.setGraphic(null);
        this.setHeaderText(null);

        this.getButtonTypes().add(delCover);
        imageView = new ImageView();
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        Stage s = (Stage) this.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image(Refs.APP_LOGO));

        ButtonBar bbar = (ButtonBar) this.getDialogPane().lookup(".button-bar");
        bbar.getButtons().get(2).setStyle("-fx-background-color: linear-gradient(#ff5050, #be1d00); -fx-text-fill: white;");
    }

    protected void buildUP(Image image)
    {
        VBox vbox = new VBox();
        StackPane sp = new StackPane();

        vbox.setPadding(new Insets(20, 13, 21, 10));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        sp.setAlignment(Pos.CENTER);
        sp.setPrefSize(500d, 500d);

        imageView.setImage(image);

        sp.setOnMouseClicked(newCoverJChooserEvent());

        sp.getChildren().add(imageView);
        vbox.getChildren().add(sp);
        this.getDialogPane().setContent(vbox);
    }

    protected Image getImage() { return imageView.getImage(); }

    private EventHandler<? super MouseEvent> newCoverJChooserEvent() {
        return (EventHandler<MouseEvent>) event -> {
            FileChooser jc = new FileChooser();
            jc.setTitle("Select a new cover art for the track...");
            jc.setInitialDirectory(new File(System.getProperty("user.home")));

            // just filter only jpg ang png files
            FileChooser.ExtensionFilter fileExtensions =
                    new FileChooser.ExtensionFilter(
                            "Jpg and png", "*.jpg", "*.png", "*.jpeg");
            jc.getExtensionFilters().add(fileExtensions);

            File newCover = jc.showOpenDialog(this.getOwner());

            try {
                if (newCover != null) {
                    BufferedImage bi = ImageIO.read(newCover);
                    imageView.setImage(SwingFXUtils.toFXImage(bi, null));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        };
    }
}
