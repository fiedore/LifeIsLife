package lifeislife.data;

import lifeislife.data.constants.StatisticType;
import lifeislife.utils.Distributor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Random;

public class Statistic {

    private int value;
    private Random roll;
    private StatisticType statType;

    /**
     * Experience needed to raise the level of the stat. After reaching <b>experienceForNextLevel</b>(default: 1000) the stat goes up by 1.
     */
    private int experience;

    private int experienceForNextLevel = 1000;

    /**
     * Holds and processes the value of various statistics of people( Strength, Intelligence etc.)
     *
     * @param type  constant referring to the type of the statistic
     * @param value upper bound of the value of this randomly generated statistic (0-value). If value isn't stated, the default is 100
     */
    public Statistic(StatisticType type, int value) {
        roll = new Random();
        this.statType = type;
        experience = 0;
        setValue(Distributor.generateDistributedValue(value));
        if (this.value < 2) {                   // pity reroll
            setValue(roll.nextInt(5) + 1);
        }

    }

    public Statistic(StatisticType type) {
        this(type, 100);
    }

    @XmlAttribute(name = "Value")
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        if (value > 100) {
            this.value = 100;
        }
    }

    public void gainExperience(int experience) {
        this.experience += experience;
        if (this.experience >= experienceForNextLevel) {
            this.experience = 0;
            setValue(++value);
        }
    }

    @XmlAttribute(name = "StatType")
    public StatisticType getStatType() {
        return statType;
    }

    @XmlAttribute(name = "Exp")
    public int getExperience() {
        return experience;
    }

    public int getExperienceForNextLevel() {
        return experienceForNextLevel;
    }

    @Override
    public String toString() {
        return getStatType() + ": " + (getValue() < 10 ? "  " : "") + getValue();
    }

}
