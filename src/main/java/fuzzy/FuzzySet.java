package fuzzy;

import model.HotelBookingRepository;
import model.NumericVariable;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FuzzySet implements SummarySet<FuzzySet> {
    private TreeMap<Integer,Double> entries;

    public FuzzySet(LinguisticVariable variable, String label) {
        entries = new TreeMap<>();
        HotelBookingRepository HBR = HotelBookingRepository.getInstance();
        for(int i=HotelBookingRepository.MIN_INDEX_DATABASE;i<=HotelBookingRepository.MAX_INDEX_DATABASE;i++) {
            NumericVariable variableName = variable.getName();
            MembershipFunction function = variable.getLabels().get(label);
            entries.put(i,function.calcValue(HBR.getBooking(i).getNumericVariable(variableName)));
        }
    }
    public FuzzySet(TreeMap<Integer, Double> entries) {
        this.entries=entries;
    }

    public CrispSet alphaCut(Double alpha) {
        return new CrispSet(entries.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e-> e.getValue() > alpha,(existing,replacement) -> existing, TreeMap::new)));
    }

    public CrispSet support() {
        return alphaCut(0.0);
    }

    public Double calcHeight() {
        return entries.values().stream().max(Double::compareTo).orElse(0.0);
    }

    public boolean isNormal() {
        return calcHeight()>=1;
    }

    public boolean isConvex() {
        // TODO
        return false;
    }

    @Override
    public FuzzySet And(FuzzySet set) {
        return new FuzzySet(Stream.concat(entries.entrySet().stream(),set.entries.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,
                Math::min,TreeMap::new)));
    }

    @Override
    public FuzzySet Or(FuzzySet set) {
        return new FuzzySet(Stream.concat(entries.entrySet().stream(),set.entries.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,
                Math::max,TreeMap::new)));
    }

    @Override
    public void Not() {
        entries.values().forEach(e->e=1.0-e);
    }
}
