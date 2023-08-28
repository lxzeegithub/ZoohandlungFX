package com.lukas.zoohandlungfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

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
        tree.setCellFactory((TreeView<String> p) -> new TextFieldTreeCellImpl(stack));
        tree.setShowRoot(false);
        tree.setRoot(RootNode);
        tree.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                if (selectedItem != null) {
                    if (selectedItem.getParent().getParent() != null) {
                        changeStack(
                                selectedItem.getParent().getParent().getChildren().indexOf(selectedItem.getParent()),
                                selectedItem.getParent().getChildren().indexOf(selectedItem));
                    }
                }
            }
        });

        //demo();
    }

    private void demo() {
        addZoohandlung();
        addZoohandlung();
        addZoohandlung();
        tree.edit(null);
    }

    @FXML
    private void addZoohandlung() {
        addNode();
        addStack();
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

    private TextField createTextField(String exampleText, int x, int y, int width, int height, boolean numeric) {
        TextField field = new TextField();
        field.setPromptText(exampleText);
        field.setPrefWidth(width);
        field.setPrefHeight(height);
        field.setTranslateX(x);
        field.setTranslateY(y);
        if (numeric) {
            field.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        field.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        }
        return field;
    }

    private ComboBox<String> createDropdown(String exampleText, int x, int y, int width, int height, String... elements) {
        ComboBox<String> combo = new ComboBox<>();
        combo.setPromptText(exampleText);
        combo.setPrefWidth(width);
        combo.setPrefHeight(height);
        combo.setTranslateX(x);
        combo.setTranslateY(y);
        for (String element : elements) {
            combo.getItems().add(element);
        }
        return combo;
    }

    public Button createButton(String text, int x, int y, int width, int height, Font font, boolean defaultButton) {
        Button button = new Button(text);
        button.setTranslateX(x);
        button.setTranslateY(y);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setFont(font);
        button.setDefaultButton(defaultButton);
        return button;
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
        TextField nameTierTextField = createTextField("Name eingeben", 200, 60, 150, 20, false);
        TextField alterTierTextField = createTextField("Alter eingeben", 200, 90, 150, 20, true);
        TextField rasseTierTextField = createTextField("Rasse eingeben", 200, 150, 150, 20, false);
        TextField preisTierTextField = createTextField("Preis eingeben", 200, 180, 150, 20, true);
        ComboBox<String> tierArtComboBox = createDropdown("Tierart auswählen", 200, 120, 150, 20, "Katze", "Hund");
        Button neuesTierSubmit = createButton("Tier hinzufügen", 360, 380, 140, 30, new Font("Arial", 15), true);
        neuesTierSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                neuesTier();
            }
        });
        neuesTierPane.getChildren().addAll(
                titelNeuesTier,
                nameTierLabel,
                alterTierLabel,
                tierArtLabel,
                rasseTierLabel,
                preisTierLabel,
                nameTierTextField,
                alterTierTextField,
                rasseTierTextField,
                preisTierTextField,
                tierArtComboBox,
                neuesTierSubmit);

        //Tiere
        AnchorPane tierePane = new AnchorPane();


        //Neuer Pfleger
        AnchorPane neuerPflegerPane = new AnchorPane();


        //Pfleger
        AnchorPane pflegerPane = new AnchorPane();


        //Settings


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
        pane.getChildren().addAll(tiereTab, pflegerTab, settingsPane);
        stack.getChildren().add(pane);
    }

    private void changeStack(int parentIndex, int childIndex) {
        for (int i = 0; i < tree.getRoot().getChildren().size(); i++) {
            if (i == parentIndex) {
                StackPane tempStack = (StackPane) stack.getChildren().get(parentIndex);
                tempStack.setVisible(true);
                for (int j = 0; j < tempStack.getChildren().size(); j++) {
                    if (j == childIndex) {
                        tempStack.getChildren().get(childIndex).setVisible(true);
                    } else {
                        tempStack.getChildren().get(j).setVisible(false);
                    }
                }
            } else {
                stack.getChildren().get(i).setVisible(false);
            }
        }
    }

    private void neuesTier() {

    }
}

