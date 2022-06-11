package gui;

import fuzzy.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import model.NumericVariable;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class MainView {
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
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;
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

        StringConverter<Double> converter = new StringConverter<Double>() {

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
        triangleSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                triangleSelected();
            }
        });
        trapezoidalSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                trapezoidalSelected();
            }
        });
        gaussianSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gaussianSelected();
            }
        });
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

        StringConverter<Double> converter = new StringConverter<Double>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || ".".equals(s) || "-.".equals(s)|| "-".equals(s)) {
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
                labelNameField.setText(k);
                labelNameAdditional.setText(variable.getSummarizerTitle());
                disabledAndVisibilitiesFirstSet(true);
                if(v instanceof TriangleFunction) {
                    TriangleFunction tempV =(TriangleFunction) v;
                    triangleSelected();
                    functionField1.setText(String.valueOf(tempV.getStart()));
                    functionField2.setText(String.valueOf(tempV.getMiddle()));
                    functionField3.setText(String.valueOf(tempV.getEnd()));
                } else if(v instanceof TrapezoidalFunction) {
                    TrapezoidalFunction tempV =(TrapezoidalFunction) v;
                    trapezoidalSelected();
                    functionField1.setText(String.valueOf(tempV.getStart()));
                    functionField2.setText(String.valueOf(tempV.getStartMiddle()));
                    functionField3.setText(String.valueOf(tempV.getEndMiddle()));
                    functionField4.setText(String.valueOf(tempV.getEnd()));
                } else if(v instanceof GaussianFunction) {
                    GaussianFunction tempV = (GaussianFunction) v;
                    gaussianSelected();
                    functionField1.setText(String.valueOf(tempV.getMiddle()));
                    functionField2.setText(String.valueOf(tempV.getVariance()));
                }
            });
            labelSelect.getItems().add(item1);
        });
        MenuItem addItem = new MenuItem("---Add new---");
        addItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                labelSelect.setText("---Adding new variable---");
                labelNameField.clear();
                labelNameAdditional.setText(variable.getSummarizerTitle());
                disabledAndVisibilitiesFirstSet(true);
                triangleSelected();
                functionField1.clear();
                functionField2.clear();
                functionField3.clear();
                functionField4.clear();
            }
        });
        labelSelect.getItems().add(addItem);
    }

    public void disabledAndVisibilitiesFirstSet(Boolean bool) {
        advancedThirdColumn.setVisible(bool);
        labelNameField.setDisable(!bool);
        functionSelect.setDisable(!bool);
        saveLabel.setDisable(!bool);
    }

    @FXML
    public void makePreview() {
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
            assert variable != null;
            membershipFunctionChart.setTitle(variable.getName());
            ((NumberAxis) membershipFunctionChart.getXAxis()).setUpperBound(universe.getRightLimit());
            ((NumberAxis) membershipFunctionChart.getXAxis()).setLowerBound(universe.getLeftLimit());
            membershipFunctionChart.getData().clear();

        } catch(IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
        assert function != null;
        XYChart.Series series = new XYChart.Series();
        Double increment = (universe.getInterval()!=null?(universe.getInterval()==0.01?1.0: universe.getInterval()):(universe.getRightLimit()==1.0?0.01:100));
        for(double i = universe.getLeftLimit(); i<= universe.getRightLimit(); i+=increment) {
            series.getData().add(new XYChart.Data(i,function.calcValue(i)));
        }
        series.setName(labelNameField.getText().equals("")?"unnamed":labelNameField.getText());
        membershipFunctionChart.getData().add(series);
    }

}
