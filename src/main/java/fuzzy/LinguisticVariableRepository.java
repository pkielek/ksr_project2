package fuzzy;

import fuzzy.summaries.SummaryResult;
import lombok.Getter;
import model.NumericVariable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.StringJoiner;

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

    public void saveVariable(NumericVariable variable) throws IOException {
        if(variable!=NumericVariable.undefined) {
            LinguisticVariable lVariable = getVariables().get(variable);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ling_var/"+ variable.toString() + ".txt"), StandardCharsets.UTF_8));
            bw.write(variable.toString());
            bw.newLine();
            StringJoiner universeLine = new StringJoiner(",");

            universeLine.add(lVariable.getUniverse().getIsContinuous().toString()).add(lVariable.getUniverse().getLeftLimit().toString())
                    .add(lVariable.getUniverse().getRightLimit().toString());
            if(!lVariable.getUniverse().getIsContinuous()) {
                universeLine.add(lVariable.getUniverse().getInterval().toString());
            }
            bw.write(universeLine.toString());
            lVariable.getLabels().forEach(
                    (k,v) -> {
                        try {
                            bw.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        StringJoiner labelLine = new StringJoiner(",");
                        labelLine.add(k);
                        if(v instanceof TriangleFunction tempV) {
                            labelLine.add("1");
                            labelLine.add(tempV.getStart().toString());
                            labelLine.add(tempV.getMiddle().toString());
                            labelLine.add(tempV.getEnd().toString());
                        } else if(v instanceof TrapezoidalFunction tempV) {
                            labelLine.add("2");
                            labelLine.add(tempV.getStart().toString());
                            labelLine.add(tempV.getStartMiddle().toString());
                            labelLine.add(tempV.getEndMiddle().toString());
                            labelLine.add(tempV.getEnd().toString());
                        } else if(v instanceof GaussianFunction tempV) {
                            labelLine.add("0");
                            labelLine.add(tempV.getMiddle().toString());
                            labelLine.add(tempV.getVariance().toString());
                        }
                        try {
                            bw.write(labelLine.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
            bw.flush();
            bw.close();
        }
    }
}
