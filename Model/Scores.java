package Model;

//Keeps track of scores, rounds, and coins in both player.vs player and player vs. ai
public class Scores {
    private static int playerAIScore;
    private static int player1ppScore;
    private static int pp1Coins;
    private static int pp2Coins;
    private static int pai1Coins;
    private static int player1paiScore;
    private static int player2Score;
    private static int currentRoundPVP;
    private static int currentRoundPVAI;
    private static boolean[] rewardedArr1 = new boolean[9];
    private static boolean[] rewardedArr2 = new boolean[9];
    private static boolean[] rewardedArr3 = new boolean[9];
    private static String[][] circIDs1 = {{"not-owned","not-owned","not-owned"},
            {"not-owned","not-owned","not-owned"},
            {"not-owned","not-owned","not-owned"}};
    private static String[][] circIDs2 = {{"not-owned","not-owned","not-owned"},
            {"not-owned","not-owned","not-owned"},
            {"not-owned","not-owned","not-owned"}};
    private static String[][] circIDs3 = {{"not-owned","not-owned","not-owned"},
            {"not-owned","not-owned","not-owned"},
            {"not-owned","not-owned","not-owned"}};

    public static boolean[] getRewardedArr1() {
        return rewardedArr1;
    }

    public static boolean[] getRewardedArr2() {
        return rewardedArr2;
    }

    public static boolean[] getRewardedArr3() {
        return rewardedArr3;
    }

    public static String[][] getCircIDs1() {
        return circIDs1;
    }

    public static String[][] getCircIDs2() {
        return circIDs2;
    }

    public static String[][] getCircIDs3() {
        return circIDs3;
    }

    public static int getPlayer1paiScore() {
        return player1paiScore;
    }

    public static void setPlayer1paiScore(int player1paiScore) {
        Scores.player1paiScore = player1paiScore;
    }

    public static int getPp1Coins() {
        return pp1Coins;
    }

    public static int getPp2Coins() {
        return pp2Coins;
    }

    public static int getPai1Coins() {
        return pai1Coins;
    }

    public static void setPp1Coins(int pp1Coins) {
        Scores.pp1Coins = pp1Coins;
    }

    public static void setPp2Coins(int pp2Coins) {
        Scores.pp2Coins = pp2Coins;
    }

    public static void setPai1Coins(int pai1Coins) {
        Scores.pai1Coins = pai1Coins;
    }

    public static int getCurrentRoundPVP() {
        return currentRoundPVP;
    }

    public static void setCurrentRoundPVP(int currentRoundPVP) {
        Scores.currentRoundPVP = currentRoundPVP;
    }

    public static int getCurrentRoundPVAI() {
        return currentRoundPVAI;
    }

    public static void setCurrentRoundPVAI(int currentRound) {
        Scores.currentRoundPVAI = currentRound;
    }

    public static int getPlayer1ppScore() {
        return player1ppScore;
    }

    public static void setPlayer1ppScore(int player1Score) {
        Scores.player1ppScore = player1Score;
    }

    public static int getPlayer2Score() {
        return player2Score;
    }

    public static void setPlayer2Score(int player2Score) {
        Scores.player2Score = player2Score;
    }

    public static int getPlayerAIScore() {
        return playerAIScore;
    }

    public static void setPlayerAIScore(int playerAIScore) {
        Scores.playerAIScore = playerAIScore;
    }

}
