package gui.helpers;

import fuzzy.summaries.SummaryResult;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.StringJoiner;

@Getter
@Setter
@AllArgsConstructor
public class SingleSummaryTable {
    private SimpleStringProperty resultSummary;
    private HashMap<String,SimpleStringProperty> stringProperties;
    private SimpleBooleanProperty isSelected = new SimpleBooleanProperty(false);

    public SingleSummaryTable(SummaryResult summaryResult) {
        stringProperties = new HashMap<>();
        this.resultSummary = new SimpleStringProperty(summaryResult.getSummary());
        for(int i=1;i<=11;i++) {
            stringProperties.put("t"+i,new SimpleStringProperty(summaryResult.getMeasures().get("t"+i).isNaN()?String.valueOf(Double.NaN):(summaryResult.getMeasures().get("t"+i)<0.005&&summaryResult.getMeasures().get("t"+i)!=0.0?"~0.0":String.valueOf(Math.round(summaryResult.getMeasures().get("t"+i)*100.0)/100.0))));
        }
        stringProperties.put("Optimum",new SimpleStringProperty(summaryResult.getMeasures().get("Optimum").isNaN()?String.valueOf(Double.NaN):(summaryResult.getMeasures().get("Optimum")<0.005&&summaryResult.getMeasures().get("Optimum")!=0.0?"~0.0":String.valueOf(Math.round(summaryResult.getMeasures().get("Optimum")*100.0)/100.0))));

    }
    public String toCsvLine() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(resultSummary.get());
        for(int i=1;i<=11;i++) {
            joiner.add(stringProperties.get("t"+i).get());
        }
        joiner.add(stringProperties.get("Optimum").get());
        return joiner.toString();
    }
}
