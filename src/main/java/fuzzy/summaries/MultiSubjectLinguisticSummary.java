package fuzzy.summaries;

import fuzzy.CrispSet;
import fuzzy.FuzzySet;
import lombok.Getter;
import lombok.Setter;

public abstract class MultiSubjectLinguisticSummary extends LinguisticSummary{
    @Getter
    @Setter
    private CrispSet secondSubject;
    @Getter
    @Setter
    private FuzzySet firstSubjectSummaryResultSet;
    @Getter
    @Setter
    private FuzzySet secondSubjectSummaryResultSet;
    @Getter
    @Setter
    private FuzzySet secondSummaryResultSet;
    @Getter
    @Setter
    private Double t;

    @Override
    public Double optimalMeasure() {
        return getT();
    }
}
