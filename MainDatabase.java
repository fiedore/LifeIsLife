package lifeislife;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import lifeislife.data.Person;
import lifeislife.data.school.ClassRegister;
import lifeislife.data.school.School;
import lifeislife.data.constants.SchoolType;
import lifeislife.utils.Randomizer;

public class MainDatabase {

    private List<School> schools;
    private List<Person> playerCharacters;
    public static Calendar calendar = new GregorianCalendar(2018, 0, 2){
        @Override
        public String toString(){
            return String.format("%1$tY.%1$tm.%1$te", calendar);
        }
    };

    public MainDatabase() {

        schools = new ArrayList<>();
        playerCharacters = new ArrayList<>();


    }

    public void generateSchools(int amount) {
        for (int i = 0; i < amount; i++) {
            SchoolType s = Randomizer.randomEnum(SchoolType.class);
            int schoolNumber = findNextValidSchoolNumber(s);
            schools.add(new School(s, schoolNumber));
        }
    }

    public void generateAndFillClasses() {
        for (School s : schools) {
            s.generateClasses();
            s.fillClasses();
        }
    }

    private int findNextValidSchoolNumber(SchoolType s) {
        List<School> schoolsOfSelectedType = findSchoolsByType(s);
        int maxNumber = 0;
        if (!schoolsOfSelectedType.isEmpty()) {
            for (School school : schoolsOfSelectedType) {
                if (maxNumber < school.getSchoolNumber()) {
                    maxNumber = school.getSchoolNumber();
                }
            }
        }
        return maxNumber + 1;
    }

    private List<School> findSchoolsByType(SchoolType s) {
        List<School> schoolsOfSelectedType = new ArrayList<>();
        if (!schools.isEmpty()) {
            for (School school : schools) {
                if (school.getSchoolType() == s) {
                    schoolsOfSelectedType.add(school);
                }
            }
        }
        return schoolsOfSelectedType;
    }

    public void printSchools() {
        for (School school : schools) {
            System.out.println(school);
        }
    }

    public List<Person> getPlayerCharacters() {
        return playerCharacters;
    }

    public void printClassesOfSchool(School school) {
        for (ClassRegister ClassRegister : school.getClasses()) {
            System.out.println(ClassRegister);
            ClassRegister.printStudents();
        }
    }

    public List<School> getSchools() {
        return schools;
    }



    public void addPlayerCharacter(Person person) {
        playerCharacters.add(person);
    }

    public void advanceDay(){
        calendar.add(Calendar.DATE, 1);
    }

    public Calendar getCalendar() {
        return calendar;
    }


}
