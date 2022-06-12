package gui;

import com.google.common.collect.Sets;
import fuzzy.*;
import fuzzy.summaries.*;
import gui.helpers.MultiSummaryTable;
import gui.helpers.SingleSummaryTable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.NumericVariable;
import model.StringVariable;
import org.paukov.combinatorics3.Generator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainView {

    public TableView<SingleSummaryTable> singleFormTable;
    public TableColumn<SingleSummaryTable, String> singleFormResult;
    public TableColumn<SingleSummaryTable, String> singleFormT1;
    public TableColumn<SingleSummaryTable, String> singleFormT2;
    public TableColumn<SingleSummaryTable, String> singleFormT3;
    public TableColumn<SingleSummaryTable, String> singleFormT4;
    public TableColumn<SingleSummaryTable, String> singleFormT5;
    public TableColumn<SingleSummaryTable, String> singleFormT6;
    public TableColumn<SingleSummaryTable, String> singleFormT7;
    public TableColumn<SingleSummaryTable, String> singleFormT8;
    public TableColumn<SingleSummaryTable, String> singleFormT9;
    public TableColumn<SingleSummaryTable, String> singleFormT10;
    public TableColumn<SingleSummaryTable, String> singleFormT11;
    public TableColumn<SingleSummaryTable, String> singleFormOptimum;
    public TableColumn<SingleSummaryTable, Boolean> singleFormTableCheckBox;
    public TableView<MultiSummaryTable> multiFormTable;
    public TableColumn<MultiSummaryTable, String> multiFormResult;
    public TableColumn<MultiSummaryTable, String> multiFormT;
    public TableColumn<MultiSummaryTable, Boolean> multiFormTableCheckBox;

    @FXML
    TreeView<String> treeViewCheckBox;
    @FXML
    TextField t1Field;
    @FXML
    TextField t2Field;
    @FXML
    TextField t3Field;
    @FXML
    TextField t4Field;
    @FXML
    TextField t5Field;
    @FXML
    TextField t6Field;
    @FXML
    TextField t7Field;
    @FXML
    TextField t8Field;
    @FXML
    TextField t9Field;
    @FXML
    TextField t10Field;
    @FXML
    TextField t11Field;
    @FXML
    MenuButton linguisticVariableSelect;
    @FXML
    MenuButton labelSelect;
    @FXML
    TextField labelNameField;
    @FXML
    Label labelNameAdditional;
    @FXML
    MenuButton functionSelect;
    @FXML
    Label functionLabel1;
    @FXML
    TextField functionField1;
    @FXML
    Label functionLabel2;
    @FXML
    TextField functionField2;
    @FXML
    Label functionLabel3;
    @FXML
    TextField functionField3;
    @FXML
    Label functionLabel4;
    @FXML
    TextField functionField4;
    @FXML
    LineChart<Number, Number> membershipFunctionChart;
    @FXML
    Button saveLabel;
    @FXML
    Button removeLabel;
    @FXML
    VBox advancedThirdColumn;
    @FXML
    MenuItem triangleSelect;
    @FXML
    MenuItem trapezoidalSelect;
    @FXML
    MenuItem gaussianSelect;
    @FXML
    HBox functionHBox3;
    @FXML
    HBox functionHBox4;
    @FXML
    Label errorLabel;
    @FXML
    Label summaryErrorLabel;
    @FXML
    MenuButton firstSubjectSelect;
    @FXML
    MenuButton secondSubjectSelect;

    StringVariable currentStringVariable;
    String subject1;
    String subject2;

    ObservableList<SingleSummaryTable> singleSummaryObservableList = FXCollections.observableArrayList();
    ObservableList<MultiSummaryTable> multiSummaryObservableList = FXCollections.observableArrayList();

    LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();

    HashMap<String, Double> weights;
    HashMap<String,TextField> textFields;

    @FXML
    private void initialize() {
        membershipFunctionChart.getXAxis().setAutoRanging(false);
        membershipFunctionChart.getYAxis().setAutoRanging(false);
        membershipFunctionChart.setCreateSymbols(false);
        ((NumberAxis) membershipFunctionChart.getYAxis()).setUpperBound(1.0);

        initializeFunctionFields();
        initializeWeights();
        initializeAdvanced();
        initializeMembershipSelects();
        rebuildTree();
        initializeSubjects();
        initializeTable();
    }

    private void initializeSubjects() {
        firstSubjectSelect.setText("All reservations");
        currentStringVariable=null;
        subject1=null;
        subject2=null;
        MenuItem item = new MenuItem("All reservations");

        item.setOnAction(actionEvent -> {
            firstSubjectSelect.setText("All reservations");
            secondSubjectSelect.setDisable(true);
            secondSubjectSelect.getItems().clear();
            secondSubjectSelect.setText("---Select second subject---");
            currentStringVariable=null;
            subject1=null;
            subject2=null;
        });
        ArrayList<String> subjects = new ArrayList<>();
        subjects.add("Portugal");
        subjects.add("United Kingdom");
        subjects.add("France");
        subjects.add("Spain");
        subjects.add("Germany");
        firstSubjectSelect.getItems().add(item);
        firstSubjectSelectInitialize(subjects,"Country",StringVariable.countryCode);
        subjects = new ArrayList<>();
        subjects.add("Resort Hotel");
        subjects.add("City Hotel");
        firstSubjectSelectInitialize(subjects,"Hotel",StringVariable.hotel);
        subjects = new ArrayList<>();
        subjects.add("Check-Out");
        subjects.add("Canceled");
        subjects.add("No-Show");
        firstSubjectSelectInitialize(subjects,"Reservation status",StringVariable.status);
        subjects = new ArrayList<>();
        subjects.add("Transient");
        subjects.add("Transient-Party");
        subjects.add("Contract");
        subjects.add("Group");
        firstSubjectSelectInitialize(subjects,"Customer type",StringVariable.customerType);
    }

    private void firstSubjectSelectInitialize(ArrayList<String> names, String header, StringVariable variable) {
        MenuItem item;
        item = new MenuItem("---"+header+"---");
        item.setDisable(true);
        firstSubjectSelect.getItems().add(item);
        for(String name : names) {
            item = new MenuItem(name);
            item.setOnAction(actionEvent -> {
                secondSubjectSelect.getItems().clear();
                firstSubjectSelect.setText(name);
                secondSubjectSelect.setDisable(false);
                currentStringVariable = variable;
                subject1 = name;
                subject2 = null;
                secondSubjectSelect.setText("No second subject");
                MenuItem noSecondSubjectItem = new MenuItem("No second subject");
                noSecondSubjectItem.setOnAction(actionEvent2 -> {
                    subject2=null;
                    secondSubjectSelect.setText("No second subject");
                });
                secondSubjectSelect.getItems().add(noSecondSubjectItem);
                for(String name2: names) {
                    if(!name.equals(name2)) {
                        MenuItem item2 = new MenuItem(name2);
                        item2.setOnAction(actionEvent1 -> {
                            subject2 = name2;
                            secondSubjectSelect.setText(name2);
                        ;});
                        secondSubjectSelect.getItems().add(item2);
                    }
                }
            });
            firstSubjectSelect.getItems().add(item);
        }
    }

    private void initializeTable() {
        HashMap<String,Double> weights = new HashMap<>();
        weights.put("t1",0.1);
        weights.put("t2",0.2);
        weights.put("t3",0.3);
        weights.put("t4",0.4);
        weights.put("t5",0.5);
        weights.put("t6",0.6);
        weights.put("t7",0.7);
        weights.put("t8",0.8);
        weights.put("t9",0.9);
        weights.put("t10",0.0);
        weights.put("t11",0.0);
        weights.put("Optimum",0.5);
        SingleSummaryTable sst1 = new SingleSummaryTable(new SummaryResult("raz",weights));
        SingleSummaryTable sst2 = new SingleSummaryTable(new SummaryResult("dwa",weights));
        SingleSummaryTable sst3 = new SingleSummaryTable(new SummaryResult("trzyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",weights));
        singleSummaryObservableList.addAll(sst1, sst2, sst3);

        HashMap<String,Double> weights1 = new HashMap<>();
        weights1.put("t1",0.1);
        weights1.put("t2",0.06);
        weights1.put("t3",0.004);
        weights1.put("t4",0.4);
        weights1.put("t5",0.5);
        weights1.put("t6",0.6);
        weights1.put("t7",0.7);
        weights1.put("t8",0.8);
        weights1.put("t9",0.9);
        weights1.put("t10",0.0);
        weights1.put("t11",0.0);
        weights1.put("Optimum",0.5);
        SingleSummaryTable sst11 = new SingleSummaryTable(new SummaryResult("raz",weights1));
        SingleSummaryTable sst21 = new SingleSummaryTable(new SummaryResult("dwa",weights1));
        SingleSummaryTable sst31 = new SingleSummaryTable(new SummaryResult("trzyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",weights));
        singleSummaryObservableList.addAll(sst11, sst21, sst31);


        HashMap<String,Double> weights2 = new HashMap<>();
        weights2.put("T",0.1);
        MultiSummaryTable sst12 = new MultiSummaryTable(new SummaryResult("raz",weights2));
        weights2.put("T",0.2);
        MultiSummaryTable sst22 = new MultiSummaryTable(new SummaryResult("dwa",weights2));
        weights2.put("T",0.001);
        MultiSummaryTable sst32 = new MultiSummaryTable(new SummaryResult("trzyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",weights2));
        multiSummaryObservableList.addAll(sst12, sst22, sst32);

        singleFormTableCheckBox.setCellFactory(cellData -> new CheckBoxTableCell<>());
        singleFormResult.setCellValueFactory(cellData -> cellData.getValue().getResultSummary());
        singleFormT1.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t1"));
        singleFormT2.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t2"));
        singleFormT3.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t3"));
        singleFormT4.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t4"));
        singleFormT5.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t5"));
        singleFormT6.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t6"));
        singleFormT7.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t7"));
        singleFormT8.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t8"));
        singleFormT9.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t9"));
        singleFormT10.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t10"));
        singleFormT11.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t11"));
        singleFormOptimum.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("Optimum"));
        singleFormTableCheckBox.setCellValueFactory(cellData -> {
            SingleSummaryTable cellValue = cellData.getValue();
            SimpleBooleanProperty property = cellValue.getIsSelected();
            property.addListener((observable, oldValue, newValue) ->
                    cellValue.setIsSelected(new SimpleBooleanProperty(newValue)));
            return property;
        });
        singleFormTable.setEditable(true);
        singleFormTable.setItems(singleSummaryObservableList);


        multiFormTableCheckBox.setCellFactory(cellData -> new CheckBoxTableCell<>());
        multiFormResult.setCellValueFactory(cellData -> cellData.getValue().getResultSummary());
        multiFormT.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("T"));
        multiFormTableCheckBox.setCellValueFactory(cellData -> {
            MultiSummaryTable cellValue = cellData.getValue();
            SimpleBooleanProperty property = cellValue.getIsSelected();
            property.addListener((observable, oldValue, newValue) ->
                    cellValue.setIsSelected(new SimpleBooleanProperty(newValue)));
            return property;
        });
        multiFormTable.setEditable(true);
        multiFormTable.setItems(multiSummaryObservableList);
    }

    private void rebuildTree() {
        treeViewCheckBox.setRoot(null);
        CheckBoxTreeItem<String> treeRoot = new CheckBoxTreeItem<>("Summary options");
        treeRoot.setExpanded(true);
        CheckBoxTreeItem<String> relativeQuantifier = new CheckBoxTreeItem<>("Relative Quantifier");
        CheckBoxTreeItem<String> absoluteQuantifier = new CheckBoxTreeItem<>("Absolute Quantifier");
        CheckBoxTreeItem<String> summarizer = new CheckBoxTreeItem<>("Summarizers");
        CheckBoxTreeItem<String> qualifier = new CheckBoxTreeItem<>("Qualifiers");

        for (NumericVariable variable : NumericVariable.values()) {
            if (!variable.name().equals(NumericVariable.undefined.name())) {
                if (variable.name().equals(NumericVariable.relativeQuantifier.name())) {
                    LBR.getVariables().get(variable).getLabels().forEach((k,v) ->
                            relativeQuantifier.getChildren().add(new CheckBoxTreeItem<>(k)));
                } else if (variable.name().equals(NumericVariable.absoluteQuantifier.name())) {
                    LBR.getVariables().get(variable).getLabels().forEach((k,v) ->
                            absoluteQuantifier.getChildren().add(new CheckBoxTreeItem<>(k)));
                } else {
                    CheckBoxTreeItem<String> newCheckBox = new CheckBoxTreeItem<>(variable.getName());
                    LBR.getVariables().get(variable).getLabels().forEach((k,v) ->
                            newCheckBox.getChildren().add(new CheckBoxTreeItem<>(variable.getTitleBeforeLabel()? variable.getSummarizerTitle()+" "+k:k+" "+variable.getSummarizerTitle())));
                    CheckBoxTreeItem<String> newCheckBox2 = new CheckBoxTreeItem<>(variable.getName());
                    LBR.getVariables().get(variable).getLabels().forEach((k,v) ->
                            newCheckBox2.getChildren().add(new CheckBoxTreeItem<>(variable.getTitleBeforeLabel()? variable.getSummarizerTitle()+" "+k:k+" "+variable.getSummarizerTitle())));
                    summarizer.getChildren().add(newCheckBox);
                    qualifier.getChildren().add(newCheckBox2);
                }
            }
        }
        treeRoot.getChildren().addAll(relativeQuantifier, absoluteQuantifier, summarizer, qualifier);
        treeViewCheckBox.setRoot(treeRoot);
        treeViewCheckBox.setCellFactory(CheckBoxTreeCell.forTreeView());
    }

    private void initializeWeights() {
        weights = new HashMap<>();
        textFields = new HashMap<>();
        textFields.put("t1",t1Field);
        textFields.put("t2",t2Field);
        textFields.put("t3",t3Field);
        textFields.put("t4",t4Field);
        textFields.put("t5",t5Field);
        textFields.put("t6",t6Field);
        textFields.put("t7",t7Field);
        textFields.put("t8",t8Field);
        textFields.put("t9",t9Field);
        textFields.put("t10",t10Field);
        textFields.put("t11",t11Field);
        Pattern validEditingState = Pattern.compile("(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Double> converter = new StringConverter<>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || ".".equals(s)) {
                    return 1.0;
                } else {
                    return Double.valueOf(s);
                }
            }


            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };
        for(int i=1;i<=11;i++) {
            weights.put("t"+i,1.0);
            TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 1.0, filter);
            textFields.get("t"+i).setTextFormatter(textFormatter);
        }
    }

    private void initializeAdvanced() {
        labelNameField.textProperty().addListener((observableValue, s, t1) -> {
            String joinedString = t1+" "+labelNameAdditional.getText();
            for(MenuItem item : labelSelect.getItems()) {
                if(item.getText().equals(joinedString)) {
                    removeLabel.setDisable(false);
                    saveLabel.setText("Save changes");
                    return;
                }
            }
            removeLabel.setDisable(true);
            saveLabel.setText("Save new");
        });

        for(NumericVariable variable : NumericVariable.values()) {
            if(variable!=NumericVariable.undefined) {
                MenuItem item = new MenuItem(variable.getName());
                item.setOnAction(actionEvent -> {
                    removeLabel.setDisable(true);
                    disabledAndVisibilitiesFirstSet(false);
                    renewLabels(variable);
                });
                linguisticVariableSelect.getItems().add(item);
            }
        }
    }

    private void initializeMembershipSelects() {
        triangleSelect.setOnAction(actionEvent -> triangleSelected());
        trapezoidalSelect.setOnAction(actionEvent -> trapezoidalSelected());
        gaussianSelect.setOnAction(actionEvent -> gaussianSelected());
    }

    public void triangleSelected() {
        functionSelect.setText("Triangle");
        functionHBox3.setVisible(true);
        functionHBox4.setVisible(false);
        functionLabel1.setText("Point A");
        functionLabel2.setText("Point B");
        functionLabel3.setText("Point C");
    }

    public void trapezoidalSelected() {
        functionSelect.setText("Trapezoidal");
        functionHBox3.setVisible(true);
        functionHBox4.setVisible(true);
        functionLabel1.setText("Point A");
        functionLabel2.setText("Point B");
        functionLabel3.setText("Point C");
        functionLabel4.setText("Point D");
    }

    public void gaussianSelected() {
        functionSelect.setText("Gaussian");
        functionHBox3.setVisible(false);
        functionHBox4.setVisible(false);
        functionLabel1.setText("Middle");
        functionLabel2.setText("Variance");
    }

    public void initializeFunctionFields() {
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Double> converter = new StringConverter<>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || ".".equals(s) || "-.".equals(s) || "-".equals(s)) {
                    return 1.0;
                } else {
                    return Double.valueOf(s);
                }
            }


            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };
        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 1.0, filter);
        functionField1.setTextFormatter(textFormatter);
        textFormatter = new TextFormatter<>(converter, 1.0, filter);
        functionField2.setTextFormatter(textFormatter);
        textFormatter = new TextFormatter<>(converter, 1.0, filter);
        functionField3.setTextFormatter(textFormatter);
        textFormatter = new TextFormatter<>(converter, 1.0, filter);
        functionField4.setTextFormatter(textFormatter);

    }

    @FXML
    private void applyWeights() {
        for(int i=1;i<=11;i++) {
            weights.put("t"+i,Double.valueOf(textFields.get("t"+i).getText()));
        }
    }

    private void renewLabels(NumericVariable variable) {
        labelSelect.setText("---Select label---");
        labelSelect.getItems().clear();
        linguisticVariableSelect.setText(variable.getName());
        LBR.getVariables().get(variable).getLabels().forEach((k,v) -> {
            MenuItem item1 = new MenuItem(k+" "+variable.getSummarizerTitle());
            item1.setOnAction(actionEvent1 -> {
                removeLabel.setDisable(false);
                labelSelect.setText(k+" "+variable.getSummarizerTitle());
                labelNameAdditional.setText(variable.getSummarizerTitle());
                labelNameField.setText(k);
                disabledAndVisibilitiesFirstSet(true);
                if(v instanceof TriangleFunction tempV) {
                    triangleSelected();
                    functionField1.setText(String.valueOf(tempV.getStart()));
                    functionField2.setText(String.valueOf(tempV.getMiddle()));
                    functionField3.setText(String.valueOf(tempV.getEnd()));
                } else if(v instanceof TrapezoidalFunction tempV) {
                    trapezoidalSelected();
                    functionField1.setText(String.valueOf(tempV.getStart()));
                    functionField2.setText(String.valueOf(tempV.getStartMiddle()));
                    functionField3.setText(String.valueOf(tempV.getEndMiddle()));
                    functionField4.setText(String.valueOf(tempV.getEnd()));
                } else if(v instanceof GaussianFunction tempV) {
                    gaussianSelected();
                    functionField1.setText(String.valueOf(tempV.getMiddle()));
                    functionField2.setText(String.valueOf(tempV.getVariance()));
                }
            });
            labelSelect.getItems().add(item1);
        });
        MenuItem addItem = new MenuItem("---Add new---");
        addItem.setOnAction(actionEvent -> {
            labelSelect.setText("---Adding new variable---");
            labelNameField.clear();
            labelNameAdditional.setText(variable.getSummarizerTitle());
            disabledAndVisibilitiesFirstSet(true);
            triangleSelected();
            functionField1.clear();
            functionField2.clear();
            functionField3.clear();
            functionField4.clear();
        });
        labelSelect.getItems().add(addItem);
    }

    public void disabledAndVisibilitiesFirstSet(Boolean bool) {
        advancedThirdColumn.setVisible(bool);
        labelNameField.setDisable(!bool);
        functionSelect.setDisable(!bool);
        saveLabel.setDisable(!bool);
    }

    public MembershipFunction createFunctionFromFields() {
        NumericVariable variable = NumericVariable.findByName(linguisticVariableSelect.getText());
        UniverseOfDiscourse universe = LBR.getVariables().get(variable).getUniverse();
        MembershipFunction function = null;
        try {
            switch (functionSelect.getText()) {
                case "Triangle" -> function = new TriangleFunction(universe, Double.valueOf(functionField1.getText()),
                        Double.valueOf(functionField2.getText()), Double.valueOf(functionField3.getText()));
                case "Trapezoidal" -> function = new TrapezoidalFunction(universe, Double.valueOf(functionField1.getText()),
                        Double.valueOf(functionField2.getText()), Double.valueOf(functionField3.getText()),
                        Double.valueOf(functionField4.getText()));
                case "Gaussian" -> function = new GaussianFunction(universe, Double.valueOf(functionField1.getText()),
                        Double.valueOf(functionField2.getText()));
                default -> throw new IllegalArgumentException("Fatal error: invalid function");
            }
            errorLabel.setText("");
        } catch(IllegalArgumentException ex) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e-> errorLabel.setText(ex.getMessage())),
                    new KeyFrame(Duration.seconds(3.0), e-> errorLabel.setText("")));
            timeline.playFromStart();
        }
        return function;
    }

    @FXML
    public void makePreview() {
        NumericVariable variable = NumericVariable.findByName(linguisticVariableSelect.getText());
        UniverseOfDiscourse universe = LBR.getVariables().get(variable).getUniverse();
        MembershipFunction function = createFunctionFromFields();
        assert variable != null;
        membershipFunctionChart.setTitle(variable.getName());
        ((NumberAxis) membershipFunctionChart.getXAxis()).setUpperBound(universe.getRightLimit());
        ((NumberAxis) membershipFunctionChart.getXAxis()).setLowerBound(universe.getLeftLimit());
        membershipFunctionChart.getData().clear();
        if (errorLabel.getText().equals((""))) {
            XYChart.Series series = new XYChart.Series();
            double increment = (universe.getInterval()!=null?(universe.getInterval()==0.01?1.0: universe.getInterval()):(universe.getRightLimit()==1.0?0.01:100));
            for(double i = universe.getLeftLimit(); i<= universe.getRightLimit(); i+=increment) {
                series.getData().add(new XYChart.Data(i,function.calcValue(i)));
            }
            series.setName(labelNameField.getText().equals("")?"unnamed":labelNameField.getText());
            membershipFunctionChart.getData().add(series);
        }
    }

    @FXML
    public void saveFunction() throws IOException {
        NumericVariable variable = NumericVariable.findByName(linguisticVariableSelect.getText());
        MembershipFunction function = createFunctionFromFields();
        if(function!=null) {
            LBR.getVariables().get(variable).getLabels().put(labelNameField.getText(),function);
            LBR.saveVariable(variable);
            rebuildTree();
        }
        assert variable != null;
        renewLabels(variable);
        removeLabel.setDisable(false);
        saveLabel.setText("Save changes");
        labelSelect.setText(labelNameField.getText()+" "+labelNameAdditional.getText());
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e-> {
            errorLabel.setTextFill(Color.color(0,1,0));
            errorLabel.setText("Label saved sucessfully");
        }),new KeyFrame(Duration.seconds(3.0), e-> {
            errorLabel.setTextFill(Color.color(1,0,0));
            errorLabel.setText("");
        }));
        timeline.playFromStart();

    }

    @FXML
    public void removeFunction() throws IOException {
        NumericVariable variable = NumericVariable.findByName(linguisticVariableSelect.getText());
        LBR.getVariables().get(variable).getLabels().remove(labelNameField.getText());
        LBR.saveVariable(variable);
        rebuildTree();
        assert variable != null;
        renewLabels(variable);
        removeLabel.setDisable(true);
        disabledAndVisibilitiesFirstSet(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e-> {
            errorLabel.setTextFill(Color.color(0,1,0));
            errorLabel.setText("Label removed succesfully");
        }),new KeyFrame(Duration.seconds(3.0), e-> {
            errorLabel.setTextFill(Color.color(1,0,0));
            errorLabel.setText("");
        }));
        timeline.playFromStart();
    }

    @FXML
    public void generateSummaries() {
         HashSet<String> relativeQuantifierLabels = new HashSet<>();
        HashSet<String> absoluteQuantifierLabels = new HashSet<>();
        TreeMap<NumericVariable,TreeSet<String>> summarizerLabels  = new TreeMap<>();
        TreeMap<NumericVariable,TreeSet<String>> qualifierLabels = new TreeMap<>();
        CheckBoxTreeItem currentTreeItem;
        CheckBoxTreeItem currentInnerTreeItem;
        CheckBoxTreeItem currentInnerInnerTreeItem;

        for(int i=0;i<treeViewCheckBox.getRoot().getChildren().size();i++) {
            currentTreeItem = (CheckBoxTreeItem) treeViewCheckBox.getRoot().getChildren().get(i);
            if(currentTreeItem.isIndeterminate()|| currentTreeItem.isSelected()) {
                for(int j=0;j<currentTreeItem.getChildren().size();j++) {
                    currentInnerTreeItem = (CheckBoxTreeItem) currentTreeItem.getChildren().get(j);
                    if(i<2 && currentInnerTreeItem.isSelected()) {
                        if(i==0) {
                            relativeQuantifierLabels.add(currentInnerTreeItem.getValue().toString());
                        } else {
                            absoluteQuantifierLabels.add(currentInnerTreeItem.getValue().toString());
                        }
                    } else if(currentInnerTreeItem.isSelected()||currentInnerTreeItem.isIndeterminate()) {
                        NumericVariable variable = NumericVariable.findByName(currentInnerTreeItem.getValue().toString());
                        TreeSet<String> labels = new TreeSet<>();
                        for(int k=0;k<currentInnerTreeItem.getChildren().size();k++) {
                            currentInnerInnerTreeItem = (CheckBoxTreeItem) currentInnerTreeItem.getChildren().get(k);
                            if(currentInnerInnerTreeItem.isSelected()) {
                                labels.add(currentInnerInnerTreeItem.getValue().toString());
                            }
                        }
                        if(i==2) {
                            summarizerLabels.put(variable,labels);
                        }
                        if(i==3) {
                            qualifierLabels.put(variable,labels);
                        }
                    }
                }
            }
        }
        try {
            if(subject2==null&&relativeQuantifierLabels.isEmpty()&&absoluteQuantifierLabels.isEmpty()) {
                throw new IllegalArgumentException("Single subject summaries must have at least one quantifier");
            }
            if(summarizerLabels.isEmpty()) {
                throw new IllegalArgumentException("Summaries must have at least one summarizer");
            }
        } catch(IllegalArgumentException ex) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e-> {
                summaryErrorLabel.setText(ex.getMessage());
            }),new KeyFrame(Duration.seconds(3.0), e-> {
                summaryErrorLabel.setText("");
            }));
            timeline.playFromStart();
        }
        if(subject2==null) {
            //Single subject all forms summarizers
            for(int i=1;i<=summarizerLabels.size();i++) {
                System.out.println(i);
                Generator.combination(summarizerLabels.keySet()).simple(i).stream().forEach((summarizerCombination) -> {
                    System.out.println(summarizerCombination);
                    TreeSet<NumericVariable> filteredQualifiers = new TreeSet<>();
                    qualifierLabels.forEach((k,v) -> {
                        if(!summarizerCombination.contains(k)) {
                            filteredQualifiers.add(k);
                            System.out.println(k);
                        }
                    });
                    ArrayList<TreeSet<String>> summarizersLabelsList = new ArrayList<>();
                    for (NumericVariable set : summarizerCombination) {
                        summarizersLabelsList.add(summarizerLabels.get(set));
                    }
                    Sets.cartesianProduct(summarizersLabelsList).forEach((summarizerFinalSet) -> {
                        TreeMap<String,String> summarizerFinalMap = new TreeMap<>();
                        for(int j=0;j<summarizerFinalSet.size();j++) {
                            NumericVariable summarizerVariable = summarizerCombination.get(j);
                            String qualifierVariableString = summarizerFinalSet.get(j).replace(summarizerVariable.getSummarizerTitle()+" ","").replace(" "+summarizerVariable.getSummarizerTitle(),"");
                            summarizerFinalMap.put(summarizerVariable.toString(), qualifierVariableString);

                        }
                        relativeQuantifierLabels.forEach((relativeQuantifierLabel) -> {
                            singleSummaryObservableList.add(new SingleSummaryTable(new FirstFormSingleSummary(subject1==null?null:new CrispSet(currentStringVariable,subject1),
                                    true,relativeQuantifierLabel,summarizerFinalMap).retrieveResults()));
                        });
                        absoluteQuantifierLabels.forEach((absoluteQuantifierLabel) -> {
                            singleSummaryObservableList.add(new SingleSummaryTable(new FirstFormSingleSummary(subject1==null?null:new CrispSet(currentStringVariable,subject1),
                                    false,absoluteQuantifierLabel,summarizerFinalMap).retrieveResults()));
                        });
                        for(int j=1;j<=filteredQualifiers.size();j++) {
                            Generator.combination(filteredQualifiers).simple(j).forEach((qualifierCombination) -> {
                                ArrayList<TreeSet<String>> qualifiersLabelsList = new ArrayList<>();
                                for (NumericVariable set : qualifierCombination) {
                                    qualifiersLabelsList.add(qualifierLabels.get(set));
                                }
                                Sets.cartesianProduct(qualifiersLabelsList).forEach((qualifierFinalSet) -> {
                                    TreeMap<String,String> qualifierFinalMap = new TreeMap<>();
                                    for(int k=0;k<qualifierFinalSet.size();k++) {
                                        NumericVariable qualifierVariable = qualifierCombination.get(k);
                                        String qualifierVariableString = qualifierFinalSet.get(k).replace(qualifierVariable.getSummarizerTitle()+" ","").replace(" "+qualifierVariable.getSummarizerTitle(),"");
                                        qualifierFinalMap.put(qualifierVariable.toString(), qualifierVariableString);
                                    }
                                    relativeQuantifierLabels.forEach((relativeQuantifierLabel) -> {
                                        singleSummaryObservableList.add(new SingleSummaryTable(new SecondFormSingleSummary(subject1==null?null:new CrispSet(currentStringVariable,subject1),
                                                relativeQuantifierLabel,summarizerFinalMap,qualifierFinalMap).retrieveResults()));
                                    });
                                });
                            });
                        }
                    });
                });
            }
        } else {
            for(int i=1;i<=summarizerLabels.size();i++) {
                Generator.combination(summarizerLabels.keySet()).simple(i).stream().forEach((summarizerCombination) -> {
                    System.out.println(summarizerCombination);
                    TreeSet<NumericVariable> filteredQualifiers = new TreeSet<>();
                    qualifierLabels.forEach((k,v) -> {
                        if(!summarizerCombination.contains(k)) {
                            filteredQualifiers.add(k);
                        }
                    });
                    ArrayList<TreeSet<String>> summarizersLabelsList = new ArrayList<>();
                    for (NumericVariable set : summarizerCombination) {
                        summarizersLabelsList.add(summarizerLabels.get(set));
                    }
                    Sets.cartesianProduct(summarizersLabelsList).forEach((summarizerFinalSet) -> {
                        TreeMap<String,String> summarizerFinalMap = new TreeMap<>();
                        for(int j=0;j<summarizerFinalSet.size();j++) {
                            NumericVariable summarizerVariable = summarizerCombination.get(j);
                            String qualifierVariableString = summarizerFinalSet.get(j).replace(summarizerVariable.getSummarizerTitle()+" ","").replace(" "+summarizerVariable.getSummarizerTitle(),"");
                            summarizerFinalMap.put(summarizerVariable.toString(), qualifierVariableString);

                        }
                        multiSummaryObservableList.add(new MultiSummaryTable(new FourthFormMultiSummary(new CrispSet(currentStringVariable,subject1),
                                new CrispSet(currentStringVariable,subject2),summarizerFinalMap).retrieveResults()));
                        relativeQuantifierLabels.forEach((relativeQuantifierLabel) -> {
                            multiSummaryObservableList.add(new MultiSummaryTable(new FirstFormMultiSummary(new CrispSet(currentStringVariable,subject1),
                                    new CrispSet(currentStringVariable,subject2),relativeQuantifierLabel,summarizerFinalMap).retrieveResults()));
                            multiSummaryObservableList.add(new MultiSummaryTable(new FirstFormMultiSummary(new CrispSet(currentStringVariable,subject2),
                                    new CrispSet(currentStringVariable,subject1),relativeQuantifierLabel,summarizerFinalMap).retrieveResults()));
                        });
                        for(int j=1;j<=filteredQualifiers.size();j++) {
                            Generator.combination(filteredQualifiers).simple(j).forEach((qualifierCombination) -> {
                                ArrayList<TreeSet<String>> qualifiersLabelsList = new ArrayList<>();
                                for (NumericVariable set : qualifierCombination) {
                                    qualifiersLabelsList.add(qualifierLabels.get(set));
                                }
                                Sets.cartesianProduct(qualifiersLabelsList).forEach((qualifierFinalSet) -> {
                                    TreeMap<String,String> qualifierFinalMap = new TreeMap<>();
                                    for(int k=0;k<qualifierFinalSet.size();k++) {
                                        NumericVariable qualifierVariable = qualifierCombination.get(k);
                                        String qualifierVariableString = qualifierFinalSet.get(k).replace(qualifierVariable.getSummarizerTitle()+" ","").replace(" "+qualifierVariable.getSummarizerTitle(),"");
                                        qualifierFinalMap.put(qualifierVariable.toString(), qualifierVariableString);
                                    }
                                    relativeQuantifierLabels.forEach((relativeQuantifierLabel) -> {
                                        multiSummaryObservableList.add(new MultiSummaryTable(new SecondFormMultiSummary(new CrispSet(currentStringVariable,subject1),
                                                new CrispSet(currentStringVariable,subject2),relativeQuantifierLabel,summarizerFinalMap,qualifierFinalMap).retrieveResults()));
                                        multiSummaryObservableList.add(new MultiSummaryTable(new SecondFormMultiSummary(new CrispSet(currentStringVariable,subject2),
                                                new CrispSet(currentStringVariable,subject1),relativeQuantifierLabel,summarizerFinalMap,qualifierFinalMap).retrieveResults()));
                                        multiSummaryObservableList.add(new MultiSummaryTable(new ThirdFormMultiSummary(new CrispSet(currentStringVariable,subject1),
                                                new CrispSet(currentStringVariable,subject2),relativeQuantifierLabel,summarizerFinalMap,qualifierFinalMap).retrieveResults()));
                                        multiSummaryObservableList.add(new MultiSummaryTable(new ThirdFormMultiSummary(new CrispSet(currentStringVariable,subject2),
                                                new CrispSet(currentStringVariable,subject1),relativeQuantifierLabel,summarizerFinalMap,qualifierFinalMap).retrieveResults()));
                                    });
                                });
                            });
                        }
                    });
                });
            }
        }
    }

    @FXML
    public void removeAll() {
        singleSummaryObservableList.clear();
        multiSummaryObservableList.clear();
    }

    @FXML
    public void removeSelected() {
        singleSummaryObservableList.removeIf((k)->k.getIsSelected().get());
        multiSummaryObservableList.removeIf((k)->k.getIsSelected().get());
    }

    @FXML
    public void saveAll() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("SingleSubjectResults-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(System.currentTimeMillis())+ ".csv"), StandardCharsets.UTF_8));
        bw.write("Linguistic Summary,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,Optimum");
        bw.newLine();
        for (SingleSummaryTable table : singleSummaryObservableList) {
            bw.write(table.toCsvLine());
            bw.newLine();
        }
        bw.flush();
        bw.close();
        bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("MultiSubjectResults-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(System.currentTimeMillis())+ ".csv"), StandardCharsets.UTF_8));
        bw.write("Linguistic Summary,T");
        bw.newLine();
        for (MultiSummaryTable table : multiSummaryObservableList) {
            bw.write(table.toCsvLine());
            bw.newLine();
        }
        bw.flush();
        bw.close();
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e-> {
            summaryErrorLabel.setTextFill(Color.color(0,1,0));
            summaryErrorLabel.setText("Summaries saved succesfully");
        }),new KeyFrame(Duration.seconds(3.0), e-> {
            summaryErrorLabel.setTextFill(Color.color(1,0,0));
            summaryErrorLabel.setText("");
        }));
        timeline.playFromStart();
    }

    @FXML
    public void saveSelected() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("SingleSubjectResults-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(System.currentTimeMillis())+ ".csv"), StandardCharsets.UTF_8));
        bw.write("Linguistic Summary,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,Optimum");
        bw.newLine();
        for (SingleSummaryTable table : singleSummaryObservableList) {
            if(table.getIsSelected().get()) {
                bw.write(table.toCsvLine());
                bw.newLine();
            }
        }
        bw.flush();
        bw.close();
        bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("MultiSubjectResults-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(System.currentTimeMillis())+ ".csv"), StandardCharsets.UTF_8));
        bw.write("Linguistic Summary,T");
        bw.newLine();
        for (MultiSummaryTable table : multiSummaryObservableList) {
            if(table.getIsSelected().get()) {
                bw.write(table.toCsvLine());
                bw.newLine();
            }
        }
        bw.flush();
        bw.close();
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e-> {
            summaryErrorLabel.setTextFill(Color.color(0,1,0));
            summaryErrorLabel.setText("Summaries saved succesfully");
        }),new KeyFrame(Duration.seconds(3.0), e-> {
            summaryErrorLabel.setTextFill(Color.color(1,0,0));
            summaryErrorLabel.setText("");
        }));
        timeline.playFromStart();
    }
}
