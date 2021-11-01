package com.asa.jtools.switchhost;

import com.asa.base.utils.StringUtils;
import com.asa.jtools.switchhost.bean.HostItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;

/**
 * @author andrew_asa
 * @date 2021/10/19.
 */
public class SwitchHostEditPane extends BorderPane {

    private SwitchHostService switchHostService;

    private TextArea textArea;

    private HostItem currentItem;

    public SwitchHostEditPane(SwitchHostService switchHostService) {

        super();
        init(switchHostService);
    }

    private void init(SwitchHostService switchHostService) {

        this.switchHostService = switchHostService;
        this.textArea = new TextArea();
        setCenter(textArea);
        customMenu();
    }

    private void customMenu() {

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuSave = new MenuItem("保存(S)");
        menuSave.setAccelerator(KeyCombination.valueOf("Ctrl+S"));
        menuSave.setOnAction(e -> {
            SwitchHostEvent event = new SwitchHostEvent(SwitchHostEditPane.this, SwitchHostEvent.SWITCH_HOST_SAVE_EVENT, currentItem);
            event.setContent(textArea.getText());
            fireEvent(event);
        });
        contextMenu.getItems().add(menuSave);
        textArea.setContextMenu(contextMenu);
    }

    public void setContent(HostItem currentItem, String content) {

        this.currentItem = currentItem;
        textArea.setText(content);
    }

    public String getContent() {

        return textArea.getText();
    }

    public void invalidItemContent(HostItem item, String newContent) {

        if (currentItem != null && item != null && StringUtils.equals(item.getId(), currentItem.getId())) {
            setContent(currentItem, newContent);
        }
    }
}
