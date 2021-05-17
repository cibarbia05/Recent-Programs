package View;

import Model.Scores;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Optional;

public class SkinStore {
    private Stage stage;
    private Scene scene;
    private TabPane tabPane;
    private AnchorPane pane;
    private AnchorPane pane2;
    private boolean border1Added;
    private boolean border2Added;
    private static Circle[][] circleItems;
    private static Circle[][] circleItems2;
    private static Circle[][] circleItems3;
    private static Label[][] labels1;
    private static Label[][] labels2;
    private static Label[][] labels3;
    private int xCounter = 100;
    private int yCounter = 120;
    private int finalCounter;
    private Rectangle borderSelected;
    private Rectangle borderSelected2;
    private Rectangle borderSelected3;
    private boolean isACharacterSelected;
    private boolean isACharacterSelected2;
    private boolean isACharacterSelected3;
    private boolean isACharacterSelected4;
    private Button back1;
    private Button back2;
    private ToggleButton p1;
    private ToggleButton p2;
    private ToggleGroup tg;
    private Tab tab1;
    private Tab tab2;
    private Button lCoinspp1;
    private Button lCoinspp2;
    private Button lCoinspai1;
    private Label selectCurPlyL;
    private Button helpB;
    private Button helpB2;

    private static Color PVPp1Color;
    private static Color PVAIp1Color;
    private static Color PVPp2Color;

    private static ImagePattern PVPp1Pat;
    private static ImagePattern PVAIp1Pat;
    private static ImagePattern PVPp2Pat;

    public static Circle[][] getCircleItems() {
        return circleItems;
    }

    public static Color getPVPp1Color() {
        return PVPp1Color;
    }

    public static Color getPVAIp1Color() {
        return PVAIp1Color;
    }

    public static Color getPVPp2Color() {
        return PVPp2Color;
    }

    public static ImagePattern getPVPp1Pat() {
        return PVPp1Pat;
    }

    public static ImagePattern getPVAIp1Pat() {
        return PVAIp1Pat;
    }

    public static ImagePattern getPVPp2Pat() {
        return PVPp2Pat;
    }

    public static Circle[][] getCircleItems2() {
        return circleItems2;
    }

    public static Circle[][] getCircleItems3() {
        return circleItems3;
    }

