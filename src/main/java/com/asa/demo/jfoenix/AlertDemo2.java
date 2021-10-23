package com.asa.demo.jfoenix;

import com.asa.demo.jfoenix.base.Overdrive;
import com.asa.jtools.base.utils.FontIconUtils;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome.FontAwesome;

public class AlertDemo2 extends Application {

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

        leftButton = new JFXButton("Alert");
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setBody(new Label("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        JFXAlert<Void> alert = new JFXAlert<>(stage);
        alert.setOverlayClose(true);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(layout);
        alert.initModality(Modality.NONE);
        leftButton.setOnAction(action-> alert.showAndWait());


        JFXButton rightButton = new JFXButton("right");
        Alert alert2 = new Alert(Alert.AlertType.ERROR, "Oops! An unrecoverable error occurred.\n" +
                "Please contact your software vendor.\n\n" +
                "The application will stop now.");
        rightButton.setOnAction(action-> alert2.showAndWait());
        vBox.getChildren().addAll(leftButton,rightButton);


        stage.show();
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });

    }


    public HBox creatBox(String text, boolean on) {

        HBox hBox = new HBox();
        Button textLabel = new Button(text);
        //textLabel.setPrefWidth(100);
        //textLabel.setTextAlignment(TextAlignment.LEFT);
        //textLabel.setButtonType(JFXButton.ButtonType.FLAT);
        textLabel.setStyle("text-align: left");
        Button button;
        if (on) {
            button = FontIconUtils.createIconButton(FontAwesome.TOGGLE_ON, 16, Color.GREEN);
        } else {
            button = FontIconUtils.createIconButton(FontAwesome.TOGGLE_OFF, 16);
        }
        hBox.getChildren().addAll(textLabel, button);
        return hBox;
    }
}
