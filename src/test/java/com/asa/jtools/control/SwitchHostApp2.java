package com.asa.jtools.control;

import com.asa.jtools.base.utils.FontIconUtils;
import com.asa.base.utils.io.FileSystemResource;
import com.asa.base.utils.io.IOUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;

/**
 * @author andrew_asa
 * @date 2021/10/5.
 */
public class SwitchHostApp2 extends Application {



    @Override
    public void start(Stage stage) {


        VBox vBox = new VBox();
        //vBox.getChildren().add(creatBox("test", true));
        //vBox.getChildren().add(creatBox("test1", false));

        TreeItem<HBox> root = new TreeItem<HBox>(creatBox("root", true));
        TreeItem<HBox> system = new TreeItem<HBox>(creatBox("system hosts", true),FontIconUtils.createIconButton(FontAwesome.DESKTOP, 16));
        TreeItem<HBox> defaultHosts = new TreeItem<HBox>(creatBox("default", true),FontIconUtils.createIconButton(FontAwesome.FILE, 16));
        system.getChildren().add(defaultHosts);

        TreeItem<HBox> net = new TreeItem<HBox>(creatBox("net hosts", false),FontIconUtils.createIconButton(FontAwesome.GLOBE, 18));
        root.getChildren().addAll(system, net);

        root.setExpanded(true);
        TreeView treeView = new JFXTreeView();
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        vBox.getChildren().add(treeView);

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
