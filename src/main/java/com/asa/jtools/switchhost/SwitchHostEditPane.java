package com.asa.jtools.switchhost;

import javafx.beans.NamedArg;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * @author andrew_asa
 * @date 2021/10/19.
 */
public class SwitchHostEditPane extends BorderPane {

    private SwitchHostService switchHostService;

    public SwitchHostEditPane(SwitchHostService switchHostService) {

        super();
        init(switchHostService);
    }

    private void init(SwitchHostService switchHostService) {
        this.switchHostService = switchHostService;
        TextArea textArea = new TextArea();
        setCenter(textArea);
    }


}
