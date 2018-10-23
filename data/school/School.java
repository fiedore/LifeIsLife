package lifeislife.data.school;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import lifeislife.MainDatabase;
import lifeislife.data.constants.Alphabet;
import lifeislife.data.constants.SchoolSubject;
import lifeislife.data.constants.SchoolType;

public class School {

    private SchoolType schoolType;
    private List<ClassRegister> classes;
    private int schoolNumber;
    private Map<SchoolSubject, List<BigInteger>> grades;

    public School(SchoolType schoolType, int schoolNumber) {
        this.schoolType = schoolType;
        this.schoolNumber = schoolNumber;
        classes = new ArrayList<>();

    }



    public void generateClasses() {
        for (int i = 1; i <= schoolType.getClassLevel(); i++) {
            for (int j = 0; j < 3; j++) {
                classes.add(new ClassRegister(i, Alphabet.values()[j], schoolType));
            }
        }
    }

    public void fillClasses() {
        for (ClassRegister classRegister : classes) {
            classRegister.generateClassMembers();
        }
    }

    public boolean isItSchoolDay(){
        boolean isWeekend = (MainDatabase.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (MainDatabase.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
        boolean isSummerHoliday = (MainDatabase.calendar.get(Calendar.MONTH) == Calendar.JULY) || (MainDatabase.calendar.get(Calendar.MONTH) == Calendar.AUGUST);
        return !(isWeekend || isSummerHoliday);
    }

    public List<ClassRegister> getClasses() {
        return classes;
    }

    public int getSchoolNumber() {
        return schoolNumber;
    }

    public SchoolType getSchoolType() {
        return schoolType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSchoolType().getName());
        sb.append(" school no. ");
        sb.append(getSchoolNumber());
        sb.append("\n");
        sb.append(classes.size());
        sb.append(" classes");
        return sb.toString();
    }
    

}
