package fuzzy;

import model.HotelBookingRepository;
import model.NumericVariable;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

public class FirstFormSingleSummary extends LinguisticSummary {
    private HashMap<String, Double> weights = new HashMap<>();
    public FirstFormSingleSummary(CrispSet subject, Boolean relativeQuantifier, String quantifierLabel, TreeMap<String,String> summarizersByVariableAndLabel) {

        setSummarizersByVariableAndLabel(summarizersByVariableAndLabel);
        setSubject(subject);
        setQualifierResultSet(null);
        setQualifiersByVariableAndLabel(null);
        setQualityMeasures(new HashMap<>());

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
            setSummaryResultSet(new FuzzySet(LBR.getVariables().get(NumericVariable.valueOf(firstKey)).getName(),LBR.getVariables().get(NumericVariable.valueOf(firstKey)).getLabels().get(summarizersByVariableAndLabel.firstEntry().getValue())));
        } else {
            setSummaryResultSet(new FuzzySet(subject, LBR.getVariables().get(NumericVariable.valueOf(firstKey)).getName(),LBR.getVariables().get(NumericVariable.valueOf(firstKey)).getLabels().get(summarizersByVariableAndLabel.firstEntry().getValue())));
        }
        summarizersByVariableAndLabel.forEach((k,v) -> {
            if(k.equals(firstKey)) {
                setSummaryResultSet(getSummaryResultSet().And((subject==null
                        ?new FuzzySet(LBR.getVariables().get(NumericVariable.valueOf(k)).getName(),LBR.getVariables().get(NumericVariable.valueOf(k)).getLabels().get(v))
                        :new FuzzySet(subject,LBR.getVariables().get(NumericVariable.valueOf(k)).getName(),LBR.getVariables().get(NumericVariable.valueOf(k)).getLabels().get(v)))));
            }
        });
        System.out.println(t1());
        System.out.println(t2());
        System.out.println(t3());
        System.out.println(t4());
        System.out.println(t5());
        System.out.println(t6());
        System.out.println(t7());
        System.out.println(t8());
        System.out.println(t9());
        System.out.println(t10());
        System.out.println(t11());
        initializeWeights();
        System.out.println(optimalMeasure());
    }

    private void initializeWeights() {
        weights.put("t1",1.0);
        weights.put("t2",1.0);
        weights.put("t3",1.0);
        weights.put("t4",1.0);
        weights.put("t5",1.0);
        weights.put("t6",1.0);
        weights.put("t7",1.0);
        weights.put("t8",1.0);
        weights.put("t9",1.0);
        weights.put("t10",1.0);
        weights.put("t11",1.0);
    }

    public Double t1() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t1",LBR.getVariables().get(isRelativeQuantifier()?NumericVariable.relativeQuantifier:NumericVariable.absoluteQuantifier)
                .getLabels().get(getQuantifierLabel()).calcValue(
                        getSummaryResultSet().getEntries().values().stream().reduce(0.0,Double::sum)/
                                (isRelativeQuantifier()?getSummaryResultSet().getEntries().size():1.0)
                ));
        return getQualityMeasures().get("t1");
    }


    public Double t2() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t2",1.0-Math.pow(getSummarizersByVariableAndLabel().entrySet().stream().reduce(1.0,(product,v) -> product*
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getLabels().get(v.getValue()).calcCardinality()/
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getUniverse().calcCardinality(),((aDouble, aDouble2) -> aDouble*aDouble2)),
                1.0/getSummarizersByVariableAndLabel().size()));
        return getQualityMeasures().get("t2");
    }

    public Double t3() {
        getQualityMeasures().put("t3",Double.valueOf(getSummaryResultSet().support().count())/ (double) getSummaryResultSet().getEntries().size());
        return getQualityMeasures().get("t3");
    }

    public Double t4() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        HotelBookingRepository HBR = HotelBookingRepository.getInstance();
        AtomicReference<Double> product = new AtomicReference<>(1.0);
        TreeSet<Integer> subjectEntries;
        int size;
        if(getSubject()!=null) {
            subjectEntries = getSubject().inSet();
            size = subjectEntries.size();
        } else {
            subjectEntries = new TreeSet<>();
            for(int i = HotelBookingRepository.MIN_INDEX_DATABASE; i<=HotelBookingRepository.MAX_INDEX_DATABASE ; i++) {
                subjectEntries.add(i);
            }
            size = subjectEntries.size();
        }
        getSummarizersByVariableAndLabel().forEach((key,value) -> {
            AtomicReference<Integer> sum = new AtomicReference<>(0);
            NumericVariable numVar = NumericVariable.valueOf(key);
            subjectEntries.forEach((elem) -> sum.updateAndGet(v -> v + LBR.getVariables().get(numVar).getLabels().get(value).calcValue(HBR.getBooking(elem).getNumericVariable(numVar)) > 0 ? 1 : 0));
            product.updateAndGet(v -> v * Double.valueOf(sum.get())/ (double) size);
        });
        getQualityMeasures().put("t4",Math.abs(product.get()-(getQualityMeasures().containsKey("t3")? getQualityMeasures().get("t3") :t3())));
        return getQualityMeasures().get("t4");
    }

    public Double t5() {
        getQualityMeasures().put("t5",2*Math.pow(0.5,getSummarizersByVariableAndLabel().size()));
        return getQualityMeasures().get("t5");
    }

    public Double t6() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t6",1.0-LBR.getVariables().get(isRelativeQuantifier()?NumericVariable.relativeQuantifier:NumericVariable.absoluteQuantifier)
                .getLabels().get(getQuantifierLabel()).calcCardinality()/LBR.getVariables().get(
                isRelativeQuantifier()?NumericVariable.relativeQuantifier:NumericVariable.absoluteQuantifier).getUniverse().calcCardinality());
        return getQualityMeasures().get("t6");
    }

    public Double t7() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t7",1.0-LBR.getVariables().get(isRelativeQuantifier()?NumericVariable.relativeQuantifier:NumericVariable.absoluteQuantifier)
                .getLabels().get(getQuantifierLabel()).calcArea()/LBR.getVariables().get(
                isRelativeQuantifier()?NumericVariable.relativeQuantifier:NumericVariable.absoluteQuantifier).getUniverse().calcCardinality());
        return getQualityMeasures().get("t7");
    }

    public Double t8() {
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t8",1.0-Math.pow(getSummarizersByVariableAndLabel().entrySet().stream().reduce(1.0,(product,v) -> product*
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getLabels().get(v.getValue()).calcArea()/
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getUniverse().calcCardinality(),((aDouble, aDouble2) -> aDouble*aDouble2)),
                1.0/getSummarizersByVariableAndLabel().size()));
        return getQualityMeasures().get("t8");
    }

    public Double t9() {
        getQualityMeasures().put("t9",0.0);
        return getQualityMeasures().get("t9");
    }

    public Double t10() {
        getQualityMeasures().put("t10",0.0);
        return getQualityMeasures().get("t10");
    }

    public Double t11() {
        getQualityMeasures().put("t11",1.0);
        return getQualityMeasures().get("t11");
    }


    @Override
    public Double optimalMeasure() {
        double weightsFactor = weights.isEmpty()?1.0/11.0:1/weights.values().stream().reduce(0.0,Double::sum);
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        getQualityMeasures().forEach((k,v) -> sum.updateAndGet(v1 -> v1 + (v * weights.getOrDefault(k,1.0)) * weightsFactor));
        return sum.get();
    }
}
