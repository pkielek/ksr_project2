package fuzzy.summaries;

import fuzzy.CrispSet;
import fuzzy.FuzzySet;
import fuzzy.LinguisticVariableRepository;
import model.NumericVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FirstFormMultiSummary extends MultiLinguisticSummary {

    public FirstFormMultiSummary(CrispSet subject, CrispSet secondSubject, String quantifierLabel, TreeMap<String,String> summarizersByVariableAndLabel) {

        setSummarizersByVariableAndLabel(summarizersByVariableAndLabel);
        setSubject(subject);
        setSecondSubject(secondSubject);
        setRelativeQuantifier(true);
        setQualifiersByVariableAndLabel(null);
        setQualityMeasures(new HashMap<>());

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

        setSummary(quantifierLabel +
                " reservations " + subject.getVariable().getPrefix() + " " + subject.getFilterValue() + " " + subject.getVariable().getPostfix()
                + "compared to reservations" + secondSubject.getVariable().getPrefix() + " " + secondSubject.getFilterValue() + " " + secondSubject.getVariable().getPostfix() +
                " " + summarizerSummary);

        setQuantifierLabel(quantifierLabel);

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

        calcTByQuantifier(quantifierLabel);
    }

    public void calcTByQuantifier(String quantifierLabel) {
        setQuantifierLabel(quantifierLabel);
        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        int firstSubjectCount = getSubject().count();
        int secondSubjectCount = getSecondSubject().count();

        Double firstSubjectSummarizerSigmaCount = getFirstSubjectSummaryResultSet().getEntries().values().stream().reduce(0.0,Double::sum);
        setT(LBR.getVariables().get(NumericVariable.relativeQuantifier).getLabels().get(quantifierLabel).calcValue(
                (firstSubjectSummarizerSigmaCount/firstSubjectCount)/(
                        (firstSubjectSummarizerSigmaCount/firstSubjectCount)+
                                (getSecondSubjectSummaryResultSet().getEntries().values().stream().reduce(0.0,Double::sum)/secondSubjectCount)
                )
        ));
    }

    @Override
    public SummaryResult retrieveResults() {
        getQualityMeasures().put("T",optimalMeasure());
        return new SummaryResult(getSummary(),getQualityMeasures());
    }
}