    /*Constructor creates skin store once called*/
    public SkinStore(){
        pane = new AnchorPane();
        pane2 = new AnchorPane();
        createTabPane();
        scene = new Scene(tabPane,600,600);
        scene.getStylesheets().addAll("/View/Stylesheets/Stylesheet.css");
        stage = StartView.getStage();
        stage.setScene(scene);
        stage.setResizable(false);
        createScoreNeededLabels();
        createHelpButton();
        createHelpButton2();
        helpActions();
        helpActions2();
        createBackButtons();
        backButtonActions();
        buttonToggles1();
        toggleActions();
        selectCurPlayerLM();
        resetCounters();
        setCircleItems3(pane);
        removeFromPane();
        placeCircles3(pane);
        circleActions3(pane);
        labelLayouts3();
        dimCircles3();
        resetCounters();
        stage.show();
    }
    public SkinStore(int n){
        setCircleItems(pane);
        setCircleItems2(pane);
        setCircleItems3(pane);
    }
    /*Creates Labels that will show the score needed to unlock a specific skin*/
    private void createScoreNeededLabels(){
        labels1 = new Label[3][3];
        labels2 = new Label[3][3];
        labels3= new Label[3][3];
        for(int r =0;r<labels1.length;r++) {
            for (int c = 0; c < labels1[0].length; c++) {
                labels1[r][c] = new Label();
                labels2[r][c] = new Label();
                labels3[r][c] = new Label();
            }
        }
        int count = 1;
        for(int r=0;r<labels1.length;r++){
            for(int c=0;c<labels1[0].length;c++){
                labels1[r][c].setText(count +"");
                labels2[r][c].setText(count +"");
                labels3[r][c].setText(count +"");
                count += 2;
            }
        }

    }
    /*Sets the position of the scores needed labels for player1 in player vs. player*/
    private void labelLayouts1(){
        for(int r =0;r<labels1.length;r++) {
            for (int c = 0; c < labels1[0].length; c++) {
                if (r <= 1) {
                    labels1[r][c].setLayoutX(circleItems[r][c].getLayoutX() - 5);
                }
                else{
                    labels1[r][c].setLayoutX(circleItems[r][c].getLayoutX() - 10);
                }
                labels1[r][c].setLayoutY(circleItems[r][c].getLayoutY()-10);
                pane.getChildren().add(labels1[r][c]);
            }
        }
    }
    /*Sets the position of the scores needed labels for player2 in player vs. player*/
    private void labelLayouts2(){
        for(int r =0;r<labels2.length;r++) {
            for (int c = 0; c < labels2[0].length; c++) {
                if (r <= 1) {
                    labels2[r][c].setLayoutX(circleItems2[r][c].getLayoutX() - 5);
                }
                else{
                    labels2[r][c].setLayoutX(circleItems2[r][c].getLayoutX() - 10);
                }
                labels2[r][c].setLayoutY(circleItems2[r][c].getLayoutY() - 10);
                pane.getChildren().add(labels2[r][c]);
            }
        }
    }
    /*Sets the position of the scores needed labels for player1 in player vs. ai*/
    private void labelLayouts3(){
        for(int r =0;r<labels3.length;r++) {
            for (int c = 0; c < labels3[0].length; c++) {
                if (r <= 1) {
                    labels3[r][c].setLayoutX(circleItems3[r][c].getLayoutX() - 5);
                }
                else{
                    labels3[r][c].setLayoutX(circleItems3[r][c].getLayoutX() - 10);
                }
                labels3[r][c].setLayoutY(circleItems3[r][c].getLayoutY()-10);
                pane2.getChildren().add(labels3[r][c]);
            }
        }
    }
    /*Creates a tab pane to switch between the player vs. player skins and player vs. ai skins*/
    private void createTabPane(){
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tab1 = new Tab("Player vs, Player",pane);
        tab2 = new Tab("Player vs. AI",pane2);
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
    }
    /*Creates a guide label*/
    private void selectCurPlayerLM(){
        selectCurPlyL = new Label("Select the current Player");
        selectCurPlyL.setLayoutX(150);
        selectCurPlyL.setLayoutY(200);
        pane.getChildren().addAll(selectCurPlyL);
    }
    /*Sets the skins in the store for player 1 in player vs. player*/
    private void setCircleItems(AnchorPane pane){
        circleItems = new Circle[3][3];
        for(int r =0;r<circleItems.length;r++){
            for(int c=0;c<circleItems[0].length;c++){
                circleItems[r][c] = new Circle(38);
                circleItems[r][c].setId(Scores.getCircIDs1()[r][c]);
            }
        }
        circleItems[0][0].setFill(Color.GREEN);
        circleItems[0][1].setFill(Color.BEIGE);
        circleItems[0][2].setFill(Color.PINK);
        circleItems[1][0].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/flash.png"))));
        circleItems[1][1].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/iron.png"))));
        circleItems[1][2].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/super.png"))));
        circleItems[2][0].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/Spider.png"))));
        circleItems[2][1].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/CapShield.png"))));
        circleItems[2][2].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/Batman.png"))));
    }
    /*Sets the skins in the store for player 2 in player vs. player*/
    private void setCircleItems2(AnchorPane pane){
        circleItems2 = new Circle[3][3];
        for(int r =0;r<circleItems2.length;r++){
            for(int c=0;c<circleItems2[0].length;c++){
                circleItems2[r][c] = new Circle(38);
                circleItems2[r][c].setId(Scores.getCircIDs2()[r][c]);
            }
        }
        circleItems2[0][0].setFill(Color.GREEN);
        circleItems2[0][1].setFill(Color.BEIGE);
        circleItems2[0][2].setFill(Color.PINK);
        circleItems2[1][0].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/flash.png"))));
        circleItems2[1][1].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/iron.png"))));
        circleItems2[1][2].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/super.png"))));
        circleItems2[2][0].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/Spider.png"))));
        circleItems2[2][1].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/CapShield.png"))));
        circleItems2[2][2].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/Batman.png"))));
    }
    /*Sets the skins in the store for player 1 in player vs. ai*/
    private void setCircleItems3(AnchorPane pane){
        circleItems3 = new Circle[3][3];
        for(int r =0;r<circleItems3.length;r++){
            for(int c=0;c<circleItems3[0].length;c++){
                circleItems3[r][c] = new Circle(38);
                circleItems3[r][c].setId(Scores.getCircIDs3()[r][c]);
            }
        }
        circleItems3[0][0].setFill(Color.GREEN);
        circleItems3[0][1].setFill(Color.BEIGE);
        circleItems3[0][2].setFill(Color.PINK);
        circleItems3[1][0].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/flash.png"))));
        circleItems3[1][1].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/iron.png"))));
        circleItems3[1][2].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/super.png"))));
        circleItems3[2][0].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/Spider.png"))));
        circleItems3[2][1].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/CapShield.png"))));
        circleItems3[2][2].setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/View/Images/Batman.png"))));
    }
    /*Sets a dim effect on the skins that are not yet available for player 1 in player vs. player*/
    private void dimCircles1(){
        ColorAdjust selectGlow = new ColorAdjust();
        selectGlow.setBrightness(-0.7);
        for(int r =0;r<circleItems.length;r++){
            for(int c=0;c<circleItems[0].length;c++) {
                if(Scores.getCircIDs1()[r][c].equals("not-owned")) {
                    circleItems[r][c].setEffect(selectGlow);
                    circleItems[r][c].setDisable(true);
                }

            }
        }
    }
    /*Sets a dim effect on the skins that are not yet available for player 2 in player vs. player*/
    private void dimCircles2(){
        ColorAdjust selectGlow = new ColorAdjust();
        selectGlow.setBrightness(-0.7);
        for(int r =0;r<circleItems2.length;r++){
            for(int c=0;c<circleItems2[0].length;c++) {
                if(Scores.getCircIDs2()[r][c].equals("not-owned")) {
                    circleItems2[r][c].setEffect(selectGlow);
                    circleItems2[r][c].setDisable(true);
                }

            }
        }
    }
    /*Sets a dim effect on the skins that are not yet available for player 1 in player vs. ai*/
    private void dimCircles3(){
        ColorAdjust selectGlow = new ColorAdjust();
        selectGlow.setBrightness(-0.7);
        for(int r =0;r<circleItems3.length;r++){
            for(int c=0;c<circleItems3[0].length;c++) {
                if(Scores.getCircIDs3()[r][c].equals("not-owned")) {
                    circleItems3[r][c].setEffect(selectGlow);
                    circleItems3[r][c].setDisable(true);
                }

            }
        }
    }
    /*Places all skins in the corresponding pane for player 1 in player vs. player*/
    private void placeCircles(AnchorPane pane){
        for(int r =0;r<circleItems.length;r++){
            for(int c=0;c<circleItems[0].length;c++) {
                if(finalCounter < circleItems[0].length) {

                    circleItems[r][c].setLayoutX(xCounter);
                    circleItems[r][c].setLayoutY(yCounter);
                    xCounter+=150;
                    finalCounter++;
                }
                else if(finalCounter >= circleItems[0].length){
                    finalCounter = 0;
                    xCounter=100;
                    yCounter+=150;
                    circleItems[r][c].setLayoutX(xCounter);
                    circleItems[r][c].setLayoutY(yCounter);

                    xCounter+=150;
                    finalCounter++;
                }
                pane.getChildren().addAll(circleItems[r][c]);
            }
        }
    }
    /*Places all skins in the corresponding pane for player 2 in player vs. player*/
    private void placeCircles2(AnchorPane pane){
        for(int r =0;r<circleItems2.length;r++){
            for(int c=0;c<circleItems2[0].length;c++) {
                if(finalCounter < circleItems2[0].length) {

                    circleItems2[r][c].setLayoutX(xCounter);
                    circleItems2[r][c].setLayoutY(yCounter);
                    xCounter+=150;
                    finalCounter++;
                }
                else if(finalCounter >= circleItems2[0].length){
                    finalCounter = 0;
                    xCounter=100;
                    yCounter+=150;
                    circleItems2[r][c].setLayoutX(xCounter);
                    circleItems2[r][c].setLayoutY(yCounter);

                    xCounter+=150;
                    finalCounter++;
                }
                pane.getChildren().addAll(circleItems2[r][c]);
            }
        }
    }
    /*Places all skins in the corresponding pane for player 1 in player vs. ai*/
    private void placeCircles3(AnchorPane pane){
        for(int r =0;r<circleItems3.length;r++){
            for(int c=0;c<circleItems3[0].length;c++) {
                if(finalCounter < circleItems3[0].length) {

                    circleItems3[r][c].setLayoutX(xCounter);
                    circleItems3[r][c].setLayoutY(yCounter);
                    xCounter+=150;
                    finalCounter++;
                }
                else if(finalCounter >= circleItems3[0].length){
                    finalCounter = 0;
                    xCounter=100;
                    yCounter+=150;
                    circleItems3[r][c].setLayoutX(xCounter);
                    circleItems3[r][c].setLayoutY(yCounter);

                    xCounter+=150;
                    finalCounter++;
                }
                pane2.getChildren().addAll(circleItems3[r][c]);
            }
        }
    }
    /*Sets actions on mouse click on a skin for player 1 in player vs. player*/
    private void circleActions(AnchorPane pane){
        for(int r =0;r<circleItems.length;r++){
            for(int c=0;c<circleItems[0].length;c++) {
                int finalC = c;
                int finalR = r;
                circleItems[r][c].setOnMouseClicked(event -> {
                    if(tabPane.getSelectionModel().getSelectedItem()==tab1) {
                        if(tg.getSelectedToggle()==null){
                            Alert alert = new Alert(Alert.AlertType.NONE, "Select the current player");
                            alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
                            ButtonType alertButton = new ButtonType("Ok");
                            alert.getButtonTypes().setAll(alertButton);
                            alert.showAndWait();
                        }
                        else if(tg.getSelectedToggle()==p1) {
                            if (isACharacterSelected) {
                                pane.getChildren().removeAll(borderSelected);
                                border1Added = false;
                                isACharacterSelected = false;
                            }
                            isACharacterSelected = true;
                            if(finalR <1) {
                                PVPp1Color = (Color) circleItems[finalR][finalC].getFill();
                                PVPp1Pat=null;
                            }
                            else{
                                PVPp1Pat = (ImagePattern) circleItems[finalR][finalC].getFill();
                                PVPp1Color=null;
                            }
                            borderSelected = new Rectangle(circleItems[finalR][finalC].getLayoutX() - circleItems[finalR][finalC].getRadius(), circleItems[finalR][finalC].getLayoutY() - circleItems[finalR][finalC].getRadius(), circleItems[finalR][finalC].getRadius() * 2, circleItems[finalR][finalC].getRadius() * 2);
                            borderSelected.setFill(Color.TRANSPARENT);
                            borderSelected.setStroke(Color.DEEPSKYBLUE);
                            border1Added = true;
                            pane.getChildren().addAll(borderSelected);
                        }
                    }
                });
            }
        }
    }
    /*Sets actions on mouse click on a skin for player 2 in player vs. player*/
    private void circleActions2(AnchorPane pane){
        for(int r =0;r<circleItems2.length;r++){
            for(int c=0;c<circleItems2[0].length;c++) {
                int finalC = c;
                int finalR = r;
                circleItems2[r][c].setOnMouseClicked(event -> {
                    if(tabPane.getSelectionModel().getSelectedItem()==tab1) {
                        if(tg.getSelectedToggle()==null){
                            Alert alert = new Alert(Alert.AlertType.NONE, "Select the current player");
                            alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
                            ButtonType alertButton = new ButtonType("Ok");
                            alert.getButtonTypes().setAll(alertButton);
                            alert.showAndWait();
                        }
                        else if(tg.getSelectedToggle()==p2){
                            if (isACharacterSelected3) {
                                pane.getChildren().removeAll(borderSelected2);
                                border2Added = false;
                                isACharacterSelected3 = false;
                            }
                            isACharacterSelected3 = true;
                            if(finalR <1) {
                                PVPp2Color = (Color) circleItems2[finalR][finalC].getFill();
                                PVPp2Pat=null;
                            }
                            else{
                                PVPp2Pat = (ImagePattern) circleItems2[finalR][finalC].getFill();
                                PVPp2Color = null;
                            }
                            borderSelected2 = new Rectangle(circleItems2[finalR][finalC].getLayoutX() - circleItems2[finalR][finalC].getRadius(), circleItems2[finalR][finalC].getLayoutY() - circleItems2[finalR][finalC].getRadius(), circleItems2[finalR][finalC].getRadius() * 2, circleItems2[finalR][finalC].getRadius() * 2);
                            borderSelected2.setFill(Color.TRANSPARENT);
                            borderSelected2.setStroke(Color.RED);
                            border2Added = true;
                            pane.getChildren().addAll(borderSelected2);
                        }
                    }
                });
            }
        }
    }
    /*Sets actions on mouse click on a skin for player 1 in player vs. ai*/
    private void circleActions3(AnchorPane pane){
        for(int r =0;r<circleItems3.length;r++) {
            for (int c = 0; c < circleItems3[0].length; c++) {
                int finalC = c;
                int finalR = r;
                circleItems3[r][c].setOnMouseClicked(event -> {
                    if (tabPane.getSelectionModel().getSelectedItem() == tab2) {
                        if (isACharacterSelected2) {
                            pane2.getChildren().removeAll(borderSelected3);
                            isACharacterSelected2 = false;
                        }
                        isACharacterSelected2 = true;
                        if(finalR <1) {
                            PVAIp1Color = (Color) circleItems3[finalR][finalC].getFill();
                            PVAIp1Pat = null;
                        }
                        else{
                            PVAIp1Pat = (ImagePattern) circleItems3[finalR][finalC].getFill();
                            PVAIp1Color = null;
                        }
                        borderSelected3 = new Rectangle(circleItems3[finalR][finalC].getLayoutX() - circleItems3[finalR][finalC].getRadius(), circleItems3[finalR][finalC].getLayoutY() - circleItems3[finalR][finalC].getRadius(), circleItems3[finalR][finalC].getRadius() * 2, circleItems3[finalR][finalC].getRadius() * 2);
                        borderSelected3.setFill(Color.TRANSPARENT);
                        borderSelected3.setStroke(Color.DEEPSKYBLUE);
                        pane2.getChildren().addAll(borderSelected3);
                    }
                });
            }
        }
    }
    /*Resets the positions of the skins counters*/
    private void resetCounters(){
        xCounter = 100;
        yCounter = 120;
        finalCounter = 0;
    }
    /*Creates buttons to go back to menu*/
    private void createBackButtons(){
        back1 = new Button("Back");
        back1.setLayoutX(20);
        back1.setLayoutY(500);
        back2 = new Button("Back");
        back2.setLayoutX(20);
        back2.setLayoutY(500);
        pane.getChildren().addAll(back1);
        pane2.getChildren().addAll(back2);
    }
    /*Sets back button actions*/
    private void backButtonActions(){
        back1.setOnAction(event -> {
            stage.close();
            StartView sv = new StartView();
        });
        back2.setOnAction(event -> {
            stage.close();
            StartView sv = new StartView();
        });
    }
    /*Creates toggle buttons to switch between player 1 and player 2 on the skins store*/
    private void buttonToggles1(){
        p1 = new ToggleButton("Player 1");
        p1.setLayoutX(470);
        p1.setLayoutY(200);
        p2 = new ToggleButton("Player 2");
        p2.setLayoutX(470);
        p2.setLayoutY(250);
        tg = new ToggleGroup();
        p1.setToggleGroup(tg);
        p2.setToggleGroup(tg);
        pane.getChildren().addAll(p1,p2);
    }
    /*Sets the toggle actions to switch between the skins store of player 1 and 2*/
    private void toggleActions(){
        p1.setOnAction(event -> {
            if(tg.getSelectedToggle()!=null) {
                if (border1Added) {
                    pane.getChildren().addAll(borderSelected);
                }
                resetCounters();
                setCircleItems(pane);
                removeFromPane();
                placeCircles(pane);
                circleActions(pane);
                dimCircles1();
                resetCounters();
                labelLayouts1();
                if (border2Added) {
                    pane.getChildren().removeAll(borderSelected2);
                }
            }
        });
        p2.setOnAction(event -> {
            if(tg.getSelectedToggle()!=null) {
                if (border2Added) {
                    pane.getChildren().addAll(borderSelected2);
                }
                resetCounters();
                setCircleItems2(pane);
                removeFromPane();
                placeCircles2(pane);
                circleActions2(pane);
                dimCircles2();
                labelLayouts2();
                resetCounters();
                if (border1Added) {
                    pane.getChildren().removeAll(borderSelected);
                }
            }
        });

    }
    /*Removes the previous pane when a toggle button is selected*/
    private void removeFromPane(){
        if(tg.getSelectedToggle() == p2) {
            if(selectCurPlyL.getText()!="!") {
                pane.getChildren().removeAll(selectCurPlyL);
                selectCurPlyL.setText("!");
            }
            if(circleItems!=null) {
                for (int r = 0; r < circleItems.length; r++) {
                    for (int c = 0; c < circleItems[0].length; c++) {
                        pane.getChildren().removeAll(circleItems[r][c]);
                        pane.getChildren().removeAll(labels1[r][c]);
                    }
                }
            }
        }
        else if(tg.getSelectedToggle() == p1){
            if(selectCurPlyL.getText()!="!") {
                pane.getChildren().removeAll(selectCurPlyL);
                selectCurPlyL.setText("!");
            }
            if(circleItems2!=null) {
                for (int r = 0; r < circleItems2.length; r++) {
                    for (int c = 0; c < circleItems2[0].length; c++) {
                        pane.getChildren().removeAll(labels2[r][c]);
                    }
                }
            }
        }

    }
    /*Creates help button for the player vs. player skins store pane*/
    private void createHelpButton(){
        helpB = new Button("?");
        helpB.setId("help-button");
        helpB.setShape(new Circle(20));
        helpB.setLayoutX(500);
        helpB.setLayoutY(500);
        pane.getChildren().addAll(helpB);
    }
    /*Sets help button actions for the player vs. player skins store pane*/
    private void helpActions(){
        helpB.setOnAction(event -> {
            try {
                Alert alert = new Alert(Alert.AlertType.NONE, "As you play and win, both of you will start unlocking skins that you can use");
                alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
                ButtonType alertButton = new ButtonType("Next");
                alert.getButtonTypes().setAll(alertButton);
                Optional<ButtonType> result= alert.showAndWait();
                try {
                    if (result.get() == alertButton) {
                        Alert alert2 = new Alert(Alert.AlertType.NONE, "The number in front of the skin is the number of wins needed to unlock it");
                        alert2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/View/Images/UnlockHelp.JPG"))));
                        alert2.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
                        ButtonType alertButton2 = new ButtonType("Ok");
                        alert2.getButtonTypes().setAll(alertButton2);
                        alert2.showAndWait();
                    }
                }
                catch(Exception e){
                }
            }
            catch (Exception e){
            }
        });

    }
    /*Creates help button for the player vs. ai skins store pane*/
    private void createHelpButton2(){
        helpB2 = new Button("?");
        helpB2.setId("help-button");
        helpB2.setShape(new Circle(20));
        helpB2.setLayoutX(500);
        helpB2.setLayoutY(500);
        pane2.getChildren().addAll(helpB2);
    }
    /*Creates help button for the player vs. ai skins store pane*/
    private void helpActions2(){
        helpB2.setOnAction(event -> {
            try {
                Alert alert = new Alert(Alert.AlertType.NONE, "As you play and win, you will start unlocking skins that you can use");
                alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
                ButtonType alertButton = new ButtonType("Next");
                alert.getButtonTypes().setAll(alertButton);
                Optional<ButtonType> result= alert.showAndWait();
                try {
                    if (result.get() == alertButton) {
                        Alert alert2 = new Alert(Alert.AlertType.NONE, "The number in front of the skin is the number of wins needed to unlock it");
                        alert2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/View/Images/UnlockHelp.JPG"))));
                        alert2.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
                        ButtonType alertButton2 = new ButtonType("Ok");
                        alert2.getButtonTypes().setAll(alertButton2);
                        alert2.showAndWait();
                    }
                }
                catch(Exception e){
                }
            }
            catch (Exception e){
            }
        });

    }

}
