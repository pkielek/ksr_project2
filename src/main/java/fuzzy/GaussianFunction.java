package fuzzy;

import lombok.Data;
import lombok.Getter;

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
    public Double calcValue(Double x) {
        if(!getUniverse().inUniverse(x))
            throw(new IllegalArgumentException("Value not in universe of discourse!"));
        return Math.exp(-(((x-middle)*(x-middle))/2*variance*variance));
    }
}
