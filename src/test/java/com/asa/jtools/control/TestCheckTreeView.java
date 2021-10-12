package com.asa.jtools.control;

import com.asa.jtools.base.utils.FontIconUtils;
import com.jfoenix.controls.JFXTreeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.CheckTreeView;
import org.kordamp.ikonli.fontawesome.FontAwesome;

/**
 * @author andrew_asa
 * @date 2021/10/9.
 */
public class TestCheckTreeView extends Application {


    @Override
    public void start(Stage stage) {


        VBox vBox = new VBox();
        vBox.getChildren().add(createTreeView());
        vBox.getChildren().add(createJFXTreeView());
        vBox.getChildren().add(createCheckBoxTreeView());
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(borderPane);

        Scene scene = new Scene(stackPane, 900, 700);
        stage.setScene(scene);

        stage.show();
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });

    }

    public TreeView createTreeView() {

        TreeItem<String> root = new TreeItem<String>("Root");
        root.setExpanded(true);
        root.getChildren().addAll(
                new CheckBoxTreeItem<String>("Jonathan"),
                new CheckBoxTreeItem<String>("Eugene"),
                new CheckBoxTreeItem<String>("Henri"),
                new CheckBoxTreeItem<String>("Samir"));
        TreeView<String> checkTreeView = new TreeView<>(root);
        return checkTreeView;
    }

    public TreeView createJFXTreeView() {

        TreeItem<String> root = new TreeItem<String>("Root");
        root.setExpanded(true);
        root.getChildren().addAll(
                new CheckBoxTreeItem<String>("Jonathan"),
                new CheckBoxTreeItem<String>("Eugene"),
                new CheckBoxTreeItem<String>("Henri"),
                new CheckBoxTreeItem<String>("Samir"));
        TreeView<String> checkTreeView = new JFXTreeView<>(root);
        return checkTreeView;
    }

    public TreeView createCheckBoxTreeView() {

        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>("Root");
        root.setExpanded(true);
        root.getChildren().addAll(
                new CheckBoxTreeItem<String>("Jonathan"),
                new CheckBoxTreeItem<String>("Eugene"),
                new CheckBoxTreeItem<String>("Henri"),
                new CheckBoxTreeItem<String>("Samir"));
        CheckTreeView<String> checkTreeView = new CheckTreeView<>(root);
        return checkTreeView;
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

    public static void main(String[] args) {

        launch(args);
    }

}
