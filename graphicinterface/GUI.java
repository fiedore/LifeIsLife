package lifeislife.graphicinterface;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lifeislife.UserInterface;

public class GUI extends Application {
private UserInterface ui;
    @Override
    public void start(Stage primaryStage) throws Exception{
        ui = new UserInterface();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("GUIconfig.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.loadData(ui);
        primaryStage.setTitle("Life is Life The Game™");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }


    /*@Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TESTING");
        GridPane gridPane = new GridPane();

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setId("welcome-text");
//        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        Label username = new Label("User name:");
        gridPane.add(username, 0, 1);

        TextField userTextField = new TextField();
        gridPane.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        gridPane.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        gridPane.add(pwBox, 1, 2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        gridPane.add(hbBtn, 1, 4);
        final Text actionTarget = new Text();
        actionTarget.setId("actiontarget");
        gridPane.add(actionTarget, 1, 6);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("Sign in button pressed");
            }
        });
        Scene scene = new Scene(gridPane, 300, 275);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(GUI.class.getResource("GUI.css").toExternalForm());
        primaryStage.show();


    }*/

    public static void gui(String[] args) {
       launch(args);
    }
}