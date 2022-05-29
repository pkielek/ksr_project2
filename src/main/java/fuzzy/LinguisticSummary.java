package fuzzy;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


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
    private FuzzySet resultSet;
    @Getter
    @Setter
    private HashMap<String,Double> qualityMeasures = new HashMap<>();
    public abstract Double optimalMeasure();
}
