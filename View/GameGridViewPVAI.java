package View;

import Model.GameBoard;
import Model.Scores;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/*Template for a player vs. ai game*/
public class GameGridViewPVAI {
    private Timeline timeline;
    private GameBoard pos;
    private GridPane pane;
    private AnchorPane ancPane;
    private GridPane midPane;
    private StackPane stackPane;
    private Scene scene;
    private Stage stage;
    private Circle[][] circles;
    private Circle user1C;
    private Circle user2C;
    private static final int NUMCOLS = 7;
    private static final int NUMROWS = 6;
    private int clickCount;
    private static final int RADIUS = 38;
    ColumnConstraints colConst;
    RowConstraints rowConst;
    private Label player1ScoreL;
    private Label playerAIScoreL;
    private Label curRoundL;
    private int player1Score;
    private int playerAIScore;
    private boolean winnerDisplayed;
    private double colConstI;
    private double rowConstI;
    private Stage afterGameStage;
    private Scene afterGameScene;
    private AnchorPane afterGamePane;
    private Button afterGoBack;
    private Button afterGameReplay;
    private Label afterWinnerL;
    private Polygon pointerTriangle;
    private SkinStore sn;

    /*Creates player vs. ai game */
    public GameGridViewPVAI() {
        sn = new SkinStore(0);
        Scores.setCurrentRoundPVAI(Scores.getCurrentRoundPVAI()+1);
        pos = new GameBoard(0, 0);
        ancPane = new AnchorPane();
        pane = new GridPane();
        setGridToAnc();
        pane.setPadding(new Insets(0, 5, 5, 5));
        scene = new Scene(ancPane, 730, 770);
        scene.getRoot().setId("scene");
        scene.getStylesheets().add("/View/Stylesheets/Stylesheet.css");
        scene.getRoot().setEffect(new DropShadow());
        stage = StartView.getStage();
        stage.setScene(scene);
        stage.centerOnScreen();
        createScoreLabels();
        setPaneConstraints();
        initializeCirclesArr();
        setPaneBackCircles();
        createUserCircles();
        addCircleToCurClick();
        initializePointerTriangle();
        pathTransition();
        stage.show();
    }
    /*Adds the grid of the background circles to main pane
     *where the player circles will be placed on top
     */
    private void setGridToAnc(){
        pane.setLayoutX(20);
        pane.setLayoutY(110);
        ancPane.getChildren().add(pane);
    }
    /*Creates the score labels for both player1 and the ai and one for the current round*/
    private void createScoreLabels() {
        player1Score = Scores.getPlayer1paiScore();
        player1ScoreL = new Label("Player 1: " + player1Score);
        player1ScoreL.setId("score-label");
        player1ScoreL.setLayoutX(265);
        player1ScoreL.setLayoutY(30);
        playerAIScore = Scores.getPlayerAIScore();
        playerAIScoreL = new Label("AI :" + playerAIScore);
        playerAIScoreL.setId("score-label");
        playerAIScoreL.setLayoutX(395);
        playerAIScoreL.setLayoutY(30);
        curRoundL = new Label("Round " + Scores.getCurrentRoundPVAI());
        curRoundL.setLayoutX(530);
        curRoundL.setLayoutY(30);
        ancPane.getChildren().addAll(player1ScoreL, playerAIScoreL,curRoundL);
    }
    /*Creates a Pointer Triangle that will ove with the mouse for better style*/
    private void initializePointerTriangle(){
        pointerTriangle = new Polygon();
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.GRAY);
        pointerTriangle.setFill(Color.web("#cfcdcd"));
        pointerTriangle.setEffect(ds);
        pointerTriangle.getPoints().addAll(new Double[]{
                20.0, 80.0,
                70.0, 80.0,
                45.0, 105.0 });
        ancPane.getChildren().addAll(pointerTriangle);
    }
    /*Changes the position of the Pointer Triangle to the current position of the mouse*/
    private void pathTransition(){
        scene.setOnMouseMoved(event -> {
            if(event.getSceneX()<=665 && event.getSceneX()>60) {
                pointerTriangle.getPoints().setAll(event.getSceneX()-25, 80.0,
                        event.getSceneX()+25, 80.0,
                        event.getSceneX(), 105.0);
            }
        });
    }
    /*Checks if a player has reached a certain score, and if they have it they earn a new item in the store*/
    private void giveRewards() {
        if (Scores.getPlayer1paiScore() == 1) {
            Scores.getCircIDs3()[0][0] = "owned";
        }
        if (Scores.getPlayer1paiScore() == 3) {
            Scores.getCircIDs3()[0][1] = "owned";
        }
        if (Scores.getPlayer1paiScore() == 5) {
            Scores.getCircIDs3()[0][2] = "owned";
        }
        if (Scores.getPlayer1paiScore() == 7) {
            Scores.getCircIDs3()[1][0] = "owned";
        }
        if (Scores.getPlayer1paiScore() == 8) {
            Scores.getCircIDs3()[1][1] = "owned";
        }
        if (Scores.getPlayer1paiScore() == 9) {
            Scores.getCircIDs3()[1][2] = "owned";
        }
        if (Scores.getPlayer1paiScore() == 12) {
            Scores.getCircIDs3()[2][0] = "owned";
        }
        if (Scores.getPlayer1paiScore() == 14) {
            Scores.getCircIDs3()[2][1] = "owned";
        }
        if (Scores.getPlayer1paiScore() == 14) {
            Scores.getCircIDs3()[2][2] = "owned";
        }
    }
    /*Creates the constraints of the circles that will represent the background of the game,
     * where the red and yellow circles will be place on a mouse click
     */
    private void setPaneConstraints() {
        for (int i = 0; i < NUMCOLS; i++) {
            colConst = new ColumnConstraints();
            colConstI =  100.0 / NUMCOLS;
            colConst.setPercentWidth(100.0 / NUMCOLS);
            pane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < NUMROWS; i++) {
            rowConst = new RowConstraints();
            colConstI =  100.0 / (NUMROWS+20);
            rowConst.setPercentHeight(100.0 / (NUMROWS+20));
            pane.getRowConstraints().add(rowConst);
        }
    }
    /*Creates the styling of the circles that will represent the background of the game,
     * where the red and yellow circles will be place on a mouse click
     */
    private void initializeCirclesArr() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.GRAY);
        circles = new Circle[NUMROWS][NUMCOLS];
        for (int r = 0; r < circles.length; r++) {
            for (int c = 0; c < circles[0].length; c++) {
                circles[r][c] = new Circle(RADIUS);
                circles[r][c].setFill(Color.web("#21487c"));
                circles[r][c].setEffect(ds);

            }
        }
    }
    /*Creates the positions of the circles that will represent the background of the game,
     * where the red and yellow circles will be place on a mouse click
     */
    private void setPaneBackCircles() {
        for (int r = 0; r < NUMROWS; r++) {
            for (int c = 0; c < NUMCOLS; c++) {
                Rectangle r2 = new Rectangle(100,110);
                r2.setFill(Color.TRANSPARENT);
                pane.add(r2, c, r, 1, 1);
                pane.add(circles[r][c], c, r, 1, 1);
            }
        }
    }
    /*Initializes and styles he circles that corresponds to each user that will be placed on mouse click.
     *Red for one player and yellow for the other
     */
    private void createUserCircles() {
        InnerShadow ds = new InnerShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.web("#626262"));
        InnerShadow ds2 = new InnerShadow();
        ds2.setOffsetY(3.0);
        ds2.setOffsetX(3.0);
        ds2.setColor(Color.web("#626262"));
        user1C = new Circle(RADIUS);
        if (SkinStore.getPVAIp1Color() == null && SkinStore.getPVAIp1Pat() == null) {
            user1C.setFill(Color.web("#d02525"));
        } else if (SkinStore.getPVAIp1Color() == null) {
            user1C.setFill(SkinStore.getPVAIp1Pat());
        } else if (SkinStore.getPVAIp1Pat() == null) {
            user1C.setFill(SkinStore.getPVAIp1Color());
        }
        user1C.setEffect(ds);
        user2C = new Circle(RADIUS);
        user2C.setFill(Color.web("#ffc903"));
        user2C.setEffect(ds2);
    }
    /*Sets the actions of a mouse click so that the circles belong to the corresponding
     *player and the board updates properly
     */
    private void addCircleToCurClick() {
        pane.setOnMouseClicked(event -> {
            if (pos.winnerCheck().equals("NONE")) {
                if (clickCount == 0) {
                    Node source = (Node) event.getTarget();
                    Integer colIndex = GridPane.getColumnIndex(source);
                    if(colIndex!=null) {
                        setToNearestOpenPos(colIndex);
                        checkAndDisplayWinner();
                        if(!winnerDisplayed) {
                            computerTurn();
                        }
                        if(pos.checkForTie()){
                            createAfterGameStage("Tie");
                        }
                    }

                }
            }
        });
    }
    /*After player1 has played, this method is called for the computer to play*/
    private void computerTurn(){
        if(clickCount==1) {
            timeline = new Timeline();
            timeline.setCycleCount(1);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(0.5),
                            (EventHandler) event -> {
                                setToNearestOpenPosAI();
                            }));
            timeline.play();
        }
    }

    /*Sets a circle to nearest available position on the click on a column on the turn of player1*/
    private void setToNearestOpenPos(int col) {
        if (clickCount == 0) {
            int curUnfilled = pos.nearestUnfilledRow(col,  1);
            if (curUnfilled != -999) {
                clickCount = 1;
                Circle user1C = new Circle(RADIUS);
                if(SkinStore.getPVAIp1Color()==null && SkinStore.getPVAIp1Pat()==null) {
                    user1C.setFill(Color.web("#d02525"));
                }
                else if(SkinStore.getPVAIp1Color()==null){
                    user1C.setFill(SkinStore.getPVAIp1Pat());
                }
                else if(SkinStore.getPVAIp1Pat()==null){
                    user1C.setFill(SkinStore.getPVAIp1Color());
                }
                InnerShadow ds = new InnerShadow();
                ds.setOffsetY(3.0);
                ds.setOffsetX(3.0);
                ds.setColor(Color.web("#626262"));
                user1C.setEffect(ds);
                pane.add(user1C, col, curUnfilled);
            }
        }
    }
    /*Sets a circle to nearest available position on the click on a column on the turn of the ai*/
    private void setToNearestOpenPosAI() {
        int chosenCol =  setAILevel();
        if (clickCount != 0) {
            int curUnfilled = pos.nearestUnfilledRow(chosenCol, 2);
            if (curUnfilled != -999) {
                clickCount = 0;
                Circle user2C = new Circle(RADIUS);
                user2C.setFill(Color.web("#ffc903"));
                InnerShadow ds = new InnerShadow();
                ds.setOffsetY(3.0);
                ds.setOffsetX(3.0);
                ds.setColor(Color.web("#626262"));
                user2C.setEffect(ds);
                pane.add(user2C, chosenCol, curUnfilled);
                checkAndDisplayWinner();
            }
        }
    }
    /*Sets the level of difficulty of the ai to the one chosen in the Main menu*/
    private int setAILevel(){
        if(StartView.getDifficulty().equals("Easy")){
            for(int c=0;c<pos.getBoard()[0].length;c++){
                pos.checkColToRemove(c);
            }
            int rnd = (int)(Math.random()*pos.getRndList().size());
            return pos.getRndList().get(rnd);
        }
        else if(StartView.getDifficulty().equals("Medium")){
            return pos.mediumAIChosenCol();
        }
        else if(StartView.getDifficulty().equals("Hard")){
            return pos.hardAIChosenCol();
        }
        return 0;

    }
    /*Checks if there is a winner and if so the game ends,
     *the rewards are given and the scores are updated
     */
    private void checkAndDisplayWinner() {
        if(!winnerDisplayed){
            String winner = pos.winnerCheck();
            if (winner.equals("Player 1") || winner.equals("Player 2")) {
                winnerDisplayed = true;
                if (winner.equals("Player 1")) {
                    Scores.setPlayer1paiScore(Scores.getPlayer1paiScore() + 1);
                    player1ScoreL.setText("Player 1: " + Scores.getPlayer1paiScore());
                } else if (winner.equals("Player 2")) {
                    Scores.setPlayerAIScore(Scores.getPlayerAIScore() + 1);
                    playerAIScoreL.setText("AI: " + Scores.getPlayerAIScore());
                }
                ImageView winIV = new ImageView(new Image(getClass().getResourceAsStream("/View/Images/Crown.png")));
                winIV.setFitWidth(55);
                winIV.setFitHeight(45);
                if (winner.equals("Player 1")) {
                    winIV.setLayoutX(285);
                    winIV.setLayoutY(60);
                    ancPane.getChildren().add(winIV);
                    ancPane.getChildren().removeAll(pointerTriangle);
                } else if (winner.equals("Player 2")) {
                    winIV.setLayoutX(385);
                    winIV.setLayoutY(60);
                    ancPane.getChildren().add(winIV);
                    ancPane.getChildren().removeAll(pointerTriangle);
                }
                createAfterGameStage(winner);
            }
        }
    }
    /*Creates a stage after the gam is over where the rewsrd and the winner wil be shown*/
    private void createAfterGameStage(String winnerLabel){
        afterGamePane = new AnchorPane();
        afterGameScene = new Scene(afterGamePane,375,75);
        afterGameScene.getStylesheets().addAll("/View/Stylesheets/Stylesheet.css");
        afterGameStage = new Stage();
        afterGameStage.initStyle(StageStyle.DECORATED);
        afterGameStage.setResizable(false);
        afterGameStage.initModality(Modality.APPLICATION_MODAL);
        afterGameStage.setOnCloseRequest(event -> event.consume());
        afterGameStage.setScene(afterGameScene);
        setAfterButtons();
        afterButtonActions();
        incrementCoins(winnerLabel);
        giveRewards();
        createRewardAlert();
        if(winnerLabel.equals("Player 1")) {
            setWinnerLabel(winnerLabel + " wins!");
        }
        else if(winnerLabel.indexOf("Player 2")!=-1) {
            setWinnerLabel("The AI wins!");
        }
        else if(winnerLabel.equals("Tie")){
            setWinnerLabel("Tie");
        }
        afterGameStage.show();

    }
    /*Adds the winner to the stage after the game is over*/
    private void setWinnerLabel(String winnerLabel){
        afterWinnerL = new Label(winnerLabel);
        afterWinnerL.setLayoutX(30);
        afterWinnerL.setLayoutY(20);
        afterWinnerL.setId("afterStage-label");
        afterGamePane.getChildren().addAll(afterWinnerL);
    }
    /*Adds the play again and go back buttons to the stage after the game is over*/
    private void setAfterButtons(){
        afterGoBack = new Button("Go back");
        afterGoBack.setLayoutX(200);
        afterGoBack.setLayoutY(45);
        afterGameReplay = new Button("Play Again");
        afterGameReplay.setLayoutX(285);
        afterGameReplay.setLayoutY(45);
        afterGamePane.getChildren().addAll(afterGoBack,afterGameReplay);
    }
    /*Sets the actions for the play again and go back buttons*/
    private void afterButtonActions(){
        afterGoBack.setOnAction(event -> {
            stage.close();
            afterGameStage.close();
            StartView sv = new StartView();
        });
        afterGameReplay.setOnAction(event -> {
            stage.close();
            afterGameStage.close();
            GameGridViewPVAI pvai = new GameGridViewPVAI();

        });
    }
    /*Increments the score of the winner*/
    private void incrementCoins(String winner){
        if(winner.equals("Player 1")) {
            Scores.setPai1Coins(Scores.getPai1Coins()+5);
        }
    }
    /*Creates a reward alert if a specified score for a skin is reached for player 1*/
    private void createRewardAlert(){
        int count = 0;
        Alert alert = new Alert(Alert.AlertType.NONE, "Player 1 has unlocked a new skin");
        alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
        ButtonType alertButton = new ButtonType("Ok");
        alert.getButtonTypes().setAll(alertButton);

        if(Scores.getPlayer1paiScore()==1 && !Scores.getRewardedArr3()[0]){
            Scores.getRewardedArr3()[0] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems3()[0][0]);
        }
        else if(Scores.getPlayer1paiScore()==3 && !Scores.getRewardedArr3()[1]){
            Scores.getRewardedArr3()[1] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems3()[0][1]);
        }
        else if(Scores.getPlayer1paiScore()==5 && !Scores.getRewardedArr3()[2]){
            Scores.getRewardedArr3()[2] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems3()[0][2]);
        }
        else if(Scores.getPlayer1paiScore()==7 && !Scores.getRewardedArr3()[3]){
            Scores.getRewardedArr3()[3] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems3()[1][0]);
        }
        else if(Scores.getPlayer1paiScore()==8 && !Scores.getRewardedArr3()[4]){
            Scores.getRewardedArr3()[4] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems3()[1][1]);
        }
        else if(Scores.getPlayer1paiScore()==9 && !Scores.getRewardedArr3()[5]){
            Scores.getRewardedArr3()[5] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems3()[1][2]);
        }
        else if(Scores.getPlayer1paiScore()==12 && !Scores.getRewardedArr3()[6]){
            Scores.getRewardedArr3()[6] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems3()[2][0]);
        }
        else if(Scores.getPlayer1paiScore()==14 && !Scores.getRewardedArr3()[7]){
            Scores.getRewardedArr3()[7] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems3()[2][1]);
        }
        else if(Scores.getPlayer1paiScore()==15 && !Scores.getRewardedArr3()[8]){
            Scores.getRewardedArr3()[8] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems3()[2][2]);
        }
        if(count>=1) {
            alert.showAndWait();
        }
    }
}