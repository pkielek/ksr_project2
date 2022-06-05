package fuzzy.summaries;

import fuzzy.CrispSet;
import fuzzy.FuzzySet;
import fuzzy.LinguisticVariableRepository;
import model.HotelBookingRepository;
import model.NumericVariable;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

public class SecondFormSingleSummary extends LinguisticSummary {
    private HashMap<String, Double> weights = new HashMap<>();
    public SecondFormSingleSummary(CrispSet subject, String quantifierLabel, TreeMap<String,String> summarizersByVariableAndLabel, TreeMap<String,String> qualifiersByVariableAndLabel) {

        setSummarizersByVariableAndLabel(summarizersByVariableAndLabel);
        setSubject(subject);
        setQualifiersByVariableAndLabel(qualifiersByVariableAndLabel);
        setQualityMeasures(new HashMap<>());

        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        HotelBookingRepository HBR = HotelBookingRepository.getInstance();

        StringBuilder qualifierSummary = new StringBuilder();
        qualifiersByVariableAndLabel.forEach((k,v)->{
            if(!qualifierSummary.toString().equals((""))) {
                qualifierSummary.append(" and ");
            }
            NumericVariable variable = NumericVariable.valueOf(k);
            if(variable.getTitleBeforeLabel()) {
                qualifierSummary.append(variable.getQualifierPrefix()).append(" ").append(variable.getSummarizerTitle()).append(" ").append(v);
            } else {
                qualifierSummary.append(variable.getQualifierPrefix()).append(" ").append(v).append(" ").append(variable.getSummarizerTitle());
            }
        });

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
        setSummary(quantifierLabel+
                " reservations "+(subject==null?"":subject.getVariable().getPrefix()+" "+ subject.getFilterValue()+" "+subject.getVariable().getPostfix())
                +qualifierSummary + " " + summarizerSummary);

        setRelativeQuantifier(true);
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
            if(!k.equals(firstKey)) {
                setSummaryResultSet(getSummaryResultSet().And((subject==null
                        ?new FuzzySet(LBR.getVariables().get(NumericVariable.valueOf(k)).getName(),LBR.getVariables().get(NumericVariable.valueOf(k)).getLabels().get(v))
                        :new FuzzySet(subject,LBR.getVariables().get(NumericVariable.valueOf(k)).getName(),LBR.getVariables().get(NumericVariable.valueOf(k)).getLabels().get(v)))));
            }
        });


        if(qualifiersByVariableAndLabel.isEmpty()) {
            throw new IllegalArgumentException("No qualifiers in summary");
        }
        String firstQualifierKey = qualifiersByVariableAndLabel.firstKey();
        if(subject==null) {
            setQualifierResultSet(new FuzzySet(LBR.getVariables().get(NumericVariable.valueOf(firstQualifierKey)).getName(),LBR.getVariables().get(NumericVariable.valueOf(firstQualifierKey)).getLabels().get(qualifiersByVariableAndLabel.firstEntry().getValue())));
        } else {
            setQualifierResultSet(new FuzzySet(subject, LBR.getVariables().get(NumericVariable.valueOf(firstQualifierKey)).getName(),LBR.getVariables().get(NumericVariable.valueOf(firstQualifierKey)).getLabels().get(qualifiersByVariableAndLabel.firstEntry().getValue())));
        }
        qualifiersByVariableAndLabel.forEach((k,v) -> {
            if(!k.equals(firstKey)) {
                setQualifierResultSet(getQualifierResultSet().And((subject==null
                        ?new FuzzySet(LBR.getVariables().get(NumericVariable.valueOf(k)).getName(),LBR.getVariables().get(NumericVariable.valueOf(k)).getLabels().get(v))
                        :new FuzzySet(subject,LBR.getVariables().get(NumericVariable.valueOf(k)).getName(),LBR.getVariables().get(NumericVariable.valueOf(k)).getLabels().get(v)))));
            }
        });

        initializeWeights();
        calcMeasures();
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

    private void calcMeasures() {
        t1();
        t2();
        t3();
        t4();
        t5();
        t6();
        t7();
        t8();
        t9();
        t10();
        t11();
    }

    public Double t1() {
        if(getQualityMeasures().get("t1")!=null)
            return getQualityMeasures().get("t1");
        FuzzySet summaryAndQualifierResultSet = getSummaryResultSet().And(getQualifierResultSet());
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t1",LBR.getVariables().get(NumericVariable.relativeQuantifier)
                .getLabels().get(getQuantifierLabel()).calcValue(
                        summaryAndQualifierResultSet.getEntries().values().stream().reduce(0.0,Double::sum)/ getQualifierResultSet().getEntries().values().stream().reduce(0.0,Double::sum)
                ));
        return getQualityMeasures().get("t1");
    }


    public Double t2() {
        if(getQualityMeasures().get("t2")!=null)
            return getQualityMeasures().get("t2");
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t2",1.0-Math.pow(getSummarizersByVariableAndLabel().entrySet().stream().reduce(1.0,(product,v) -> product*
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getLabels().get(v.getValue()).calcCardinality()/
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getUniverse().calcCardinality(),((aDouble, aDouble2) -> aDouble*aDouble2)),
                1.0/getSummarizersByVariableAndLabel().size()));
        return getQualityMeasures().get("t2");
    }

    public Double t3() {
        if(getQualityMeasures().get("t3")!=null)
            return getQualityMeasures().get("t3");
        getQualityMeasures().put("t3",Double.valueOf(getSummaryResultSet().support().count())/ (double) getSummaryResultSet().getEntries().size());
        return getQualityMeasures().get("t3");
    }

    public Double t4() {
        if(getQualityMeasures().get("t4")!=null)
            return getQualityMeasures().get("t4");
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
        if(getQualityMeasures().get("t5")!=null)
            return getQualityMeasures().get("t5");
        getQualityMeasures().put("t5",2*Math.pow(0.5,getSummarizersByVariableAndLabel().size()));
        return getQualityMeasures().get("t5");
    }

    public Double t6() {
        if(getQualityMeasures().get("t6")!=null)
            return getQualityMeasures().get("t6");
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t6",1.0-LBR.getVariables().get(NumericVariable.relativeQuantifier)
                .getLabels().get(getQuantifierLabel()).calcCardinality()/LBR.getVariables().get(
                NumericVariable.relativeQuantifier).getUniverse().calcCardinality());
        return getQualityMeasures().get("t6");
    }

    public Double t7() {
        if(getQualityMeasures().get("t7")!=null)
            return getQualityMeasures().get("t7");
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t7",1.0-LBR.getVariables().get(NumericVariable.relativeQuantifier)
                .getLabels().get(getQuantifierLabel()).calcArea()/LBR.getVariables().get(
                NumericVariable.relativeQuantifier).getUniverse().calcCardinality());
        return getQualityMeasures().get("t7");
    }

    public Double t8() {
        if(getQualityMeasures().get("t8")!=null)
            return getQualityMeasures().get("t8");
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t8",1.0-Math.pow(getSummarizersByVariableAndLabel().entrySet().stream().reduce(1.0,(product,v) -> product*
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getLabels().get(v.getValue()).calcArea()/
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getUniverse().calcCardinality(),((aDouble, aDouble2) -> aDouble*aDouble2)),
                1.0/getSummarizersByVariableAndLabel().size()));
        return getQualityMeasures().get("t8");
    }

    public Double t9() {
        if(getQualityMeasures().get("t9")!=null)
            return getQualityMeasures().get("t9");
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t9",1.0-Math.pow(getQualifiersByVariableAndLabel().entrySet().stream().reduce(1.0,(product,v) -> product*
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getLabels().get(v.getValue()).calcCardinality()/
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getUniverse().calcCardinality(),((aDouble, aDouble2) -> aDouble*aDouble2)),
                1.0/getQualifiersByVariableAndLabel().size()));
        return getQualityMeasures().get("t9");
    }

    public Double t10() {
        if(getQualityMeasures().get("t10")!=null)
            return getQualityMeasures().get("t10");
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        getQualityMeasures().put("t10",1.0-Math.pow(getQualifiersByVariableAndLabel().entrySet().stream().reduce(1.0,(product,v) -> product*
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getLabels().get(v.getValue()).calcArea()/
                        LBR.getVariables().get(NumericVariable.valueOf(v.getKey())).getUniverse().calcCardinality(),((aDouble, aDouble2) -> aDouble*aDouble2)),
                1.0/getQualifiersByVariableAndLabel().size()));
        return getQualityMeasures().get("t10");
    }

    public Double t11() {
        if(getQualityMeasures().get("t11")!=null)
            return getQualityMeasures().get("t11");
        getQualityMeasures().put("t11",2*Math.pow(0.5,getQualifiersByVariableAndLabel().size()));
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
