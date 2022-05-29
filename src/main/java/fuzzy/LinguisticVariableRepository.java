package fuzzy;

import model.NumericVariable;

import java.util.HashMap;

public class LinguisticVariableRepository{
    private static LinguisticVariableRepository instance;
    private HashMap<NumericVariable,LinguisticVariable> variables;

    private LinguisticVariableRepository() {
        variables = new HashMap<>();
    }
    public static LinguisticVariableRepository getInstance() {
        if (instance == null) {
            instance = new LinguisticVariableRepository();
        }
        return instance;
    }
}
