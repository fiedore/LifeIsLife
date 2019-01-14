package lifeislife.utils;

import lifeislife.data.Person;
import lifeislife.data.constants.StatisticType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

public class Distributor {
    private static Random roll = new Random();
    private static BigDecimal low = BigDecimal.valueOf(0.2);
    private static BigDecimal average = BigDecimal.valueOf(0.4);
    private static BigDecimal high = BigDecimal.valueOf(0.6);
    private static BigDecimal top = BigDecimal.valueOf(0.8);


    public Distributor() {
    }

    /**
     * Works just like {@link Distributor#generateDistributedValue(int, int)} except the lower bound is always
     * 0.
     *
     * @see Distributor#generateDistributedValue(int, int)
     */
    public static int generateDistributedValue(int upperBoundOfRandomization) {
        return generateDistributedValue(0, upperBoundOfRandomization);
    }

    /**
     * Generates a random value for the statistic between
     * the range of lowerBoundOfRandomization - upperBoundOfRandomization parameter, where roughly:<br><br>
     * 50% of the results fall between <b>bottomAverage</b>(default 0.4) and <b>bottomHigh</b>(default 0.6)<br><br>
     * 35% of the results fal between <b>bottomLow</b>(default 0.2) and <b>bottomAverage</b>(default 0.4) or between <b>bottomHigh</b>(default 0.6) and <b>bottomTop</b>(default 0.8)<br><br>
     * 15% of the results fall between lowerBoundOfRandomization and <b>bottomLow</b>(default 0.2) or between <b>bottomTop</b>(default 0.8) and boundOfRandomization<br><br>
     * The distribution of scores loosely follows bell curve distribution, although it does not use perfect gaussian. (Which would result in unnaturally high amount of duplicate numbers. The variance is too low.)
     *
     * @param lowerBoundOfRandomization the min amount of stat value that can be randomly assigned
     * @param upperBoundOfRandomization the max amount of stat value that can be randomly assigned
     */
    public static int generateDistributedValue(int lowerBoundOfRandomization, int upperBoundOfRandomization) {
        int result = 0;
        int numbersRange = upperBoundOfRandomization - lowerBoundOfRandomization;
        int bottomLowInclusive = low.multiply(BigDecimal.valueOf((double) numbersRange)).setScale(0, RoundingMode.HALF_UP).intValue();
        int bottomAverageInclusive = average.multiply(BigDecimal.valueOf((double) numbersRange)).setScale(0, RoundingMode.HALF_UP).intValue();
        int bottomHighInclusive = high.multiply(BigDecimal.valueOf((double) numbersRange)).setScale(0, RoundingMode.HALF_UP).intValue();
        int bottomTopInclusive = top.multiply(BigDecimal.valueOf((double) numbersRange)).setScale(0, RoundingMode.HALF_UP).intValue();
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
                result = roll.nextInt(bottomLowInclusive) + lowerBoundOfRandomization;
            } else {
                result = roll.nextInt(numbersRange - bottomTopInclusive) + bottomTopInclusive;
            }
        }
        return result + lowerBoundOfRandomization;
    }

    public static Person findTopStatPersonInAList(List<Person> peopleList, StatisticType statisticType) {
        int topStatValue = 0;
        Person topStatPerson = null;
        for (Person person : peopleList) {
            if (person.getStatisticValue(statisticType) > topStatValue) {
                topStatValue = person.getStatisticValue(statisticType);
                topStatPerson = person;
            }
        }
        return topStatPerson;
    }

    public static Person findBotStatPersonInAList(List<Person> peopleList, StatisticType statisticType) {
        int botStatValue = 100;
        Person botStatPerson = null;
        for (Person person : peopleList) {
            if (person.getStatisticValue(statisticType) < botStatValue) {
                botStatValue = person.getStatisticValue(statisticType);
                botStatPerson = person;
            }
        }
        return botStatPerson;
    }
}