package fuzzy;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import model.HotelBookingRepository;
import model.StringVariable;
import org.apache.commons.text.WordUtils;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrispSet implements SetOperations<CrispSet> {
    @Getter
    private final StringVariable variable;
    @Getter
    private final String filterValue;
    @Getter
    private TreeMap<Integer, Boolean> entries;

    public CrispSet(StringVariable variable, String filterValue) {
        this.variable = variable;
        this.filterValue = WordUtils.capitalizeFully(filterValue.replace('_',' '));
        this.entries = new TreeMap<>();
        if (variable==StringVariable.countryCode && CountryCode.findByName(filterValue).isEmpty()) {
            throw new IllegalArgumentException("No such country found");
        }
        HotelBookingRepository HBR = HotelBookingRepository.getInstance();
        for (int i = HotelBookingRepository.MIN_INDEX_DATABASE; i <= HotelBookingRepository.MAX_INDEX_DATABASE; i++) {
            entries.put(i, HBR.getBooking(i).getStringVariable(variable).equals(filterValue));
        }
    }

    public CrispSet(TreeMap<Integer, Boolean> entries) {
        this.variable = StringVariable.undefined;
        this.filterValue = "";
        this.entries = entries;
    }

    public Integer count() {
        long count = entries.values().stream().filter(e -> e).count();
        return (int) count;
    }

    public TreeSet<Integer> inSet() {
        return new TreeSet<>(entries.entrySet().stream().filter(Map.Entry::getValue).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,
        (existing, replacement) -> replacement, TreeMap::new)).keySet());
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

