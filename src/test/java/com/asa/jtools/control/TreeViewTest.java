/*
 * Copyright (c) 2011, 2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.asa.jtools.control;
import java.util.Arrays;
import java.util.List;

import com.asa.jtools.base.utils.FontIconUtils;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.fontawesome.FontAwesome;


public class TreeViewTest extends Application {

    private final Node rootIcon =
            FontIconUtils.createIconButton(FontAwesome.FOLDER_OPEN, 16);
    private final Node depIcon =
            FontIconUtils.createIconButton(FontAwesome.USER, 16);
    List<Employee> employees = Arrays.<Employee>asList(
            new Employee("Jacob Smith", "Accounts Department"),
            new Employee("Isabella Johnson", "Accounts Department"),
            new Employee("Ethan Williams", "Sales Department"),
            new Employee("Emma Jones", "Sales Department"),
            new Employee("Michael Brown", "Sales Department"),
            new Employee("Anna Black", "Sales Department"),
            new Employee("Rodger York", "Sales Department"),
            new Employee("Susan Collins", "Sales Department"),
            new Employee("Mike Graham", "IT Support"),
            new Employee("Judy Mayer", "IT Support"),
            new Employee("Gregory Smith", "IT Support"));
    TreeItem<String> rootNode;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public TreeViewTest() {
        this.rootNode = new TreeItem<>("MyCompany Human Resources", FontIconUtils.createIconButton(FontAwesome.FOLDER_OPEN, 18));
    }

    @Override
    public void start(Stage stage) {
        rootNode.setExpanded(true);
        for (Employee employee : employees) {
            TreeItem<String> empLeaf = new TreeItem<>(employee.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(employee.getDepartment())){
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> depNode = new TreeItem<>(
                        employee.getDepartment(),
                        FontIconUtils.createIconButton(FontAwesome.USER, 18)
                );
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }

        stage.setTitle("Tree View Sample");
        VBox box = new VBox();
        final Scene scene = new Scene(box, 400, 300);
        scene.setFill(Color.LIGHTGRAY);

        TreeView<String> treeView = new TreeView<>(rootNode);
        // 设置为可编辑的
        treeView.setEditable(true);
        // 单元设置，TreeView下的每个子控件都支持,包扩子子控件,所以添加菜单栏那里只对有儿子有父亲的进行设置
        treeView.setCellFactory((TreeView<String> p) ->
                                        new TextFieldTreeCellImpl());

        box.getChildren().add(treeView);
        stage.setScene(scene);
        stage.show();
    }

    // 对TreeView下的每个单元格进行处理
    private final class TextFieldTreeCellImpl extends TreeCell<String> {

        // 文本编辑
        private TextField textField;

        // 添加菜单
        private final ContextMenu addMenu = new ContextMenu();

        // 构造方法,如果对儿子单元有特殊的需求可以在构造方法里实现
        public TextFieldTreeCellImpl() {

            // 新增菜单栏目
            MenuItem addMenuItem = new MenuItem("Add Employee");
            addMenu.getItems().add(addMenuItem);
            addMenuItem.setOnAction((ActionEvent t) -> {
                TreeItem newEmployee =
                        new TreeItem<>("New Employee");
                getTreeItem().getChildren().add(newEmployee);
            });
        }


        // 所有的TreeView的儿子控件都是支持Edit的,所有每次选中编辑的时候都会执行该方法
        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                // 每个单元会拥有一个文本框
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            // 清空编辑
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            // 为空不处理
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                // 如果正在编辑
                if (isEditing()) {
                    if (textField != null) {
                        // 设置为原来Item的内容
                        textField.setText(getString());
                    }
                    setText(null);
                    // 设置显示为文本内容
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    // 如果有儿子  并且 有父亲，则拥有添加顾客菜单栏
                    if (!getTreeItem().isLeaf() && getTreeItem().getParent() != null) {
                        setContextMenu(addMenu);
                    }else {
                        // 其他情况没有菜单栏
                        setContextMenu(null);
                    }
                }
            }
        }

        // 创建文本区，名字为该item名
        private void createTextField() {
            textField = new TextField(getString());

            // 键盘事件处理，松开键盘时
            textField.setOnKeyReleased((KeyEvent t) -> {
                if (t.getCode() == KeyCode.ENTER) {
                    // 输入回车时，单元编辑为文本框内容
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    // 输入退出键时,清空编辑
                    cancelEdit();
                }
            });
        }

        // 获取该Item的名字
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    public static class Employee {

        private final SimpleStringProperty name;
        private final SimpleStringProperty department;

        private Employee(String name, String department) {
            this.name = new SimpleStringProperty(name);
            this.department = new SimpleStringProperty(department);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String fName) {
            name.set(fName);
        }

        public String getDepartment() {
            return department.get();
        }

        public void setDepartment(String fName) {
            department.set(fName);
        }
    }
}
