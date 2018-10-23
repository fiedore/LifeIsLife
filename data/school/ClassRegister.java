package lifeislife.data.school;

import lifeislife.MainDatabase;
import lifeislife.data.Person;
import lifeislife.data.constants.Alphabet;
import lifeislife.data.constants.SchoolGrade;
import lifeislife.data.constants.SchoolSubject;
import lifeislife.data.constants.SchoolType;

import java.util.*;

public class ClassRegister {

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
        Person newPerson;
        boolean sex;
        String name;
        int age;
        for (int i = 0; i < size; i++) {
            sex = Person.randomizeSex();
            name = Person.randomizeName(sex);
            age = schoolType.getStartingAge() + getYear() - 1;
            newPerson = new Person(name, age, sex);
            students.add(newPerson);
        }
    }
    public void advanceDay(){
    for (Person person:students){
        for (SchoolSubject schoolSubject: dailyClasses.get(MainDatabase.calendar.get(Calendar.DAY_OF_WEEK))){

        }
    }
    }

    public int getYear() {
        return year;
    }


    public void addStudent(Person person) {
        students.add(person);
    }

    public Alphabet getClassLetter() {
        return classLetter;
    }

    public List<Person> getStudents() {
        return students;
    }

    public void printStudents() {
        for (int i = 1; i <= students.size(); i++) {
            System.out.println(i + ". " + students.get(i - 1));
        }
    }

    @Override
    public String toString() {
        return "Class " + getClassLetter() + " of year " + getYear();
    }

}
