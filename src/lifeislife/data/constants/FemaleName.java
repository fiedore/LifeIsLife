
package lifeislife.data.constants;


public enum FemaleName {
    BETTY("Betty"),
    ASHLEY("Ashley"),
    EMMA("Emma"),
    LORA("Lora"),
    TINA("Tina"),
    OLIVIA("Olivia"),
    MIA("Mia"),
    SOPHIE("Sophie"),
    AMELIA("Amelia"),
    ABIGAIL("Abigail"),
    ALISON("Alison"),
    DEBORAH("Deborah"),
    KATE("Kate");

    private String name;

    private FemaleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int longestNameLength() {
        int topLength = 0;
        for (FemaleName femaleName : FemaleName.values()) {
            if (femaleName.getName().length() > topLength) {
                topLength = femaleName.getName().length();
            }
        }
        return topLength;
    }

}
