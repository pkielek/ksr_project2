package fuzzy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@ToString
public abstract class MembershipFunction {
    @Getter
    private final UniverseOfDiscourse universe;

    public MembershipFunction(UniverseOfDiscourse universe) {
        this.universe = universe;
    }

    abstract Double calcValue(Double x);
    public Double calcArea() {
        Double area = 0.0;
        if(!getUniverse().getIsContinuous()) {
            for(double i=getUniverse().getLeftLimit();i<getUniverse().getRightLimit();i+= getUniverse().getInterval()) {
                area+=calcValue(i);
            }
        }
        else {
            Double eps = 0.001;
            for(Double i=getUniverse().getLeftLimit();i<getUniverse().getRightLimit();i+=eps) {
                area+=calcValue(i)*eps;
            }
        }
        return area;
    }
}
