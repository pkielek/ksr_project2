package gui;

import gui.helpers.Observer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.*;


public class MainApp extends Application implements EventHandler<ActionEvent> {

    public static Stage stage;
    private static Scene scene;

    public static void main(String[] args) throws IOException {
        launch();
    }



    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loadingScreen"));
        stage.setScene(scene);
        stage.setTitle("Podsumowania lingwistyczne bazy hoteli");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }


    @Override
    public void handle(ActionEvent actionEvent) {

    }


//    public static void main(String[] args) throws IOException {
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        LBR.loadAllVariables();
//        launch(args);
////        generate_v2(new FirstFormSingleSummary(null,true,"some",new TreeMap(Map.of("leadTime","very short"))));
////        generate_v2(new FirstFormSingleSummary(null,true,"all",new TreeMap(Map.of("numberOfAdults","little"))));
////        generate_v2(new FirstFormSingleSummary(null,true,"some",new TreeMap(Map.of("requiredCarParkingSpaces","little"))));
////        generate_v2(new FirstFormSingleSummary(null,true,"hardly any",new TreeMap(Map.of("daysInWaitingList","long"))));
////        generate_v2(new FirstFormSingleSummary(null,true,"a lot of",new TreeMap(Map.of("numberOfChildren","none of"))));
////
////        generate_v2(new FirstFormSingleSummary(null,false,"less than 10000",new TreeMap(Map.of("bookingChanges","little"))));
////        generate_v2(new FirstFormSingleSummary(null,false,"more than 100000",new TreeMap(Map.of("numberOfChildren","none of"))));
////        generate_v2(new FirstFormSingleSummary(null,false,"around 50000",new TreeMap(Map.of("adr","very high"))));
////        generate_v2(new FirstFormSingleSummary(null,false,"around 70000",new TreeMap(Map.of("staysInWeekNights","very short"))));
////        generate_v2(new FirstFormSingleSummary(null,false,"around 30000",new TreeMap(Map.of("leadTime","very short"))));
////
////        generate_v2(new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "Germany"),true,"a lot of",new TreeMap(Map.of("staysInWeekNights","short"))));
////        generate_v2(new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "United Kingdom"),true,"all",new TreeMap(Map.of("daysInWaitingList","very short"))));
////        generate_v2(new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "Spain"),true,"some",new TreeMap(Map.of("bookingChanges","little"))));
////        generate_v2(new FirstFormSingleSummary(new CrispSet(StringVariable.hotel, "CITY_HOTEL"),true,"hardly any",new TreeMap(Map.of("staysInWeekNights","none of"))));
////        generate_v2(new FirstFormSingleSummary(new CrispSet(StringVariable.hotel, "RESORT_HOTEL"),true,"a lot of",new TreeMap(Map.of("requiredCarParkingSpaces","none of"))));
////
////        generate_v2(new FirstFormSingleSummary(null,true,"hardly any",new TreeMap(Map.of("daysInWaitingList","long","leadTime","very long"))));
////        generate_v2(new FirstFormSingleSummary(null,true,"half of",new TreeMap(Map.of("leadTime","very short","requiredCarParkingSpaces","few"))));
////        generate_v2(new FirstFormSingleSummary(null,true,"a lot of",new TreeMap(Map.of("numberOfAdults","little","staysInWeekNights","very short"))));
////        generate_v2(new FirstFormSingleSummary(null,true,"some",new TreeMap(Map.of("leadTime","short","numberOfChildren","none of"))));
////        generate_v2(new FirstFormSingleSummary(null,true,"half of",new TreeMap(Map.of("numberOfChildren","none of","staysInWeekendNights","none of"))));
////
////        generate_v2(new SecondFormSingleSummary(null,"hardly any",new TreeMap(Map.of("numberOfAdults","little")),new TreeMap(Map.of("staysInWeekNights","long"))));
////        generate_v2(new SecondFormSingleSummary(null,"a lot of",new TreeMap(Map.of("numberOfAdults","little")),new TreeMap(Map.of("totalOfSpecialRequests","few"))));
////        generate_v2(new SecondFormSingleSummary(null,"some",new TreeMap(Map.of("totalOfSpecialRequests","few")),new TreeMap(Map.of("bookingChanges","little"))));
////        generate_v2(new SecondFormSingleSummary(null,"hardly any",new TreeMap(Map.of("leadTime","very long")),new TreeMap(Map.of("requiredCarParkingSpaces","little"))));
////        generate_v2(new SecondFormSingleSummary(null,"half of",new TreeMap(Map.of("totalOfSpecialRequests","none of")),new TreeMap(Map.of("staysInWeekNights","none of"))));
////        generate_v2(new SecondFormSingleSummary(null,"half of",new TreeMap(Map.of("leadTime","long")),new TreeMap(Map.of("totalOfSpecialRequests","few"))));
////        generate_v2(new SecondFormSingleSummary(null,"all",new TreeMap(Map.of("leadTime","very long")),new TreeMap(Map.of("numberOfAdults","a lot of"))));
////
////        generate_v2(new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Germany"),"hardly any",new TreeMap(Map.of("numberOfAdults","none of")),new TreeMap(Map.of("requiredCarParkingSpaces","none of"))));
////        generate_v2(new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "United Kingdom"),"hardly any",new TreeMap(Map.of("requiredCarParkingSpaces","little")),new TreeMap(Map.of("totalOfSpecialRequests","none of"))));
////        generate_v2(new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Portugal"),"some",new TreeMap(Map.of("leadTime","very long")),new TreeMap(Map.of("numberOfAdults","little"))));
////        generate_v2(new SecondFormSingleSummary(new CrispSet(StringVariable.hotel, "CITY_HOTEL"),"some",new TreeMap(Map.of("leadTime","very long")),new TreeMap(Map.of("staysInWeekNights","long"))));
////        generate_v2(new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Germany"),"a lot of",new TreeMap(Map.of("totalOfSpecialRequests","none of")),new TreeMap(Map.of("staysInWeekNights","short"))));
////        generate_v2(new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Spain"),"a lot of",new TreeMap(Map.of("totalOfSpecialRequests","none of")),new TreeMap(Map.of("staysInWeekNights","none of"))));
////        generate_v2(new SecondFormSingleSummary(new CrispSet(StringVariable.hotel, "RESORT_HOTEL"),"all",new TreeMap(Map.of("totalOfSpecialRequests","many")),new TreeMap(Map.of("staysInWeekNights","none of"))));
////
////
////        generate_v2(new SecondFormSingleSummary(null,"a lot of",new TreeMap(Map.of("totalOfSpecialRequests","few")),new TreeMap(Map.of("requiredCarParkingSpaces","few","staysInWeekNights","short"))));
////        generate_v2(new SecondFormSingleSummary(null,"some",new TreeMap(Map.of("requiredCarParkingSpaces","little")),new TreeMap(Map.of("numberOfAdults","little","staysInWeekNights","long"))));
////        generate_v2(new SecondFormSingleSummary(null,"some",new TreeMap(Map.of("leadTime","long")),new TreeMap(Map.of("requiredCarParkingSpaces","little","totalOfSpecialRequests","many"))));
////        generate_v2(new SecondFormSingleSummary(null,"all",new TreeMap(Map.of("staysInWeekNights","very short")),new TreeMap(Map.of("leadTime","long","staysInWeekNights","very short"))));
////        generate_v2(new SecondFormSingleSummary(null,"hardly any",new TreeMap(Map.of("staysInWeekNights","long")),new TreeMap(Map.of("bookingChanges","many","leadTime","short"))));
////        generate_v2(new SecondFormSingleSummary(null,"half of",new TreeMap(Map.of("totalOfSpecialRequests","none of")),new TreeMap(Map.of("bookingChanges","few","leadTime","long"))));
////        generate_v2(new SecondFormSingleSummary(null,"all",new TreeMap(Map.of("leadTime","very long")),new TreeMap(Map.of("bookingChanges","none of","numberOfAdults","many"))));
//
//
////        generate();
////        new FourthFormMultiSummary(new CrispSet(StringVariable.countryCode,"United Kingdom"),new CrispSet(StringVariable.countryCode, "Portugal"),new TreeMap<>(Map.of("leadTime","short")));
////        new SecondFormSingleSummary(null,true,"all",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
//
//    }



