package lifeislife.data.workplace;

import lifeislife.MainDatabase;
import lifeislife.data.Facility;
import lifeislife.data.Person;
import lifeislife.data.constants.FemaleName;
import lifeislife.data.constants.MaleName;
import lifeislife.data.constants.StatisticType;
import lifeislife.data.constants.WorkplaceType;
import lifeislife.utils.Distributor;
import lifeislife.utils.Randomizer;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.*;

public class Workplace implements Facility {

    private WorkplaceType workplaceType;
    private Random roll;
    private Map<StatisticType, Person> topPeopleByStat;
    private Map<StatisticType, Person> botPeopleByStat;
    private Map<StatisticType, Integer> topStatValue;
    private Map<StatisticType, Integer> botStatValue;
    private List<Person> employees;
    private int amountOfEmployees;
    private String name;

    public Workplace(WorkplaceType workplaceType, int amountOfEmployees) {
        roll = new Random();
        this.workplaceType = workplaceType;
        this.amountOfEmployees = amountOfEmployees;
        this.employees = new ArrayList<>();
        this.topPeopleByStat = new HashMap<>();
        this.botPeopleByStat = new HashMap<>();
        this.topStatValue = new HashMap<>();
        this.botStatValue = new HashMap<>();
        if (roll.nextBoolean()) {
            this.name = Randomizer.randomEnum(MaleName.class).getName() + "'s " + workplaceType.getName().toLowerCase();
        }
        else {
            this.name = Randomizer.randomEnum(FemaleName.class).getName() + "'s " + workplaceType.getName().toLowerCase();
        }

    }

    public void generateEmployees() {
        employees.clear();
        boolean sex;
        String name;
        int age;
        for (int i = 0; i < amountOfEmployees; i++) {
            sex = roll.nextInt(100) + 1 < workplaceType.getMalePercentage();
            name = Person.randomizeName(sex);
            age = Distributor.generateDistributedValue(workplaceType.getMinAge(), 70);
            employees.add(new Person(name, age, sex));

        }
        fillTopAndBotByStatType();
    }

    private void fillTopAndBotByStatType(){
        for (StatisticType statisticType : StatisticType.values()) {
            Person personWithTopStat = Distributor.findTopStatPersonInAList(employees, statisticType);
            Person personWithBotStat = Distributor.findBotStatPersonInAList(employees, statisticType);
            topPeopleByStat.put(statisticType, personWithTopStat);
            botPeopleByStat.put(statisticType, personWithBotStat);
            topStatValue.put(statisticType, personWithTopStat.getStatisticValue(statisticType));
            botStatValue.put(statisticType, personWithBotStat.getStatisticValue(statisticType));
        }
    }

    public Map<StatisticType, Integer> getBotStatValue() {
        return botStatValue;
    }

    public Map<StatisticType, Integer> getTopStatValue() {
        return topStatValue;
    }

    public Map<StatisticType, Person> getBotPeopleByStat() {
        return botPeopleByStat;
    }

    public Map<StatisticType, Person> getTopPeopleByStat() {
        return topPeopleByStat;
    }
@XmlElements(@XmlElement(name = "Employee"))
    public List<Person> getEmployees() {
        return employees;
    }

    @Override
    public void advanceDay() {
        if (!workplaceType.isWorksWeekends() && MainDatabase.isItWeekend()) {
            return;
        }
        for (Person person : employees) {
            for (StatisticType statisticType : workplaceType.getAttributeCorrelation().keySet()) {
                person.gainExperience(statisticType, workplaceType.getAttributeCorrelation().get(statisticType) / 8, true);
            }
        }
    }
@XmlAttribute(name = "Name")
    public String getName() {
        return name;
    }

    @Override
    public void reinitValues() {
        fillTopAndBotByStatType();
    }

    @Override
    public String toString() {
        return name + "\nEmployees: " + getEmployees().size();
    }


}
