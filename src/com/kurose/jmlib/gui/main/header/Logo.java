package com.kurose.jmlib.gui.main.header;


import com.kurose.jmlib.gui.main.body.LibColumn;
import com.kurose.jmlib.song.MusicLib;
import com.kurose.jmlib.song.Sorter;
import com.kurose.jmlib.util.Refs;
import com.kurose.jmlib.util.Util;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class Logo {

    LibColumn l;

    public Logo(ImageView imageView, LibColumn libColumn) {
        imageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(Refs.APP_LOGO)));

        l = libColumn;
        imageView.setOnMouseClicked(logoMClickEvent());

    }

    private javafx.event.EventHandler<? super MouseEvent> logoMClickEvent() {
        return event -> {

            if (event.getButton() == MouseButton.PRIMARY){

                LogoAlert logoAlert = new LogoAlert(Alert.AlertType.CONFIRMATION);

                Optional<ButtonType> result = logoAlert.showAndWait();

                if (result.isPresent()) {
                    if (result.get() == ButtonType.OK) {

                        Refs.LIB_HOME = logoAlert.getNewDir();
                        Refs.MXM_API_KEY = logoAlert.getMXMKey();

                        Util.writeConfigJsonFile(Refs.LIB_HOME, Refs.MXM_API_KEY);

                        Sorter.updateLib(new MusicLib(Refs.LIB_HOME).getLib());
                        System.out.println("LOG:: Library folder Changed to: " + Refs.LIB_HOME);

                        l.updateC1(Sorter.sortArtists());
                        l.nullifyFocus();
                        l.clearSelection();
                    }
                }
            }
            else if
                    (event.getButton() == MouseButton.SECONDARY){

                try {
                    Desktop.getDesktop().open(new File(Refs.LIB_HOME));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private class LogoAlert extends Alert {

        private TextField libPath;
        private TextField mxmApikey;

        LogoAlert(AlertType alertType) {
            super(alertType);
            this.setTitle("Configutation");
            this.setGraphic(null);
            this.setHeaderText(null);
            this.getDialogPane().setHeader(null);
            this.getDialogPane().setStyle("-fx-background: black");

            Stage s = (Stage) this.getDialogPane().getScene().getWindow();
            s.initStyle(StageStyle.UNDECORATED);
            s.getIcons().add
                    (new Image(getClass().getClassLoader().getResourceAsStream(Refs.APP_LOGO)));


            // layout
            GridPane grid = new GridPane();

            libPath = new TextField(Refs.LIB_HOME);
            mxmApikey = new TextField(Refs.MXM_API_KEY);

            Label labelDir = new Label("Library folder:");
            labelDir.setMinWidth(Control.USE_PREF_SIZE);
            Label labelKey = new Label("MXM API Key; ");
            labelKey.setMinWidth(labelDir.getWidth());

            libPath.setMinWidth(250d);
            libPath.setEditable(false);
            libPath.setTooltip(new Tooltip(Refs.LIB_HOME));
            libPath.setFocusTraversable(false);
            mxmApikey.setMinWidth(250d);
            mxmApikey.setFocusTraversable(false);

            grid.setHgap(20d); grid.setVgap(10d);
            grid.setPadding(new Insets(10d));
            ColumnConstraints column1 = new ColumnConstraints();
            ColumnConstraints column2 = new ColumnConstraints(250d, 250d, 320d);
            column2.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().addAll(column1, column2);

            // add label 1
            GridPane.setHalignment(labelDir, HPos.RIGHT);
            grid.add(labelDir, 0, 0);
            // add label 2
            GridPane.setHalignment(labelKey, HPos.RIGHT);
            grid.add(labelKey, 0, 1);
            // add txt 1
            GridPane.setHalignment(libPath, HPos.LEFT);
            grid.add(libPath, 1, 0);
            // add txt 2
            GridPane.setHalignment(mxmApikey, HPos.LEFT);
            grid.add(mxmApikey, 1, 1);

            libPath.setOnMouseClicked(browseAction());

            this.getDialogPane().setContent(grid);

        }

        private String getNewDir() {
            return libPath.getText();
        }
        private String getMXMKey() { return mxmApikey.getText(); }

        private EventHandler<MouseEvent> browseAction() {
            return event -> {
                DirectoryChooser jc = new DirectoryChooser();
                jc.setTitle("Select new library folder");
                jc.setInitialDirectory(new File(Refs.LIB_HOME));

                File res = jc.showDialog(this.getOwner());

                // TODO remember the choice of absolute path
                if (res != null){
                    libPath.setText(res.getAbsolutePath().replace("\\", "/"));
                }
            };
        }
    }
}
