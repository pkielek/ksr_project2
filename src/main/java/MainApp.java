import fuzzy.CrispSet;
import fuzzy.FirstFormSingleSummary;
import fuzzy.LinguisticVariableRepository;
import model.HotelBookingRepository;
import model.NumericVariable;
import model.StringVariable;

import java.util.Map;
import java.util.TreeMap;


public class MainApp {

    public static void main(String[] args) {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        LBR.loadAllVariables();

        new FirstFormSingleSummary(new CrispSet(StringVariable.countryCode,"Portugal"),true,"a lot of",new TreeMap(Map.of("arrivalDateWeekNumber","for the summer")));

    }


}
