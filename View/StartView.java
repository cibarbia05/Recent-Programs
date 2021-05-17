package View;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StartView {
    private AnchorPane pane;
    private Scene scene;
    private static Stage stage;
    private Button PVP;
    private Button PVAI;
    private Button skins;
    private ImageView title;
    private Label copyRight;
    private static ComboBox<String> difficulty;

    public static String getDifficulty() {
        return difficulty.getValue();
    }
    public static Stage getStage() {
        return stage;
    }


    public StartView() {
        initializePane();
        initializeStageAndScene();
        createGameTitle();
        initializeButtons();
        setButtonActions();
        initializeComboBox();
        stage.show();
    }
    /*Initializes the pane where all of the main view assets will be placed*/
    private void initializePane(){
        pane = new AnchorPane();
    }
    /*Initializes the Stage and Scene*/
    private void initializeStageAndScene(){
        scene = new Scene(pane, 600, 500);
        stage = new Stage();
        stage.setScene(scene);
        scene.getStylesheets().add("/View/Stylesheets/Stylesheet.css");
    }
    /*Adds the title of the game and a copyright to the pane*/
    private void createGameTitle(){
        title = new ImageView(new Image(getClass().getResourceAsStream("/View/Images/Title.png")));
        title.setFitHeight(230);
        title.setFitWidth(460);
        title.setLayoutX(80);
        title.setLayoutY(15);
        copyRight = new Label("© Christian Ibarbia");
        copyRight.setLayoutX(195);
        copyRight.setLayoutY(420);
        copyRight.setId("copy-label");
        pane.getChildren().addAll(title,copyRight);
    }
    /*Initializes and adds the player vs. player, player vs. ai, and skins buttons to the pane*/
    private void initializeButtons() {
        PVP = new Button("Player vs. Player");
        PVP.setPrefSize(130,35);
        PVP.setLayoutX(170);
        PVP.setLayoutY(300);
        PVAI = new Button("Player vs. AI");
        PVAI.setPrefSize(130,35);
        PVAI.setLayoutX(170);
        PVAI.setLayoutY(230);

        skins = new Button("Skins");
        skins.setPrefSize(130,35);
        skins.setLayoutX(170);
        skins.setLayoutY(370);

        pane.getChildren().addAll(PVP, PVAI,skins);
    }
    /*Initializes and adds a combobox for the level of difficulty against the ai*/
    private void initializeComboBox() {
        difficulty = new ComboBox<>();
        difficulty.setPromptText("Difficulty");
        difficulty.setPrefSize(130,35);
        ObservableList<String> list = difficulty.getItems();
        list.add("Easy");
        list.add("Medium");
        list.add("Hard");
        difficulty.setLayoutX(330);
        difficulty.setLayoutY(230);
        pane.getChildren().add(difficulty);
    }

    /*Sets the actions for all buttons*/
    private void setButtonActions() {
        PVP.setOnAction(event -> {
            GameGridViewPVP pvp = new GameGridViewPVP();
        });
        PVAI.setOnAction(event -> {
            if (difficulty.getValue() != null) {
                GameGridViewPVAI pvai = new GameGridViewPVAI();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE, "Select a level of difficulty");
                alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
                ButtonType alertButton = new ButtonType("Ok");
                alert.getButtonTypes().setAll(alertButton);
                alert.showAndWait();
            }
        });
        skins.setOnAction(event -> {
            SkinStore st = new SkinStore();
        });
    }

}