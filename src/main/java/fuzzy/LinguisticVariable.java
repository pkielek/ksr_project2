package fuzzy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import model.NumericVariable;

import java.util.HashMap;

@ToString
@AllArgsConstructor
public class LinguisticVariable {
    @Getter
    private final NumericVariable name;
    @Getter
    private HashMap<String, MembershipFunction> labels;
    @Getter
    private final UniverseOfDiscourse universe;
}
