package lifeislife.data;

import lifeislife.data.constants.StatisticType;

import java.util.Map;

public interface Facility {
    public void advanceDay();
    public String getName();

    void reinitValues();
    Map<StatisticType, Integer> getBotStatValue();
    Map<StatisticType, Integer> getTopStatValue();
}
