package com.asa.jtools.control;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Arrays;

/**
 * @author andrew_asa
 * @date 2021/7/5.
 */
public class TestFontIcon extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle(this.getClass().getSimpleName());
        primaryStage.setScene(getRootScene());
        primaryStage.show();
    }

    public Scene getRootScene() {

        return new Scene(getContentNode(), 1024, 560);
    }

    public Parent getContentNode() {

        FlowPane root = new FlowPane();
        root.setPrefWidth(1000);
        root.setPrefHeight(560);
        ScrollPane sc = new ScrollPane();
        Ikon[] ikon = FontAwesome.values();
        Arrays.stream(ikon).forEach(ic->{
            VBox box = new VBox();
            JFXButton button = new JFXButton();
            button.setGraphic(FontIcon.of(ic,45));
            JFXButton description = new JFXButton(ic.getDescription());
            box.getChildren().addAll(button, description);
            box.setAlignment(Pos.CENTER);
            root.getChildren().add(box);
        });
        sc.setContent(root);
        return sc;
    }
}
