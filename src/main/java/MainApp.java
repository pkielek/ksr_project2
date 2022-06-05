import fuzzy.*;
import model.HotelBookingRepository;
import model.NumericVariable;
import model.StringVariable;
import org.paukov.combinatorics3.Generator;

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

        firstFormList.add(new FirstFormSingleSummary(null,true,"all",new TreeMap(Map.of("bookingChanges","none of"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"hardly any",new TreeMap(Map.of("adr","very high"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"half of",new TreeMap(Map.of("leadTime","very short"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"some",new TreeMap(Map.of("arrivalDateDayOfMonth","for middle of the month"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"hardly any",new TreeMap(Map.of("staysInWeekendNights","long"))));

        firstFormList.add(new FirstFormSingleSummary(null,false,"less than 10000",new TreeMap(Map.of("leadTime","short"))));
        firstFormList.add(new FirstFormSingleSummary(null,false,"less than 10000",new TreeMap(Map.of("staysInWeekNights","very short"))));
        firstFormList.add(new FirstFormSingleSummary(null,false,"around 50000",new TreeMap(Map.of("leadTime","short"))));
        firstFormList.add(new FirstFormSingleSummary(null,false,"around 70000",new TreeMap(Map.of("arrivalDateDayOfMonth","for beginning of the month"))));
        firstFormList.add(new FirstFormSingleSummary(null,false,"more than 100000",new TreeMap(Map.of("requiredCarParkingSpaces","none of"))));

        firstFormList.add(new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "Portugal"),true,"hardly any",new TreeMap(Map.of("leadTime","very short"))));
        firstFormList.add(new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "Germany"),true,"some",new TreeMap(Map.of("totalOfSpecialRequests","few"))));
        firstFormList.add(new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "United Kingdom"),true,"half of",new TreeMap(Map.of("leadTime","long"))));
        firstFormList.add(new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "France"),true,"some",new TreeMap(Map.of("daysInWaitingList","long"))));
        firstFormList.add(new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode, "Spain"),true,"a lot of",new TreeMap(Map.of("numberOfChildren","none of"))));

        firstFormList.add(new FirstFormSingleSummary(null,true,"all",new TreeMap(Map.of("numberOfAdults","little","numberOfChildren","none of"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"half of",new TreeMap(Map.of("leadTime","short","numberOfChildren","little"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"some",new TreeMap(Map.of("leadTime","short","numberOfChildren","little"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"some",new TreeMap(Map.of("arrivalDateDayOfMonth","for middle of the month","numberOfChildren","a few"))));
        firstFormList.add(new FirstFormSingleSummary(null,true,"hardly any",new TreeMap(Map.of("staysInWeekendNights","very long","numberOfChildren","a few"))));

        secondFormList.add(new SecondFormSingleSummary(null,"all",new TreeMap(Map.of("bookingChanges","little")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(null,"a lot of",new TreeMap(Map.of("bookingChanges","little")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(null,"half of",new TreeMap(Map.of("adr","very low")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(null,"some",new TreeMap(Map.of("adr","very low")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(null,"hardly any",new TreeMap(Map.of("adr","very low")),new TreeMap(Map.of("numberOfAdults","little"))));

        secondFormList.add(new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Portugal"),"a lot of",new TreeMap(Map.of("bookingChanges","little")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Germany"),"a lot of",new TreeMap(Map.of("adr","very low")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "United Kingdom"),"half of",new TreeMap(Map.of("leadTime","short")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Poland"),"some",new TreeMap(Map.of("arrivalDateDayOfMonth","for middle of the month")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(new CrispSet(StringVariable.countryCode, "Spain"),"hardly any",new TreeMap(Map.of("staysInWeekendNights","long")),new TreeMap(Map.of("numberOfAdults","little"))));

        secondFormList.add(new SecondFormSingleSummary(null,"all",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(null,"a lot of",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(null,"half of",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(null,"some",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little"))));
        secondFormList.add(new SecondFormSingleSummary(null,"hardly any",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little"))));

        secondFormList.add(new SecondFormSingleSummary(null,"all",new TreeMap(Map.of("bookingChanges","little")),new TreeMap(Map.of("numberOfAdults","a few","numberOfChildren","little"))));
        secondFormList.add(new SecondFormSingleSummary(null,"a lot of",new TreeMap(Map.of("daysInWaitingList","short")),new TreeMap(Map.of("numberOfAdults","little","numberOfChildren","little"))));
        secondFormList.add(new SecondFormSingleSummary(null,"half of",new TreeMap(Map.of("daysInWaitingList","very short")),new TreeMap(Map.of("numberOfAdults","a few","requiredCarParkingSpaces","none of"))));
        secondFormList.add(new SecondFormSingleSummary(null,"some",new TreeMap(Map.of("numberOfAdults","a few")),new TreeMap(Map.of("requiredCarParkingSpaces","little","adr","very low"))));
        secondFormList.add(new SecondFormSingleSummary(null,"hardly any",new TreeMap(Map.of("bookingChanges","few")),new TreeMap(Map.of("numberOfAdults","a few","adr","very low"))));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\subsection{Podsumowania lingwistyczne jednopodmiotwe w pierwszej formie}\n");
        for(int i =0; i<firstFormList.size(); i++){
            stringBuilder.append((i+1)+". " + firstFormList.get(i).getSummary() +"[" +Math.round(firstFormList.get(i).t1()*100.0)/100.0+"]\\\\\n");
        }

        stringBuilder.append("\\begin{table}[H]\n");
        stringBuilder.append("\\caption{Tabela zawięrająca miary jakościowe od T1 do T11 dla każdego opisanego podsumowania lingwistycznego jednopodmiotowego w pierwszej formie.}\n");
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

        stringBuilder.append("\\end{tabular}\n");
        stringBuilder.append("\\end{table}\n");

        stringBuilder.append("\\subsection{Podsumowania lingwistyczne jednopodmiotwe w drugiej formie}\n");
        for(int i =0; i<secondFormList.size(); i++){
            stringBuilder.append((i+1)+". " + secondFormList.get(i).getSummary() +"[" +Math.round(secondFormList.get(i).t1()*100.0)/100.0+"]\\\\\n");
        }

        stringBuilder.append("\\begin{table}[H]\n");
        stringBuilder.append("\\caption{Tabela zawięrająca miary jakościowe od T1 do T11 dla każdego opisanego podsumowania lingwistycznego jednopodmiotowego w drugiej formie.}\n");
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
