package com.lukas.zoohandlungfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.security.Key;
import java.security.spec.ECField;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    private final int UP = 0;
    private final int DOWN = 1;
    private final int NAME = 0;
    private final int ALTER = 1;
    private final int TIERART = 2;
    private final int RASSE = 3;
    private final int PREIS = 4;
    private final int GESCHLECHT = 2;
    private final int GEHALT = 3;

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

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(60), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                updateOpenZoohandlung();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void demo() {
        addZoohandlung();
        addZoohandlung();
        addZoohandlung();
        tree.edit(null);
        zoohandlungen[0].neuesTier("Karl", "Perscheid", 12, 100, "Hund");
        zoohandlungen[0].neuesTier("Emil", "Werner", 16, 250, "Vogel");
        zoohandlungen[0].neuesTier("Daniel", "Baur", 5, 14, "Katze");
        zoohandlungen[0].neuesTier("Florian", "Rex", 88, 56, "Hamster");
        zoohandlungen[0].neuesTier("Mika", "Kuppke", 54, 11, "Hund");
        zoohandlungen[0].neuerPfleger("Karl", "Männlich", 12, 1500);
        zoohandlungen[0].neuerPfleger("Ute", "Weiblich", 54, 1200);
        zoohandlungen[0].neuerPfleger("Mona", "Weiblich", 33, 2700);
        zoohandlungen[0].neuerPfleger("Felix", "Männlich", 18, 800);
        zoohandlungen[0].neuerPfleger("Hans", "Männlich", 67, 2450);
        StackPane stackPane = (StackPane) stack.getChildren().get(0);
        TabPane tab = (TabPane) stackPane.getChildren().get(0);
        Tab tiereTab = tab.getTabs().get(1);
        AnchorPane tierePane = (AnchorPane) tiereTab.getContent();
        TableView table = (TableView) tierePane.getChildren().get(3);
        table.getItems().setAll(zoohandlungen[0].getTiere());

        TabPane tabPfleger = (TabPane) stackPane.getChildren().get(1);
        Tab pflegerTab = tabPfleger.getTabs().get(1);
        AnchorPane pflegerPane = (AnchorPane) pflegerTab.getContent();
        TableView tablePfleger = (TableView) pflegerPane.getChildren().get(3);
        tablePfleger.getItems().setAll(zoohandlungen[0].getPfleger());
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
        TableView<Tier> table = new TableView<>();
        TableView<Pfleger> tablePfleger = new TableView<>();

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
                neuesTier(nameTierTextField, alterTierTextField, rasseTierTextField, preisTierTextField, tierArtComboBox, pane.getParent().getChildrenUnmodifiable().indexOf(pane), table);
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
        TextField searchBar = createTextField("Suchen", 150, 50, 360, 20, false);
        ComboBox<String> searchDropdown = createDropdown("", 30, 50, 100, 20, "Name", "Alter", "Tierart", "Rasse", "Preis");
        searchDropdown.setValue("Name");
        table.setEditable(true);
        table.setPrefWidth(480);
        table.setPrefHeight(300);
        table.setTranslateX(30);
        table.setTranslateY(80);
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setReorderable(false);
        nameColumn.setResizable(false);
        nameColumn.setPrefWidth(120);
        TableColumn alterColumn = new TableColumn("Alter");
        alterColumn.setCellValueFactory(new PropertyValueFactory<>("alter"));
        alterColumn.setReorderable(false);
        alterColumn.setResizable(false);
        alterColumn.setPrefWidth(70);
        TableColumn tierartColumn = new TableColumn("Tierart");
        tierartColumn.setCellValueFactory(new PropertyValueFactory<>("tierart"));
        tierartColumn.setReorderable(false);
        tierartColumn.setResizable(false);
        tierartColumn.setEditable(false);
        tierartColumn.setPrefWidth(100);
        TableColumn rasseColumn = new TableColumn("Rasse");
        rasseColumn.setCellValueFactory(new PropertyValueFactory<>("rasse"));
        rasseColumn.setReorderable(false);
        rasseColumn.setResizable(false);
        rasseColumn.setEditable(false);
        rasseColumn.setPrefWidth(100);
        TableColumn preisColumn = new TableColumn("Preis");
        preisColumn.setCellValueFactory(new PropertyValueFactory<>("preis"));
        preisColumn.setReorderable(false);
        preisColumn.setResizable(false);
        preisColumn.setPrefWidth(90);
        table.getColumns().addAll(nameColumn, alterColumn, tierartColumn, rasseColumn, preisColumn);
        table.sortPolicyProperty().set(t -> {
            TableColumn sortingColumn = null;
            TableColumn.SortType type = null;
            String searchBarText = searchBar.getText();
            String searchDropdownText = searchDropdown.getValue();
            int zooIndex;
            try {
                zooIndex = pane.getParent().getChildrenUnmodifiable().indexOf(pane);
            } catch(Exception e) {
                return true;
            }
            if (table.getSortOrder().size() > 0) {
                sortingColumn = table.getSortOrder().get(0);
                type = table.getColumns().get(table.getColumns().indexOf(sortingColumn)).getSortType();
            }
            Tier[] workingArray = zoohandlungen[zooIndex].getTiere();

            //Suchen
            if (!searchBarText.equals("")) {
                Tier[] searched = new Tier[workingArray.length];
                int help = 0;
                for (int i = 0; i < workingArray.length; i++) {
                    if (lineareSuche(workingArray[i], searchBarText, searchDropdownText)) {
                        searched[help] = workingArray[i];
                        help++;
                    }
                }
                workingArray = searched;
                for (int i = 0; i < workingArray.length; i++) {
                    if (workingArray[i] == null) {
                        workingArray = Arrays.copyOf(workingArray, workingArray.length-(workingArray.length-i));
                        break;
                    }
                }
            }

            if (sortingColumn != null) {
                if (type == TableColumn.SortType.ASCENDING) {
                    switch (sortingColumn.getText()) {
                        case "Name" -> quickSort(workingArray, 0, workingArray.length-1, NAME, UP);
                        case "Alter" -> quickSort(workingArray, 0, workingArray.length-1, ALTER, UP);
                        case "Tierart" -> quickSort(workingArray, 0, workingArray.length-1, TIERART, UP);
                        case "Rasse" -> quickSort(workingArray, 0, workingArray.length-1, RASSE, UP);
                        case "Preis" -> quickSort(workingArray, 0, workingArray.length-1, PREIS, UP);
                    }
                } else {
                    switch (sortingColumn.getText()) {
                        case "Name" -> quickSort(workingArray, 0, workingArray.length-1, NAME, DOWN);
                        case "Alter" -> quickSort(workingArray, 0, workingArray.length-1, ALTER, DOWN);
                        case "Tierart" -> quickSort(workingArray, 0, workingArray.length-1, TIERART, DOWN);
                        case "Rasse" -> quickSort(workingArray, 0, workingArray.length-1, RASSE, DOWN);
                        case "Preis" -> quickSort(workingArray, 0, workingArray.length-1, PREIS, DOWN);
                    }
                }
            }

            table.getItems().setAll(workingArray);
            return true;
        });
        final boolean[] numberListenerActive = {false};
        final boolean[] numberListenerTriggered = {false};

        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!numberListenerActive[0]) {
                    try {
                        table.sort();
                    } catch (Exception e) {
                        ;
                    }
                }
            }
        });
        ChangeListener<String> searchNumberListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (numberListenerTriggered[0]) {
                    numberListenerTriggered[0] = false;
                    return;
                }
                if (!newValue.matches("\\d*")) {
                    numberListenerTriggered[0] = true;
                    searchBar.setText(newValue.replaceAll("[^\\d]", ""));
                }
                table.sort();
            }
        };
        searchDropdown.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if ((t1 == "Alter" || t1 == "Preis") && !numberListenerActive[0]) {
                    searchBar.setText("");
                    searchBar.textProperty().addListener(searchNumberListener);
                    numberListenerActive[0] = true;
                } else if (t1 != "Alter" && t1 != "Preis" && numberListenerActive[0]){
                    searchBar.textProperty().removeListener(searchNumberListener);
                    numberListenerActive[0] = false;
                }
                table.sort();
            }
        });

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Tier, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Tier, String> t) {
                        Tier tier = t.getRowValue();
                        tier.setName(t.getNewValue());
                    }
                }
        );
        alterColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        alterColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Tier, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Tier, Integer> t) {
                        Tier tier = t.getRowValue();
                        tier.setAlter(t.getNewValue());
                    }
                }
        );
        preisColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        preisColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Tier, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Tier, Integer> t) {
                        Tier tier = t.getRowValue();
                        tier.setPreis(t.getNewValue());
                    }
                }
        );
        table.setRowFactory(new Callback<TableView<Tier>, TableRow<Tier>>() {
            @Override
            public TableRow<Tier> call(TableView<Tier> tableView) {
                TableRow<Tier> row = new TableRow<>();
                ContextMenu contextMenu = new ContextMenu();

                MenuItem deleteItem = new MenuItem("Löschen");
                MenuItem sellItem = new MenuItem("Verkaufen");
                deleteItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Tier tier = row.getItem();
                        zoohandlungen[pane.getParent().getChildrenUnmodifiable().indexOf(pane)].tierEntfernen(tier);
                        tableView.getItems().remove(tier);
                    }
                });

                sellItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Tier tier = row.getItem();
                        balance = balance + tier.getPreis();
                        deleteItem.fire();
                    }
                });

                contextMenu.getItems().addAll(deleteItem, sellItem);
                row.setContextMenu(contextMenu);

                return row;
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
                neuerPfleger(namePflegerTextField, alterPflegerTextField, gehaltPflegerTextField, geschlechtPflegerComboBox, pane.getParent().getChildrenUnmodifiable().indexOf(pane), tablePfleger);
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
        TextField searchBarPfleger = createTextField("Suchen", 150, 50, 360, 20, false);
        ComboBox<String> searchDropdownPfleger = createDropdown("", 30, 50, 100, 20, "Name", "Alter", "Geschlecht", "Gehalt");
        searchDropdownPfleger.setValue("Name");
        tablePfleger.setEditable(true);
        tablePfleger.setPrefWidth(480);
        tablePfleger.setPrefHeight(300);
        tablePfleger.setTranslateX(30);
        tablePfleger.setTranslateY(80);
        TableColumn nameColumnPfleger = new TableColumn("Name");
        nameColumnPfleger.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumnPfleger.setReorderable(false);
        nameColumnPfleger.setResizable(false);
        nameColumnPfleger.setPrefWidth(150);
        TableColumn alterColumnPfleger = new TableColumn("Alter");
        alterColumnPfleger.setCellValueFactory(new PropertyValueFactory<>("alter"));
        alterColumnPfleger.setReorderable(false);
        alterColumnPfleger.setResizable(false);
        alterColumnPfleger.setPrefWidth(80);
        TableColumn geschlechtColumnPfleger = new TableColumn("Geschlecht");
        geschlechtColumnPfleger.setCellValueFactory(new PropertyValueFactory<>("geschlecht"));
        geschlechtColumnPfleger.setReorderable(false);
        geschlechtColumnPfleger.setResizable(false);
        geschlechtColumnPfleger.setEditable(false);
        geschlechtColumnPfleger.setPrefWidth(130);
        TableColumn gehaltColumnPfleger = new TableColumn("Gehalt");
        gehaltColumnPfleger.setCellValueFactory(new PropertyValueFactory<>("gehalt"));
        gehaltColumnPfleger.setReorderable(false);
        gehaltColumnPfleger.setResizable(false);
        gehaltColumnPfleger.setPrefWidth(120);
        tablePfleger.getColumns().addAll(nameColumnPfleger, alterColumnPfleger, geschlechtColumnPfleger, gehaltColumnPfleger);
        tablePfleger.sortPolicyProperty().set(t -> {
            TableColumn sortingColumn = null;
            TableColumn.SortType type = null;
            String searchBarText = searchBarPfleger.getText();
            String searchDropdownText = searchDropdownPfleger.getValue();
            int zooIndex;
            try {
                zooIndex = pane.getParent().getChildrenUnmodifiable().indexOf(pane);
            } catch(Exception e) {
                return true;
            }
            if (tablePfleger.getSortOrder().size() > 0) {
                sortingColumn = tablePfleger.getSortOrder().get(0);
                type = tablePfleger.getColumns().get(tablePfleger.getColumns().indexOf(sortingColumn)).getSortType();
            }
            Pfleger[] workingArray = zoohandlungen[zooIndex].getPfleger();

            //Suchen
            if (!searchBarText.equals("")) {
                Pfleger[] searched = new Pfleger[workingArray.length];
                int help = 0;
                for (int i = 0; i < workingArray.length; i++) {
                    if (lineareSuchePfleger(workingArray[i], searchBarText, searchDropdownText)) {
                        searched[help] = workingArray[i];
                        help++;
                    }
                }
                workingArray = searched;
                for (int i = 0; i < workingArray.length; i++) {
                    if (workingArray[i] == null) {
                        workingArray = Arrays.copyOf(workingArray, workingArray.length-(workingArray.length-i));
                        break;
                    }
                }
            }

            if (sortingColumn != null) {
                if (type == TableColumn.SortType.ASCENDING) {
                    switch (sortingColumn.getText()) {
                        case "Name" -> quickSortPfleger(workingArray, 0, workingArray.length-1, NAME, UP);
                        case "Alter" -> quickSortPfleger(workingArray, 0, workingArray.length-1, ALTER, UP);
                        case "Geschlecht" -> quickSortPfleger(workingArray, 0, workingArray.length-1, GESCHLECHT, UP);
                        case "Gehalt" -> quickSortPfleger(workingArray, 0, workingArray.length-1, GEHALT, UP);
                    }
                } else {
                    switch (sortingColumn.getText()) {
                        case "Name" -> quickSortPfleger(workingArray, 0, workingArray.length-1, NAME, DOWN);
                        case "Alter" -> quickSortPfleger(workingArray, 0, workingArray.length-1, ALTER, DOWN);
                        case "Geschlecht" -> quickSortPfleger(workingArray, 0, workingArray.length-1, GESCHLECHT, DOWN);
                        case "Gehalt" -> quickSortPfleger(workingArray, 0, workingArray.length-1, GEHALT, DOWN);
                    }
                }
            }

            tablePfleger.getItems().setAll(workingArray);
            return true;
        });
        final boolean[] numberListenerActivePfleger = {false};
        final boolean[] numberListenerTriggeredPfleger = {false};

        searchBarPfleger.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!numberListenerActive[0]) {
                    try {
                        tablePfleger.sort();
                    } catch (Exception e) {
                        ;
                    }
                }
            }
        });
        ChangeListener<String> searchNumberListenerPfleger = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (numberListenerTriggeredPfleger[0]) {
                    numberListenerTriggeredPfleger[0] = false;
                    return;
                }
                if (!newValue.matches("\\d*")) {
                    numberListenerTriggeredPfleger[0] = true;
                    searchBarPfleger.setText(newValue.replaceAll("[^\\d]", ""));
                }
                tablePfleger.sort();
            }
        };
        searchDropdownPfleger.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if ((t1 == "Alter" || t1 == "Gehalt") && !numberListenerActivePfleger[0]) {
                    searchBarPfleger.setText("");
                    searchBarPfleger.textProperty().addListener(searchNumberListenerPfleger);
                    numberListenerActivePfleger[0] = true;
                } else if (t1 != "Alter" && t1 != "Gehalt" && numberListenerActivePfleger[0]){
                    searchBarPfleger.textProperty().removeListener(searchNumberListenerPfleger);
                    numberListenerActivePfleger[0] = false;
                }
                tablePfleger.sort();
            }
        });

        nameColumnPfleger.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumnPfleger.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Pfleger, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Pfleger, String> t) {
                        Pfleger pfleger = t.getRowValue();
                        pfleger.setName(t.getNewValue());
                    }
                }
        );
        alterColumnPfleger.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        alterColumnPfleger.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Pfleger, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Pfleger, Integer> t) {
                        Pfleger pfleger = t.getRowValue();
                        pfleger.setAlter(t.getNewValue());
                    }
                }
        );
        gehaltColumnPfleger.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        gehaltColumnPfleger.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Pfleger, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Pfleger, Integer> t) {
                        Pfleger pfleger = t.getRowValue();
                        pfleger.setGehalt(t.getNewValue());
                    }
                }
        );
        tablePfleger.setRowFactory(new Callback<TableView<Pfleger>, TableRow<Pfleger>>() {
            @Override
            public TableRow<Pfleger> call(TableView<Pfleger> t) {
                TableRow<Pfleger> row = new TableRow<>();
                ContextMenu contextMenu = new ContextMenu();

                MenuItem deleteItem = new MenuItem("Löschen");
                MenuItem actionItem = new MenuItem("Aktion");
                deleteItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Pfleger pfleger = row.getItem();
                        zoohandlungen[pane.getParent().getChildrenUnmodifiable().indexOf(pane)].pflegerEntfernen(pfleger);
                        t.getItems().remove(pfleger);
                    }
                });

                actionItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Pfleger pfleger = row.getItem();
                        Tier[] tiere = zoohandlungen[pane.getParent().getChildrenUnmodifiable().indexOf(pane)].getTiere();
                        if (tiere.length > 0) {
                            ComboBox<String> actionBox = new ComboBox<>(FXCollections.observableArrayList("Füttern", "Waschen", "Streicheln"));
                            actionBox.setValue("füttern");
                            ComboBox<String> tiereBox = new ComboBox<>();
                            tiereBox.setValue(tiere[0].getName());
                            for (int i = 0; i < tiere.length; i++) {
                                tiereBox.getItems().add(tiere[i].getName());
                            }
                            VBox vbox = new VBox(actionBox, tiereBox);
                            ChoiceDialog<String> dialog = new ChoiceDialog<>();
                            dialog.getDialogPane().setContent(vbox);
                            dialog.setTitle("Aktionen");
                            dialog.setHeaderText("");
                            dialog.setGraphic(null);
                            dialog.showAndWait();
                            String action = actionBox.getValue();
                            String tier = tiereBox.getValue();
                            switch(action) {
                                case "Füttern":
                                    for (int i = 0; i < tiere.length; i++) {
                                        if (tiere[i].getName() == tier) {
                                            pfleger.fuettern(tiere[i]);
                                        }
                                    }
                                    break;
                                case "Waschen":

                                    for (int i = 0; i < tiere.length; i++) {
                                        if (tiere[i].getName() == tier) {
                                            pfleger.waschen(tiere[i]);
                                        }
                                    }
                                    break;
                                case "Streicheln":
                                    for (int i = 0; i < tiere.length; i++) {
                                        if (tiere[i].getName() == tier) {
                                            pfleger.streicheln(tiere[i]);
                                        }
                                    }
                                    break;
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Keine Tiere verfügbar");
                            alert.setHeaderText("");
                            alert.setGraphic(null);
                            alert.setContentText("Es ist kein Tür für Aktionen verfügbar");
                            alert.showAndWait();
                        }

                    }
                });

                contextMenu.getItems().addAll(deleteItem, actionItem);
                row.setContextMenu(contextMenu);

                return row;
            }
        });

        pflegerPane.getChildren().addAll(
                titlePfleger,
                searchBarPfleger,
                searchDropdownPfleger,
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
                int index = pane.getParent().getChildrenUnmodifiable().indexOf(pane);
                try {
                    zoohandlungen[index].setOeffnungszeiten(new int[] {
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
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ungültige Eingabe");
                    alert.setHeaderText("");
                    alert.setGraphic(null);
                    alert.setContentText("Bitte füllen Sie alle Felder aus!");
                    alert.showAndWait();
                }
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

    private boolean lineareSuche(Tier tier, String search, String dropDown) {
        switch (dropDown) {
            case "Name":
                return tier.getName().startsWith(search);
            case "Alter":
                return Integer.toString(tier.getAlter()).startsWith(search);
            case "Tierart":
                return tier.getTierart().startsWith(search);
            case "Rasse":
                return tier.getRasse().startsWith(search);
            case "Preis":
                return Integer.toString(tier.getPreis()).startsWith(search);
            default:
                throw new IllegalArgumentException("Ungültiges Suchkriterium: " + dropDown);
        }
    }

    private boolean lineareSuchePfleger(Pfleger pfleger, String search, String dropDown) {
        switch (dropDown) {
            case "Name":
                return pfleger.getName().startsWith(search);
            case "Alter":
                return Integer.toString(pfleger.getAlter()).startsWith(search);
            case "Geschlecht":
                return pfleger.getGeschlecht().startsWith(search);
            case "Gehalt":
                return Integer.toString(pfleger.getGehalt()).startsWith(search);
            default:
                throw new IllegalArgumentException("Ungültiges Suchkriterium: " + dropDown);
        }
    }

    public void quickSort(Tier[] tiere, int low, int high, int sortCriteria, int sortOrder) {
        if (low < high) {
            int pivotIndex = partition(tiere, low, high, sortCriteria, sortOrder);
            quickSort(tiere, low, pivotIndex - 1, sortCriteria, sortOrder);
            quickSort(tiere, pivotIndex + 1, high, sortCriteria, sortOrder);
        }
    }

    public int partition(Tier[] tiere, int low, int high, int sortCriteria, int sortOrder) {
        Tier pivotTier = tiere[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            int comparisonResult = compareTiere(tiere[j], pivotTier, sortCriteria);

            if ((sortOrder == 0 && comparisonResult < 0) || (sortOrder == 1 && comparisonResult > 0)) {
                i++;

                Tier temp = tiere[i];
                tiere[i] = tiere[j];
                tiere[j] = temp;
            }
        }

        Tier temp = tiere[i + 1];
        tiere[i + 1] = tiere[high];
        tiere[high] = temp;

        return i + 1;
    }

    public void quickSortPfleger(Pfleger[] pfleger, int low, int high, int sortCriteria, int sortOrder) {
        if (low < high) {
            int pivotIndex = partitionPfleger(pfleger, low, high, sortCriteria, sortOrder);
            quickSortPfleger(pfleger, low, pivotIndex - 1, sortCriteria, sortOrder);
            quickSortPfleger(pfleger, pivotIndex + 1, high, sortCriteria, sortOrder);
        }
    }

    public int partitionPfleger(Pfleger[] pfleger, int low, int high, int sortCriteria, int sortOrder) {
        Pfleger pivotPfleger = pfleger[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            int comparisonResult = comparePfleger(pfleger[j], pivotPfleger, sortCriteria);

            if ((sortOrder == 0 && comparisonResult < 0) || (sortOrder == 1 && comparisonResult > 0)) {
                i++;

                Pfleger temp = pfleger[i];
                pfleger[i] = pfleger[j];
                pfleger[j] = temp;
            }
        }

        Pfleger temp = pfleger[i + 1];
        pfleger[i + 1] = pfleger[high];
        pfleger[high] = temp;

        return i + 1;
    }

    public int compareTiere(Tier a, Tier b, int sortCriteria) {
        switch (sortCriteria) {
            case 0:
                return a.getName().compareTo(b.getName());
            case 1:
                return Integer.compare(a.getAlter(), b.getAlter());
            case 2:
                return a.getTierart().compareTo(b.getTierart());
            case 3:
                return a.getRasse().compareTo(b.getRasse());
            case 4:
                return Integer.compare(a.getPreis(), b.getPreis());
            default:
                throw new IllegalArgumentException("Ungültiges Sortierkriterium: " + sortCriteria);
        }
    }

    public int comparePfleger(Pfleger a, Pfleger b, int sortCriteria) {
        switch (sortCriteria) {
            case 0:
                return a.getName().compareTo(b.getName());
            case 1:
                return Integer.compare(a.getAlter(), b.getAlter());
            case 2:
                return a.getGeschlecht().compareTo(b.getGeschlecht());
            case 3:
                return Integer.compare(a.getGehalt(), b.getGehalt());
            default:
                throw new IllegalArgumentException("Ungültiges Sortierkriterium: " + sortCriteria);
        }
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

    private void neuesTier(TextField name, TextField alter, TextField rasse, TextField preis, ComboBox<String> tierart, int zooIndex, TableView<Tier> table) {
        if (name.getText() != "" && alter.getText() != "" && rasse.getText() != "" && preis.getText() != "" && tierart.getValue() != null) {
            zoohandlungen[zooIndex].neuesTier(name.getText(), rasse.getText(), Integer.parseInt(alter.getText()), Integer.parseInt(preis.getText()), tierart.getValue()); // Parameter Hinzufügen
            table.getItems().add(zoohandlungen[zooIndex].getTiere()[zoohandlungen[zooIndex].getTiere().length-1]);
            tierart.setValue(null);
            name.setText("");
            alter.setText("");
            rasse.setText("");
            preis.setText("");
        }
    }

    private void neuerPfleger(TextField name, TextField alter, TextField gehalt, ComboBox<String> geschlecht, int zooIndex, TableView<Pfleger> table) {
        if (name.getText() !=  "" && alter.getText() != "" && gehalt.getText() != "" && geschlecht.getValue() != null) {
            zoohandlungen[zooIndex].neuerPfleger(name.getText(), geschlecht.getValue(), Integer.parseInt(alter.getText()), Integer.parseInt(gehalt.getText()));
            table.getItems().add(zoohandlungen[zooIndex].getPfleger()[zoohandlungen[zooIndex].getPfleger().length-1]);
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

    private void updateOpenZoohandlung() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        int time = Integer.parseInt(currentTime.format(formatter));
        String day = LocalDate.now().getDayOfWeek().toString();
        int indexOpen = 0;
        int indexClose = 7;
        switch (day) {
            case "TUESDAY":
                indexOpen = 1;
                indexClose = 8;
                break;
            case "WEDNESDAY":
                indexOpen = 2;
                indexClose = 9;
                break;
            case "THURSDAY":
                indexOpen = 3;
                indexClose = 10;
            case "FRIDAY":
                indexOpen = 4;
                indexClose = 11;
                break;
            case "SATURDAY":
                indexOpen = 5;
                indexClose = 12;
                break;
            case "SUNDAY":
                indexOpen = 6;
                indexClose = 13;
                break;
        }

        for(int i = 0; i < zoohandlungen.length; i++) {
            if (zoohandlungen[i].getAutomatisch()) {
                System.out.println(Arrays.toString(zoohandlungen[i].getOeffnungszeiten()));
                if (zoohandlungen[i].getOeffnungszeiten()[indexOpen] == time) {
                    tree.getRoot().getChildren().get(i).setGraphic(new ImageView(greenDot));
                    zoohandlungen[i].oeffnen();
                    tree.refresh();
                } else if (zoohandlungen[i].getOeffnungszeiten()[indexClose] == time) {
                    tree.getRoot().getChildren().get(i).setGraphic(new ImageView(redDot));
                    zoohandlungen[i].schliessen();
                    tree.refresh();
                }
            }
        }
    }

}

