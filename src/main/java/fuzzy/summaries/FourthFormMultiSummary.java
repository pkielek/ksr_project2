package fuzzy.summaries;

import fuzzy.CrispSet;
import fuzzy.FuzzySet;
import fuzzy.LinguisticVariableRepository;
import model.NumericVariable;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FourthFormMultiSummary extends MultiLinguisticSummary {

    public FourthFormMultiSummary(CrispSet subject, CrispSet secondSubject, TreeMap<String,String> summarizersByVariableAndLabel) {

        setSummarizersByVariableAndLabel(summarizersByVariableAndLabel);
        setSubject(subject);
        setSecondSubject(secondSubject);
        setRelativeQuantifier(true);
        setQualifiersByVariableAndLabel(null);
        setQualityMeasures(null);
        setQuantifierLabel(null);


        if (subject == null || secondSubject == null) {
            throw new IllegalArgumentException("Cannot calculate duo subject summary without subjects");
        }

        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();

        StringBuilder summarizerSummary = new StringBuilder();
        summarizersByVariableAndLabel.forEach((k, v) -> {
            if (!summarizerSummary.toString().equals((""))) {
                summarizerSummary.append(" and ");
            }
            NumericVariable variable = NumericVariable.valueOf(k);
            if (variable.getTitleBeforeLabel()) {
                summarizerSummary.append(variable.getSummarizerPrefix()).append(" ").append(variable.getSummarizerTitle()).append(" ").append(v);
            } else {
                summarizerSummary.append(variable.getSummarizerPrefix()).append(" ").append(v).append(" ").append(variable.getSummarizerTitle());
            }
        });
        setSummary(
                "More reservations " + subject.getVariable().getPrefix() + " " + subject.getFilterValue() + " " + subject.getVariable().getPostfix()
                        + " than reservations " + secondSubject.getVariable().getPrefix() + " " + secondSubject.getFilterValue() + " " + secondSubject.getVariable().getPostfix()
                        + summarizerSummary);

        if (summarizersByVariableAndLabel.isEmpty()) {
            throw new IllegalArgumentException("No summarizers in summary");
        }

        CrispSet joinSubject = subject.Or(secondSubject);
        String firstKey = summarizersByVariableAndLabel.firstKey();
        setSummaryResultSet(new FuzzySet(joinSubject, LBR.getVariables().get(NumericVariable.valueOf(firstKey)).getName(), LBR.getVariables().get(NumericVariable.valueOf(firstKey)).getLabels().get(summarizersByVariableAndLabel.firstEntry().getValue())));
        summarizersByVariableAndLabel.forEach((k, v) -> {
            if(!k.equals(firstKey)) {
                setSummaryResultSet(getSummaryResultSet().And(
                        new FuzzySet(joinSubject, LBR.getVariables().get(NumericVariable.valueOf(k)).getName(), LBR.getVariables().get(NumericVariable.valueOf(k)).getLabels().get(v))));
            }
        });

        setFirstSubjectSummaryResultSet(new FuzzySet(getSummaryResultSet().getEntries().entrySet().stream().filter(k -> subject.getEntries().get(k.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing, TreeMap::new))));
        setSecondSubjectSummaryResultSet(new FuzzySet(getSummaryResultSet().getEntries().entrySet().stream().filter(k -> secondSubject.getEntries().get(k.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing, TreeMap::new))));

        ArrayList<Double> inclusionMembershipFunctionValues = new ArrayList<>();
        getSecondSubjectSummaryResultSet().getEntries().forEach(
                (k1,v1)-> {
                    getFirstSubjectSummaryResultSet().getEntries().forEach(
                            (k2,v2) -> {
                                inclusionMembershipFunctionValues.add(1-v1+v1*v2);
                            }
                    );
                }
        );
        setT(1.0-inclusionMembershipFunctionValues.stream().reduce(0.0,Double::sum)/ inclusionMembershipFunctionValues.size());
    }

}
