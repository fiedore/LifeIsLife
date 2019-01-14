
package lifeislife.data.constants;


public enum Month {
JANUARY("Jan", 31),
FEBRUARY("Feb", 28),
MARCH("Mar", 31),
APRIL("Apr", 30),
MAY("May", 31),
JUNE("Jun", 30),
JULY("Jul", 31),
AUGUST("Aug", 31),
SEPTEMBER("Sep", 30),
OCTOBER("Oct", 31),
NOVEMBER("Nov", 30),
DECEMBER("Dec", 31);

private String shortName;
protected int days;

    private Month(String shortName, int days) {
        this.shortName = shortName;
        this.days = days;
    }

    public static void isItLeapYear(Boolean leap){
        if (leap){
            FEBRUARY.days=29;
        }
        else
        {
            FEBRUARY.days=28;
        }
    }

    public int getDays() {
        return days;
    }
    


}
