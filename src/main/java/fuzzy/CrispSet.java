package fuzzy;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import model.HotelBookingRepository;
import model.StringVariable;
import org.apache.commons.text.WordUtils;

import java.util.Locale;
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
        String filterValue1;
        this.variable = variable;
        filterValue1 = WordUtils.capitalizeFully(filterValue.replace('_',' '));
        if(filterValue1.contains("-")) {
            int index = filterValue1.indexOf("-");
            StringBuilder builder = new StringBuilder(filterValue1);
            builder.setCharAt(index+1,Character.toUpperCase(filterValue1.charAt(index+1)));
            filterValue1 = builder.toString();
        }
        String tempFilter = filterValue;
        if(variable!=StringVariable.countryCode) {
            tempFilter=tempFilter.toUpperCase(Locale.ROOT).replace(" ","_").replace("-","_");
        }
        this.filterValue = filterValue1;
        this.entries = new TreeMap<>();
        System.out.println(filterValue1);
        System.out.println(variable);
        if (variable==StringVariable.countryCode && CountryCode.findByName(filterValue).isEmpty()) {
            throw new IllegalArgumentException("No such country found");
        }
        HotelBookingRepository HBR = HotelBookingRepository.getInstance();
        for (int i = HotelBookingRepository.MIN_INDEX_DATABASE; i <= HotelBookingRepository.MAX_INDEX_DATABASE; i++) {
            entries.put(i, HBR.getBooking(i).getStringVariable(variable).equals(tempFilter));
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

