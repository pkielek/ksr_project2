package fuzzy;

import lombok.*;

@ToString
public abstract class MembershipFunction {
    @Getter
    private final UniverseOfDiscourse universe;
    @Getter
    @Setter
    private Double cardinality=null;
    @Getter
    @Setter
    private Double area=null;

    public MembershipFunction(UniverseOfDiscourse universe) {
        this.universe = universe;
    }

    public abstract Double calcCardinality();
    public abstract Double calcValue(Double x);
    public Double calcArea() {
        if(this.area==null) {
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
            this.area=area;
        }
        return this.area;
    }
}
