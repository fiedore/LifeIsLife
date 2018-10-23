package lifeislife.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lifeislife.data.constants.StatisticType;
import java.util.Random;

public class Statistic {

    private int value;
    private Random roll;
    private StatisticType statType;
    private BigDecimal low;
    private BigDecimal average;
    private BigDecimal high;
    private BigDecimal top;
    /**
     * Experience needed to raise the level of the stat. After reaching 1000 the stat goes up by 1.
     */
    private int experience;

    /**
     * Holds and processes the value of various statistics of people( Strength, Intelligence etc.)
     * @param type constant referring to the type of the statistic
     * @param value upper bound of the value of this randomly generated statistic (0-value). If value isn't stated, the default is 100
     */
    public Statistic(StatisticType type, int value) {
        roll = new Random();
        this.statType = type;
        experience = 0;
        low = BigDecimal.valueOf(0.2);
        average = BigDecimal.valueOf(0.4);
        high = BigDecimal.valueOf(0.6);
        top = BigDecimal.valueOf(0.8);
        generateValue(value);
    }

    public Statistic(StatisticType type) {
        this(type, 100);
    }

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
        if (this.experience>=1000){
            this.experience = 0;
            setValue(++value);
        }
    }

    /**
     * Generates a random value for the statistic between
     * the range of 0 - boundOfRandomization parameter, where roughly:<br><br>
     * 50% of the results fall between <b>bottomAverage</b>(default 0.4) and <b>bottomHigh</b>(default 0.6)<br><br>
     * 35% of the results fal between <b>bottomLow</b>(default 0.2) and <b>bottomAverage</b>(default 0.4) or between <b>bottomHigh</b>(default 0.6) and <b>bottomTop</b>(default 0.8)<br><br>
     * 15% of the results fall between 1 and <b>bottomLow</b>(default 0.2) or between <b>bottomTop</b>(default 0.8) and boundOfRandomization<br><br>
     * The distribution of scores loosely follows bell curve distribution, although it does not use perfect gaussian due to unnaturally high amount of duplicate numbers. (The variance is too low)
     * Additionally, for the scores of 1, there is also a pity re-roll between 1-5 to reduce the number of absolute bottoms (which should be extremely unlikely and definitely rarer than those with max score)
     *
     * @param boundOfRandomization the max amount of stat value that can be randomly assigned
     */
    private void generateValue(int boundOfRandomization) {
        int result = 0;
        BigDecimal percentageUtil = new BigDecimal(boundOfRandomization);

        int bottomLowInclusive = percentageUtil.multiply(low).setScale(0, RoundingMode.HALF_UP).intValue();
        int bottomAverageInclusive = percentageUtil.multiply(average).setScale(0, RoundingMode.HALF_UP).intValue();
        int bottomHighInclusive = percentageUtil.multiply(high).setScale(0, RoundingMode.HALF_UP).intValue();
        int bottomTopInclusive = percentageUtil.multiply(top).setScale(0, RoundingMode.HALF_UP).intValue();
        int rarity = roll.nextInt(100) + 1;
        if (rarity < 50) {
            result = roll.nextInt(bottomHighInclusive - bottomAverageInclusive) + bottomAverageInclusive;
        }

        if (rarity > 50 && rarity <= 85) {
            if (roll.nextBoolean()) {
                result = roll.nextInt(bottomAverageInclusive - bottomLowInclusive) + bottomLowInclusive;
            } else {
                result = roll.nextInt(bottomTopInclusive - bottomHighInclusive) + bottomHighInclusive;
            }

        }

        if (rarity > 85) {
            if (roll.nextBoolean()) {
                result = roll.nextInt(bottomLowInclusive) + 1;
            } else {
                result = roll.nextInt(boundOfRandomization - bottomTopInclusive) + bottomTopInclusive;
            }
        }
        /*do {
            result = (int) roll.nextGaussian() * (boundOfRandomization / 4) + (boundOfRandomization / 2);
        } while (result <= 0 || result > boundOfRandomization);*/
        if (result < 2) {                   // pity reroll
            result = roll.nextInt(5) + 1;
        }
        setValue(result);
    }

    public StatisticType getStatType() {
        return statType;
    }



    @Override
    public String toString() {
        return getStatType() + ": " + getValue();
    }

}
