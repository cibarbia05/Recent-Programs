package Model;

import java.util.ArrayList;

public class GameBoard {

    private int[][] board;

    private ArrayList<Integer> rndList;
    private int row, col;
    private static final int PLAYER1 = 1;
    private static final int PLAYER2 = 2;


    /*Creates a GameBoard*/
    public GameBoard(int r, int c) {
        board = new int[][]{{0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0}};
        row = r;
        col = c;
        rndList = new ArrayList<>();
        rndList.add(0);
        rndList.add(1);
        rndList.add(2);
        rndList.add(3);
        rndList.add(4);
        rndList.add(5);
        rndList.add(6);
    }
    public ArrayList<Integer> getRndList() {
        return rndList;
    }
    /*Returns board with player values*/
    public int[][] getBoard() {
        return board;
    }

    /*Returns the index of the nearest unfilled row in the specified column and fills it
     * with the value passed into the parameter (either 1 for player 1 or 2 for player 2)
     */
    public int nearestUnfilledRow(int col, int value){
        int count = -1;
        for(int r=board.length-1;r>=0;r--){
            if(board[r][col]==0){
                count = 0;
                board[r][col] = value;
                return r;
            }
        }
        return -999;
    }
    /*Checks horizontally, vertically, and diagonally to see if there are any four consecutive balls in the board*/
    public String winnerCheck(){
        //horizontalCheck
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[0].length-3;j++){
                if (board[i][j] == PLAYER1 && board[i][j+1] == PLAYER1 && board[i][j+2] == PLAYER1 && board[i][j+3] == PLAYER1){
                    return "Player 1";
                }
                else if (board[i][j] == PLAYER2 && board[i][j+1] == PLAYER2 && board[i][j+2] == PLAYER2 && board[i][j+3] == PLAYER2){
                    return "Player 2";
                }
            }
        }
        //verticalCheck
        for (int i=0;i<board.length-3;i++){
            for (int j=0;j<board[0].length;j++){
                if (board[i][j] == PLAYER1 && board[i+1][j] == PLAYER1 && board[i+2][j] == PLAYER1 && board[i+3][j] == PLAYER1){
                    return "Player 1";
                }
                else if (board[i][j] == PLAYER2 && board[i+1][j] == PLAYER2 && board[i+2][j] == PLAYER2 && board[i+3][j] == PLAYER2){
                    return "Player 2";
                }
            }
        }
        //ascendingDiagonalCheck
        for (int i=3;i<board.length;i++){
            for (int j=0;j<board[0].length-3;j++){
                if (board[i][j] == PLAYER1 && board[i-1][j+1] == PLAYER1 && board[i-2][j+2] == PLAYER1 && board[i-3][j+3] == PLAYER1) {
                    return "Player 1";
                }
                else if (board[i][j] == PLAYER2 && board[i-1][j+1] == PLAYER2 && board[i-2][j+2] == PLAYER2 && board[i-3][j+3] == PLAYER2) {
                    return "Player 2";
                }
            }
        }
        //descendingDiagonalCheck
        for (int i=3;i<board.length;i++ ){
            for (int j=3;j<board[0].length;j++){
                if (board[i][j] == PLAYER1 && board[i-1][j-1] == PLAYER1 && board[i-2][j-2] == PLAYER1 && board[i-3][j-3] == PLAYER1) {
                    return "Player 1";
                }
                else if (board[i][j] == PLAYER2 && board[i-1][j-1] == PLAYER2 && board[i-2][j-2] == PLAYER2 && board[i-3][j-3] == PLAYER2) {
                    return "Player 2";
                }
            }
        }
        return "NONE";
    }
    /*Computer game play if medium difficulty level is chosen*/
    public int mediumAIChosenCol(){
        int rndPlay = (int)(Math.random()*2);
        if(rndPlay==0) {
            int avaiWin = checkForAvailableWinAI();
            if (avaiWin != -999) {
                return checkForAvailableWinAI();
            }
            if (avaiWin == -999) {
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[0].length - 3; j++) {
                        if (board[i][j] == 0 && board[i][j + 1] == PLAYER1 && board[i][j + 2] == PLAYER1 && board[i][j + 3] == PLAYER1) {
                            if (i + 1 < board.length && board[i + 1][j] != 0) {
                                return j;
                            } else if (i == board.length - 1) {
                                return j;
                            }
                        } else if (board[i][j] == PLAYER1 && board[i][j + 1] == PLAYER1 && board[i][j + 2] == PLAYER1 && board[i][j + 3] == 0) {
                            if (i + 1 < board.length && board[i + 1][j + 3] != 0) {
                                return j + 3;
                            } else if (i == board.length - 1) {
                                return j + 3;
                            }
                        } else if (board[i][j] == PLAYER1 && board[i][j + 1] == PLAYER1 && board[i][j + 2] == 0 && board[i][j + 3] == PLAYER1) {
                            if (i + 1 < board.length && board[i + 1][j + 2] != 0) {
                                return j + 2;
                            } else if (i == board.length - 1) {
                                return j + 2;
                            }
                        } else if (board[i][j] == PLAYER1 && board[i][j + 1] == 0 && board[i][j + 2] == PLAYER1 && board[i][j + 3] == PLAYER1) {
                            if (i + 1 < board.length && board[i + 1][j + 1] != 0) {
                                return j + 1;
                            } else if (i == board.length - 1) {
                                return j + 1;
                            }
                        }
                    }
                }
                for (int i = 0; i < board.length - 3; i++) {
                    for (int j = 0; j < board[0].length; j++) {
                        if (board[i][j] == 0 && board[i + 1][j] == PLAYER1 && board[i + 2][j] == PLAYER1 && board[i + 3][j] == PLAYER1) {
                            return j;
                        }
                    }
                }
                for (int i = 3; i < board.length; i++) {
                    for (int j = 0; j < board[0].length - 3; j++) {
                        if (board[i][j] == PLAYER1 && board[i - 1][j + 1] == PLAYER1 && board[i - 2][j + 2] == PLAYER1 && board[i - 3][j + 3] == 0) {
                            if (i - 2 >= 0 && board[i - 2][j + 3] != 0) {
                                return j + 3;
                            }
                        } else if (board[i][j] == 0 && board[i - 1][j + 1] == PLAYER1 && board[i - 2][j + 2] == PLAYER1 && board[i - 3][j + 3] == PLAYER1) {
                            if (i + 1 < board.length && board[i + 1][j] != 0) {
                                return j;
                            } else if (i == board.length - 1) {
                                return j;
                            }
                        } else if (board[i][j] == PLAYER1 && board[i - 1][j + 1] == 0 && board[i - 2][j + 2] == PLAYER1 && board[i - 3][j + 3] == PLAYER1) {
                            if (board[i][j + 1] != 0) {
                                return j + 1;
                            }
                        } else if (board[i][j] == PLAYER1 && board[i - 1][j + 1] == PLAYER1 && board[i - 2][j + 2] == 0 && board[i - 3][j + 3] == PLAYER1) {
                            if (board[i - 1][j + 2] != 0) {
                                return j + 2;
                            }
                        }
                    }
                }
                for (int i = 3; i < board.length; i++) {
                    for (int j = 3; j < board[0].length; j++) {
                        if (board[i][j] == PLAYER1 && board[i - 1][j - 1] == PLAYER1 && board[i - 2][j - 2] == PLAYER1 && board[i - 3][j - 3] == 0) {
                            if (i - 2 >= 0 && board[i - 2][j - 3] != 0) {
                                return j - 3;
                            }
                        } else if (board[i][j] == 0 && board[i - 1][j - 1] == PLAYER1 && board[i - 2][j - 2] == PLAYER1 && board[i - 3][j - 3] == PLAYER1) {
                            if (i + 1 < board.length && board[i + 1][j] != 0) {
                                return j;
                            } else if (i == board.length - 1) {
                                return j;
                            }
                        } else if (board[i][j] == PLAYER1 && board[i - 1][j - 1] == 0 && board[i - 2][j - 2] == PLAYER1 && board[i - 3][j - 3] == PLAYER1) {
                            if (board[i][j - 1] != 0) {
                                return j - 1;
                            }
                        } else if (board[i][j] == PLAYER1 && board[i - 1][j - 1] == PLAYER1 && board[i - 2][j - 2] == 0 && board[i - 3][j - 3] == PLAYER1) {
                            if (board[i - 1][j - 2] != 0) {
                                return j - 2;
                            }
                        }
                    }
                }
            }
        }
        for(int c=0;c<board[0].length;c++){
            checkColToRemove(c);
        }
        int rnd = (int)(Math.random()*rndList.size());
        return rndList.get(rnd);
    }
    /*Computer game play if hard difficulty level is chosen*/
    public int hardAIChosenCol(){
        int avaiWin = checkForAvailableWinAI();
        if(avaiWin!=-999){
            return avaiWin;
        }
        if(avaiWin==-999) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length - 3; j++) {
                    if (board[i][j] == 0 && board[i][j+1] == PLAYER1 && board[i][j+2] == PLAYER1 && board[i][j+3] == PLAYER1){
                        if(i+1<board.length && board[i+1][j]!=0) {
                            return j;
                        }
                        else if(i == board.length-1){
                            return j;
                        }
                    }
                    else if (board[i][j] == PLAYER1 && board[i][j+1] == PLAYER1 && board[i][j+2] == PLAYER1&& board[i][j+3] == 0){
                        if(i+1<board.length && board[i+1][j+3]!=0) {
                            return j + 3;
                        }
                        else if(i == board.length-1){
                            return j+3;
                        }
                    }
                    else if (board[i][j] == PLAYER1 && board[i][j+1] == PLAYER1 && board[i][j+2] ==0 && board[i][j+3] == PLAYER1){
                        if(i+1<board.length && board[i+1][j+2]!=0) {
                            return j + 2;
                        }
                        else if(i == board.length-1){
                            return j+2;
                        }
                    }
                    else if (board[i][j] == PLAYER1 && board[i][j+1] ==0 && board[i][j+2] == PLAYER1 && board[i][j+3] == PLAYER1){
                        if(i+1<board.length && board[i+1][j+1]!=0) {
                            return j + 1;
                        }
                        else if(i == board.length-1){
                            return j+1;
                        }
                    }
                }
            }
            for (int i = 0; i < board.length - 3; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == 0 && board[i+1][j] == PLAYER1 && board[i+2][j] == PLAYER1 && board[i+3][j] == PLAYER1){
                        return j;
                    }
                }
            }
            for (int i = 3; i < board.length; i++) {
                for (int j = 0; j < board[0].length - 3; j++) {
                    if (board[i][j] == PLAYER1 && board[i - 1][j + 1] == PLAYER1 && board[i - 2][j + 2] == PLAYER1 &&  board[i - 3][j + 3] == 0 ) {
                        if(i-2>=0 && board[i-2][j+3]!=0) {
                            return j + 3;
                        }
                    }
                    else if (board[i][j] == 0 && board[i - 1][j + 1] == PLAYER1 && board[i - 2][j + 2] == PLAYER1 &&  board[i - 3][j + 3] == PLAYER1 ) {
                        if(i+1< board.length&& board[i+1][j]!=0) {
                            return j;
                        }
                        else if(i==board.length-1){
                            return j;
                        }
                    }
                    else if (board[i][j] == PLAYER1 && board[i - 1][j + 1] == 0 && board[i - 2][j + 2] == PLAYER1 &&  board[i - 3][j + 3] == PLAYER1 ) {
                        if(board[i][j+1]!=0) {
                            return j + 1;
                        }
                    }
                    else if (board[i][j] == PLAYER1 && board[i - 1][j + 1] == PLAYER1 && board[i - 2][j + 2] == 0 &&  board[i - 3][j + 3] == PLAYER1 ) {
                        if(board[i-1][j+2]!=0) {
                            return j + 2;
                        }
                    }
                }
            }
            for (int i = 3; i < board.length; i++) {
                for (int j = 3; j < board[0].length; j++) {
                    if (board[i][j] == PLAYER1 && board[i - 1][j - 1] == PLAYER1 && board[i - 2][j - 2] == PLAYER1 && board[i - 3][j - 3] == 0) {
                        if(i-2>=0 && board[i-2][j-3]!=0) {
                            return j - 3;
                        }
                    }
                    else if (board[i][j] == 0 && board[i - 1][j - 1] == PLAYER1 && board[i - 2][j - 2] == PLAYER1 && board[i - 3][j - 3] == PLAYER1) {
                        if(i+1<board.length && board[i+1][j]!=0) {
                            return j;
                        }
                        else if(i== board.length-1){
                            return j;
                        }
                    }
                    else if(board[i][j] == PLAYER1 && board[i - 1][j - 1] == 0 && board[i - 2][j - 2] == PLAYER1 && board[i - 3][j - 3] == PLAYER1) {
                        if(board[i][j-1]!=0) {
                            return j - 1;
                        }
                    }
                    else if(board[i][j] == PLAYER1 && board[i - 1][j - 1] == PLAYER1 && board[i - 2][j - 2] == 0 && board[i - 3][j - 3] == PLAYER1) {
                        if(board[i-1][j-2]!=0) {
                            return j - 2;
                        }
                    }
                }
            }
        }
        for(int c=0;c<board[0].length;c++){
            checkColToRemove(c);
        }
        int rnd = (int)(Math.random()*rndList.size());
        return rndList.get(rnd);
    }
    /*Checks if there is a column in which the computer can win*/
    private int checkForAvailableWinAI(){
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[0].length-3;j++){
                if (board[i][j]==0 && board[i][j+1] == PLAYER2 && board[i][j+2] == PLAYER2 && board[i][j+3] == PLAYER2){
                    if(i+1<board.length && board[i+1][j]!=0) {
                        return j;
                    }
                    else if(i == board.length-1){
                        return j;
                    }
                }
                else if (board[i][j] == PLAYER2 && board[i][j+1] == PLAYER2 && board[i][j+2] == PLAYER2&& board[i][j+3] == 0){
                    if(i+1<board.length && board[i+1][j+3]!=0) {
                        return j + 3;
                    }
                    else if(i == board.length-1){
                        return j+3;
                    }
                }
                else if (board[i][j] == PLAYER2 && board[i][j+1] == PLAYER2 && board[i][j+2] == 0 && board[i][j+3] == PLAYER2){
                    if(i+1<board.length && board[i+1][j+2]!=0) {
                        return j + 2;
                    }
                    else if(i == board.length-1){
                        return j+2;
                    }
                }
                else if (board[i][j] == PLAYER2 && board[i][j+1] == 0 && board[i][j+2] == PLAYER2 && board[i][j+3] == PLAYER2){
                    if(i+1<board.length && board[i+1][j+1]!=0) {
                        return j + 1;
                    }
                    else if(i == board.length-1){
                        return j+1;
                    }
                }
            }
        }
        for (int i=0;i<board.length-3;i++){
            for (int j=0;j<board[0].length;j++){
                if (board[i][j] == 0 && board[i+1][j] == PLAYER2 && board[i+2][j] == PLAYER2 && board[i+3][j] == PLAYER2){
                    return j;
                }
            }
        }
        for (int i=3;i<board.length;i++){
            for (int j=0;j<board[0].length-3;j++){
                if (board[i][j] == 0 && board[i-1][j+1] == PLAYER2 && board[i-2][j+2] == PLAYER2 && board[i-3][j+3] == PLAYER2) {
                    if(i+1< board.length&& board[i+1][j]!=0) {
                        return j;
                    }
                    else if(i==board.length-1){
                        return j;
                    }
                }
                else if (board[i][j] == PLAYER2 && board[i-1][j+1] == 0 && board[i-2][j+2] == PLAYER2 && board[i-3][j+3] == PLAYER2) {
                    if(board[i][j+1]!=0) {
                        return j + 1;
                    }
                }
                else if (board[i][j] == PLAYER2 && board[i-1][j+1] == PLAYER2 && board[i-2][j+2] == 0 && board[i-3][j+3] == PLAYER2) {
                    if(board[i-1][j+2]!=0) {
                        return j + 2;
                    }
                }
                else if (board[i][j] == PLAYER2 && board[i-1][j+1] == PLAYER2 && board[i-2][j+2] == PLAYER2 && board[i-3][j+3] == 0) {
                    if(i-2>=0 && board[i-2][j+3]!=0) {
                        return j + 3;
                    }
                }
            }
        }
        for (int i=3;i<board.length;i++ ){
            for (int j=3;j<board[0].length;j++){
               if (board[i][j] == 0 && board[i-1][j-1] == PLAYER2 && board[i-2][j-2] == PLAYER2 && board[i-3][j-3] == PLAYER2) {
                   if(i+1<board.length && board[i+1][j]!=0) {
                       return j;
                   }
                   else if(i== board.length-1){
                       return j;
                   }
                }
               else if (board[i][j] == PLAYER2 && board[i-1][j-1] == 0 && board[i-2][j-2] == PLAYER2 && board[i-3][j-3] == PLAYER2) {
                   if(board[i][j-1]!=0) {
                       return j - 1;
                   }
               }
               else if (board[i][j] == PLAYER2 && board[i-1][j-1] == PLAYER2 && board[i-2][j-2] == 0 && board[i-3][j-3] == PLAYER2) {
                   if(board[i-1][j-2]!=0) {
                       return j - 2;
                   }
               }
               else if (board[i][j] == PLAYER2 && board[i-1][j-1] == PLAYER2 && board[i-2][j-2] == PLAYER2 && board[i-3][j-3] == 0) {
                   if(i-2>=0 && board[i-2][j-3]!=0) {
                       return j - 3;
                   }
               }
            }
        }
        return -999;
    }
    /*Checks if the board is completelu filled and if so there is a tie*/
    public boolean checkForTie(){
        for(int r=0;r<board.length;r++){
            for(int c=0;c<board[0].length;c++){
                if(board[r][c]==0){
                    return false;
                }
            }
        }
        return true;
    }
    /*If a column is full remove it from the array by setting all its rows to -**/
    public int checkColToRemove(int col){
        for (int row=0; row<board.length; row++) {
            if(board[row][col] == 0){
                return -1;
            }
        }
        for(int i = 0;i<rndList.size();i++) {
            if(rndList.get(i)==col) {
                rndList.remove(i);
            }
        }
        return col;
    }
}
