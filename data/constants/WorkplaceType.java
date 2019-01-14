package lifeislife.data.constants;

import java.util.EnumMap;
import java.util.Map;

public enum WorkplaceType {
    SOFTWARE_HOUSE("Software house", 85, 20, 400, false) {
        @Override
        protected void setAttributeCorrelation() {
            attributeCorrelation.put(StatisticType.INT, 90);
        }
    },
    LAWYERS_OFFICE("Lawyer's office", 50, 26, 30, true) {
        @Override
        protected void setAttributeCorrelation() {
            attributeCorrelation.put(StatisticType.INT, 40);
            attributeCorrelation.put(StatisticType.EMOINT, 70);
        }
    },
    GYM("Gym", 40, 18, 20, true) {
        @Override
        protected void setAttributeCorrelation() {
            attributeCorrelation.put(StatisticType.STR, 60);
            attributeCorrelation.put(StatisticType.EMOINT, 70);
        }
    };

    private String name;
    private int malePercentage;
    private int minAge;
    private int maxInitialEmployees;
    private boolean worksWeekends;

    protected Map<StatisticType, Integer> attributeCorrelation;

    WorkplaceType(String name, int malePercentage, int minAge, int maxInitialEmployees, boolean worksWeekends) {
        this.name = name;
        this.malePercentage = malePercentage;
        this.minAge = minAge;
        this.worksWeekends = worksWeekends;
        this.maxInitialEmployees = maxInitialEmployees;
        attributeCorrelation = new EnumMap<>(StatisticType.class);
        setAttributeCorrelation();
    }

    public int getMaxInitialEmployees() {
        return maxInitialEmployees;
    }

    public String getName() {
        return name;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMalePercentage() {
        return malePercentage;
    }

    public boolean isWorksWeekends() {
        return worksWeekends;
    }

    public Map<StatisticType, Integer> getAttributeCorrelation() {
        return attributeCorrelation;
    }

    protected abstract void setAttributeCorrelation();
}
