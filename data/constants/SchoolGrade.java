package lifeislife.data.constants;

import java.math.BigDecimal;

public enum SchoolGrade {
    ONE(BigDecimal.valueOf(1), "1"),
    ONE_PLUS(BigDecimal.valueOf(1.25), "1+"),
    TWO_MINUS(BigDecimal.valueOf(1.75), "2-"),
    TWO(BigDecimal.valueOf(2), "2"),
    TWO_PLUS(BigDecimal.valueOf(2.25), "2+"),
    THREE_MINUS(BigDecimal.valueOf(2.75), "3-"),
    THREE(BigDecimal.valueOf(3), "3"),
    THREE_PLUS(BigDecimal.valueOf(3.25), "3+"),
    FOUR_MINUS(BigDecimal.valueOf(3.75), "4-"),
    FOUR(BigDecimal.valueOf(4), "4"),
    FOUR_PLUS(BigDecimal.valueOf(4.25), "4+"),
    FIVE_MINUS(BigDecimal.valueOf(4.75), "5-"),
    FIVE(BigDecimal.valueOf(5), "5"),
    FIVE_PLUS(BigDecimal.valueOf(5.25), "5+"),
    SIX_MINUS(BigDecimal.valueOf(5.75), "6-"),
    SIX(BigDecimal.valueOf(6), "6");

    private BigDecimal value;
    private String registerEntry;

    private SchoolGrade(BigDecimal value, String registerEntry) {
        this.value = value;
        this.registerEntry = registerEntry;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getRegisterEntry() {
        return registerEntry;
    }

}
