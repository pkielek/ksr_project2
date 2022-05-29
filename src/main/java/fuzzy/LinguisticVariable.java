package fuzzy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import model.NumericVariable;

import java.util.HashMap;

@AllArgsConstructor
public class LinguisticVariable {
    @Getter
    NumericVariable name;
    @Getter
    HashMap<String, MembershipFunction> labels;
    @Getter
    UniverseOfDiscourse universe;
}
