package lifeislife.data.school;

import lifeislife.MainDatabase;
import lifeislife.data.Facility;
import lifeislife.data.Person;
import lifeislife.data.constants.*;
import lifeislife.utils.Distributor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;

public class ClassRegister implements Facility {

    private int size;
    private int year;
    private Alphabet classLetter;
    private SchoolType schoolType;
    private List<Person> students;
    private List<SchoolSubject> subjects;
    private Map<Person, Map<SchoolSubject, List<SchoolGrade>>> grades;
    private Map<Integer, List<SchoolSubject>> dailyClasses;
    private Random roll;
    private int classesPerDay;
    private int weeklyClassesTotal;
    private Map<StatisticType, Integer> topStatValue;
    private Map<StatisticType, Integer> botStatValue;

    public ClassRegister(int year, Alphabet letter, SchoolType schoolType) {
        this(new Random().nextInt(11) + 20, year, letter, schoolType);
    }

    public ClassRegister(int size, int year, Alphabet letter, SchoolType schoolType) {
        roll = new Random();
        classesPerDay = 8;
        weeklyClassesTotal = 5 * classesPerDay;
        this.year = year;
        this.schoolType = schoolType;
        this.subjects = generateSubjects();
        this.classLetter = letter;
        this.size = size;
        students = new ArrayList<>();
        grades = new HashMap<>();
        dailyClasses = new HashMap<>();
        topStatValue = new HashMap<>();
        botStatValue = new HashMap<>();
        loadStudents();
        loadSubjects();
        generateDailyClasses();

    }

    private void loadStudents() {
        for (Person person : students) {
            grades.put(person, new HashMap<>());
        }
    }

    /**
     * Iterates over every student (assuming the map <b>grades</b> already has its keys [students of the class connected to this ClassRegister] defined),
     * it loads all the <b>subjects</b> as keys into a map where Person </person>(each student) acts as the key, <<b>schoolSubject</b>, List<SchoolGrade>>> as the value.
     * It essentially creates an empty new ArrayList for holding SchoolGrades, intended to hold multiple grades for each subject.
     */
    private void loadSubjects() {
        for (Person person : grades.keySet()) {
            for (SchoolSubject schoolSubject : subjects) {
                grades.get(person).put(schoolSubject, new ArrayList<>());
            }
        }
    }

    private void generateDailyClasses() {
        for (int i = 2; i < 7; i++) {
            dailyClasses.put(i, new ArrayList<>());
        }
        int amountOfSubjects = subjects.size();
        int subjectClassesPerWeek = weeklyClassesTotal / amountOfSubjects;
        int classesPerWeekDivisionRemainder = weeklyClassesTotal % amountOfSubjects;
        int day;
        for (SchoolSubject subject : subjects) {
            for (int j = 0; j < subjectClassesPerWeek; j++) {
                do {
                    day = roll.nextInt(5) + 2;
                } while (dailyClasses.get(day).size() >= classesPerDay);
                dailyClasses.get(day).add(subject);
            }
        }

        for (int i = 0; i < classesPerWeekDivisionRemainder; i++) {
            for (int j = 2; j < 7; j++) {
                while (dailyClasses.get(j).size() < classesPerDay) {
                    dailyClasses.get(j).add(subjects.get(roll.nextInt(subjects.size())));
                }
            }
        }

    }

    public List<SchoolSubject> generateSubjects() {
        List<SchoolSubject> subjectsList = new ArrayList<>();
        int gradeYear = schoolType.getStartingGradeYear() + year - 1;
        for (SchoolSubject schoolSubject : SchoolSubject.values()) {
            if (schoolSubject.isThisSubjectInThisYear(gradeYear)) {
                subjectsList.add(schoolSubject);
            }
        }
        return subjectsList;
    }

    public int getSize() {
        return size;
    }

    public void generateClassMembers() {
        int age;
        for (int i = 0; i < size; i++) {
            age = schoolType.getStartingAge() + getYear() - 1;
            students.add(Person.generatePerson(age));
        }
        fillTopAndBotByStatType();
    }

