package fuzzy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.lang.Math.floor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class UniverseOfDiscourse {
    private final Double leftLimit;
    private final Double rightLimit;
    private final Boolean isContinuous;
    private Double interval;

    public Boolean inUniverse(double x) {
        if(x<leftLimit||x>rightLimit)
            return false;
        if (isContinuous)
            return true;
        return (x-leftLimit)%interval<0.0000001;
    }

    public Double calcCardinality() {
        if(isContinuous) {
            return rightLimit-leftLimit;
        } else {
            double cardinalityEnd =  rightLimit-(rightLimit%interval<0.0000001?0.0:interval);
            return (floor(((cardinalityEnd-leftLimit)/interval))+1);
        }
    }

}
