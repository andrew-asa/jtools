package com.asa.jtools.switchhost;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * @author andrew_asa
 * @date 2021/10/5.
 */
public class SwitchHostApp extends Application {

    private SwitchHostService switchHostService;

    private StackPane stackPane;

    private BorderPane borderPane;

    private SwitchHostNavPane nav;

    private SwitchHostEditPane edit;

    @Override
    public void start(Stage stage) {

        stackPane = new StackPane();
        borderPane = new BorderPane();
        nav = new SwitchHostNavPane(stackPane, switchHostService);
        addNavListener(nav, switchHostService);
        borderPane.setCenter(nav);
        edit = new SwitchHostEditPane(switchHostService);
        edit.setPrefWidth(600);
        borderPane.setRight(edit);
        stackPane.getChildren().add(borderPane);

        Scene scene = new Scene(stackPane, 900, 700);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });
    }

    private void addNavListener(SwitchHostNavPane nav, SwitchHostService switchHostService) {

        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_ADD_EVENT, e -> switchHostService.addItem(e.getValue()));
        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_REMOVE_EVENT, e -> switchHostService.removeItem(e.getValue()));
        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_REMOVE_EVENT, e -> switchHostService.removeItem(e.getValue()));
    }

    public void init() throws Exception {

        super.init();
        switchHostService = new SwitchHostService();
        switchHostService.init();
    }

    public static void main(String[] args) {

        launch(args);
    }


}
