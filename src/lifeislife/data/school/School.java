package lifeislife.data.school;

import lifeislife.data.Facility;
import lifeislife.data.constants.Alphabet;
import lifeislife.data.constants.SchoolSubject;
import lifeislife.data.constants.SchoolType;
import lifeislife.data.constants.StatisticType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class School implements Facility {

    private SchoolType schoolType;
    private List<ClassRegister> classes;
    private int schoolNumber;
    private Map<SchoolSubject, List<BigInteger>> grades;

    public School(SchoolType schoolType, int schoolNumber) {
        this.schoolType = schoolType;
        this.schoolNumber = schoolNumber;
        classes = new ArrayList<>();

    }

    //SETTERS
    public void setSchoolType(SchoolType schoolType) {
        this.schoolType = schoolType;
    }

    public void setClasses(List<ClassRegister> classes) {
        this.classes = classes;
    }

    public void setSchoolNumber(int schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public void setGrades(Map<SchoolSubject, List<BigInteger>> grades) {
        this.grades = grades;
    }

    //GETTERS
    @XmlTransient
    public Map<SchoolSubject, List<BigInteger>> getGrades() {return grades;}
    @XmlElements(@XmlElement(name = "Class"))
    public List<ClassRegister> getClasses() {
        return classes;
    }
    @XmlAttribute(name = "SchoolNumber")
    public int getSchoolNumber() {
        return schoolNumber;
    }
    @XmlAttribute(name = "SchoolType")
    public SchoolType getSchoolType() {
        return schoolType;
    }

    /////////

    public void generateClasses() {
        for (int i = 1; i <= schoolType.getClassLevel(); i++) {
            for (int j = 0; j < 3; j++) {
                classes.add(new ClassRegister(i, Alphabet.values()[j], schoolType));
            }
        }
    }

    @Override
    public void advanceDay() {
//        for (ClassRegister classRegister : classes) {
//            classRegister.advanceDay();
//        }
    }

    @Override
    public String getName() {
        return getSchoolType().getName() + " school no. " + getSchoolNumber();
    }

    @Override
    public void reinitValues() {

    }

    @Override
    public Map<StatisticType, Integer> getBotStatValue() {
        throw new UnsupportedOperationException("This method should never be called on School objects");
    }

    @Override
    public Map<StatisticType, Integer> getTopStatValue() {
        throw new UnsupportedOperationException("This method should never be called on School objects");
    }

    public void fillClasses() {
        for (ClassRegister classRegister : classes) {
            classRegister.generateClassMembers();
        }
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
