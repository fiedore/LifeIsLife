package lifeislife.data.constants;

import java.util.EnumMap;
import java.util.Map;

public enum SchoolSubject {
    MATH(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12) {
        @Override
        public void setDependence() {
            statDependence.put(StatisticType.INT, 80);
        }
    },
    PE(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12) {
        @Override
        public void setDependence() {
            statDependence.put(StatisticType.INT, 15);
            statDependence.put(StatisticType.STR, 70);
        }
    },
    ENGLISH(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12) {
        @Override
        public void setDependence() {
            statDependence.put(StatisticType.INT, 60);
        }
    };

    protected Map<StatisticType, Integer> statDependence;
    private int[] applicableGradeYears;

    private SchoolSubject(int... applicableGradeYears) {
        this.applicableGradeYears = applicableGradeYears;
        statDependence = new EnumMap<>(StatisticType.class);
        setDependence();
    }

    public Map<StatisticType, Integer> getStatDependence() {
        return statDependence;
    }

    public int[] getApplicableGradeYears() {
        return applicableGradeYears;
    }

    public boolean isThisSubjectInThisYear(int year) {
        for (int i : applicableGradeYears) {
            if (year == i) {
                return true;
            }
        }
        return false;
    }

    public abstract void setDependence();

}
