package fuzzy;

import lombok.Data;
import lombok.Getter;

import static java.lang.Math.floor;

public class GaussianFunction extends MembershipFunction {
    @Getter
    private final Double middle;
    @Getter
    private final Double variance;


    public GaussianFunction(UniverseOfDiscourse universe, Double middle, Double variance) {
        super(universe);
        this.middle = middle;
        this.variance = variance;
    }

    @Override
    public Double calcCardinality() {
        if(getUniverse().getIsContinuous()) {
            return getUniverse().getRightLimit()-getUniverse().getLeftLimit();
        } else {
            int count=0;
            double cardinalityStart = getUniverse().getLeftLimit();
            double cardinalityEnd =  getUniverse().getRightLimit()-
                    (getUniverse().getRightLimit()% getUniverse().getInterval()<0.0000001?0.0: getUniverse().getInterval());
            return floor(((cardinalityEnd-getUniverse().getLeftLimit())/getUniverse().getInterval()))+1;
        }
    }


    @Override
    public Double calcValue(Double x) {
        if(!getUniverse().inUniverse(x))
            throw(new IllegalArgumentException("Value not in universe of discourse!"));
        return Math.exp(-(((x-middle)*(x-middle))/2*variance*variance));
    }
}
