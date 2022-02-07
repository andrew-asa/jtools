package com.asa.jtools.switchhost;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * @author andrew_asa
 * @date 2021/10/5.
 */
public class SwitchHostApp extends Application {


    private SwitchHostMainPane mainPane;

    @Override
    public void start(Stage stage) {

        mainPane = new SwitchHostMainPane();
        mainPane.init(stage);
        Scene scene = new Scene(mainPane.getNode(), 900, 700);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            onClose();
            System.exit(0);
        });
    }

    private void onClose() {

        mainPane.onClose();
    }


    public void init() throws Exception {

        super.init();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
