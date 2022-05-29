package fuzzy;

import model.HotelBookingRepository;
import model.NumericVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

public class FirstFormSingleSummary extends LinguisticSummary {
    private HashMap<String, Double> weights = new HashMap<>(Map.of("t1",1.0,"t2",1.0,"t3",1.0,"t4",1.0,"t5",0.5,"t6",0.5,"t7",0.5,"t8",1.0));
    private CrispSet subject;
    private TreeMap<String,String> summarizersByVariableAndLabel;

    public FirstFormSingleSummary(CrispSet subject, Boolean relativeQuantifier, String quantifierLabel, TreeMap<String,String> summarizersByVariableAndLabel) {
        this.summarizersByVariableAndLabel = summarizersByVariableAndLabel;
        this.subject = subject;
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        HotelBookingRepository HBR = HotelBookingRepository.getInstance();
        StringBuilder summarizerSummary = new StringBuilder();
        summarizersByVariableAndLabel.forEach((k,v)->{
            if(!summarizerSummary.toString().equals((""))) {
                summarizerSummary.append(" and ");
            }
            NumericVariable variable = NumericVariable.valueOf(k);
            if(variable.getTitleBeforeLabel()) {
                summarizerSummary.append(variable.getSummarizerPrefix()).append(" ").append(variable.getSummarizerTitle()).append(" ").append(v);
            } else {
                summarizerSummary.append(variable.getSummarizerPrefix()).append(" ").append(v).append(" ").append(variable.getSummarizerTitle());
            }
        });
        setSummary(quantifierLabel+" reservations "+(subject==null?"":subject.getVariable().getPrefix()+" "+ subject.getFilterValue()+" "+subject.getVariable().getPostfix())
                + summarizerSummary);
        System.out.println(getSummary());

        setRelativeQuantifier(relativeQuantifier);
        setQuantifierLabel(quantifierLabel);
        if(summarizersByVariableAndLabel.isEmpty()) {
            throw new IllegalArgumentException("No summarizers in summary");
        }
        String firstKey = summarizersByVariableAndLabel.firstKey();
        if(subject==null) {
            setResultSet(new FuzzySet(LBR.getVariables().get(NumericVariable.valueOf(firstKey)),summarizersByVariableAndLabel.firstEntry().getValue()));
        } else {
            setResultSet(new FuzzySet(subject, LBR.getVariables().get(NumericVariable.valueOf(firstKey)),summarizersByVariableAndLabel.firstEntry().getValue() ));
        }
        summarizersByVariableAndLabel.forEach((k,v) -> {
            if(k.equals(firstKey)) {
                setResultSet(getResultSet().And((subject==null?new FuzzySet(LBR.getVariables().get(NumericVariable.valueOf(k)),v):new FuzzySet(subject,LBR.getVariables().get(NumericVariable.valueOf(k)),v))));
            }
        });
        t1();
        t2();
        t3();
        t4();
        t5();
        t6();
        t7();
        t8();
    }

    public Double t1() {
        System.out.println(getResultSet().getEntries().values().stream().reduce(0.0,Double::sum)/getResultSet().getEntries().size());
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t1",LBR.getVariables().get(isRelativeQuantifier()?NumericVariable.relativeQuantifier:NumericVariable.absoluteQuantifier)
                .getLabels().get(getQuantifierLabel()).calcValue(
                        getResultSet().getEntries().values().stream().reduce(0.0,Double::sum)/getResultSet().getEntries().size()
                ));
        return getQualityMeasures().get("t1");
    }


    public Double t2() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t2",1-Math.pow(summarizersByVariableAndLabel.entrySet().stream().reduce(1.0,(product,v) -> product*
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getLabels().get(v.getValue()).calcCardinality()/
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getUniverse().calcCardinality(),((aDouble, aDouble2) -> aDouble*aDouble2)),
                1.0/summarizersByVariableAndLabel.size()));
        return getQualityMeasures().get("t2");
    }

    public Double t3() {
        getQualityMeasures().put("t3",Double.valueOf(getResultSet().support().count())/ (double) getResultSet().getEntries().size());
        return getQualityMeasures().get("t3");
    }

    public Double t4() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        HotelBookingRepository HBR = HotelBookingRepository.getInstance();
        AtomicReference<Double> product = new AtomicReference<>(1.0);
        TreeSet<Integer> subjectEntries;
        int size;
        if(subject!=null) {
            subjectEntries = subject.inSet();
            size = subjectEntries.size();
        } else {
            subjectEntries = new TreeSet<>();
            for(int i = HotelBookingRepository.MIN_INDEX_DATABASE; i<=HotelBookingRepository.MAX_INDEX_DATABASE ; i++) {
                subjectEntries.add(i);
            }
            size = subjectEntries.size();
        }
        summarizersByVariableAndLabel.forEach((key,value) -> {
            AtomicReference<Integer> sum = new AtomicReference<>(0);
            NumericVariable numVar = NumericVariable.valueOf(key);
            subjectEntries.forEach((elem) -> sum.updateAndGet(v -> v + LBR.getVariables().get(numVar).getLabels().get(value).calcValue(HBR.getBooking(elem).getNumericVariable(numVar)) > 0 ? 1 : 0));
            product.updateAndGet(v -> v * Double.valueOf(sum.get())/ (double) size);
        });
        getQualityMeasures().put("t4",Math.abs(product.get()-(getQualityMeasures().containsKey("t3")? getQualityMeasures().get("t3") :t3())));
        return getQualityMeasures().get("t4");
    }

    public Double t5() {
        getQualityMeasures().put("t5",2*Math.pow(0.5,summarizersByVariableAndLabel.size()));
        return getQualityMeasures().get("t5");
    }

    public Double t6() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t6",LBR.getVariables().get(isRelativeQuantifier()?NumericVariable.relativeQuantifier:NumericVariable.absoluteQuantifier)
                .getLabels().get(getQuantifierLabel()).calcCardinality()/LBR.getVariables().get(
                isRelativeQuantifier()?NumericVariable.relativeQuantifier:NumericVariable.absoluteQuantifier).getUniverse().calcCardinality());
        return getQualityMeasures().get("t6");
    }

    public Double t7() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t7",LBR.getVariables().get(isRelativeQuantifier()?NumericVariable.relativeQuantifier:NumericVariable.absoluteQuantifier)
                .getLabels().get(getQuantifierLabel()).calcArea()/LBR.getVariables().get(
                isRelativeQuantifier()?NumericVariable.relativeQuantifier:NumericVariable.absoluteQuantifier).getUniverse().calcCardinality());
        return getQualityMeasures().get("t7");
    }

    public Double t8() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t8",1-Math.pow(summarizersByVariableAndLabel.entrySet().stream().reduce(1.0,(product,v) -> product*
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getLabels().get(v.getValue()).calcArea()/
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getUniverse().calcCardinality(),((aDouble, aDouble2) -> aDouble*aDouble2)),
                1.0/summarizersByVariableAndLabel.size()));
        return getQualityMeasures().get("t8");
    }

    @Override
    public Double optimalMeasure() {
        double weightsFactor = 1/weights.values().stream().reduce(0.0,Double::sum);
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        getQualityMeasures().forEach((k,v) -> sum.updateAndGet(v1 -> v1 + (v * weights.get(k)) * weightsFactor));
        return sum.get();
    }
}
