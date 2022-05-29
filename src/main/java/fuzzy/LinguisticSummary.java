package fuzzy;

import lombok.Getter;
import lombok.Setter;

public abstract class LinguisticSummary {
    @Getter
    @Setter
    private String summary;
    @Getter
    @Setter
    private FuzzySet resultSet;
    public abstract Double optimalMeasure();
}
