package com.asa.demo.base;

import com.asa.jtools.base.utils.FontIconUtils;
import com.asa.jtools.base.utils.Message;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome.FontAwesome;

/**
 * @author andrew_asa
 * @date 2021/10/23.
 */
public class MessageDemo extends Application {

    private Button leftButton;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {


        VBox vBox = new VBox();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(vBox);

        Scene scene = new Scene(stackPane, 900, 700);
        stage.setScene(scene);

        leftButton = new JFXButton("Message.confirm");
        
        leftButton.setOnAction(action-> {
            Message.confirm(stage, "这是一个可以取消/确认的提示");
        });



        vBox.getChildren().addAll(leftButton);


        stage.show();
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });

    }
}
