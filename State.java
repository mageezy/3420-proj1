public class State {

    private int stateNum;
    private char optMove;
    private double value;

    public State(int stateNum) {
        this.stateNum = stateNum;
        this.optMove = '\0';
        this.value = -Double.MAX_VALUE;
    }

    public State(int stateNum, char optMove, double value) {
        this.stateNum = stateNum;
        this.optMove = optMove;
        this.value = value;
    }

    public int getStateNum() {
        return stateNum;
    }
    public void setOptMove(char move) {
        this.optMove = move;
    }
    public char getOptMove() {
        return optMove;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public double getValue() {
        return value;
    }
}