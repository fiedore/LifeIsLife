
package lifeislife.data.constants;


public enum StatisticType {
    //AGE("Age"), 
    INT("Intelligence"), 
    STR("Strength"),
    EMOINT("Emotional intelligence");
    
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
