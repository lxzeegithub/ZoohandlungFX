package com.lukas.zoohandlungfx;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public final class TextFieldTreeCellImpl extends TreeCell<String> {

    private TextField textField;
    private final ContextMenu addMenu = new ContextMenu();

    public TextFieldTreeCellImpl(StackPane stack) {
        MenuItem addMenuItem = new MenuItem("Zoohandlung Entfernen");
        addMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction((ActionEvent t) -> {
            stack.getChildren().remove(getTreeItem().getParent().getChildren().indexOf(getTreeItem()));
            getTreeItem().getParent().getChildren().remove(getTreeItem());
        });
    }

    @Override
    public void startEdit() {
        if (getTreeItem().getParent().getParent() == null) {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText((String) getItem());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
                if (
                        !getTreeItem().isLeaf()&&getTreeItem().getParent().getParent() == null
                ){
                    setContextMenu(addMenu);
                }
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.ENTER) {
                commitEdit(textField.getText());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}