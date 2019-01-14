package lifeislife.graphicinterface;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lifeislife.MainDatabase;
import lifeislife.UserInterface;
import lifeislife.data.Facility;
import lifeislife.data.Person;
import lifeislife.data.Statistic;
import lifeislife.data.constants.StatisticType;
import lifeislife.data.school.ClassRegister;
import lifeislife.data.school.School;
import lifeislife.data.workplace.Workplace;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class Controller {
    @FXML
    private Text actiontarget;
    @FXML
    private GridPane grid;
    @FXML
    private TextField bgcolor;
    @FXML
    private Button schools;
    @FXML
    private Button workplaces;
    @FXML
    private Button generator;



    private UserInterface userInterface;
    private MainDatabase database;
    private ObservableList buttonListSchools;
    private ObservableList buttonListFacilities;
    private Stage popupStage;
    private Stage peopleListStage;
//    private FXMLLoader schoolsLoader;
//    private FXMLLoader workplacesLoader;

    void loadData(UserInterface userInterface) {
        this.userInterface = userInterface;
        database = userInterface.getDatabase();
        database.generateWorkplaces(5);
        database.generateSchools(5);
        database.generateEmployeesForAllWorkplaces();
        database.generateAndFillClasses();
        popupStage = new Stage();
        peopleListStage = new Stage();
        grid.setDisable(false);
//        schoolsLoader = new FXMLLoader();
//        schoolsLoader.setLocation(getClass().getResource("SchoolsWindowConfig.fxml"));
//        workplacesLoader = new FXMLLoader();
//        workplacesLoader.setLocation(getClass().getResource("WorkplacesWindowConfig.fxml"));
    }



    @FXML
    protected void handleSchoolsButton() {
        database.printSchools();
        popupStage.setTitle("Schools");
        List<Facility> schoolsList = new ArrayList<>(database.getSchools());
        displayFacilitiesList(schoolsList, "school");
    }

//    @FXML
//    protected void handleGenerateButton(ActionEvent event) {
//        database.generateWorkplaces(5);
//        database.generateSchools(5);
//        database.generateEmployeesForAllWorkplaces();
//        database.generateAndFillClasses();
//        generator.setDisable(true);
//    }

    /////////////////////////////////////////BUTTON HANDLERS/////////////////////////////////////////
    @FXML
    protected void handleWorkplacesButton(ActionEvent event) {
        popupStage.setTitle("Workplaces");
        List<Facility> workplacesList = new ArrayList<>(database.getWorkplaces());
        displayFacilitiesList(workplacesList, "workplace");
    }


    private void handleClassButton(ClassRegister classRegister) {
        peopleListStage.setTitle(classRegister.getName());
        System.out.println(classRegister.getDailyClasses());
        displayPeopleList(classRegister.getStudents(), classRegister);
    }

    @FXML
    private void handleSchoolButton(School school) {
        popupStage.setTitle("Classes");
        List<Facility> classesList = new ArrayList<>(school.getClasses());
        displayFacilitiesList(classesList, "class");
    }

    protected void handleWorkplaceButton(Workplace workplace) {
        peopleListStage.setTitle(workplace.getName());
        displayPeopleList(workplace.getEmployees(), workplace);

    }

    @FXML
    public void handleSaveAsXMLButtonAction(ActionEvent actionEvent) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MainDatabase.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(database, System.out);
            marshaller.marshal(database, new File("Database.xml"));

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        Random roll = new Random();
        int r = roll.nextInt(256);
        int g = roll.nextInt(256);
        int b = roll.nextInt(256);
        grid.setBackground(new Background(new BackgroundFill(Color.rgb(r, g, b), CornerRadii.EMPTY, Insets.EMPTY)));
        actiontarget.setText("RGB: " + r + "," + g + "," + b);
    }

    @FXML
    public void handleAdvanceDayButtonAction(ActionEvent actionEvent) {
        System.out.println(database.getCalendar().get(Calendar.DAY_OF_WEEK));
       database.advanceDay();
    }
    /////////////////////////////////////////END OF BUTTON HANDLERS/////////////////////////////////////////

    /////////////////////////////////////////DISPLAYERS/////////////////////////////////////////
    private void displayFacilitiesList(List<Facility> facilities, String facilityType) {
        Button b;
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        buttonListFacilities = vbox.getChildren();
        for (int i = 0; i < facilities.size(); i++) {
            Facility facility = facilities.get(i);
            System.out.println(facility);
            b = new Button(facility.getName());
//            b = new Button(facility.getName() + " (" + workplace.getEmployees().size() + " employee" +
//                    (workplace.getEmployees().size() != 1 ? "s)" : ")"));
            buttonListFacilities.add(b);
            VBox.setMargin(b, new Insets(10, 20, 10, 20));
            b.setMinWidth(400);
            b.setAlignment(Pos.CENTER);
            if (facilityType.equalsIgnoreCase("workplace")) {
                b.setOnAction(btnEvent -> handleWorkplaceButton((Workplace) facility));
            } else if (facilityType.equalsIgnoreCase("school")) {
                b.setOnAction(btnEvent -> handleSchoolButton((School) facility));
            } else if (facilityType.equalsIgnoreCase("class")) {
                b.setOnAction(btnEvent -> handleClassButton((ClassRegister) facility));
            }
        }
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("/lifeislife/graphicinterface/GUI.css");
        popupStage.setResizable(false);
        popupStage.setScene(scene);
        popupStage.show();
    }

    private void displayPeopleList(List<Person> peopleList, Facility sourcePlace) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(1);
        gridPane.setPadding(new Insets(1));
//        gridPane.setGridLinesVisible(true);
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setPadding(new Insets(15));
        sourcePlace.reinitValues();
        for (int i = 0; i < peopleList.size(); i++) {
            Text nameLine = new Text((i + 1) + ". " + peopleList.get(i).getName());
            gridPane.add(nameLine, 0, i, 1, 1);
            Text ageText = new Text("Age: " + peopleList.get(i).getAge());
            ageText.getStyleClass().add("text-age");
            gridPane.add(ageText, 1, i);
            for (int j = 0; j < peopleList.get(i).getStatisticList().size(); j++) {
                Statistic currentStat = peopleList.get(i).getStatisticList().get(j);
                StatisticType currentStatType = peopleList.get(i).getStatisticList().get(j).getStatType();
                Text statText = new Text(currentStat.toString());
                if (peopleList.get(i).getStatisticValue(currentStatType) == sourcePlace.getBotStatValue().get(currentStatType)) {
                    statText.getStyleClass().add("text-stat-bot");
                } else if (peopleList.get(i).getStatisticValue(currentStatType) == sourcePlace.getTopStatValue().get(currentStatType)) {
                    statText.getStyleClass().add("text-stat-top");
                } else {
                    statText.getStyleClass().add("text-stat");
                }

                Tooltip.install(statText, new Tooltip("EXP: " + currentStat.getExperience() + "/" + currentStat.getExperienceForNextLevel()));
                gridPane.add(statText, 2 + j, i);
            }
            nameLine.getStyleClass().add("text-nameline");
            gridPane.getStyleClass().add("grid-peoplelist");
        }
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        scrollPane.getStyleClass().add("spane-peoplelist");
        scrollPane.setMaxHeight(700);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        gridPane.minWidth(600);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Scene singleWorkplace = new Scene(scrollPane);
        singleWorkplace.getStylesheets().add("/lifeislife/graphicinterface/GUI.css");
        peopleListStage.setScene(singleWorkplace);
        peopleListStage.setResizable(false);
        peopleListStage.show();
    }


}
