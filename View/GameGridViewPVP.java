package View;

import Model.GameBoard;
import Model.Scores;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javafx.stage.Stage;
import java.util.Optional;

/*Template for a player vs. player game*/
public class GameGridViewPVP {
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
    private Label player2ScoreL;
    private Label curRoundL;
    private int player1Score;
    private int player2Score;
    private boolean winnerDisplayed;
    private double colConstI;
    private double rowConstI;
    private Circle curTurnCircle;
    private Polygon pointerTriangle;
    private SkinStore sn;

    /*Creates player vs. player game */
    public GameGridViewPVP() {
        sn = new SkinStore(0);
        pos = new GameBoard(0, 0);
        Scores.setCurrentRoundPVP(Scores.getCurrentRoundPVP()+1);
        initializePanes();
        initializeStageAndScene();
        setGridToAnc();
        initializePointerTriangle();
        createScoreLabels();
        setPaneConstraints();
        initializeCirclesArr();
        setPaneBackCircles();
        createUserCircles();
        createCurTurnCircle();
        addCircleToCurClick();
        pathTransition();
        stage.show();
    }
    /*Initializes Stage and Scene*/
    private void initializeStageAndScene(){
        scene = new Scene(ancPane, 730, 770);
        scene.getRoot().setId("scene");
        scene.getStylesheets().add("/View/Stylesheets/Stylesheet.css");
        scene.getRoot().setEffect(new DropShadow());
        stage = StartView.getStage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
    }
    /*Initializes panes*/
    private void initializePanes(){
        ancPane = new AnchorPane();
        pane = new GridPane();
        pane.setPadding(new Insets(0, 5, 0, 5));
    }

