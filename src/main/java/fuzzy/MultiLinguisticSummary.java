package fuzzy;

import lombok.Getter;
import lombok.Setter;

public abstract class MultiLinguisticSummary extends LinguisticSummary{
    @Getter
    @Setter
    private CrispSet secondSubject;
}
