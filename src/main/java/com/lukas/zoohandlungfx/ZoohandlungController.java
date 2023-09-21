package com.lukas.zoohandlungfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ZoohandlungController implements Initializable {

    @FXML
    private TreeView<String> tree;
    @FXML
    private StackPane stack;
    private Zoohandlung[] zoohandlungen = new Zoohandlung[0];
    private int balance = 5000;
    private final Image greenDot = new Image("green.png", 16,16,false, false);
    private final Image redDot = new Image("red.png", 16, 16, false, false);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TreeItem<String> RootNode = new TreeItem<>("Zoohandlungen");
        RootNode.setExpanded(true);
        tree.setEditable(true);
        tree.setCellFactory((TreeView<String> p) -> new TextFieldTreeCellImpl(stack) {
            @Override
            public void removeZoohandlung(int index) {
                for (int i = index; i < zoohandlungen.length-1; i++){
                    zoohandlungen[i] = zoohandlungen[i+1];
                }
                zoohandlungen = Arrays.copyOf(zoohandlungen, zoohandlungen.length-1);
            }

            @Override
            public void renameZoohandlung(int index, String name) {
                zoohandlungen[index].setName(name);
            }
        });
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

        demo();
    }

    private void demo() {
        addZoohandlung();
        addZoohandlung();
        addZoohandlung();
        tree.edit(null);
    }

    @FXML
    private void addZoohandlung() {
        zoohandlungen = Arrays.copyOf(zoohandlungen, zoohandlungen.length+1);
        zoohandlungen[zoohandlungen.length-1] = new Zoohandlung("Unbenannt");
        addNode();
        addStack();
    }

    private void addNode() {
        TreeItem<String> zoohandlung = new TreeItem<>("Unbenannt", new ImageView(redDot));
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
                neuesTier(nameTierTextField, alterTierTextField, rasseTierTextField, preisTierTextField, tierArtComboBox, pane.getParent().getChildrenUnmodifiable().indexOf(pane));
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
        TableView<String> table = new TableView<>();
        table.setEditable(true);
        table.setPrefWidth(480);
        table.setPrefHeight(300);
        table.setTranslateX(30);
        table.setTranslateY(80);
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setReorderable(false);
        nameColumn.setResizable(false);
        nameColumn.setPrefWidth(120);
        TableColumn alterColumn = new TableColumn("Alter");
        alterColumn.setReorderable(false);
        alterColumn.setResizable(false);
        alterColumn.setPrefWidth(70);
        TableColumn tierartColumn = new TableColumn("Tierart");
        tierartColumn.setReorderable(false);
        tierartColumn.setResizable(false);
        tierartColumn.setEditable(false);
        tierartColumn.setPrefWidth(100);
        TableColumn rasseColumn = new TableColumn("Rasse");
        rasseColumn.setReorderable(false);
        rasseColumn.setResizable(false);
        rasseColumn.setEditable(false);
        rasseColumn.setPrefWidth(100);
        TableColumn preisColumn = new TableColumn("Preis");
        preisColumn.setReorderable(false);
        preisColumn.setResizable(false);
        preisColumn.setPrefWidth(90);
        table.getColumns().addAll(nameColumn, alterColumn, tierartColumn, rasseColumn, preisColumn);
        final boolean[] numberListenerActive = {false}; // muss wegen zugriff aus inner class change listener
        TextField searchBar = createTextField("Suchen", 150, 50, 360, 20, false);
        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!numberListenerActive[0]) { //Change Listener Suchleiste update sortieren
                    System.out.println(t1);
                }
            }
        });
        ComboBox<String> searchDropdown = createDropdown("", 30, 50, 100, 20, "Name", "Alter", "Tierart", "Rasse", "Preis");
        searchDropdown.setValue("Name");
        ChangeListener<String> searchNumberListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    searchBar.setText(newValue.replaceAll("[^\\d]", ""));
                }
                System.out.println(searchBar.getText()); //Change Listenerr Suchleiste update Sortieren
            }
        };
        searchDropdown.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if ((t1 == "Alter" || t1 == "Preis") && !numberListenerActive[0]) {
                    searchBar.setText("");
                    searchBar.textProperty().addListener(searchNumberListener);
                    numberListenerActive[0] = true;
                } else if (numberListenerActive[0]){
                    searchBar.textProperty().removeListener(searchNumberListener);
                    numberListenerActive[0] = false;
                }
                // Dropdown Change Listener update sortieren
                System.out.println(t1);
            }
        });

        nameColumn.sortTypeProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println(nameColumn.getText() + " " + nameColumn.getSortType());
            }
        });
        alterColumn.sortTypeProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println(alterColumn.getText() + " " + alterColumn.getSortType());
            }
        });
        tierartColumn.sortTypeProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println(tierartColumn.getText() + " " + tierartColumn.getSortType());
            }
        });
        rasseColumn.sortTypeProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println(rasseColumn.getText() + " " + rasseColumn.getSortType());
            }
        });
        preisColumn.sortTypeProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println(preisColumn.getText() + " " + preisColumn.getSortType());
            }
        });

        tierePane.getChildren().addAll(
                titleTiere,
                searchDropdown,
                searchBar,
                table
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
                neuerPfleger(namePflegerTextField, alterPflegerTextField, gehaltPflegerTextField, geschlechtPflegerComboBox, pane.getParent().getChildrenUnmodifiable().indexOf(pane));
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
        ComboBox<String> searchDropDownPfleger = createDropdown("Suche", 30, 50, 100, 20, "Name", "Alter", "Tierart", "Rasse", "Preis");
        TextField searchBarPfleger = createTextField("Suchen", 150, 50, 360, 20, false);
        TableView<String> tablePfleger = new TableView<>();
        tablePfleger.setEditable(true);
        tablePfleger.setPrefWidth(480);
        tablePfleger.setPrefHeight(300);
        tablePfleger.setTranslateX(30);
        tablePfleger.setTranslateY(80);
        TableColumn nameColumnPfleger = new TableColumn("Name");
        nameColumnPfleger.setReorderable(false);
        nameColumnPfleger.setResizable(false);
        nameColumnPfleger.setPrefWidth(150);
        TableColumn alterColumnPfleger = new TableColumn("Alter");
        alterColumnPfleger.setReorderable(false);
        alterColumnPfleger.setResizable(false);
        alterColumnPfleger.setPrefWidth(100);
        TableColumn geschlechtColumnPfleger = new TableColumn("Geschlecht");
        geschlechtColumnPfleger.setReorderable(false);
        geschlechtColumnPfleger.setResizable(false);
        geschlechtColumnPfleger.setEditable(false);
        geschlechtColumnPfleger.setPrefWidth(120);
        TableColumn gehaltColumnPfleger = new TableColumn("Gehalt");
        gehaltColumnPfleger.setReorderable(false);
        gehaltColumnPfleger.setResizable(false);
        gehaltColumnPfleger.setPrefWidth(110);
        tablePfleger.getColumns().addAll(nameColumnPfleger, alterColumnPfleger, geschlechtColumnPfleger, gehaltColumnPfleger);
        pflegerPane.getChildren().addAll(
                titlePfleger,
                searchDropDownPfleger,
                searchBarPfleger,
                tablePfleger
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
                    int index = automatischeOeffnungszeitenCheck.getParent().getParent().getParent().getChildrenUnmodifiable().indexOf(automatischeOeffnungszeitenCheck.getParent().getParent());
                    zoohandlungen[index].setAutomatisch(true);
                    oeffnungszeitenPane.setDisable(false);

                } else {
                    int index = automatischeOeffnungszeitenCheck.getParent().getParent().getParent().getChildrenUnmodifiable().indexOf(automatischeOeffnungszeitenCheck.getParent().getParent());
                    zoohandlungen[index].setAutomatisch(false);
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
        Button oeffnungsZeitenButton = createButton("Speichern", 380, 100, 110, 30, new Font("Arial",15), false);
        oeffnungsZeitenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = oeffnungsZeitenButton.getParent().getParent().getParent().getChildrenUnmodifiable().indexOf(oeffnungsZeitenButton.getParent().getParent());
                zoohandlungen[index].setOeffnungszeiten(new int[]{
                        Integer.parseInt(montagOeffnenField.getText()),
                        Integer.parseInt(dienstagOeffnenField.getText()),
                        Integer.parseInt(mittwochOeffnenField.getText()),
                        Integer.parseInt(donnerstagOeffnenField.getText()),
                        Integer.parseInt(freitagOeffnenField.getText()),
                        Integer.parseInt(samstagOeffnenField.getText()),
                        Integer.parseInt(sonntagOeffnenField.getText()),
                        Integer.parseInt(montagSchliessenField.getText()),
                        Integer.parseInt(dienstagSchliessenField.getText()),
                        Integer.parseInt(mittwochSchliessenField.getText()),
                        Integer.parseInt(donnerstagSchliessenField.getText()),
                        Integer.parseInt(freitagSchliessenField.getText()),
                        Integer.parseInt(samstagSchliessenField.getText()),
                        Integer.parseInt(sonntagSchliessenField.getText())
                });
            }
        });

        Label manuelOpen = createLabel("Manuelles Öffnen", new Font("Arial", 14), 30, 280);
        Button oeffnenButton = createButton("Öffnen", 160, 273, 80, 30, new Font("Arial", 15), false);
        oeffnenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = oeffnenButton.getParent().getParent().getParent().getChildrenUnmodifiable().indexOf(oeffnenButton.getParent().getParent());
                tree.getRoot().getChildren().get(index).setGraphic(new ImageView(greenDot));
                tree.refresh();
                zoohandlungen[index].oeffnen();
            }
        });
        Button schliessenButton = createButton("Schließen", 260, 273, 80, 30, new Font("Arial", 15), false);
        schliessenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = schliessenButton.getParent().getParent().getParent().getChildrenUnmodifiable().indexOf(schliessenButton.getParent().getParent());
                tree.getRoot().getChildren().get(index).setGraphic(new ImageView(redDot));
                tree.refresh();
                zoohandlungen[index].schliessen();
            }
        });


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
                sonntagSchliessenField,
                oeffnungsZeitenButton
        );
        settingsPane.getChildren().addAll(
                titelSettings,
                automatischeOeffnungszeitenLabel,
                automatischeOeffnungszeitenCheck,
                oeffnungszeitenPane,
                manuelOpen,
                oeffnenButton,
                schliessenButton
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

    private void updateTableTiere() {

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

    private void neuesTier(TextField name, TextField alter, TextField rasse, TextField preis, ComboBox<String> tierart, int zooIndex) {
        if (name.getText() != "" && alter.getText() != "" && rasse.getText() != "" && preis.getText() != "" && tierart.getValue() != null) {
            zoohandlungen[zooIndex].neuesTier(name.getText(), rasse.getText(), Integer.parseInt(alter.getText()), Integer.parseInt(preis.getText()), tierart.getValue()); // Parameter Hinzufügen
            tierart.setValue(null);
            name.setText("");
            alter.setText("");
            rasse.setText("");
            preis.setText("");
        }
    }

    private void neuerPfleger(TextField name, TextField alter, TextField gehalt, ComboBox<String> geschlecht, int zooIndex) {
        if (name.getText() !=  "" && alter.getText() != "" && gehalt.getText() != "" && geschlecht.getValue() != null) {
            zoohandlungen[zooIndex].neuerPfleger(name.getText(), geschlecht.getValue(), Integer.parseInt(alter.getText()), Integer.parseInt(gehalt.getText()));
            geschlecht.setValue(null);
            name.setText("");
            alter.setText("");
            gehalt.setText("");
        }
    }

    @FXML
    private void openAll() {
        for (int i = 0; i < tree.getRoot().getChildren().size(); i++) {
            tree.getRoot().getChildren().get(i).setGraphic(new ImageView(greenDot));
            zoohandlungen[i].oeffnen();
            tree.refresh();
        }
    }

    @FXML
    private void closeAll() {
        for (int i = 0; i < tree.getRoot().getChildren().size(); i++) {
            tree.getRoot().getChildren().get(i).setGraphic(new ImageView(redDot));
            zoohandlungen[i].schliessen();
            tree.refresh();
        }
    }

    @FXML
    private void addMoney() {
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
        td.showAndWait();
        try {
            balance = balance + Integer.parseInt(td.getEditor().getText());
        } catch (RuntimeException e){}
    }

    @FXML
    private void removeMoney() {
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
        td.setTitle("Geld entfernen");
        td.getDialogPane().setPrefWidth(250);
        td.getDialogPane().setPrefHeight(90);
        td.setResizable(false);
        td.showAndWait();
        try {
            balance = balance - Integer.parseInt(td.getEditor().getText());
        } catch (RuntimeException e){}
    }

    @FXML
    private void getMoney() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kontostand");
        alert.setHeaderText("Kontostand");
        alert.setGraphic(null);
        alert.setContentText("Betrag: " + balance);
        alert.showAndWait();
    }


}

