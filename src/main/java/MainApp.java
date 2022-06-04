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

        new SecondFormSingleSummary(null,true,"all",new TreeMap(Map.of("bookingChanges","little","adr","very low")),new TreeMap(Map.of("numberOfAdults","little")));

    }


}
