package com.lukas.zoohandlungfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ZoohandlungController implements Initializable {

    @FXML
    private TreeView<String> tree;
    @FXML
    private StackPane stack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TreeItem<String> RootNode = new TreeItem<>("Zoohandlungen");
        RootNode.setExpanded(true);
        tree.setShowRoot(false);
        tree.setRoot(RootNode);
        addNode();
        addStack();


    }

    private void addZoohandlung() {
        //addNode
        //addStack
    }

    private void addNode() {
        TreeItem<String> zoohandlung = new TreeItem<>("Unbenannt");
        TreeItem<String> tiere = new TreeItem<>("Tiere");
        TreeItem<String> pfleger = new TreeItem<>("Pfleger");
        TreeItem<String> einstellungen = new TreeItem<>("Einstellungen");
        zoohandlung.getChildren().add(tiere);
        zoohandlung.getChildren().add(pfleger);
        zoohandlung.getChildren().add(einstellungen);
        tree.getRoot().getChildren().add(zoohandlung);
    }

    private void addStack() {
        StackPane pane = new StackPane();
        TabPane tiereTab = new TabPane();
        TabPane pflegerTab = new TabPane();
        AnchorPane settingsPane = new AnchorPane();
        AnchorPane neuesTierPane = new AnchorPane();

        neuesTierPane.getChildren().add(new Button("Meister"));
        //Add Object to Pane

        AnchorPane tierePane = new AnchorPane();
        AnchorPane neuerPflegerPane = new AnchorPane();
        AnchorPane pflegerPane = new AnchorPane();
        Tab neuesTier = new Tab("Neues Tier", neuesTierPane);
        Tab tiere = new Tab("Tiere", tierePane);
        Tab neuerPfleger = new Tab("Neuer Pfleger", neuerPflegerPane);
        Tab pfleger = new Tab("Pfleger", pflegerPane);
        tiereTab.getTabs().addAll(neuesTier, tiere);
        pflegerTab.getTabs().addAll(neuerPfleger, pfleger);
        settingsPane.setVisible(false);
        tiereTab.setVisible(true);
        pflegerTab.setVisible(false);
        pane.setVisible(true);
        pane.getChildren().addAll(settingsPane, tiereTab, pflegerTab);
        stack.getChildren().add(pane);
    }
}