package fuzzy;

import lombok.Getter;
import lombok.ToString;

import static java.lang.Math.floor;

@ToString
public class TriangleFunction extends MembershipFunction {
    @Getter
    private final Double start;
    @Getter
    private final Double middle;
    @Getter
    private final Double end;

    public TriangleFunction(UniverseOfDiscourse universe, Double start, Double middle, Double end) {
        super(universe);
        if(start>middle||middle>end)
            throw new IllegalArgumentException("Triangle function arguments must be in sorted order!");
        this.start = start;
        this.middle = middle;
        this.end = end;
    }

    @Override
    public Double calcCardinality() {
        if(getCardinality()==null) {
            if(getUniverse().getIsContinuous()) {
                setCardinality(end-start);
            } else {
                boolean startGreaterEqualThanLeftLimit = start >= getUniverse().getLeftLimit();
                boolean endLessEqualThanRightLimit = end <= getUniverse().getRightLimit();
                double cardinalityStart = startGreaterEqualThanLeftLimit?start: getUniverse().getLeftLimit();
                double cardinalityEnd =  endLessEqualThanRightLimit?end: getUniverse().getRightLimit()-
                        (getUniverse().getRightLimit()% getUniverse().getInterval()<0.0000001?0.0: getUniverse().getInterval());
                setCardinality(floor(((cardinalityEnd-cardinalityStart)/getUniverse().getInterval()))+1
                -(startGreaterEqualThanLeftLimit?1:0)-(endLessEqualThanRightLimit?1:0));
            }
        }
        if(getCardinality()<0)
            setCardinality(0.0);
        return getCardinality();

    }

    @Override
    public Double calcValue(Double x) {
        if(!getUniverse().inUniverse(x)) {
            throw(new IllegalArgumentException("Value not in universe of discourse!"));
        }
        if(x<=start||x>=end)
            return 0.0;
        if(x<=middle)
            return (x-start)/(middle-start);
        return (end-x)/(end-middle);
    }

}
