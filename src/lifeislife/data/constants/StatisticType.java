
package lifeislife.data.constants;


public enum StatisticType {
    INT("Intelligence"), 
    STR("Strength"),
    EMOINT("Emotional intelligence"),
    ART("Art skill");
    
    private String typeName;

    private StatisticType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
    
}
