package lifeislife.data;

import lifeislife.data.constants.StatisticType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import lifeislife.data.constants.FemaleName;
import lifeislife.data.constants.MaleName;
import lifeislife.data.constants.Talent;
import lifeislife.utils.Randomizer;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class Person {

    private String name;
    private int age;
    private boolean isMale;
    private List<Statistic> statisticList;
    private List<Talent> talentList;
    private boolean showTalentsInPrint = true;
    private static Random roll = new Random();

    public Person(String name, int age, boolean isMale) {

        this.name = name;
        this.age = age;
        this.isMale = isMale;
        statisticList = new ArrayList<>();
        int ageStatFactor = 75 + findAgeFactor(age);
        for (StatisticType st : StatisticType.values()) {
            statisticList.add(new Statistic(st, ageStatFactor));
        }
        talentList = new ArrayList<>();
        generateTalents();
        adjustStatsByTalents(40);
    }

    public Person(String name, boolean isMale) {
        this(name, roll.nextInt(100) + 1, isMale);
    }

    /*
    public Person(String name, int ageStat, int intStat, int strStat) {
        this.name = name;
        statisticList = new ArrayList<>();
        //statisticList.add(new Statistic(ageStat, StatisticType.AGE));
        statisticList.add(new Statistic(intStat, StatisticType.INT));
        statisticList.add(new Statistic(strStat, StatisticType.STR));
        talentList = new ArrayList<>();
        generateTalents();
        adjustStatsByTalents(25);

    }*/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" (age ");
        sb.append(age);
        sb.append(")\n");
        for (Statistic s : statisticList) {
            sb.append(s.toString());
            sb.append("\n");
        }
        if (showTalentsInPrint) {
            if (!talentList.isEmpty()) {
                sb.append("Talents: \n");
                for (Talent t : talentList) {
                    sb.append(t.getDescriptiveName());
                    sb.append("\n");
                }
            } else {
                sb.append("No talents.\n");
            }
        }
        return sb.toString();
    }



    public void setStatistic(StatisticType statType, int value) {
        getStatisticInstance(statType).setValue(value);
    }

    public int getStatisticValue(StatisticType statType) {
        return getStatisticInstance(statType).getValue();
    }

    public Statistic getStatisticInstance(StatisticType statisticType) {
        for (Statistic stat : statisticList) {
            if (stat.getStatType() == statisticType) {
                return stat;
            }
        }
        return null;
    }

    private void generateTalents() {
        for (Talent value : Talent.values()) {
            if (roll.nextInt(100) + 1 > 95) {
                talentList.add(value);
            }
        }
    }

    private void adjustStatsByTalents(int percentage) {
        if (talentList.isEmpty()) {
            return;
        }
        StatisticType talentStat;
        int adjustedStatValue;
        for (Talent t : talentList) {
            talentStat = t.getTopAssociatedStat();
            adjustedStatValue = getStatisticValue(talentStat) + (((100 - getStatisticValue(talentStat)) * percentage) / 100);
            if (adjustedStatValue > 100) {
                setStatistic(talentStat, 100);
            } else {
                setStatistic(talentStat, adjustedStatValue);
            }
        }
    }

    public void setShowTalentsInPrint(boolean showTalentsInPrint) {
        this.showTalentsInPrint = showTalentsInPrint;
    }

    private int findAgeFactor(int age) {
        if (age >= 25 && age <= 50) {
            return 25;
        }
        if (age > 50) {
            return 75 - age - age % 2;
        }
        return age;
    }

    public void gainExperience(StatisticType statisticType, int experience, boolean considerTalents) {
        if (!considerTalents) {
            getStatisticInstance(statisticType).gainExperience(experience);
        } else {
            List<Talent> statRelatedTalents = getTalentsMatchingStatHelper(statisticType);
            int adjustedExperience = (int) ((0.5 * statRelatedTalents.size()) + 1) * experience; // each talent related to the statistic considered increases exp gained by 50%
            getStatisticInstance(statisticType).gainExperience(adjustedExperience);
        }
    }

    /**
     * Randomizes a boolean type which determines the sex of the person to be
     * created.
     *
     * @return true if male, false if female
     */
    public static boolean randomizeSex() {
        return roll.nextBoolean();
    }

    private List<Talent> getTalentsMatchingStatHelper(StatisticType statisticType) {
        List<Talent> talentsMatching = new ArrayList<>();
        for (Talent talent : this.talentList) {
            if (talent.getTopAssociatedStat() == statisticType) {
                talentsMatching.add(talent);
            }
        }
        return talentsMatching;
    }

    public static Person generatePerson(int age) {
        return generatePerson(age, Person.randomizeSex());
    }

    public static Person generatePerson(int age, boolean sex) {
        String name;
        name = Person.randomizeName(sex);
        return new Person(name, age, sex);
    }

    public static String randomizeName(boolean sex) {
        if (sex) {
            return Randomizer.randomEnum(MaleName.class).getName();
        } else {
            return Randomizer.randomEnum(FemaleName.class).getName();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                isMale == person.isMale &&
                showTalentsInPrint == person.showTalentsInPrint &&
                Objects.equals(name, person.name) &&
                Objects.equals(statisticList, person.statisticList) &&
                Objects.equals(talentList, person.talentList);
    }
//GETTERS
    @XmlElements(@XmlElement(name = "Talent"))
public List<Talent> getTalentList() {
    return talentList;
}
@XmlElements(@XmlElement(name = "Statistic"))
    public List<Statistic> getStatisticList() {
        return statisticList;
    }
@XmlAttribute(name = "Name")
    public String getName() {
        return name;
    }
@XmlAttribute(name = "Age")
    public int getAge() {
        return age;
    }
@XmlAttribute(name = "Male")
    public boolean isMale() {
        return isMale;
    }
//SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public void setStatisticList(List<Statistic> statisticList) {
        this.statisticList = statisticList;
    }



    public void setTalentList(List<Talent> talentList) {
        this.talentList = talentList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isMale, statisticList, talentList, showTalentsInPrint);
    }
}

