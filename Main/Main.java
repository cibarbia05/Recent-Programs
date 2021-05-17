package Main;

import View.StartView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StartView sv = new StartView();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
