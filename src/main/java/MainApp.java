import fuzzy.CrispSet;
import fuzzy.FirstFormSingleSummary;
import fuzzy.LinguisticVariableRepository;
import fuzzy.SecondFormSingleSummary;
import model.HotelBookingRepository;
import model.NumericVariable;
import model.StringVariable;

import java.util.Map;
import java.util.TreeMap;


public class MainApp {

    public static void main(String[] args) {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        LBR.loadAllVariables();
        FirstFormSingleSummary firstFormSingleSummary21 = new FirstFormSingleSummary(null,false,"less than 10000",new TreeMap(Map.of("leadTime","short")));
        //new SecondFormSingleSummary(null,true,"hardly any",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));
    }


}
