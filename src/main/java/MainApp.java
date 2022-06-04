import com.neovisionaries.i18n.CountryCode;
import fuzzy.*;
import model.HotelBookingRepository;
import model.NumericVariable;
import model.StringVariable;

import java.io.NotActiveException;
import java.util.Map;
import java.util.TreeMap;


public class MainApp {

    public static void main(String[] args) {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        LBR.loadAllVariables();
        new FourthFormMultiSummary(new CrispSet(StringVariable.countryCode,"United Kingdom"),new CrispSet(StringVariable.countryCode, "Portugal"),new TreeMap<>(Map.of("leadTime","short")));
    }


}
