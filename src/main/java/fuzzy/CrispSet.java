package fuzzy;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import model.HotelBookingRepository;
import model.StringVariable;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrispSet implements SummarySet<CrispSet> {
    @Getter
    private final StringVariable variable;
    @Getter
    private TreeMap<Integer, Boolean> entries;

    public CrispSet(StringVariable variable, String filterValue) {
        this.variable = variable;
        this.entries = new TreeMap<>();
        if (CountryCode.findByName(filterValue).isEmpty()) {
            throw new IllegalArgumentException("No such country found");
        }
        HotelBookingRepository HBR = HotelBookingRepository.getInstance();
        for (int i = HotelBookingRepository.MIN_INDEX_DATABASE; i <= HotelBookingRepository.MAX_INDEX_DATABASE; i++) {
            entries.put(i, HBR.getBooking(i).getStringVariable(variable).equals(filterValue));
        }
    }

    public CrispSet(TreeMap<Integer, Boolean> entries) {
        this.variable = StringVariable.undefined;
        this.entries = entries;
    }

    public Integer count() {
        long count = entries.values().stream().filter(e -> e).count();
        return (int) count;
    }

    @Override
    public CrispSet And(CrispSet set) {
        return new CrispSet(Stream.concat(entries.entrySet().stream(), set.entries.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (existing, replacement) -> existing && replacement, TreeMap::new)));
    }

    @Override
    public CrispSet Or(CrispSet set) {
        return new CrispSet(Stream.concat(entries.entrySet().stream(), set.entries.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (existing, replacement) -> existing || replacement, TreeMap::new)));
    }

    @Override
    public void Not() {
        entries.values().forEach(e -> e = !e);
    }
}

