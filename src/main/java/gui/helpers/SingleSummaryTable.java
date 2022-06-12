package gui.helpers;

import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleSummaryTable {
    private SimpleStringProperty resultSummary;
    private SimpleStringProperty  t1;
    private SimpleStringProperty  t2;
    private SimpleStringProperty  t3;
    private SimpleStringProperty  t4;
    private SimpleStringProperty  t5;
    private SimpleStringProperty  t6;
    private SimpleStringProperty  t7;
    private SimpleStringProperty  t8;
    private SimpleStringProperty  t9;
    private SimpleStringProperty  t10;
    private SimpleStringProperty  t11;
    private SimpleStringProperty  optimum;

    public SingleSummaryTable(String resultSummary, String t1, String t2, String t3, String t4, String t5, String t6, String t7, String t8, String t9, String t10, String t11, String optimum) {
        this.resultSummary = new SimpleStringProperty(resultSummary);
        this.t1 = new SimpleStringProperty(t1);
        this.t2 = new SimpleStringProperty(t2);
        this.t3 = new SimpleStringProperty(t3);
        this.t4 = new SimpleStringProperty(t4);
        this.t5 = new SimpleStringProperty(t5);
        this.t6 = new SimpleStringProperty(t6);
        this.t7 = new SimpleStringProperty(t7);
        this.t8 = new SimpleStringProperty(t8);
        this.t9 = new SimpleStringProperty(t9);
        this.t10 = new SimpleStringProperty(t10);
        this.t11 = new SimpleStringProperty(t11);
        this.optimum = new SimpleStringProperty(optimum);
    }
}
