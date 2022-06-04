package fuzzy;

import lombok.Getter;
import lombok.Setter;

public abstract class MultiLinguisticSummary extends LinguisticSummary{
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
}
