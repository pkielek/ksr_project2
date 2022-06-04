import fuzzy.*;
import model.HotelBookingRepository;
import model.NumericVariable;
import model.StringVariable;

import java.util.*;

import static model.DatabaseInitialize.databaseInitialize;


public class MainApp {

    public static void main(String[] args) {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        LBR.loadAllVariables();
        generate();
//        new FourthFormMultiSummary(new CrispSet(StringVariable.countryCode,"United Kingdom"),new CrispSet(StringVariable.countryCode, "Portugal"),new TreeMap<>(Map.of("leadTime","short")));
//        new SecondFormSingleSummary(null,true,"all",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));

    }

    public static void generate() {
        ArrayList<FirstFormSingleSummary> firstFormList = new ArrayList<>();
        ArrayList<SecondFormSingleSummary> secondFormList = new ArrayList<>();
        firstFormList.add(new FirstFormSingleSummary(null,true,"all",new TreeMap(Map.of("bookingChanges","little"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"some",new TreeMap(Map.of("adr","very low"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"half of",new TreeMap(Map.of("leadTime","short"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"some",new TreeMap(Map.of("arrivalDateDayOfMonth","for middle of the month"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"hardly any",new TreeMap(Map.of("staysInWeekendNights","long"))));

        firstFormList.add(new FirstFormSingleSummary(null,false,"less than 10000",new TreeMap(Map.of("leadTime","short"))));
        firstFormList.add(new FirstFormSingleSummary(null,false,"around 30000",new TreeMap(Map.of("leadTime","short"))));
        firstFormList.add(new FirstFormSingleSummary(null,false,"around 50000",new TreeMap(Map.of("leadTime","short"))));
        firstFormList.add(new FirstFormSingleSummary(null,false,"around 70000",new TreeMap(Map.of("arrivalDateDayOfMonth","for middle of the month"))));
        firstFormList.add(new FirstFormSingleSummary(null,false,"more than 100000",new TreeMap(Map.of("staysInWeekendNights","very long"))));

        FirstFormSingleSummary firstFormSingleSummary31 = new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "Portugal"),true,"all",new TreeMap(Map.of("leadTime","short")));
        firstFormList.add(firstFormSingleSummary31);
        FirstFormSingleSummary firstFormSingleSummary32 = new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "Germany"),true,"some",new TreeMap(Map.of("leadTime","long")));
        firstFormList.add(firstFormSingleSummary32);
        FirstFormSingleSummary firstFormSingleSummary33 = new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "United Kingdom"),true,"half of",new TreeMap(Map.of("leadTime","short")));
        firstFormList.add(firstFormSingleSummary33);
        FirstFormSingleSummary firstFormSingleSummary34 = new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "Poland"),true,"some",new TreeMap(Map.of("arrivalDateDayOfMonth","for middle of the month")));
        firstFormList.add(firstFormSingleSummary34);
        FirstFormSingleSummary firstFormSingleSummary35 = new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "Spain"),true,"hardly any",new TreeMap(Map.of("arrivalDateDayOfMonth","for end of the month")));
        firstFormList.add(firstFormSingleSummary35);

        FirstFormSingleSummary firstFormSingleSummary41 = new FirstFormSingleSummary(null,true,"all",new TreeMap(Map.of("numberOfAdults","little","numberOfChildren","none of")));
        firstFormList.add(firstFormSingleSummary41);
        FirstFormSingleSummary firstFormSingleSummary42 = new FirstFormSingleSummary(null,true,"all",new TreeMap(Map.of("leadTime","short","numberOfChildren","little")));
        firstFormList.add(firstFormSingleSummary42);
        FirstFormSingleSummary firstFormSingleSummary43 = new FirstFormSingleSummary(null,true,"some",new TreeMap(Map.of("leadTime","short","numberOfChildren","little")));
        firstFormList.add(firstFormSingleSummary43);
        FirstFormSingleSummary firstFormSingleSummary44 = new FirstFormSingleSummary(null,true,"some",new TreeMap(Map.of("arrivalDateDayOfMonth","for middle of the month","numberOfChildren","a few")));
        firstFormList.add(firstFormSingleSummary44);
        FirstFormSingleSummary firstFormSingleSummary45 = new FirstFormSingleSummary(null,true,"hardly any",new TreeMap(Map.of("staysInWeekendNights","very long","numberOfChildren","a few")));
        firstFormList.add(firstFormSingleSummary45);

        SecondFormSingleSummary secondFormSingleSummary61 = new SecondFormSingleSummary(null,"all",new TreeMap(Map.of("bookingChanges","little")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary61);
        SecondFormSingleSummary secondFormSingleSummary62 = new SecondFormSingleSummary(null,"a lot of",new TreeMap(Map.of("bookingChanges","little")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary62);
        SecondFormSingleSummary secondFormSingleSummary63 = new SecondFormSingleSummary(null,"half of",new TreeMap(Map.of("adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary63);
        SecondFormSingleSummary secondFormSingleSummary64 = new SecondFormSingleSummary(null,"some",new TreeMap(Map.of("adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary64);
        SecondFormSingleSummary secondFormSingleSummary65 = new SecondFormSingleSummary(null,"hardly any",new TreeMap(Map.of("adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary65);

        SecondFormSingleSummary secondFormSingleSummary51 = new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Portugal"),"all",new TreeMap(Map.of("bookingChanges","little")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary51);
        SecondFormSingleSummary secondFormSingleSummary52 = new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Germany"),"a lot of",new TreeMap(Map.of("adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary52);
        SecondFormSingleSummary secondFormSingleSummary53 = new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "United Kingdom"),"half of",new TreeMap(Map.of("leadTime","short")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary53);
        SecondFormSingleSummary secondFormSingleSummary54 = new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Poland"),"some",new TreeMap(Map.of("arrivalDateDayOfMonth","for middle of the month")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary54);
        SecondFormSingleSummary secondFormSingleSummary55 = new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Spain"),"hardly any",new TreeMap(Map.of("staysInWeekendNights","long")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary55);

        SecondFormSingleSummary secondFormSingleSummary71 = new SecondFormSingleSummary(null,"all",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary71);
        SecondFormSingleSummary secondFormSingleSummary72 = new SecondFormSingleSummary(null,"a lot of",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary72);
        SecondFormSingleSummary secondFormSingleSummary73 = new SecondFormSingleSummary(null,"half of",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary73);
        SecondFormSingleSummary secondFormSingleSummary74 = new SecondFormSingleSummary(null,"some",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary74);
        SecondFormSingleSummary secondFormSingleSummary75 = new SecondFormSingleSummary(null,"hardly any",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
        secondFormList.add(secondFormSingleSummary75);

        SecondFormSingleSummary secondFormSingleSummary81 = new SecondFormSingleSummary(null,"all",new TreeMap(Map.of("bookingChanges","little")),new TreeMap(Map.of("numberOfAdults","a few","numberOfChildren","little")));
        secondFormList.add(secondFormSingleSummary81);
        SecondFormSingleSummary secondFormSingleSummary82 = new SecondFormSingleSummary(null,"a lot of",new TreeMap(Map.of("daysInWaitingList","short")),new TreeMap(Map.of("numberOfAdults","little","numberOfChildren","little")));
        secondFormList.add(secondFormSingleSummary82);
        SecondFormSingleSummary secondFormSingleSummary83 = new SecondFormSingleSummary(null,"half of",new TreeMap(Map.of("daysInWaitingList","very short")),new TreeMap(Map.of("numberOfAdults","a few","requiredCarParkingSpaces","none of")));
        secondFormList.add(secondFormSingleSummary83);
        SecondFormSingleSummary secondFormSingleSummary84 = new SecondFormSingleSummary(null,"some",new TreeMap(Map.of("numberOfAdults","a few")),new TreeMap(Map.of("requiredCarParkingSpaces","little","adr","very low")));
        secondFormList.add(secondFormSingleSummary84);
        SecondFormSingleSummary secondFormSingleSummary85 = new SecondFormSingleSummary(null,"hardly any",new TreeMap(Map.of("bookingChanges","few")),new TreeMap(Map.of("numberOfAdults","a few","adr","very low")));
        secondFormList.add(secondFormSingleSummary85);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\subsection{Podsumowania lingwistyczne jednopodmiotwe w drygiej formie z 1 sumaryzatorem}\n");
        for(int i =0; i<firstFormList.size(); i++){
            stringBuilder.append((i+1)+". " + firstFormList.get(i).getSummary() +"[" +Math.round(firstFormList.get(i).t1()*100.0)/100.0+"]\\\\\n");
        }

        stringBuilder.append("\\begin{table}[H]\n");
        stringBuilder.append("\\caption{Tabela zawięrająca miary jakościowe od T1 do T11 dla każdego opisanego podsumowania lingwistycznego jednopodmiotowe w drugiej formie z 1 sumaryzatorem.}\n");
        stringBuilder.append("\\centering\n");
        stringBuilder.append("\\vspace{0.2cm}\n");
        stringBuilder.append("\\begin{tabular}{c c c c c c c c c c c c c }\n");
        stringBuilder.append("\\hline\\hline\\\\[-0.4cm]\n");
        stringBuilder.append("\\thead{L.p} &\\thead{T1} & \\thead{T2} & \\thead{T3} &\\thead{T4} & \\thead{T5} & \\thead{T6}&\\thead{T7} & \\thead{T8} & \\thead{T9}& \\thead{T10}& \\thead{T11}& \\thead{Optimum}\\\\[0.1cm]\n");
        stringBuilder.append("\\hline \n");
        for(int i =0; i<firstFormList.size(); i++){
            stringBuilder.append((i+1) + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t1()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t2()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t3()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t4()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t5()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t6()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t7()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t8()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t9()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t10()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).t11()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(firstFormList.get(i).optimalMeasure()*100.0)/100.0 );
            stringBuilder.append("\\\\[0.1cm]\n");
            stringBuilder.append("\\hline\n");
        }

        for(int i =0; i<secondFormList.size(); i++){
            stringBuilder.append((i+1)+". " + firstFormList.get(i).getSummary() +"[" +Math.round(firstFormList.get(i).t1()*100.0)/100.0+"]\\\\\n");
        }

        stringBuilder.append("\\begin{table}[H]\n");
        stringBuilder.append("\\caption{Tabela zawięrająca miary jakościowe od T1 do T11 dla każdego opisanego podsumowania lingwistycznego jednopodmiotowe w drugiej formie z 1 sumaryzatorem.}\n");
        stringBuilder.append("\\centering\n");
        stringBuilder.append("\\vspace{0.2cm}\n");
        stringBuilder.append("\\begin{tabular}{c c c c c c c c c c c c c }\n");
        stringBuilder.append("\\hline\\hline\\\\[-0.4cm]\n");
        stringBuilder.append("\\thead{L.p} &\\thead{T1} & \\thead{T2} & \\thead{T3} &\\thead{T4} & \\thead{T5} & \\thead{T6}&\\thead{T7} & \\thead{T8} & \\thead{T9}& \\thead{T10}& \\thead{T11}& \\thead{Optimum}\\\\[0.1cm]\n");
        stringBuilder.append("\\hline \n");
        for(int i =0; i<secondFormList.size(); i++) {
            stringBuilder.append((i+1) + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t1()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t2()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t3()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t4()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t5()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t6()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t7()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t8()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t9()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t10()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).t11()*100.0)/100.0 + " &");
            stringBuilder.append(Math.round(secondFormList.get(i).optimalMeasure()*100.0)/100.0 );
            stringBuilder.append("\\\\[0.1cm]\n");
            stringBuilder.append("\\hline\n");
        }

        stringBuilder.append("\\end{tabular}\n");
        stringBuilder.append("\\end{table}\n");

        System.out.println(stringBuilder.toString());

    }


}