    private void fillTopAndBotByStatType() {
        for (StatisticType statisticType : StatisticType.values()) {
            Person personWithTopStat = Distributor.findTopStatPersonInAList(getStudents(), statisticType);
            Person personWithBotStat = Distributor.findBotStatPersonInAList(getStudents(), statisticType);
            topStatValue.put(statisticType, personWithTopStat.getStatisticValue(statisticType));
            botStatValue.put(statisticType, personWithBotStat.getStatisticValue(statisticType));
        }
    }

    public void advanceDay() {
        int currentDay = MainDatabase.calendar.get(Calendar.DAY_OF_WEEK);
        if (dailyClasses.containsKey(currentDay)) {
            for (Person person : students) {
                for (SchoolSubject schoolSubject : dailyClasses.get(currentDay)) {
                    Map<StatisticType, Integer> statDependence = schoolSubject.getStatDependence();
                    for (StatisticType statisticType : statDependence.keySet()) {
                        person.gainExperience(statisticType, statDependence.get(statisticType) / 10, true);
                    }
                }
            }
        }
    }


    public void addStudent(Person person) {
        students.add(person);
    }


    public void printStudents() {
        for (int i = 1; i <= students.size(); i++) {
            System.out.println(i + ". " + students.get(i - 1));
        }
    }

    //SETTERS
    public void setSize(int size) {
        this.size = size;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setClassLetter(Alphabet classLetter) {
        this.classLetter = classLetter;
    }

    public void setSchoolType(SchoolType schoolType) {
        this.schoolType = schoolType;
    }

    public void setStudents(List<Person> students) {
        this.students = students;
    }

    public void setSubjects(List<SchoolSubject> subjects) {
        this.subjects = subjects;
    }

    public void setGrades(Map<Person, Map<SchoolSubject, List<SchoolGrade>>> grades) {
        this.grades = grades;
    }

    public void setDailyClasses(Map<Integer, List<SchoolSubject>> dailyClasses) {
        this.dailyClasses = dailyClasses;
    }

    public void setClassesPerDay(int classesPerDay) {
        this.classesPerDay = classesPerDay;
    }

    public void setWeeklyClassesTotal(int weeklyClassesTotal) {
        this.weeklyClassesTotal = weeklyClassesTotal;
    }

    public void setTopStatValue(Map<StatisticType, Integer> topStatValue) {
        this.topStatValue = topStatValue;
    }

    public void setBotStatValue(Map<StatisticType, Integer> botStatValue) {
        this.botStatValue = botStatValue;
    }

    //GETTERS
    @XmlTransient
    public Map<Person, Map<SchoolSubject, List<SchoolGrade>>> getGrades() {
        return grades;
    }

    @XmlElements(@XmlElement(name = "Subject"))
    public List<SchoolSubject> getSubjects() {
        return subjects;
    }

    @XmlTransient
    public Map<Integer, List<SchoolSubject>> getDailyClasses() {
        return dailyClasses;
    }

    @XmlAttribute(name = "SchoolType")
    public SchoolType getSchoolType() {
        return schoolType;
    }

    @XmlTransient
    public int getClassesPerDay() {
        return classesPerDay;
    }

    @XmlTransient
    public int getWeeklyClassesTotal() {
        return weeklyClassesTotal;
    }

    @XmlAttribute(name = "ClassLetter")
    public Alphabet getClassLetter() {
        return classLetter;
    }

    @XmlElements(@XmlElement(name = "Student"))
    public List<Person> getStudents() {
        return students;
    }

    @Override
    @XmlTransient
    public String getName() {
        return toString();
    }

    @Override
    public void reinitValues() {
        fillTopAndBotByStatType();
    }

    @Override
    @XmlTransient
    public Map<StatisticType, Integer> getBotStatValue() {
        return botStatValue;
    }

    @Override
    @XmlTransient
    public Map<StatisticType, Integer> getTopStatValue() {
        return topStatValue;
    }

    @XmlAttribute(name = "Year")
    public int getYear() {
        return year;
    }
    ////////////////

    @Override
    public String toString() {
        return "Class " + getClassLetter() + " of year " + getYear();
    }

}
