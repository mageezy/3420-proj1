/**
 * State object to represent a specific state in the MDP game. Each state contains its own
 * state number, and its own coordinates. it can also store the optimal move to take and each
 * of its neighbors.
 */
public class State {

    //variables necessary for a state object
    private int stateNum;
    private int[] coords = new int[3];
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
        this.optMove = null;
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
    }

    /**
     * formats the information contained in the state object into a string format
     * so as to be printed nicely.
     */
    public String toString() {
        String string = "State: ";
        string += stateNum;
        return string;
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