package lifeislife.data.constants;

public enum MaleName {
    JOHN("John"),
    TERRY("Terry"),
    MATT("Matt"),
    TOM("Tom"),
    TRACEY("Tracey"),
    TIM("Tim"),
    JERRY("Jerry");

    private String name;

    private MaleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    

}
