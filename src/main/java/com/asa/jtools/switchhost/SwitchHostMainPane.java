package com.asa.jtools.switchhost;

import com.asa.base.log.LoggerFactory;
import com.asa.base.utils.StringUtils;
import com.asa.jtools.base.ui.SubPane;
import com.asa.jtools.switchhost.bean.HostItem;
import com.asa.jtools.switchhost.bean.HostItems;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.nio.file.AccessDeniedException;

/**
 * @author andrew_asa
 * @date 2021/10/24.
 */
public class SwitchHostMainPane implements SubPane {

    private StackPane stackPane;

    private BorderPane borderPane;

    private SwitchHostNavPane nav;

    private SwitchHostEditPane edit;

    private SwitchHostService switchHostService;

    public SwitchHostMainPane() {

        stackPane = new StackPane();
        borderPane = new BorderPane();
        init();
    }

    public void init() {

        switchHostService = new SwitchHostService();
        switchHostService.init();
        stackPane.getChildren().add(borderPane);
        HostItems items = switchHostService.getHostItems();
        nav = new SwitchHostNavPane(stackPane, items);
        addNavListener(nav, switchHostService);
        borderPane.setCenter(nav);
        edit = new SwitchHostEditPane(switchHostService);
        edit.setPrefWidth(600);
        addEditListener(edit, switchHostService);
        borderPane.setRight(edit);
    }

    @Override
    public void onClose() {

        switchHostService.destroy();
    }

    @Override
    public Parent getNode() {

        return stackPane;
    }


    private void addEditListener(SwitchHostEditPane edit, SwitchHostService switchHostService) {

        edit.addEventHandler(SwitchHostEvent.SWITCH_HOST_SAVE_EVENT, e -> saveContent(e.getValue(), e.getContent()));
    }

    private void addNavListener(SwitchHostNavPane nav, SwitchHostService switchHostService) {

        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_ADD_EVENT, e -> switchHostService.addItem(e.getValue()));
        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_REMOVE_EVENT, e -> switchHostService.removeItem(e.getValue()));
        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_UPDATE_EVENT, e -> updateItem(e.getValue(), e.getOldValue()));
        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_SELECT_EVENT, e -> selectHostItem(e.getOldValue(), e.getValue()));
        nav.addEventHandler(SwitchHostEvent.SWITCH_HOST_REFRESH_REMOTE, e -> refreshRemoteItem(e.getValue()));
    }

    public void updateItem(HostItem newItem, HostItem oldItem) {

        switchHostService.updateItem(newItem, oldItem);
        //
        if (switchHostService.isApplyItem(newItem, oldItem)) {
            replaceSystemHostsContent(switchHostService.getContent(newItem));
        }
    }

    private void refreshRemoteItem(HostItem item) {

        if (switchHostService.isRemoteType(item)) {
            String content = switchHostService.getRemoteContent(item);
            LoggerFactory.getLogger().debug("refresh remote {}", item);
            if (StringUtils.isNotEmpty(content)) {
                saveContent(item, content);
            }
        }
    }

    public void saveContent(HostItem item, String content) {
        // 保存
        switchHostService.saveContent(item, content);
        //  是否应用
        if (switchHostService.isAvailableItem(item) && item.isApply()) {
            replaceSystemHostsContent(content);
        }
    }

    public void replaceSystemHostsContent(String content) {

        try {
            switchHostService.replaceSystemHostsContent(content);
        } catch (AccessDeniedException e) {
            // 无法访问文件，无法替换/etc/hosts文件

        } catch (Exception e2) {

        }
    }

    private void selectHostItem(HostItem oldItem, HostItem newItem) {

        // 保存上一个，显示当前
        switchHostService.updateContent(oldItem, edit.getContent());
        edit.setContent(newItem, switchHostService.getContent(newItem));
    }
}
