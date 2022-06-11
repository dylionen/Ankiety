package ankiety.modules.raport;

import lombok.Getter;

@Getter
public class ReportSingleAnswers {
    private final String value;
    private final Integer amount;
    private final Integer percentage;

    public ReportSingleAnswers(String value, Integer amount, Integer percentage) {
        this.value = value;
        this.amount = amount;
        this.percentage = percentage;
    }
}
