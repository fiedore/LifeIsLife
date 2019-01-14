package lifeislife.data.constants;

public enum Talent {
    MATH("Mathematical", StatisticType.INT),
    LANGUAGE("Linguistic", StatisticType.INT),
    SPORTS("Sportsperson", StatisticType.STR),
    SOCIAL("Social butterfly", StatisticType.EMOINT);

    private String descriptiveName;
    private StatisticType topAssociatedStat;

    private Talent(String descriptiveName, StatisticType topAssociatedStat) {
        this.descriptiveName = descriptiveName;
        this.topAssociatedStat = topAssociatedStat;
    }

    public String getDescriptiveName() {
        return descriptiveName;
    }

    public StatisticType getTopAssociatedStat() {
        return topAssociatedStat;
    }

}
