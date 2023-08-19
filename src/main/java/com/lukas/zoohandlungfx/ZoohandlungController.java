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
    private TreeView<String> treeZoo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TreeItem<String> RootNode = new TreeItem<>("Zoohandlungen");
        RootNode.setExpanded(true);
        treeZoo.setRoot(RootNode);

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

    public void openAll() {     //Menu Bar Management / Alle Öffnen
        System.out.println("Alle Zoohandlungen wurden Geöffnet");
    }

    public void closeAll() {    //Menu Bar Management / Alle Schließen
        System.out.println("Alle Zoohandlungen wurden geschlossen");
    }

    public void openTimes() {   //Menu Bar Management / Öffnungzeiten
        System.out.println("Öffnungszeiten ändern");
    }

    public void addZoo() {      //Menu Bar Zoohandlungen / Hinzufügen
        System.out.println("Zoohandlung hinzufügen");
    }

    public void removeZoo() {   //Menu Bar Zoohandlungen / Entfernen
        System.out.println("Zoohandlung entfern");
    }

    public void toggleAutoOpening() {   //Menu Bar Einstellungen / Automatische Öffnungszeiten
        System.out.println("Toggle automatische Öffnungszeiten");
    }

    public void balance() {     //Menu Bar Bank / Kontostand
        System.out.println("Kontostand anzeigen");
    }

    public void addBalance() {  //Menu Bar Bank / Geld Hinzufügen
        System.out.println("Geld hinzufügen");
    }

    public void removeBalance() {
        System.out.println("Geld entfernen");
    }
}