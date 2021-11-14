package com.asa.jtools.bin;

import com.asa.base.log.LoggerFactory;
import com.asa.base.utils.ListUtils;
import com.asa.base.utils.StringUtils;
import com.asa.jtools.base.lang.GuiSupport;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/10/24.
 */
public class JIconPick extends Application implements GuiSupport {

    private BorderPane pane;

    private ComboBox<String> listView;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle(this.getClass().getSimpleName());
        primaryStage.setScene(getRootScene());
        primaryStage.show();
    }

    public Scene getRootScene() {

        pane = new BorderPane();
        setContent(IConTypeString.FontAwesome, "");
        HBox searchPane = new HBox();
        TextField tf = new TextField();
        listView = new ComboBox<>();
        tf.setOnKeyPressed(event -> {
            if (KeyCode.ENTER == event.getCode()) {
                LoggerFactory.getLogger().debug("search {}", tf.getText());
                setContent(listView.getSelectionModel().getSelectedItem(), tf.getText());
            }
        });
        List<String> listItem = getTypes();
        listView.getItems().addAll(listItem);
        listView.setValue(IConTypeString.FontAwesome);
        searchPane.getChildren().addAll(tf, listView);
        pane.setTop(searchPane);

        return new Scene(pane, 1024, 560);
    }

    public List<String> getTypes() {

        List<String> ret = new ArrayList<>();
        for (IKonType type : IKonType.values()) {
            ret.add(type.toString());
        }
        return ret;
    }

    public void setContent(String type, String searchText) {

        Parent content = getContentNode(type, searchText);
        pane.setCenter(content);
    }

    public Parent getContentNode(String type, String searchText) {

        FlowPane root = new FlowPane();
        root.setPrefWidth(1000);
        root.setPrefHeight(560);
        ScrollPane sc = new ScrollPane();
        List<Ikon> ikons = getIkons(type, searchText);
        root.getChildren().clear();
        ikons.forEach(ic -> {
            VBox box = new VBox();
            JFXButton button = new JFXButton();
            button.setGraphic(FontIcon.of(ic, 45));
            JFXButton description = new JFXButton(ic.getDescription());
            box.getChildren().addAll(button, description);
            box.setAlignment(Pos.CENTER);
            root.getChildren().add(box);
        });
        sc.setContent(root);
        return sc;
    }

    public enum IKonType {
        FontAwesome
    }

    public static class IConTypeString {

        public static final String FontAwesome = IKonType.FontAwesome.toString();
    }

    public List<Ikon> getIkons(String type, String searchValue) {

        List<Ikon> ret = new ArrayList<>();
        List<Ikon> temp = new ArrayList<>();
        switch (type) {
            case "FontAwesome":
                ListUtils.safeAdd(temp, ListUtils.arrayToList(FontAwesome.values()));
                break;
        }
        if (StringUtils.isNotEmpty(searchValue)) {
            String sl = searchValue.toLowerCase();
            for (Ikon ikon : temp) {
                if (StringUtils.contains(ikon.getDescription().toLowerCase(), sl)) {
                    ret.add(ikon);
                }
            }
        } else {
            return temp;
        }
        return ret;
    }
}
