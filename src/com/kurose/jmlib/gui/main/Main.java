package com.kurose.jmlib.gui.main;

import com.kurose.jmlib.setup.InstallationManager;
import com.kurose.jmlib.util.Refs;
import com.kurose.jmlib.util.Util;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application
{
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage)
            throws IOException
    {
        setUp();

        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle(Refs.APPNAME + " " + Refs.VERSION);
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(
                new Image(getClass().getClassLoader().getResourceAsStream("com/kurose/jmlib/resources/Icon64x64.png")));

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }
    
    private void setUp() {
        InstallationManager iManager = new InstallationManager();
        iManager.install();

        Refs.LIB_HOME = Util.getPOJO().getHome_dir();
        Refs.MXM_API_KEY = Util.getPOJO().getMxm_api_key();
    }
}
