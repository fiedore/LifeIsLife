package lifeislife;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import lifeislife.data.Facility;
import lifeislife.data.Person;
import lifeislife.data.constants.WorkplaceType;
import lifeislife.data.school.ClassRegister;
import lifeislife.data.school.School;
import lifeislife.data.constants.SchoolType;
import lifeislife.data.workplace.Workplace;
import lifeislife.utils.Distributor;
import lifeislife.utils.Randomizer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "Database")
public class MainDatabase{

    private List<School> schools;
    private List<Workplace> workplaces;
    private List<Facility> facilities;
    private List<Person> playerCharacters;
    public static Calendar calendar = new GregorianCalendar(2018, 0, 2) {
        @Override
        public String toString() {
            return String.format("%1$tY.%1$tm.%1$te", calendar);
        }
    };

    public MainDatabase() {

        schools = new ArrayList<>();
        playerCharacters = new ArrayList<>();
        facilities = new ArrayList<>();
        workplaces = new ArrayList<>();


    }

    public static boolean isItSchoolDay() {
        boolean isSummerHoliday = (calendar.get(Calendar.MONTH) == Calendar.JULY) || (calendar.get(Calendar.MONTH) == Calendar.AUGUST);
        return !(isItWeekend() || isSummerHoliday);
    }

    public static boolean isItWeekend() {
        return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }


    public void generateSchools(int amount) {
        for (int i = 0; i < amount; i++) {
            SchoolType s = Randomizer.randomEnum(SchoolType.class);
            int schoolNumber = findNextValidSchoolNumber(s);
            schools.add(new School(s, schoolNumber));
        }
        facilities.addAll(schools);
    }

    public void generateAndFillClasses() {
        for (School s : schools) {
            s.generateClasses();
            s.fillClasses();
        }
    }

    public void generateWorkplaces(int amount) {
        int employees;
        for (int i = 0; i < amount; i++) {
            WorkplaceType workplaceType = Randomizer.randomEnum(WorkplaceType.class);
            employees = Distributor.generateDistributedValue(workplaceType.getMaxInitialEmployees());
            workplaces.add(new Workplace(workplaceType, employees));
        }
        facilities.addAll(workplaces);
    }

    public void generateEmployeesForAllWorkplaces() {
        for (Workplace workplace : workplaces) {
            workplace.generateEmployees();
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


    public void printClassesOfSchool(School school) {
        for (ClassRegister ClassRegister : school.getClasses()) {
            System.out.println(ClassRegister);
            ClassRegister.printStudents();
        }
    }


    public void addPlayerCharacter(Person person) {
        playerCharacters.add(person);
    }

    public void advanceDay() {
        for (Facility facility : facilities) {
            facility.advanceDay();
        }
        calendar.add(Calendar.DATE, 1);
    }

    public void advanceDay(int days) {
        for (int i = 0; i < days; i++) {
            advanceDay();
        }
    }

    //GETTERS
    @XmlElements(@XmlElement(name = "Workplace"))
    public List<Workplace> getWorkplaces() {
        return workplaces;
    }

    @XmlElements(@XmlElement(name = "PlayerCharacter"))
    public List<Person> getPlayerCharacters() {
        return playerCharacters;
    }

    @XmlElements(@XmlElement(name = "School"))
    public List<School> getSchools() {
        return schools;
    }

    @XmlElement(name = "Calendar")
    public Calendar getCalendar() {
        return calendar;
    }

    @XmlTransient
    public List<Facility> getFacilities() {
        return facilities;
    }

    //SETTERS
    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

    public void setWorkplaces(List<Workplace> workplaces) {
        this.workplaces = workplaces;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public void setPlayerCharacters(List<Person> playerCharacters) {
        this.playerCharacters = playerCharacters;
    }

    public static void setCalendar(Calendar calendar) {
        MainDatabase.calendar = calendar;
    }


}
