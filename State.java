public class State {

//delete this crap later

Game_Board[2][0][0] = new State(40, 2, 0, 0);
Game_Board[2][0][1] = new State(41, 2, 0, 1);
Game_Board[2][1][0] = new State(42, 2, 1, 0);
Game_Board[2][1][1] = new State(43, 2, 1, 1);
Game_Board[2][2][0] = new State(44, 2, 2, 0);
Game_Board[2][2][1] = new State(45, 2, 2, 1);
Game_Board[2][3][0] = new State(46, 2, 3, 0);
Game_Board[2][3][1] = new State(47, 2, 3, 1);
Game_Board[2][4][0] = new State(48, 2, 4, 0);
Game_Board[2][4][1] = new State(49, 2, 4, 1);
Game_Board[1][0][0] = new State(50, 1, 0, 0);
Game_Board[1][0][1] = new State(51, 1, 0, 1);
Game_Board[1][1][0] = new State(52, 1, 1, 0);
Game_Board[1][1][1] = new State(53, 1, 1, 1);
Game_Board[1][2][0] = new State(54, 1, 2, 0);
Game_Board[1][2][1] = new State(55, 1, 2, 1);
Game_Board[1][3][0] = new State(56, 1, 3, 0);
Game_Board[1][3][1] = new State(57, 1, 3, 1);
Game_Board[0][0][0] = new State(58, 0, 0, 0);
Game_Board[0][0][1] = new State(59, 0, 0, 1);
Game_Board[0][1][0] = new State(60, 0, 1, 0);
Game_Board[0][1][1] = new State(61, 0, 1, 1);
Game_Board[0][2][0] = new State(62, 0, 2, 0);
Game_Board[0][2][1] = new State(63, 0, 2, 1);
Game_Board[0][3][0] = new State(64, 0, 3, 0);






    private int stateNum;
    private char optMove;
    private int[] coords = new int[3];

    public State(int stateNum) {
        this.stateNum = stateNum;
        this.optMove = '\0';
    }

    public State(int stateNum, int x, int y, int z) {
        this.stateNum = stateNum;
        this.coords[0] = x;
        this.coords[1] = y;
        this.coords[2] = z;
    }

    public State(int stateNum, char optMove, int x, int y, int z) {
        this.stateNum = stateNum;
        this.optMove = optMove;
        this.coords[0] = x;
        this.coords[1] = y;
        this.coords[2] = z;
    }

    public int getStateNum() {
        return stateNum;
    }
    public void setOptMove(char move) {
        optMove = move;
    }
    public char getOptMove() {
        return optMove;
    }
    public void setCoords(int x, int y, int z) {
        coords[0] = x;
        coords[1] = y;
        coords[2] = z;
    }
    public int getXCoord() {
        return coords[0];
    }
    public int getYCoord() {
        return coords[1];
    }
    public int getZCoord() {
        return coords[2];
    }
}