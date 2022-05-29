package fuzzy;

import lombok.Getter;
import model.NumericVariable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LinguisticVariableRepository{
    private static LinguisticVariableRepository instance;
    @Getter
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

    public void loadLinguisticVariable(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ling_var/"+filename+".txt"));
            NumericVariable name = NumericVariable.valueOf(reader.readLine());
            String[] universeData = reader.readLine().split(",");
            Boolean isContinuous = Boolean.parseBoolean(universeData[0]);
            Double interval = isContinuous?null:Double.parseDouble(universeData[3]);
            UniverseOfDiscourse universeOfDiscourse = new UniverseOfDiscourse(Double.parseDouble(universeData[1]),
                    Double.parseDouble(universeData[2]),isContinuous,interval);
            variables.put(name,new LinguisticVariable(name,new HashMap<>(),universeOfDiscourse));
            for(String line = reader.readLine(); line!=null ; line = reader.readLine()) {
                String[] splitLine = line.split(",");
                switch(Integer.parseInt(splitLine[1])) {
                    case 0 -> variables.get(name).getLabels().put(splitLine[0],new GaussianFunction(universeOfDiscourse,
                            Double.parseDouble(splitLine[2]),Double.parseDouble(splitLine[3])));
                    case 1 -> variables.get(name).getLabels().put(splitLine[0],new TriangleFunction(universeOfDiscourse,
                            Double.parseDouble(splitLine[2]),Double.parseDouble(splitLine[3]),
                            Double.parseDouble(splitLine[4])));
                    case 2 -> variables.get(name).getLabels().put(splitLine[0],new TrapezoidalFunction(universeOfDiscourse,
                            Double.parseDouble(splitLine[2]),Double.parseDouble(splitLine[3]),
                            Double.parseDouble(splitLine[4]),Double.parseDouble(splitLine[5])));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAllVariables() {
        for (NumericVariable variable : NumericVariable.values()) {
            if(variable!=NumericVariable.undefined) {
                loadLinguisticVariable(variable.toString());
            }
        }
    }
}
