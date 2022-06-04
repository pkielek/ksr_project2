package fuzzy;

import model.HotelBookingRepository;
import model.NumericVariable;

import java.io.NotActiveException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class FirstFormMultiSummary extends MultiLinguisticSummary {
    private HashMap<String, Double> weights = new HashMap<>();
    public FirstFormMultiSummary(CrispSet subject, CrispSet secondSubject, Boolean relativeQuantifier, String quantifierLabel, TreeMap<String,String> summarizersByVariableAndLabel) throws NotActiveException {

        setSummarizersByVariableAndLabel(summarizersByVariableAndLabel);
        setSubject(subject);
        setSecondSubject(secondSubject);
        setQualityMeasures(new HashMap<>());

        if (subject == null || secondSubject == null) {
            throw new NotActiveException("Cannot calculate duo subject summary without subjects");
        }

        LinguisticVariableRepository LBR = LinguisticVariableRepository.getInstance();
        HotelBookingRepository HBR = HotelBookingRepository.getInstance();

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
                " reservations " + (subject == null ? "" : subject.getVariable().getPrefix() + " " + subject.getFilterValue() + " " + subject.getVariable().getPostfix())
                + "compared to reservations" + (secondSubject == null ? "" : secondSubject.getVariable().getPrefix() + " " + secondSubject.getFilterValue() + " " + secondSubject.getVariable().getPostfix()) +
                " " + summarizerSummary);

        setRelativeQuantifier(relativeQuantifier);
        setQuantifierLabel(quantifierLabel);

        if (summarizersByVariableAndLabel.isEmpty()) {
            throw new IllegalArgumentException("No summarizers in summary");
        }

        CrispSet joinSubject = subject.And(secondSubject);
        String firstKey = summarizersByVariableAndLabel.firstKey();
        setSummaryResultSet(new FuzzySet(joinSubject, LBR.getVariables().get(NumericVariable.valueOf(firstKey)).getName(), LBR.getVariables().get(NumericVariable.valueOf(firstKey)).getLabels().get(summarizersByVariableAndLabel.firstEntry().getValue())));
        summarizersByVariableAndLabel.forEach((k, v) -> {
            setSummaryResultSet(getSummaryResultSet().And(
                    new FuzzySet(joinSubject, LBR.getVariables().get(NumericVariable.valueOf(k)).getName(), LBR.getVariables().get(NumericVariable.valueOf(k)).getLabels().get(v))));
        });

        setFirstSubjectSummaryResultSet(new FuzzySet(getSummaryResultSet().getEntries().entrySet().stream().filter(k -> subject.getEntries().get(k.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing, TreeMap::new))));
        setSecondSubjectSummaryResultSet(new FuzzySet(getSummaryResultSet().getEntries().entrySet().stream().filter(k -> secondSubject.getEntries().get(k.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing, TreeMap::new))));

    }

    @Override
    public Double optimalMeasure() {
        return null;
    }
}
