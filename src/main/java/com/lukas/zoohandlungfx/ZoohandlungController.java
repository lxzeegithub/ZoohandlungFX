package com.lukas.zoohandlungfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ZoohandlungController implements Initializable {

    @FXML
    private TreeView<String> tree;
    @FXML
    private StackPane stack;
    private Zoohandlung[] zoohandlungen = new Zoohandlung[0];

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
        //Zoohandlung zum Array Hinzufügen
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

    private Button createButton(String text, int x, int y, int width, int height, Font font, boolean defaultButton) {
        Button button = new Button(text);
        button.setTranslateX(x);
        button.setTranslateY(y);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setFont(font);
        button.setDefaultButton(defaultButton);
        return button;
    }

    private Label createTitel(String text) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", 24));
        label.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 0.0);
        label.setAlignment(Pos.CENTER);
        label.setTranslateY(10);
        return label;
    }

    private CheckBox createCheckBox(boolean startValue, int x, int y) {
        CheckBox check = new CheckBox();
        check.setSelected(startValue);
        check.setTranslateX(x);
        check.setTranslateY(y);
        return check;
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
        Label titelNeuesTier = createTitel("Neues Tier");
        Label nameTierLabel = createLabel("Name", new Font("Arial", 15), 120, 60);
        Label alterTierLabel = createLabel("Alter", new Font("Arial", 15), 120, 90);
        Label tierArtLabel = createLabel("Tierart", new Font("Arial", 15), 120, 120);
        Label rasseTierLabel = createLabel("Rasse", new Font("Arial", 15), 120, 150);
        Label preisTierLabel = createLabel("Preis", new Font("Arial", 15), 120, 180);
        TextField nameTierTextField = createTextField("Name eingeben", 200, 60, 150, 20, false);
        TextField alterTierTextField = createTextField("Alter eingeben", 200, 90, 150, 20, true);
        TextField rasseTierTextField = createTextField("Rasse eingeben", 200, 150, 150, 20, false);
        TextField preisTierTextField = createTextField("Preis eingeben", 200, 180, 150, 20, true);
        ComboBox<String> tierArtComboBox = createDropdown("Tierart auswählen", 200, 120, 150, 20, "Katze", "Hund", "Vogel", "Hamster");
        Button neuesTierSubmit = createButton("Tier hinzufügen", 360, 380, 140, 30, new Font("Arial", 15), true);
        neuesTierSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                neuesTier(nameTierTextField, alterTierTextField, rasseTierTextField, preisTierTextField, tierArtComboBox);
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
        Label titleTiere = createTitel("Tiere");
        tierePane.getChildren().addAll(
                titleTiere
        );

        //Neuer Pfleger
        AnchorPane neuerPflegerPane = new AnchorPane();
        Label titleNeuerPfleger = createTitel("Neuer Pfleger");
        Label namePflegerLabel = createLabel("Name", new Font("Arial", 15), 120, 60);
        Label alterPflegerLabel = createLabel("Alter", new Font("Arial", 15), 120, 90);
        Label geschlechtPflegerLabel = createLabel("Geschlecht", new Font("Arial", 15), 120, 120);
        Label gehaltPflegerLabel = createLabel("Gehalt", new Font("Arial", 15), 120, 150);
        TextField namePflegerTextField = createTextField("Name eingeben", 200, 60, 150, 20, false);
        TextField alterPflegerTextField = createTextField("Alter eingeben", 200, 90, 150, 20, true);
        TextField gehaltPflegerTextField = createTextField("Gehalt eingeben", 200, 150, 150, 20, true);
        ComboBox<String> geschlechtPflegerComboBox = createDropdown("Geschlecht auswählen", 200, 120, 150, 20, "Mann", "Frau");
        Button neuerPflegerSubmit = createButton("Pfleger hinzufügen", 360, 380, 150, 30, new Font("Arial", 15), true);
        neuerPflegerSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                neuerPfleger(namePflegerTextField, alterPflegerTextField, gehaltPflegerTextField, geschlechtPflegerComboBox);
            }
        });
        neuerPflegerPane.getChildren().addAll(
                titleNeuerPfleger,
                namePflegerLabel,
                alterPflegerLabel,
                geschlechtPflegerLabel,
                gehaltPflegerLabel,
                namePflegerTextField,
                alterPflegerTextField,
                gehaltPflegerTextField,
                geschlechtPflegerComboBox,
                neuerPflegerSubmit
        );

        //Pfleger
        AnchorPane pflegerPane = new AnchorPane();
        Label titlePfleger = createTitel("Pfleger");
        pflegerPane.getChildren().addAll(
                titlePfleger
        );

        //Settings
        Label titelSettings = createTitel("Einstellungen");
        Label automatischeOeffnungszeitenLabel = createLabel("Automatische Öffnungszeiten", new Font("Arial", 14), 30, 60);
        CheckBox automatischeOeffnungszeitenCheck = createCheckBox(false, 230, 60);
        Pane oeffnungszeitenPane = new Pane();
        oeffnungszeitenPane.setDisable(true);
        automatischeOeffnungszeitenCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (automatischeOeffnungszeitenCheck.isSelected()) {
                    oeffnungszeitenPane.setDisable(false);
                } else {
                    oeffnungszeitenPane.setDisable(true);
                }
            }
        });

        oeffnungszeitenPane.setPrefSize(400, 250);
        oeffnungszeitenPane.setTranslateY(120);
        Label oeffnenLabel = createLabel("Öffnen", new Font("Arial", 14), 30, 35);
        Label schliessenLabel = createLabel("Schließen", new Font("Arial", 14), 30, 65);
        Label montagLabel = createLabel("Mo", new Font("Arial", 14), 120, 5);
        Label dienstagLabel = createLabel("Di", new Font("Arial", 14), 180, 5);
        Label mittwochLabel = createLabel("Mi", new Font("Arial", 14), 240, 5);
        Label donnerstagLabel = createLabel("Do", new Font("Arial", 14), 300, 5);
        Label freitagLabel = createLabel("Fr", new Font("Arial", 14), 360, 5);
        Label samstagLabel = createLabel("Sa", new Font("Arial", 14), 420, 5);
        Label sonntagLabel = createLabel("So", new Font("Arial", 14), 480, 5);
        TextField montagOeffnenField = createTextField("800", 106, 30, 50, 10, true);
        TextField dienstagOeffnenField = createTextField("800", 166, 30, 50, 10, true);
        TextField mittwochOeffnenField = createTextField("800", 226, 30, 50, 10, true);
        TextField donnerstagOeffnenField = createTextField("800", 286, 30, 50, 10, true);
        TextField freitagOeffnenField = createTextField("800", 346, 30, 50, 10, true);
        TextField samstagOeffnenField = createTextField("800", 406, 30, 50, 10, true);
        TextField sonntagOeffnenField = createTextField("800", 466, 30, 50, 10, true);
        TextField montagSchliessenField = createTextField("1800", 106, 60, 50, 10, true);
        TextField dienstagSchliessenField = createTextField("1800", 166, 60, 50, 10, true);
        TextField mittwochSchliessenField = createTextField("1800", 226, 60, 50, 10, true);
        TextField donnerstagSchliessenField = createTextField("1800", 286, 60, 50, 10, true);
        TextField freitagSchliessenField = createTextField("1800", 346, 60, 50, 10, true);
        TextField samstagSchliessenField = createTextField("1800", 406, 60, 50, 10, true);
        TextField sonntagSchliessenField = createTextField("1800", 466, 60, 50, 10, true);



        //Öffnen Schließen Button mit Funktion



        oeffnungszeitenPane.getChildren().addAll(
                oeffnenLabel,
                schliessenLabel,
                montagLabel,
                dienstagLabel,
                mittwochLabel,
                donnerstagLabel,
                freitagLabel,
                samstagLabel,
                sonntagLabel,
                montagOeffnenField,
                dienstagOeffnenField,
                mittwochOeffnenField,
                donnerstagOeffnenField,
                freitagOeffnenField,
                samstagOeffnenField,
                sonntagOeffnenField,
                montagSchliessenField,
                dienstagSchliessenField,
                mittwochSchliessenField,
                donnerstagSchliessenField,
                freitagSchliessenField,
                samstagSchliessenField,
                sonntagSchliessenField
        );
        settingsPane.getChildren().addAll(
                titelSettings,
                automatischeOeffnungszeitenLabel,
                automatischeOeffnungszeitenCheck,
                oeffnungszeitenPane
        );

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

    private void neuesTier(TextField name, TextField alter, TextField rasse, TextField preis, ComboBox<String> tierart) {
        if (name.getText() != "" && alter.getText() != "" && rasse.getText() != "" && preis.getText() != "" && tierart.getValue() != null) {
            System.out.println(name.getText());
            System.out.println(alter.getText());
            System.out.println(rasse.getText());
            System.out.println(preis.getText());
            System.out.println(tierart.getValue());
            System.out.println();
            //Neues Tier erstellen und zum Array hinzufügen
            tierart.setValue(null);
            name.setText("");
            alter.setText("");
            rasse.setText("");
            preis.setText("");
        }
    }

    private void neuerPfleger(TextField name, TextField alter, TextField gehalt, ComboBox<String> geschlecht) {
        if (name.getText() !=  "" && alter.getText() != "" && gehalt.getText() != "" && geschlecht.getValue() != null) {
            System.out.println(name.getText());
            System.out.println(alter.getText());
            System.out.println(gehalt.getText());
            System.out.println(geschlecht.getValue());
            System.out.println();
            //Neuen Pfleger erstellen und zum Array hinzufügen
            geschlecht.setValue(null);
            name.setText("");
            alter.setText("");
            gehalt.setText("");
        }
    }

    @FXML
    public void addMoney() {
        TextInputDialog td = new TextInputDialog("Betrag eigeben");
        td.setContentText("Betrag in €");
        td.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    td.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        td.setHeaderText(null);
        td.setGraphic(null);
        td.setTitle("Geld hinzufügen");
        td.getDialogPane().setPrefWidth(250);
        td.getDialogPane().setPrefHeight(90);
        td.setResizable(false);
        td.show();
    }
}

