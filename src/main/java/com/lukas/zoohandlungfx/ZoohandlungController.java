package com.lukas.zoohandlungfx;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class ZoohandlungController implements Initializable {

    @FXML
    private TreeView<String> tvZoo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TreeItem<String> RootNode = new TreeItem<>("Zoohandlungen");
        RootNode.setExpanded(true);
        tvZoo.setRoot(RootNode);

        ObservableList<TreeItem<String>> ChildList = RootNode.getChildren();
        for (int i = 1; i < 11; i++) {
            TreeItem<String> item = new TreeItem<>("Zoohandlung " + 1);
            ChildList.add(item);
            ObservableList<TreeItem<String>> subChilds = item.getChildren();
            TreeItem<String> item1 = new TreeItem<>("Tiere");
            TreeItem<String> item2 = new TreeItem<>("Pfleger");
            TreeItem<String> item3 = new TreeItem<>("Mangement");
            subChilds.addAll(item1, item2, item3);
        }
    }
}