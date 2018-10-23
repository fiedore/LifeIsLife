package lifeislife;

import lifeislife.data.Person;

/**
 * @author fiedo
 */
public class Main {
    public static void main(String[] args) {
//        MainDatabase container = new MainDatabase();
//        container.generateSchools(4);
//        container.generateAndFillClasses();
//        container.printSchools();
//        container.printClassesOfSchool(container.getSchools().get(0));
        UserInterface ui = new UserInterface();
        ui.run();

    }

}
