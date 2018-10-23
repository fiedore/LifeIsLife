
package lifeislife.data.constants;


public enum FemaleName {
    BETTY("Betty"),
    ASHLEY("Ashley"),
    EMMA("Emma"),
    LORRA("Lorra"),
    TINA("Tina"),
    DEBORAH("Deborah"),
    KATE("Kate");

    private String name;

    private FemaleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
