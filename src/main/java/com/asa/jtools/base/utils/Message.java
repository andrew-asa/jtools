package com.asa.jtools.base.utils;

import com.asa.base.ui.controls.button.JButton;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author andrew_asa
 * @date 2021/10/23.
 */
public class Message {

    private static final EventHandler<ActionEvent> NULL_ACTION = e -> {
    };

    public static void sureConfirm(Stage stage, String text, EventHandler<ActionEvent> sureAction) {

        confirm(stage, text,  sureAction,NULL_ACTION);
    }

    public static void cancelConfirm(Stage stage, String text, EventHandler<ActionEvent> cancelAction) {

        confirm(stage, text, NULL_ACTION, cancelAction);
    }

    public static boolean confirm(Stage stage, String text) {

        return confirm(stage, text, NULL_ACTION, NULL_ACTION);
    }

    public static boolean confirm(Stage stage, String text, EventHandler<ActionEvent> sureAction) {

        return confirm(stage, text, sureAction, NULL_ACTION);
    }

    public static boolean confirm(Stage stage, String text, EventHandler<ActionEvent> sureAction, EventHandler<ActionEvent> cancelAction) {

        JFXAlert<Boolean> alert = new JFXAlert(stage);
        JFXDialogLayout layout = new JFXDialogLayout();
        //BorderPane titlePane = new BorderPane();
        //titlePane.setBackground(new Background(new BackgroundFill(Color.valueOf("#f7f8fa"),
        //                                                          CornerRadii.EMPTY,
        //                                                          Insets.EMPTY)));
        //Label titleLabel = new Label("提示");
        //titlePane.setLeft(titleLabel);
        //layout.setHeading(titlePane);

        layout.setBody(new Label(text));
        alert.setContent(layout);
        JButton sure = new JButton("确定");
        JButton cancel = new JButton("取消");
        cancel.setButtonLevel(JButton.ButtonLevel.IGNORE);
        sure.setOnAction(se -> {
            alert.setResult(true);
            alert.hideWithAnimation();
            if (sureAction != null) {
                sureAction.handle(se);
            }
        });
        cancel.setOnAction(se -> {
            alert.setResult(false);
            alert.hideWithAnimation();
            if (cancelAction != null) {
                cancelAction.handle(se);
            }
        });
        layout.setActions(cancel, sure);
        return alert.showAndWait().get();
    }
}
