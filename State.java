public class State {

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