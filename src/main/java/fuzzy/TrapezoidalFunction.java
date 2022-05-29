package fuzzy;

import lombok.Getter;

import static java.lang.Math.floor;

public class TrapezoidalFunction extends MembershipFunction {
    @Getter
    private final Double start;
    @Getter
    private final Double startMiddle;
    @Getter
    private final Double endMiddle;
    @Getter
    private final Double end;

    public TrapezoidalFunction(UniverseOfDiscourse universe, Double start, Double startMiddle, Double endMiddle, Double end) {
        super(universe);
        if(start>startMiddle||startMiddle>endMiddle||endMiddle>end)
            throw new IllegalArgumentException("Trapezoidal function arguments must be in sorted order!");
        this.start = start;
        this.startMiddle = startMiddle;
        this.endMiddle = endMiddle;
        this.end = end;
    }

    @Override
    public Double calcCardinality() {
        if(getUniverse().getIsContinuous()) {
            return end-start;
        } else {
            int count=0;
            double cardinalityStart = start > getUniverse().getLeftLimit()?start: getUniverse().getLeftLimit();
            double cardinalityEnd =  end < getUniverse().getRightLimit()?end: getUniverse().getRightLimit()-
                    (getUniverse().getRightLimit()% getUniverse().getInterval()<0.0000001?0.0: getUniverse().getInterval());
            return floor(((cardinalityEnd-cardinalityStart)/getUniverse().getInterval()))+1;
        }
    }

    @Override
    public Double calcValue(Double x){
        if(!getUniverse().inUniverse(x))
            throw(new IllegalArgumentException("Value not in universe of discourse!"));
        if(x<start||x>end)
            return 0.0;
        if(x>=startMiddle&&x<= endMiddle)
            return 1.0;
        if(x<=startMiddle)
            return (x-start)/(startMiddle-start);
        return (end-x)/(end-endMiddle);
    }

}