    /*Adds the grid of the background circles to main pane
     *where the player circles will be placed on top
     */
    private void setGridToAnc(){
        pane.setLayoutX(20);
        pane.setLayoutY(110);
        ancPane.getChildren().add(pane);
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
    /*Creates the score labels for both player1 and player2 and one for the current round*/
    private void createScoreLabels() {
        player1Score = Scores.getPlayer1ppScore();
        player1ScoreL = new Label("Player 1: " + player1Score);
        player1ScoreL.setId("score-label");
        player1ScoreL.setLayoutX(240);
        player1ScoreL.setLayoutY(30);
        player2Score = Scores.getPlayer2Score();
        player2ScoreL = new Label("Player 2: " + player2Score);
        player2ScoreL.setId("score-label");
        player2ScoreL.setLayoutX(360);
        player2ScoreL.setLayoutY(30);
        curRoundL = new Label("Round " + Scores.getCurrentRoundPVP());
        curRoundL.setLayoutX(530);
        curRoundL.setLayoutY(30);
        ancPane.getChildren().addAll(player1ScoreL, player2ScoreL,curRoundL);
    }
    /*Creates a small circle ith the color of the current player*/
    private void createCurTurnCircle() {
        curTurnCircle = new Circle(25);
        if(clickCount==0) {
            if (SkinStore.getPVPp1Color() == null && SkinStore.getPVPp1Pat() == null) {
                curTurnCircle.setFill(Color.web("#d02525"));
            } else if (SkinStore.getPVPp1Color() == null) {
                curTurnCircle.setFill(SkinStore.getPVPp1Pat());
            } else if (SkinStore.getPVPp1Pat() == null) {
                curTurnCircle.setFill(SkinStore.getPVPp1Color());
            }
        }
        if (clickCount == 1) {
            if (SkinStore.getPVPp2Color() == null && SkinStore.getPVPp2Pat() == null) {
                curTurnCircle.setFill(Color.web("#ffc903"));
            } else if (SkinStore.getPVPp2Color() == null) {
                curTurnCircle.setFill(SkinStore.getPVPp2Pat());
            } else if (SkinStore.getPVPp2Pat() == null) {
                curTurnCircle.setFill(SkinStore.getPVPp2Color());
            }
        }
        curTurnCircle.setLayoutX(50);
        curTurnCircle.setLayoutY(45);
        ancPane.getChildren().addAll(curTurnCircle);
    }
    /*Changes the color of the small circle on a mouse click to signal another player's turn*/
    private void updateCurTurnCircle(){
        if(clickCount==1) {
            if (SkinStore.getPVPp1Color() == null && SkinStore.getPVPp1Pat() == null) {
                curTurnCircle.setFill(Color.web("#d02525"));
            } else if (SkinStore.getPVPp1Color() == null) {
                curTurnCircle.setFill(SkinStore.getPVPp1Pat());
            } else if (SkinStore.getPVPp1Pat() == null) {
                curTurnCircle.setFill(SkinStore.getPVPp1Color());
            }
        }
        if (clickCount == 0) {
            if (SkinStore.getPVPp2Color() == null && SkinStore.getPVPp2Pat() == null) {
                curTurnCircle.setFill(Color.web("#ffc903"));
            } else if (SkinStore.getPVPp2Color() == null) {
                curTurnCircle.setFill(SkinStore.getPVPp2Pat());
            } else if (SkinStore.getPVPp2Pat() == null) {
                curTurnCircle.setFill(SkinStore.getPVPp2Color());
            }
        }
    }
    /*Checks if a player has reached a certain score, and if they have it they earn a new item in the store*/
    private void giveRewards(){
        if(Scores.getPlayer1ppScore()==1){
            Scores.getCircIDs1()[0][0] = "owned";
        }
        if(Scores.getPlayer1ppScore()==3){
            Scores.getCircIDs1()[0][1] = "owned";
        }
        if(Scores.getPlayer1ppScore()==5){
            Scores.getCircIDs1()[0][2] = "owned";
        }
        if(Scores.getPlayer1ppScore()==7){
            Scores.getCircIDs1()[1][0] = "owned";
        }
        if(Scores.getPlayer1ppScore()==8){
            Scores.getCircIDs1()[1][1] = "owned";
        }
        if(Scores.getPlayer1ppScore()==9){
            Scores.getCircIDs1()[1][2] = "owned";
        }
        if(Scores.getPlayer1ppScore()==12){
            Scores.getCircIDs1()[2][0] = "owned";
        }
        if(Scores.getPlayer1ppScore()==14){
            Scores.getCircIDs1()[2][1] = "owned";
        }
        if(Scores.getPlayer1ppScore()==15){
            Scores.getCircIDs1()[2][2] = "owned";
        }


        if(Scores.getPlayer2Score()==1){
            Scores.getCircIDs2()[0][0] = "owned";
        }
        if(Scores.getPlayer2Score()==3){
            Scores.getCircIDs2()[0][1] = "owned";
        }
        if(Scores.getPlayer2Score()==5){
            Scores.getCircIDs2()[0][2] = "owned";
        }
        if(Scores.getPlayer2Score()==7){
            Scores.getCircIDs2()[1][0] = "owned";
        }
        if(Scores.getPlayer2Score()==8){
            Scores.getCircIDs2()[1][1] = "owned";
        }
        if(Scores.getPlayer2Score()==9){
            Scores.getCircIDs2()[1][2] = "owned";
        }
        if(Scores.getPlayer2Score()==12){
            Scores.getCircIDs2()[2][0] = "owned";
        }
        if(Scores.getPlayer2Score()==12){
            Scores.getCircIDs2()[2][1] = "owned";
        }
        if(Scores.getPlayer2Score()==12){
            Scores.getCircIDs2()[2][2] = "owned";
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
    private void initializeCirclesArr(){
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.GRAY);
        circles = new Circle[NUMROWS][NUMCOLS];
        for(int r=0;r<circles.length;r++){
            for(int c=0;c<circles[0].length;c++) {
                circles[r][c] = new Circle(RADIUS);
                circles[r][c].setFill(Color.web("#21487c"));
                circles[r][c].setEffect(ds);
            }
        }
    }
    /*Creates the positions of the circles that will represent the background of the game,
     * where the red and yellow circles will be place on a mouse click
     */
    private void setPaneBackCircles(){
        for(int r=0;r<NUMROWS;r++){
            for(int c=0;c<NUMCOLS;c++){
                Rectangle r2 = new Rectangle(100,110);
                r2.setFill(Color.TRANSPARENT);
                pane.add(r2,c,r,1,1);
                pane.add(circles[r][c],c,r,1,1);
            }
        }
    }
    /*Initializes and styles he circles that corresponds to each user that will be placed on mouse click.
     *Red for one player and yellow for the other
     */
    private void createUserCircles(){
        InnerShadow ds = new InnerShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.web("#626262"));
        InnerShadow ds2 = new InnerShadow();
        ds2.setOffsetY(3.0);
        ds2.setOffsetX(3.0);
        ds2.setColor(Color.web("#626262"));
        user1C = new Circle(RADIUS);
        if(SkinStore.getPVPp1Color()==null && SkinStore.getPVPp1Pat()==null) {
            user1C.setFill(Color.web("#d02525"));
        }
        else if(SkinStore.getPVPp1Color()==null){
            user1C.setFill(SkinStore.getPVPp1Pat());
        }
        else if(SkinStore.getPVPp1Pat()==null){
            user1C.setFill(SkinStore.getPVPp1Color());
        }
        user1C.setEffect(ds);
        user2C = new Circle(RADIUS);
        if(SkinStore.getPVPp2Color()==null && SkinStore.getPVPp2Pat()==null) {
            user2C.setFill(Color.web("#ffc903"));
        }
        else if(SkinStore.getPVPp2Color()==null){
            user2C.setFill(SkinStore.getPVPp2Pat());
        }
        else if(SkinStore.getPVPp2Pat()==null){
            user2C.setFill(SkinStore.getPVPp2Color());
        }
        user2C.setEffect(ds2);

    }
    /*Sets the actions of a mouse click so that the circles belong to the corresponding
     *player and the board updates properly
     */
    private void addCircleToCurClick(){
        pane.setOnMouseClicked(event -> {
            if(clickCount==0){
                Node source = (Node)event.getTarget() ;
                Integer colIndex = GridPane.getColumnIndex(source);
                if(colIndex!=null) {
                    setToNearestOpenPos(colIndex);
                    checkAndDisplayWinner();
                }
                if(pos.checkForTie()){
                    Alert alert = new Alert(Alert.AlertType.NONE,   "Tie");
                    alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
                    ButtonType alertButton = new ButtonType("Play Again");
                    ButtonType alertButton2 = new ButtonType("Go back");
                    alert.getButtonTypes().setAll(alertButton, alertButton2);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == alertButton) {
                        stage.close();
                        GameGridViewPVP pvp = new GameGridViewPVP();
                    }
                    else if(result.get() == alertButton2){
                        stage.close();
                        StartView sv = new StartView();
                    }
                }
            }
            else if(clickCount==1){
                Node source = (Node)event.getTarget() ;
                Integer colIndex = GridPane.getColumnIndex(source);
                if(colIndex!=null) {
                    setToNearestOpenPos(colIndex);
                    checkAndDisplayWinner();
                    if(pos.checkForTie()){
                        Alert alert = new Alert(Alert.AlertType.NONE,   "Tie");
                        alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
                        ButtonType alertButton = new ButtonType("Play Again");
                        ButtonType alertButton2 = new ButtonType("Go back");
                        alert.getButtonTypes().setAll(alertButton, alertButton2);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == alertButton) {
                            stage.close();
                            GameGridViewPVP pvp = new GameGridViewPVP();
                        }
                        else if(result.get() == alertButton2){
                            stage.close();
                            StartView sv = new StartView();
                        }
                    }
                }
            }

        });
    }
    /*Sets a circle to nearest available position on the click on a column*/
    private void setToNearestOpenPos(int col){
        if (clickCount == 0) {
            int curUnfilled = pos.nearestUnfilledRow(col,clickCount+1);
            if(curUnfilled!=-999) {
                updateCurTurnCircle();
                clickCount = 1;
                Circle user1C = new Circle(RADIUS);
                if(SkinStore.getPVPp1Color()==null && SkinStore.getPVPp1Pat()==null) {
                    user1C.setFill(Color.web("#d02525"));
                }
                else if(SkinStore.getPVPp1Color()==null){
                    user1C.setFill(SkinStore.getPVPp1Pat());
                }
                else if(SkinStore.getPVPp1Pat()==null){
                    user1C.setFill(SkinStore.getPVPp1Color());
                }
                InnerShadow ds = new InnerShadow();
                ds.setOffsetY(3.0);
                ds.setOffsetX(3.0);
                ds.setColor(Color.web("#626262"));
                user1C.setEffect(ds);
                pane.add(user1C, col, curUnfilled);
            }
        }
        else if (clickCount == 1) {
            int curUnfilled = pos.nearestUnfilledRow(col,clickCount+1);
            if(curUnfilled!=-999) {
                updateCurTurnCircle();
                clickCount = 0;
                Circle user2C = new Circle(RADIUS);
                if(SkinStore.getPVPp2Color()==null && SkinStore.getPVPp2Pat()==null) {
                    user2C.setFill(Color.web("#ffc903"));
                }
                else if(SkinStore.getPVPp2Color()==null){
                    user2C.setFill(SkinStore.getPVPp2Pat());
                }
                else if(SkinStore.getPVPp2Pat()==null){
                    user2C.setFill(SkinStore.getPVPp2Color());
                }
                InnerShadow ds = new InnerShadow();
                ds.setOffsetY(3.0);
                ds.setOffsetX(3.0);
                ds.setColor(Color.web("#626262"));
                user2C.setEffect(ds);
                pane.add(user2C, col, curUnfilled);
            }
        }
    }
    /*Checks if there is a winner and if so the game ends,
    *the rewards are given and the scores are updated
    */
    private void checkAndDisplayWinner() {
        String winner = pos.winnerCheck();
        if (winner.equals("Player 1") || winner.equals("Player 2")) {
            if(winner.equals("Player 1")) {
                Scores.setPlayer1ppScore(Scores.getPlayer1ppScore() + 1);
                player1ScoreL.setText("Player 1: " + Scores.getPlayer1ppScore());
            }
            else if(winner.equals("Player 2")) {
                Scores.setPlayer2Score(Scores.getPlayer2Score() + 1);
                player2ScoreL.setText("Player 2: " + Scores.getPlayer2Score());
            }
            ImageView winIV = new ImageView(new Image(getClass().getResourceAsStream("/View/Images/Crown.png")));
            winIV.setFitWidth(55);
            winIV.setFitHeight(45);
            if(winner.equals("Player 1")) {
                winIV.setLayoutX(245);
                winIV.setLayoutY(60);
                ancPane.getChildren().add(winIV);
                ancPane.getChildren().removeAll(pointerTriangle);
            }
            else if (winner.equals("Player 2")){
                winIV.setLayoutX(350);
                winIV.setLayoutY(60);
                ancPane.getChildren().add(winIV);
                ancPane.getChildren().removeAll(pointerTriangle);
            }
            incrementCoins(winner);
            giveRewards();
            createRewardAlert();
            createRewardAlert2();
            Alert alert = new Alert(Alert.AlertType.NONE, winner + " wins!");
            alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
            ButtonType alertButton = new ButtonType("Play Again");
            ButtonType alertButton2 = new ButtonType("Go back");
            alert.getButtonTypes().setAll(alertButton, alertButton2);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == alertButton) {
                stage.close();
                GameGridViewPVP pvp = new GameGridViewPVP();
            }
            else if(result.get() == alertButton2){
                stage.close();
                StartView sv = new StartView();
            }
        }
    }
    /*Increments the score of the winner*/
    private void incrementCoins(String winner){
        if(winner.equals("Player 1")) {
            Scores.setPp1Coins(Scores.getPp1Coins()+5);
        }
        else if (winner.equals("Player 2")){
            Scores.setPp2Coins(Scores.getPp2Coins()+5);
        }
    }
    /*Creates a reward alert if a specified score for a skin is reached for player 1*/
    private void createRewardAlert(){
        int count = 0;
        Alert alert = new Alert(Alert.AlertType.NONE, "Player 1 has unlocked a new skin");
        alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
        ButtonType alertButton = new ButtonType("Ok");
        alert.getButtonTypes().setAll(alertButton);

        if(Scores.getPlayer1ppScore()==1 && !Scores.getRewardedArr1()[0]){
            Scores.getRewardedArr1()[0] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems()[0][0]);
        }
        else if(Scores.getPlayer1ppScore()==3 && !Scores.getRewardedArr1()[1]){
            Scores.getRewardedArr1()[1] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems()[0][1]);
        }
        else if(Scores.getPlayer1ppScore()==5 && !Scores.getRewardedArr1()[2]){
            Scores.getRewardedArr1()[2] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems()[0][2]);
        }
        else if(Scores.getPlayer1ppScore()==7 && !Scores.getRewardedArr1()[3]){
            Scores.getRewardedArr1()[3] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems()[1][0]);
        }
        else if(Scores.getPlayer1ppScore()==8 && !Scores.getRewardedArr1()[4]){
            Scores.getRewardedArr1()[4] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems()[1][1]);
        }
        else if(Scores.getPlayer1ppScore()==9 && !Scores.getRewardedArr1()[5]){
            Scores.getRewardedArr1()[5] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems()[1][2]);
        }
        else if(Scores.getPlayer1ppScore()==12 && !Scores.getRewardedArr1()[6]){
            Scores.getRewardedArr1()[6] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems()[2][0]);
        }
        else if(Scores.getPlayer1ppScore()==14 && !Scores.getRewardedArr1()[7]){
            Scores.getRewardedArr1()[7] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems()[2][1]);
        }
        else if(Scores.getPlayer1ppScore()==15 && !Scores.getRewardedArr1()[8]){
            Scores.getRewardedArr1()[8] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems()[2][2]);
        }
        if(count>=1) {
            alert.showAndWait();
        }
    }
    /*Creates a reward alert if a specified score for a skin is reached for player 2*/
    private void createRewardAlert2(){
        int count = 0;
        Alert alert = new Alert(Alert.AlertType.NONE, "Player 2 has unlocked a new skin");
        alert.getDialogPane().getStylesheets().add("/View/Stylesheets/Stylesheet.css");
        ButtonType alertButton = new ButtonType("Ok");
        alert.getButtonTypes().setAll(alertButton);

        if(Scores.getPlayer2Score()==1 && !Scores.getRewardedArr2()[0]){
            Scores.getRewardedArr2()[0] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems2()[0][0]);
        }
        else if(Scores.getPlayer2Score()==3&& !Scores.getRewardedArr2()[1]){
            Scores.getRewardedArr2()[1] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems2()[0][1]);
        }
        else if(Scores.getPlayer2Score()==5&& !Scores.getRewardedArr2()[2]){
            Scores.getRewardedArr2()[2] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems2()[0][2]);
        }
        else if(Scores.getPlayer2Score()==7&& !Scores.getRewardedArr2()[3]){
            Scores.getRewardedArr2()[03] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems2()[1][0]);
        }
        else if(Scores.getPlayer2Score()==8&& !Scores.getRewardedArr2()[4]){
            Scores.getRewardedArr2()[4] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems2()[1][1]);
        }
        else if(Scores.getPlayer2Score()==9&& !Scores.getRewardedArr2()[5]){
            Scores.getRewardedArr2()[5] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems2()[1][2]);
        }
        else if(Scores.getPlayer2Score()==12&& !Scores.getRewardedArr2()[6]){
            Scores.getRewardedArr2()[6] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems2()[2][0]);
        }
        else if(Scores.getPlayer2Score()==14&& !Scores.getRewardedArr2()[7]){
            Scores.getRewardedArr2()[7] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems2()[2][1]);
        }
        else if(Scores.getPlayer2Score()==15&& !Scores.getRewardedArr2()[8]){
            Scores.getRewardedArr2()[8] = true;
            count++;
            alert.setGraphic(SkinStore.getCircleItems2()[2][2]);
        }
        if(count>=1) {
            alert.showAndWait();
        }
    }
}