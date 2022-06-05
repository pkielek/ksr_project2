package fuzzy.summaries;

import fuzzy.CrispSet;
import fuzzy.FuzzySet;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.TreeMap;


public abstract class LinguisticSummary {
    @Getter
    @Setter
    private String summary;
    @Getter
    @Setter
    private boolean relativeQuantifier;
    @Getter
    @Setter
    private String quantifierLabel;
    @Getter
    @Setter
    private FuzzySet summaryResultSet;
    @Getter
    @Setter
    private FuzzySet qualifierResultSet;
    @Getter
    @Setter
    private TreeMap<String,String> summarizersByVariableAndLabel;
    @Getter
    @Setter
    private TreeMap<String,String> qualifiersByVariableAndLabel;
    @Getter
    @Setter
    private HashMap<String,Double> qualityMeasures;
    @Getter
    @Setter
    private CrispSet subject;
    public abstract Double optimalMeasure();
}
