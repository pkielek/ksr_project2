package fuzzy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class UniverseOfDiscourse {
    private final Double leftLimit;
    private final Double rightLimit;
    private final Boolean isContinuous;
    private Double interval;

    Boolean inUniverse(double x) {
        if(x<leftLimit||x>rightLimit)
            return false;
        if (isContinuous)
            return true;
        return (x-leftLimit)%interval<0.0000001;
    }

}
