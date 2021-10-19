package com.asa.jtools.switchhost;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * @author andrew_asa
 * @date 2021/10/19.
 */
public class SwitchHostEditPane extends BorderPane {

    public SwitchHostEditPane() {

        super();
        init();
    }

    private void init() {

        TextArea textArea = new TextArea();
        setCenter(textArea);
    }
}
