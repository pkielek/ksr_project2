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
public class MultiSummaryTable {

    private SimpleStringProperty resultSummary;
    private HashMap<String,SimpleStringProperty> stringProperties;
    private SimpleBooleanProperty isSelected = new SimpleBooleanProperty(false);

    public MultiSummaryTable(SummaryResult summaryResult) {
        stringProperties = new HashMap<>();
        this.resultSummary = new SimpleStringProperty(summaryResult.getSummary());
        stringProperties.put("T",new SimpleStringProperty(summaryResult.getMeasures().get("T").isNaN()?String.valueOf(Double.NaN):(summaryResult.getMeasures().get("T")<0.005&&summaryResult.getMeasures().get("T")!=0.0?"~0.0":String.valueOf(Math.round(summaryResult.getMeasures().get("T")*100.0)/100.0))));
    }

    public String toCsvLine() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(resultSummary.get());
        joiner.add(stringProperties.get("T").get());
        return joiner.toString();
    }
}
