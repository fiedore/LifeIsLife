
package lifeislife;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.*;

import lifeislife.data.Person;
import lifeislife.data.constants.FemaleName;
import lifeislife.data.constants.MaleName;
import lifeislife.data.constants.StatisticType;
import lifeislife.utils.Randomizer;


public class UserInterface {

    private MainDatabase database;
    private Scanner reader;
    private static final Logger logger = Logger.getLogger(UserInterface.class.getName());

    public UserInterface() {
        database = new MainDatabase();
        reader = new Scanner(System.in);
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("./mylogging.properties"));
        } catch (SecurityException | IOException e1) {
            logger.log(Level.SEVERE, "Exception: ", e1);
        }
    }

    public void run() {
        creator();
        printPlayerCharacters();
        database.generateSchools(2);
        database.generateAndFillClasses();
        printCalendar();
        database.advanceDay();
        printCalendar();
        if (!database.getPlayerCharacters().isEmpty()) {
            database.getPlayerCharacters().get(0).gainExperience(StatisticType.STR, 1000, true);
        }
        printPlayerCharacters();
//        database.printSchools();
        database.printClassesOfSchool(database.getSchools().get(0));
    }


    private void creator() {
        int members;
        int age;
        boolean isSexMale;
        Person newPerson;
        log("==========LIFEisLIFE SIMULATOR==========\n");
        log("========Let's create your family========\n\n");
        log("How many members: ");
        members = Integer.parseInt(reader.nextLine());
        for (int i = 1; i <= members; i++) {
            log("Person no. " + i + "\nSex(m/f invalid entry is f): ");
            isSexMale = reader.nextLine().charAt(0) == 'm';
            log("Name (type random to get a random name): ");
            String name = reader.nextLine();
            if (name.equalsIgnoreCase("random")) {
                name = isSexMale ? Randomizer.randomEnum(MaleName.class).getName() : Randomizer.randomEnum(FemaleName.class).getName();
            }
            log("Age: ");
            age = Integer.parseInt(reader.nextLine());
            newPerson = new Person(name, age, true);
            database.addPlayerCharacter(newPerson);
        }

    }

    public void printPlayerCharacters() {
        for (Person person : database.getPlayerCharacters()) {
            log(person.toString());
        }
    }

    private void log(String text) {
        logger.log(Level.INFO, text);
    }

    private void printCalendar(){
        log(database.getCalendar().toString());
    }


}
