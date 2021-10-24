package com.asa.jtools.switchhost;

import com.asa.base.log.LoggerFactory;
import com.asa.base.utils.StringUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import com.asa.jtools.switchhost.bean.HostItems;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.rmi.Remote;


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

    private static Stage stage;

    public static Stage getStage() {

        return stage;
    }

    public static void setStage(Stage stage) {

        SwitchHostApp.stage = stage;
    }

    @Override
    public void start(Stage stage) {

        setStage(stage);
        stackPane = new StackPane();
        borderPane = new BorderPane();
        HostItems items = switchHostService.getHostItems();
        nav = new SwitchHostNavPane(stackPane, items);
        addNavListener(nav, switchHostService);
        borderPane.setCenter(nav);
        edit = new SwitchHostEditPane(switchHostService);
        edit.setPrefWidth(600);
        addEditListener(edit, switchHostService);
        borderPane.setRight(edit);
        stackPane.getChildren().add(borderPane);

        Scene scene = new Scene(stackPane, 900, 700);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            onClose();
            System.exit(0);
        });
    }

    private void onClose() {

        switchHostService.destroy();
    }

    private void addEditListener(SwitchHostEditPane edit, SwitchHostService switchHostService) {

        edit.addEventHandler(SwitchHostEvent.SWITCH_HOST_SAVE_EVENT, e -> switchHostService.saveContent(e.getValue(), e.getContent()));
    }

    private void addNavListener(SwitchHostNavPane nav, SwitchHostService switchHostService) {

        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_ADD_EVENT, e -> switchHostService.addItem(e.getValue()));
        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_REMOVE_EVENT, e -> switchHostService.removeItem(e.getValue()));
        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_UPDATE_EVENT, e -> switchHostService.updateItem(e.getValue(), e.getOldValue()));
        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_SELECT_EVENT, e -> selectHostItem(e.getOldValue(), e.getValue()));
        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_REFRESH_REMOTE, e -> refreshRemoteItem(e.getValue()));
    }

    private void refreshRemoteItem(HostItem item) {

        if (switchHostService.isRemoteType(item)) {
            String content = switchHostService.getRemoteContent(item);
            LoggerFactory.getLogger().debug("refresh remote {}", item);
            if (StringUtils.isNotEmpty(content)) {
                switchHostService.saveContent(item,content);
            }
        }
    }

    private void selectHostItem(HostItem oldItem, HostItem newItem) {

        // 保存上一个，显示当前
        switchHostService.updateContent(oldItem, edit.getContent());
        edit.setContent(newItem, switchHostService.getContent(newItem));
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