//    private static void generate_v2(FirstFormSingleSummary xd) {
//        //System.out.println("-----------------------------");
// //       System.out.println(xd.getSummary());
////        System.out.println(xd.t1());
////        System.out.println(xd.t2());
////        System.out.println(xd.t3());
//        System.out.println(xd.t4());
////        System.out.println(xd.t5());
////        System.out.println(xd.t6());
////        System.out.println(xd.t7());
////        System.out.println(xd.t8());
////        System.out.println(xd.t9());
////        System.out.println(xd.t10());
////        System.out.println(xd.t11());
////        System.out.println(xd.optimalMeasure());
//    }
//
//    private static void generate_v2(SecondFormSingleSummary xd) {
//        System.out.println("-----------------------------");
////        System.out.println(xd.getSummary());
////        System.out.println(xd.t1());
////        System.out.println(xd.t2());
//        System.out.println(xd.t3());
//        System.out.println(xd.t4());
////        System.out.println(xd.t5());
////        System.out.println(xd.t6());
////        System.out.println(xd.t7());
////        System.out.println(xd.t8());
////        System.out.println(xd.t9());
////        System.out.println(xd.t10());
////        System.out.println(xd.t11());
////        System.out.println(xd.optimalMeasure());
//    }
//
//    public static HashSet<NumericVariable> getSelectNumericVariables() {
//        HashSet<NumericVariable> numericVariables = new HashSet<>();
//        numericVariables.add(NumericVariable.bookingChanges);
//        numericVariables.add(NumericVariable.numberOfAdults);
//        numericVariables.add(NumericVariable.leadTime);
//        numericVariables.add(NumericVariable.requiredCarParkingSpaces);
//        numericVariables.add(NumericVariable.totalOfSpecialRequests);
//        numericVariables.add(NumericVariable.staysInWeekNights);
//        return numericVariables;
//    }
//
//    public static HashMap<String,StringVariable> getSelectStringVariables() {
//        HashMap<String,StringVariable> subjects = new HashMap<>();
//        subjects.put("Portugal",StringVariable.countryCode);
//        subjects.put("Germany",StringVariable.countryCode);
//        subjects.put("United Kingdom",StringVariable.countryCode);
//        subjects.put("Spain",StringVariable.countryCode);
//        subjects.put("RESORT_HOTEL",StringVariable.hotel);
//        subjects.put("CITY_HOTEL",StringVariable.hotel);
//        return subjects;
//    }
//
//    public static HashMap<StringVariable,HashSet<String>> getCompoundSelectStringVariables() {
//        HashMap<StringVariable,HashSet<String>> subjects = new HashMap<>();
////        HashSet<String> countries = new HashSet<>();
////        countries.add("Portugal");
////        countries.add("Germany");
////        countries.add("United Kingdom");
////        countries.add("Spain");
////        subjects.put(StringVariable.countryCode,countries);
//        HashSet<String> hotels = new HashSet<>();
//        hotels.add("RESORT_HOTEL");
//        hotels.add("CITY_HOTEL");
//        subjects.put(StringVariable.hotel,hotels);
//        return subjects;
//    }
//
//    public static ArrayList<SummaryResult> generateFirstFormSingleSummaryFirstList() throws IOException {
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        for(NumericVariable variable : NumericVariable.values()){
//            if (variable.getIsQuantifier()!=null && !variable.getIsQuantifier()) {
//                LBR.getVariables().get(variable).getLabels().forEach((label,membershipFunction) -> {
//                    LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
//                        list.add(new FirstFormSingleSummary(null,true,quantifierLabel,new TreeMap(Map.of(variable.toString(),label))).retrieveResults());
//                    });
//                });
//            }
//        }
//        return list;
//    }
//    public static ArrayList<SummaryResult> generateFirstFormSingleSummarySecondList() throws IOException {
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        for(NumericVariable variable : NumericVariable.values()){
//            if (variable.getIsQuantifier()!=null && !variable.getIsQuantifier()) {
//                LBR.getVariables().get(variable).getLabels().forEach((label,membershipFunction) -> {
//                    LBR.getVariables().get(NumericVariable.absoluteQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
//                        list.add(new FirstFormSingleSummary(null,false,quantifierLabel,new TreeMap(Map.of(variable.toString(),label))).retrieveResults());
//                    });
//                });
//            }
//        }
//        return list;
//    }
//    public static ArrayList<SummaryResult> generateFirstFormSingleSummaryThirdList() throws IOException {
//        HashMap<String,StringVariable> subjects = getSelectStringVariables();
//
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//
//        subjects.forEach((subjectFilter,subjectVariable) -> {
//            for(NumericVariable variable : NumericVariable.values()){
//                if (variable.getIsQuantifier()!=null && !variable.getIsQuantifier()) {
//                    LBR.getVariables().get(variable).getLabels().forEach((label,membershipFunction) -> {
//                        LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
//                            list.add(new FirstFormSingleSummary(new CrispSet(subjectVariable,subjectFilter),true,quantifierLabel,new TreeMap(Map.of(variable.toString(),label))).retrieveResults());
//                        });
//                    });
//                }
//            }
//        });
//        return list;
//    }
//
//    public static ArrayList<SummaryResult> generateFirstFormSingleSummaryFourthList() throws IOException {
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        Generator.combination(NumericVariable.values()).simple(2).forEach((variableSet) -> {
//            if (variableSet.get(0).getIsQuantifier() != null && !variableSet.get(0).getIsQuantifier() && variableSet.get(1).getIsQuantifier() != null && !variableSet.get(1).getIsQuantifier()) {
//                LBR.getVariables().get(variableSet.get(0)).getLabels().forEach((label1,membershipFunction1) -> {
//                    LBR.getVariables().get(variableSet.get(1)).getLabels().forEach((label2,membershipFunction2) -> {
//                        LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
//                            list.add(new FirstFormSingleSummary(null,true,quantifierLabel,new TreeMap(Map.of(variableSet.get(0).toString(),label1,variableSet.get(1).toString(),label2))).retrieveResults());
//                        });
//                    });
//                });
//            }
//        });
//        return list;
//    }
//
//    public static ArrayList<SummaryResult> generateSecondFormSingleSummaryFirstList() throws IOException {
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        Generator.combination(getSelectNumericVariables()).simple(2).forEach((variableSet) -> {
//            LBR.getVariables().get(variableSet.get(0)).getLabels().forEach((summarizerLabel1,membershipFunction1) -> {
//                LBR.getVariables().get(variableSet.get(1)).getLabels().forEach((qualifierLabel1,membershipFunction2) -> {
//                    LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
//                        list.add(new SecondFormSingleSummary(null,quantifierLabel,new TreeMap(Map.of(variableSet.get(0).toString(),summarizerLabel1)),new TreeMap(Map.of(variableSet.get(1).toString(),qualifierLabel1))).retrieveResults());
//                    });
//                });
//            });
//        });
//        return list;
//    }
//    public static ArrayList<SummaryResult> generateSecondFormSingleSummarySecondList() throws IOException {
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        getSelectStringVariables().forEach((subjectFilter,subjectVariable) -> {
//            Generator.combination(getSelectNumericVariables()).simple(2).forEach((variableSet) -> {
//                LBR.getVariables().get(variableSet.get(0)).getLabels().forEach((summarizerLabel1,membershipFunction1) -> {
//                    LBR.getVariables().get(variableSet.get(1)).getLabels().forEach((qualifierLabel1,membershipFunction2) -> {
//                        LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
//                            list.add(new SecondFormSingleSummary(new CrispSet(subjectVariable,subjectFilter),quantifierLabel,new TreeMap(Map.of(variableSet.get(0).toString(),summarizerLabel1)),new TreeMap(Map.of(variableSet.get(1).toString(),qualifierLabel1))).retrieveResults());
//                        });
//                    });
//                });
//            });
//        });
//
//        return list;
//    }
//
//    public static ArrayList<SummaryResult> generateSecondFormSingleSummaryThirdList() throws IOException {
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        getSelectNumericVariables().forEach((summarizer) -> {
//            HashSet<NumericVariable> filteredVariables = getSelectNumericVariables();
//            filteredVariables.remove(summarizer);
//            Generator.combination(filteredVariables).simple(2).forEach((qualifierSet) -> {
//                LBR.getVariables().get(summarizer).getLabels().forEach((summarizerLabel,membershipFunction) -> {
//                    LBR.getVariables().get(qualifierSet.get(0)).getLabels().forEach((qualifierLabel1,membershipFunction1) -> {
//                        LBR.getVariables().get(qualifierSet.get(1)).getLabels().forEach((qualifierLabel2,membershipFunction2) -> {
//                            LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
//                                list.add(new SecondFormSingleSummary(null,quantifierLabel,new TreeMap(Map.of(summarizer.toString(),summarizerLabel)),new TreeMap(Map.of(qualifierSet.get(0).toString(),qualifierLabel1,qualifierSet.get(1).toString(),qualifierLabel2))).retrieveResults());
//                            });
//                        });
//                    });
//                });
//            });
//        });
//
//        return list;
//    }
//
//    public static ArrayList<SummaryResult> generateFirstFormMultiSummaryList() throws IOException {
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        getCompoundSelectStringVariables().forEach((stringVariable,filterValues) -> {
//            Generator.combination(filterValues).simple(2).forEach((filterValueSet) -> {
//                getSelectNumericVariables().forEach((numericVariable) -> {
//                    LBR.getVariables().get(numericVariable).getLabels().forEach((summarizerLabel, membershipFunction1) -> {
//                        LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
//                            list.add(new FirstFormMultiSummary(new CrispSet(stringVariable,filterValueSet.get(0)),new CrispSet(stringVariable,filterValueSet.get(1)),quantifierLabel,new TreeMap(Map.of(numericVariable.toString(),summarizerLabel))).retrieveResults());
//                        });
//                    });
//                });
//            });
//        });
//
//        return list;
//    }
//
//    public static ArrayList<SummaryResult> generateSecondFormMultiSummaryList() throws IOException {
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        getCompoundSelectStringVariables().forEach((stringVariable,filterValues) -> {
//            Generator.combination(filterValues).simple(2).forEach((filterValueSet) -> {
//                getSelectNumericVariables().forEach((numericVariable) -> {
//                    HashSet<NumericVariable> filteredNumericVariables = getSelectNumericVariables();
//                    filteredNumericVariables.remove(numericVariable);
//                    filteredNumericVariables.forEach((qualifierNumericVariable) -> {
//                        LBR.getVariables().get(numericVariable).getLabels().forEach((summarizerLabel, membershipFunction1) -> {
//                            LBR.getVariables().get(qualifierNumericVariable).getLabels().forEach((qualifierLabel,membershipFunction2) -> {
//                                LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
//                                    list.add(new SecondFormMultiSummary(new CrispSet(stringVariable,filterValueSet.get(0)),new CrispSet(stringVariable,filterValueSet.get(1)),quantifierLabel,new TreeMap(Map.of(numericVariable.toString(),summarizerLabel)),new TreeMap(Map.of(qualifierNumericVariable.toString(),qualifierLabel))).retrieveResults());
//                                });
//                            });
//                        });
//                    });
//                });
//            });
//        });
//        return list;
//    }
//
//    public static ArrayList<SummaryResult> generateThirdFormMultiSummaryList() throws IOException {
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        getCompoundSelectStringVariables().forEach((stringVariable,filterValues) -> {
//            Generator.combination(filterValues).simple(2).forEach((filterValueSet) -> {
//                getSelectNumericVariables().forEach((numericVariable) -> {
//                    HashSet<NumericVariable> filteredNumericVariables = getSelectNumericVariables();
//                    filteredNumericVariables.remove(numericVariable);
//                    filteredNumericVariables.forEach((qualifierNumericVariable) -> {
//                        LBR.getVariables().get(numericVariable).getLabels().forEach((summarizerLabel, membershipFunction1) -> {
//                            LBR.getVariables().get(qualifierNumericVariable).getLabels().forEach((qualifierLabel,membershipFunction2) -> {
//                                LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().forEach((quantifierLabel,quantifierFunction) -> {
//                                    list.add(new ThirdFormMultiSummary(new CrispSet(stringVariable,filterValueSet.get(0)),new CrispSet(stringVariable,filterValueSet.get(1)),quantifierLabel,new TreeMap(Map.of(numericVariable.toString(),summarizerLabel)),new TreeMap(Map.of(qualifierNumericVariable.toString(),qualifierLabel))).retrieveResults());
//                                });
//                            });
//                        });
//                    });
//                });
//            });
//        });
//        return list;
//    }
//
//    public static ArrayList<SummaryResult> generateFourthFormMultiSummaryList() throws IOException {
//        ArrayList<SummaryResult> list = new ArrayList<>();
//        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
//        getCompoundSelectStringVariables().forEach((stringVariable,filterValues) -> {
//            Generator.combination(filterValues).simple(2).forEach((filterValueSet) -> {
//                getSelectNumericVariables().forEach((numericVariable) -> {
//                    LBR.getVariables().get(numericVariable).getLabels().forEach((summarizerLabel, membershipFunction1) -> {
//                            list.add(new FourthFormMultiSummary(new CrispSet(stringVariable,filterValueSet.get(0)),new CrispSet(stringVariable,filterValueSet.get(1)),new TreeMap(Map.of(numericVariable.toString(),summarizerLabel))).retrieveResults());
//                    });
//                });
//            });
//        });
//        return list;
//    }
//
//
//
//    public static void saveCsvSingleSummaryResults(ArrayList<SummaryResult> data, String filename) throws IOException {
//        data.sort(Comparator.comparingDouble(o -> o.getMeasures().get("t1")));
//        data.removeIf(p->p.getMeasures().get("t1").isNaN());
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+".csv"), StandardCharsets.UTF_8));
//        bw.write("Summary,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,Optimum");
//        bw.newLine();
//        for(SummaryResult result : data) {
//            bw.write(result.toCsvLine());
//            bw.newLine();
//        }
//        bw.flush();
//        bw.close();
//    }
//
//    public static void saveCsvMultiSummaryResults(ArrayList<SummaryResult> data, String filename) throws IOException {
//        data.sort(Comparator.comparingDouble(o -> o.getMeasures().get("T")));
//        data.removeIf(p->p.getMeasures().get("T").isNaN());
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename + ".csv"), StandardCharsets.UTF_8));
//        bw.write("Summary,T");
//        bw.newLine();
//        for (SummaryResult result : data) {
//            bw.write(result.toCsvLine());
//            bw.newLine();
//        }
//        bw.flush();
//        bw.close();
//    }
//
//    public static void saveCsvMultiSummaryFourthFormResults(ArrayList<SummaryResult> data, String filename) throws IOException {
//        data.sort(Comparator.comparingDouble(o -> o.getMeasures().get("T")));
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename + ".csv"), StandardCharsets.UTF_8));
//        bw.write("Summary,SymmetricSummary,T,SymmetricT");
//        bw.newLine();
//        for (SummaryResult result : data) {
//            bw.write(result.toCsvLine());
//            bw.newLine();
//        }
//        bw.flush();
//        bw.close();
//    }

}
