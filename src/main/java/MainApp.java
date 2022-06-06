import fuzzy.*;
import fuzzy.summaries.*;
import model.HotelBookingRepository;
import model.NumericVariable;
import model.StringVariable;
import org.paukov.combinatorics3.Generator;

import java.io.*;
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class MainApp {

    public static void main(String[] args) throws IOException {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        LBR.loadAllVariables();
//        saveCsvMultiSummaryResults(generateThirdFormMultiSummaryList(),"ThirdFormMultiSummaryList");
        saveCsvMultiSummaryFourthFormResults(generateFourthFormMultiSummaryList(),"FourthFormMultiSummaryList");
    }

    public static HashSet<NumericVariable> getSelectNumericVariables() {
        HashSet<NumericVariable> numericVariables = new HashSet<>();
        numericVariables.add(NumericVariable.bookingChanges);
        numericVariables.add(NumericVariable.numberOfAdults);
        numericVariables.add(NumericVariable.leadTime);
        numericVariables.add(NumericVariable.requiredCarParkingSpaces);
        numericVariables.add(NumericVariable.totalOfSpecialRequests);
        numericVariables.add(NumericVariable.staysInWeekNights);
        return numericVariables;
    }

    public static HashMap<String,StringVariable> getSelectStringVariables() {
        HashMap<String,StringVariable> subjects = new HashMap<>();
        subjects.put("Portugal",StringVariable.countryCode);
        subjects.put("Germany",StringVariable.countryCode);
        subjects.put("United Kingdom",StringVariable.countryCode);
        subjects.put("Spain",StringVariable.countryCode);
        subjects.put("RESORT_HOTEL",StringVariable.hotel);
        subjects.put("CITY_HOTEL",StringVariable.hotel);
        return subjects;
    }

    public static HashMap<StringVariable,HashSet<String>> getCompoundSelectStringVariables() {
        HashMap<StringVariable,HashSet<String>> subjects = new HashMap<>();
//        HashSet<String> countries = new HashSet<>();
//        countries.add("Portugal");
//        countries.add("Germany");
//        countries.add("United Kingdom");
//        countries.add("Spain");
//        subjects.put(StringVariable.countryCode,countries);
        HashSet<String> hotels = new HashSet<>();
        hotels.add("RESORT_HOTEL");
        hotels.add("CITY_HOTEL");
        subjects.put(StringVariable.hotel,hotels);
        return subjects;
    }

    public static ArrayList<SummaryResult> generateFirstFormSingleSummaryFirstList() throws IOException {
        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        for(NumericVariable variable : NumericVariable.values()){
            if (variable.getIsQuantifier()!=null && !variable.getIsQuantifier()) {
                LBR.getVariables().get(variable).getLabels().forEach((label,membershipFunction) -> {
                    LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
                        list.add(new FirstFormSingleSummary(null,true,quantifierLabel,new TreeMap(Map.of(variable.toString(),label))).retrieveResults());
                    });
                });
            }
        }
        return list;
    }
    public static ArrayList<SummaryResult> generateFirstFormSingleSummarySecondList() throws IOException {
        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        for(NumericVariable variable : NumericVariable.values()){
            if (variable.getIsQuantifier()!=null && !variable.getIsQuantifier()) {
                LBR.getVariables().get(variable).getLabels().forEach((label,membershipFunction) -> {
                    LBR.getVariables().get(NumericVariable.absoluteQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
                        list.add(new FirstFormSingleSummary(null,false,quantifierLabel,new TreeMap(Map.of(variable.toString(),label))).retrieveResults());
                    });
                });
            }
        }
        return list;
    }
    public static ArrayList<SummaryResult> generateFirstFormSingleSummaryThirdList() throws IOException {
        HashMap<String,StringVariable> subjects = getSelectStringVariables();

        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();

        subjects.forEach((subjectFilter,subjectVariable) -> {
            for(NumericVariable variable : NumericVariable.values()){
                if (variable.getIsQuantifier()!=null && !variable.getIsQuantifier()) {
                    LBR.getVariables().get(variable).getLabels().forEach((label,membershipFunction) -> {
                        LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
                            list.add(new FirstFormSingleSummary(new CrispSet(subjectVariable,subjectFilter),true,quantifierLabel,new TreeMap(Map.of(variable.toString(),label))).retrieveResults());
                        });
                    });
                }
            }
        });
        return list;
    }

    public static ArrayList<SummaryResult> generateFirstFormSingleSummaryFourthList() throws IOException {
        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        Generator.combination(NumericVariable.values()).simple(2).forEach((variableSet) -> {
            if (variableSet.get(0).getIsQuantifier() != null && !variableSet.get(0).getIsQuantifier() && variableSet.get(1).getIsQuantifier() != null && !variableSet.get(1).getIsQuantifier()) {
                LBR.getVariables().get(variableSet.get(0)).getLabels().forEach((label1,membershipFunction1) -> {
                    LBR.getVariables().get(variableSet.get(1)).getLabels().forEach((label2,membershipFunction2) -> {
                        LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
                            list.add(new FirstFormSingleSummary(null,true,quantifierLabel,new TreeMap(Map.of(variableSet.get(0).toString(),label1,variableSet.get(1).toString(),label2))).retrieveResults());
                        });
                    });
                });
            }
        });
        return list;
    }

    public static ArrayList<SummaryResult> generateSecondFormSingleSummaryFirstList() throws IOException {
        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        Generator.combination(getSelectNumericVariables()).simple(2).forEach((variableSet) -> {
            LBR.getVariables().get(variableSet.get(0)).getLabels().forEach((summarizerLabel1,membershipFunction1) -> {
                LBR.getVariables().get(variableSet.get(1)).getLabels().forEach((qualifierLabel1,membershipFunction2) -> {
                    LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
                        list.add(new SecondFormSingleSummary(null,quantifierLabel,new TreeMap(Map.of(variableSet.get(0).toString(),summarizerLabel1)),new TreeMap(Map.of(variableSet.get(1).toString(),qualifierLabel1))).retrieveResults());
                    });
                });
            });
        });
        return list;
    }
    public static ArrayList<SummaryResult> generateSecondFormSingleSummarySecondList() throws IOException {
        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getSelectStringVariables().forEach((subjectFilter,subjectVariable) -> {
            Generator.combination(getSelectNumericVariables()).simple(2).forEach((variableSet) -> {
                LBR.getVariables().get(variableSet.get(0)).getLabels().forEach((summarizerLabel1,membershipFunction1) -> {
                    LBR.getVariables().get(variableSet.get(1)).getLabels().forEach((qualifierLabel1,membershipFunction2) -> {
                        LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
                            list.add(new SecondFormSingleSummary(new CrispSet(subjectVariable,subjectFilter),quantifierLabel,new TreeMap(Map.of(variableSet.get(0).toString(),summarizerLabel1)),new TreeMap(Map.of(variableSet.get(1).toString(),qualifierLabel1))).retrieveResults());
                        });
                    });
                });
            });
        });

        return list;
    }

    public static ArrayList<SummaryResult> generateSecondFormSingleSummaryThirdList() throws IOException {
        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getSelectNumericVariables().forEach((summarizer) -> {
            HashSet<NumericVariable> filteredVariables = getSelectNumericVariables();
            filteredVariables.remove(summarizer);
            Generator.combination(filteredVariables).simple(2).forEach((qualifierSet) -> {
                LBR.getVariables().get(summarizer).getLabels().forEach((summarizerLabel,membershipFunction) -> {
                    LBR.getVariables().get(qualifierSet.get(0)).getLabels().forEach((qualifierLabel1,membershipFunction1) -> {
                        LBR.getVariables().get(qualifierSet.get(1)).getLabels().forEach((qualifierLabel2,membershipFunction2) -> {
                            LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
                                list.add(new SecondFormSingleSummary(null,quantifierLabel,new TreeMap(Map.of(summarizer.toString(),summarizerLabel)),new TreeMap(Map.of(qualifierSet.get(0).toString(),qualifierLabel1,qualifierSet.get(1).toString(),qualifierLabel2))).retrieveResults());
                            });
                        });
                    });
                });
            });
        });

        return list;
    }

    public static ArrayList<SummaryResult> generateFirstFormMultiSummaryList() throws IOException {
        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getCompoundSelectStringVariables().forEach((stringVariable,filterValues) -> {
            Generator.combination(filterValues).simple(2).forEach((filterValueSet) -> {
                getSelectNumericVariables().forEach((numericVariable) -> {
                    LBR.getVariables().get(numericVariable).getLabels().forEach((summarizerLabel, membershipFunction1) -> {
                        LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
                            list.add(new FirstFormMultiSummary(new CrispSet(stringVariable,filterValueSet.get(0)),new CrispSet(stringVariable,filterValueSet.get(1)),quantifierLabel,new TreeMap(Map.of(numericVariable.toString(),summarizerLabel))).retrieveResults());
                        });
                    });
                });
            });
        });

        return list;
    }

    public static ArrayList<SummaryResult> generateSecondFormMultiSummaryList() throws IOException {
        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getCompoundSelectStringVariables().forEach((stringVariable,filterValues) -> {
            Generator.combination(filterValues).simple(2).forEach((filterValueSet) -> {
                getSelectNumericVariables().forEach((numericVariable) -> {
                    HashSet<NumericVariable> filteredNumericVariables = getSelectNumericVariables();
                    filteredNumericVariables.remove(numericVariable);
                    filteredNumericVariables.forEach((qualifierNumericVariable) -> {
                        LBR.getVariables().get(numericVariable).getLabels().forEach((summarizerLabel, membershipFunction1) -> {
                            LBR.getVariables().get(qualifierNumericVariable).getLabels().forEach((qualifierLabel,membershipFunction2) -> {
                                LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
                                    list.add(new SecondFormMultiSummary(new CrispSet(stringVariable,filterValueSet.get(0)),new CrispSet(stringVariable,filterValueSet.get(1)),quantifierLabel,new TreeMap(Map.of(numericVariable.toString(),summarizerLabel)),new TreeMap(Map.of(qualifierNumericVariable.toString(),qualifierLabel))).retrieveResults());
                                });
                            });
                        });
                    });
                });
            });
        });
        return list;
    }

    public static ArrayList<SummaryResult> generateThirdFormMultiSummaryList() throws IOException {
        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getCompoundSelectStringVariables().forEach((stringVariable,filterValues) -> {
            Generator.combination(filterValues).simple(2).forEach((filterValueSet) -> {
                getSelectNumericVariables().forEach((numericVariable) -> {
                    HashSet<NumericVariable> filteredNumericVariables = getSelectNumericVariables();
                    filteredNumericVariables.remove(numericVariable);
                    filteredNumericVariables.forEach((qualifierNumericVariable) -> {
                        LBR.getVariables().get(numericVariable).getLabels().forEach((summarizerLabel, membershipFunction1) -> {
                            LBR.getVariables().get(qualifierNumericVariable).getLabels().forEach((qualifierLabel,membershipFunction2) -> {
                                LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
                                    list.add(new ThirdFormMultiSummary(new CrispSet(stringVariable,filterValueSet.get(0)),new CrispSet(stringVariable,filterValueSet.get(1)),quantifierLabel,new TreeMap(Map.of(numericVariable.toString(),summarizerLabel)),new TreeMap(Map.of(qualifierNumericVariable.toString(),qualifierLabel))).retrieveResults());
                                });
                            });
                        });
                    });
                });
            });
        });
        return list;
    }

    public static ArrayList<SummaryResult> generateFourthFormMultiSummaryList() throws IOException {
        ArrayList<SummaryResult> list = new ArrayList<>();
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getCompoundSelectStringVariables().forEach((stringVariable,filterValues) -> {
            Generator.combination(filterValues).simple(2).forEach((filterValueSet) -> {
                getSelectNumericVariables().forEach((numericVariable) -> {
                    LBR.getVariables().get(numericVariable).getLabels().forEach((summarizerLabel, membershipFunction1) -> {
                            list.add(new FourthFormMultiSummary(new CrispSet(stringVariable,filterValueSet.get(0)),new CrispSet(stringVariable,filterValueSet.get(1)),new TreeMap(Map.of(numericVariable.toString(),summarizerLabel))).retrieveResults());
                    });
                });
            });
        });
        return list;
    }



    public static void saveCsvSingleSummaryResults(ArrayList<SummaryResult> data, String filename) throws IOException {
        data.sort(Comparator.comparingDouble(o -> o.getMeasures().get("t1")));
        data.removeIf(p->p.getMeasures().get("t1").isNaN());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+".csv"), StandardCharsets.UTF_8));
        bw.write("Summary,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,Optimum");
        bw.newLine();
        for(SummaryResult result : data) {
            bw.write(result.toCsvLine());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static void saveCsvMultiSummaryResults(ArrayList<SummaryResult> data, String filename) throws IOException {
        data.sort(Comparator.comparingDouble(o -> o.getMeasures().get("T")));
        data.removeIf(p->p.getMeasures().get("T").isNaN());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename + ".csv"), StandardCharsets.UTF_8));
        bw.write("Summary,T");
        bw.newLine();
        for (SummaryResult result : data) {
            bw.write(result.toCsvLine());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static void saveCsvMultiSummaryFourthFormResults(ArrayList<SummaryResult> data, String filename) throws IOException {
        data.sort(Comparator.comparingDouble(o -> o.getMeasures().get("T")));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename + ".csv"), StandardCharsets.UTF_8));
        bw.write("Summary,SymmetricSummary,T,SymmetricT");
        bw.newLine();
        for (SummaryResult result : data) {
            bw.write(result.toCsvLine());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

}
