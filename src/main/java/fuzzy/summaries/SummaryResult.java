package fuzzy.summaries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
public class SummaryResult {
    String summary;
    HashMap<String,Double> measures;
}
