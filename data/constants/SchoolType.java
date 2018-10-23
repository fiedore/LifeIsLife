
package lifeislife.data.constants;


public enum SchoolType {
    PRIMARY("Primary", 8, 7, 1), HIGH("High", 4, 15, 9);
    private String name;
    private int classLevel;
    private int startingAge;
    private int startingGradeYear;

    private SchoolType(String name, int classLevels, int startingAge, int startingGradeYear) {
        this.name = name;
        this.classLevel = classLevels;
        this.startingAge = startingAge;
        this.startingGradeYear = startingGradeYear;
    }

    public int getStartingGradeYear() {
        return startingGradeYear;
    }

    public void setClassLevel(int classLevel) {
        this.classLevel = classLevel;
    }

    public int getClassLevel() {
        return classLevel;
    }

    public int getStartingAge() {
        return startingAge;
    }

    public String getName() {
        return name;
    }
    
}
