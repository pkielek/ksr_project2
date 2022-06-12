package fuzzy.summaries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SummaryResult {
    private String summary;
    private HashMap<String,Double> measures;

    public String toCsvLine() {
        StringBuilder builder = new StringBuilder();
        builder.append(summary);
        if(measures.containsKey("t1")) {
            for(int i=1;i<=11;i++) {
                builder.append(",");
                builder.append(measures.get("t"+i).isNaN()?Double.NaN:Math.round(measures.get("t"+i)*100.0)/100.0);
            }
            builder.append(",");
            builder.append(measures.get("Optimum").isNaN()?Double.NaN:Math.round(measures.get("Optimum")*100.0)/100.0);
        } else {
            builder.append(",");
            builder.append(measures.get("T").isNaN()?Double.NaN:(measures.get("T")<0.005&&measures.get("T")!=0.0?"~0.0":Math.round(measures.get("T")*100.0)/100.0));
            if(measures.containsKey("symmetricT")) {
                builder.append(",");
                builder.append(measures.get("symmetricT").isNaN()?Double.NaN:(measures.get("symmetricT")<0.005&&measures.get("symmetricT")!=0.0?"~0.0":Math.round(measures.get("symmetricT")*100.0)/100.0));
            }
        }
        return builder.toString();
    }
}
