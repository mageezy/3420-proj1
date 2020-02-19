/**
 * State object to represent a specific state in the MDP game. Each state contains its own
 * state number, and its own coordinates. it can also store the optimal move to take and each
 * of its neighbors.
 */
public class State {

    //variables necessary for a state object
    private int stateNum;
    private int[] coords = new int[3];
    private double reward;
    private double value;
    //variables that can be set
    private mdp.dir optMove;
    private State north = null;
    private State south = null;
    private State east = null;
    private State west = null;

    /**
     * constructor for a state object with only a state number
     * @param stateNum is the number of the state
     */
    public State(int stateNum) {
        this.stateNum = stateNum;
        optMove = null;
        value = 0;
    }

    /**
     * constructor for a State object with a state number and coordinates
     * @param stateNum is the number of the state
     * @param x the x coord of the state
     * @param y the y coord of the state
     * @param z the z coord of the state
     */
    public State(int stateNum, int x, int y, int z) {
        this.stateNum = stateNum;
        this.coords[0] = x;
        this.coords[1] = y;
        this.coords[2] = z;
        optMove = null;
        value = 0;
    }

    /**
     * formats the information contained in the state object into a string format
     * so as to be printed nicely.
     */
    public String toString() {
        String string = "(";
        if (stateNum < 10) string += " ";
        string += stateNum + ") ";
        if (value >= 0) string += " ";
        string += String.format("%.2f", value);
        string += " (" + optMove + ")";
        return string;
    }

    /**
     * uses state number and coordinates to determine if two states are the same state
     * @param state
     * @return true if the input state is the same as the current state
     */
    public boolean equals(State state) {
        int otherXCoord = state.getXCoord();
        int otherYCoord = state.getYCoord();
        int otherZCoord = state.getZCoord();
        int otherStateNum = state.getStateNum();

        if (this.getXCoord() == otherXCoord 
        && this.getYCoord()== otherYCoord 
        && this.getZCoord() == otherZCoord 
        && stateNum == otherStateNum) return true;
        else return false;
    }

    /**
     * Getters and Setters for State object variables.
     */
    public int getStateNum() {
        return stateNum;
    }
    public void setOptMove(mdp.dir move) {
        optMove = move;
    }
    public mdp.dir getOptMove() {
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
    public int[] getCoords() {
        return coords;
    }
    public void setReward(double reward) {
        this.reward = reward;
    }
    public double getReward() {
        return reward;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public double getValue() {
        return value;
    }
    /**
     * set input neighbor to an input direction
     * @param dir the direction or side that this neighbor is on
     * @param state the reference to the state object
     */
    public void setNeighbor(mdp.dir dir, State state) {
        switch(dir) {
            case N:
                this.north = state;
                break;
            case S:
                this.south = state;
                break;
            case E:
                this.east = state;
                break;
            case W:
                this.west = state;
                break;
            default:
                System.out.println("Invalid direction");

        }
    }
    /**
     * gets the neighbor at the input direction or side
     */
    public State getNeighbor(mdp.dir dir) {
        switch (dir) {
            case N:
                return north;
            case S:
                return south;
            case E:
                return east;
            case W:
                return west;
            default:
                System.out.println("invalid direction");
                return null;

        }
    }
}