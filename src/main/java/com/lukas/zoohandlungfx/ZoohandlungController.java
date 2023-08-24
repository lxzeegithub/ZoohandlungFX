package com.lukas.zoohandlungfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

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
        tree.setEditable(true);
        tree.setCellFactory((TreeView<String> p) -> new TextFieldTreeCellImpl());
        tree.setShowRoot(false);
        tree.setRoot(RootNode);
        TreeItem<String> zoohandlung = new TreeItem<>("Unbenannt");
        TreeItem<String> tiere = new TreeItem<>("Tiere");
        TreeItem<String> pfleger = new TreeItem<>("Pfleger");
        TreeItem<String> einstellungen = new TreeItem<>("Einstellungen");
        zoohandlung.getChildren().add(tiere);
        zoohandlung.getChildren().add(pfleger);
        zoohandlung.getChildren().add(einstellungen);
        tree.getRoot().getChildren().add(zoohandlung);
        addStack();


    }

    @FXML
    private void addZoohandlung() {
        addNode();
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
        tree.edit(zoohandlung);
    }

    private Label createLabel(String text, Font font, int x, int y) {
        Label label = new Label(text);
        label.setFont(font);
        label.setTranslateX(x);
        label.setTranslateY(y);
        return label;
    }

    private void addStack() {
        StackPane pane = new StackPane();
        TabPane tiereTab = new TabPane();
        TabPane pflegerTab = new TabPane();
        AnchorPane settingsPane = new AnchorPane();
        tiereTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        pflegerTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Neues Tier
        AnchorPane neuesTierPane = new AnchorPane();
        Label titelNeuesTier = new Label("Neues Tier");
        titelNeuesTier.setFont(new Font("Arial", 24));
        titelNeuesTier.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(titelNeuesTier, 0.0);
        AnchorPane.setRightAnchor(titelNeuesTier, 0.0);
        titelNeuesTier.setAlignment(Pos.CENTER);
        titelNeuesTier.setTranslateY(10);
        Label nameTierLabel = createLabel("Name", new Font("Arial", 15), 120, 60);
        Label alterTierLabel = createLabel("Alter", new Font("Arial", 15), 120, 90);
        Label tierArtLabel = createLabel("Tierart", new Font("Arial", 15), 120, 120);
        Label rasseTierLabel = createLabel("Rasse", new Font("Arial", 15), 120, 150);
        Label preisTierLabel = createLabel("Preis", new Font("Arial", 15), 120, 180);
        neuesTierPane.getChildren().addAll(
                titelNeuesTier,
                nameTierLabel,
                alterTierLabel,
                tierArtLabel,
                rasseTierLabel,
                preisTierLabel);

        //Tiere
        AnchorPane tierePane = new AnchorPane();


        //Neuer Pfleger
        AnchorPane neuerPflegerPane = new AnchorPane();


        //Pfleger
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

