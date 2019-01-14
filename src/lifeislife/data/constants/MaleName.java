package lifeislife.data.constants;

public enum MaleName {
    JOHN("John"),
    TERRY("Terry"),
    MATT("Matt"),
    TOM("Tom"),
    TRACEY("Tracey"),
    TIM("Tim"),
    WILLIAM("William"),
    JAMES("James"),
    MASON("Mason"),
    ELIJAH("Elijah"),
    OLIVER("Oliver"),
    JACOB("Jacob"),
    MICHAEL("Michael"),
    ETHAN("Ethan"),
    DANIEL("Daniel"),
    HENRY("Henry"),
    SEBASTIAN("Sebastian"),
    JERRY("Jerry");

    private String name;

    private MaleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int longestNameLength() {
        int topLength = 0;
        for (MaleName maleName : MaleName.values()) {
            if (maleName.getName().length() > topLength) {
                topLength = maleName.getName().length();
            }
        }
        return topLength;
    }

}
