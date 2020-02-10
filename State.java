public class State {

    private int stateNum;
    private char optMove;
    private double value;

    public State(int stateNum, char optMove, double value) {
        this.stateNum = stateNum;
        this.optMove = optMove;
        this.value = value;
    }

    public int getStateNum() {
        return stateNum;
    }
    public char getOptMove() {
        return optMove;
    }
    public double getValue() {
        return value;
    }
}