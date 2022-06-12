package gui;

import fuzzy.*;
import fuzzy.summaries.SummaryResult;
import gui.helpers.SingleSummaryTable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.NumericVariable;
import model.StringVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class MainView {

    public TableView<SingleSummaryTable> singleFirstFormTable;
    public TableColumn<SingleSummaryTable, String> singleFirstFormResult;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT1;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT2;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT3;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT4;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT5;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT6;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT7;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT8;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT9;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT10;
    public TableColumn<SingleSummaryTable, String> singleFirstFormT11;
    public TableColumn<SingleSummaryTable, String> singleFirstFormOptimum;

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
        singleFirstFormTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        HashMap<String,Double> weights = new HashMap<String,Double>();
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
        SingleSummaryTable sst3 = new SingleSummaryTable(new SummaryResult("trzy",weights));
        singleSummaryObservableList.addAll(sst1, sst2, sst3);

        singleFirstFormResult.setCellValueFactory(cellData -> cellData.getValue().getResultSummary());
        singleFirstFormT1.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t1"));
        singleFirstFormT2.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t2"));
        singleFirstFormT3.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t3"));
        singleFirstFormT4.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t4"));
        singleFirstFormT5.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t5"));
        singleFirstFormT6.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t6"));
        singleFirstFormT7.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t7"));
        singleFirstFormT8.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t8"));
        singleFirstFormT9.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t9"));
        singleFirstFormT10.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t10"));
        singleFirstFormT11.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("t11"));
        singleFirstFormOptimum.setCellValueFactory(cellData -> cellData.getValue().getStringProperties().get("Optimum"));
        singleFirstFormTable.setItems(singleSummaryObservableList);

    }

    private void rebuildTree() {
        treeViewCheckBox.setRoot(null);
        CheckBoxTreeItem<String> treeRoot = new CheckBoxTreeItem<>("Summary options");

        CheckBoxTreeItem<String> relativeQuantifier = new CheckBoxTreeItem<>("Relative Quantifier");
        CheckBoxTreeItem<String> absoluteQuantifier = new CheckBoxTreeItem<>("Absolute Quantifier");
        CheckBoxTreeItem<String> summarizer = new CheckBoxTreeItem<>("Summarizers");
        CheckBoxTreeItem<String> qualifier = new CheckBoxTreeItem<>("Qualifiers");

        for (NumericVariable variable : NumericVariable.values()) {
            if (!variable.name().equals(NumericVariable.undefined.name())) {
                if (variable.name().equals(NumericVariable.relativeQuantifier.name())) {
                    LBR.getVariables().get(variable).getLabels().forEach((k,v) ->
                            relativeQuantifier.getChildren().add(new CheckBoxTreeItem<>(k+" "+variable.getSummarizerTitle())));
                } else if (variable.name().equals(NumericVariable.absoluteQuantifier.name())) {
                    LBR.getVariables().get(variable).getLabels().forEach((k,v) ->
                            absoluteQuantifier.getChildren().add(new CheckBoxTreeItem<>(k+" "+variable.getSummarizerTitle())));
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
        HashMap<NumericVariable,HashSet<String>> summarizerLabels  = new HashMap<>();
        HashMap<NumericVariable,HashSet<String>> qualifierLabels = new HashMap<>();
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
                        HashSet<String> labels = new HashSet<>();
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
    }
}
