package fuzzy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
public class LinguisticVariable {
    @Getter
    String name;
    @Getter
    HashMap<String, MembershipFunction> labels;
    @Getter
    UniverseOfDiscourse universe;
}
