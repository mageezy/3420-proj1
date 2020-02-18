import java.lang.Math;

/**
 * Our MDP class encompasses the entire project 1, this is the main function with which all
 * functions and objects will be created and called from.
 */
public class mdp {

    //==========  Global Variables  ==========

    public static double Discount_Factor = 1;
    public static double Max_Error = 1.0E-6;
    public static double Pos_Reward = 1;
    public static double Neg_Reward = -1;
    public static double Step_Cost = -.04;
    public static double Key_Loss_Prob = .5;
    public static double Forward_Prob = .8;
    public static double Clockwise_Prob = .1;
    public static double Counter_Prob = .1;
    public static int iters = 0;
    //private static double[][][] Rewards = new double[65][65][4];
    //private static double[][][] Transition = new double[65][65][4];
    private static State[][][] Game_Board= new State[5][15][2];//odds z = 1, evens z = 0
    private static State[] states = new State[65];

    //enums to keep directions mapped to one specific value.
    public enum dir {
        N(0),
        S(1),
        E(2),
        W(3);

        public final int val;
        dir(int val) { 
            this.val = val; 
        }

        //get direction clockwise of current direction
        public dir clockwise() {
            switch(this) {
                case N:
                    return E;
                case S:
                    return W;
                case E:
                    return S;
                case W:
                    return N;
                default:
                    System.out.println("invalid direction");
                    return null;
            }
        }

        //get direction counterclockwise of current direction
        public dir counter() {
            switch(this) {
                case N:
                    return W;
                case S:
                    return E;
                case E:
                    return N;
                case W:
                    return S;
                default:
                    System.out.println("invalid direction");
                    return null;
            }
        }
    }

    /**
     * Generates a game board, populates an array of every state, initializes every states
     * neighbors, and then runs test cases on reward function, transition function, and 
     * neghbor assignments.
     * @param args
     */
    public static void main(String [] args) {
        generateBoard();
        genStateArray();
        genNeighbors();
        setRewards();


        printGameBoard();
        solveMDP('v');
        System.out.println();
        printGameBoard();
        //test functions for the transition function.
        // double prob1 = transition(states[62], dir.W, states[62]);//should be .1
        // double prob2 = transition(states[63], dir.E, states[64]);//should be .8
        // double prob3 = transition(states[44], dir.E, states[46]);//should be 0
        // double prob4 = transition(states[1], dir.W, states[1]);//should be .8
        // double prob5 = transition(states[32], dir.N, states[34]);//should be .1

        // //test functions for the reward function.
        // double item1 = reward(states[26], dir.E, states[28]);//should be .96
        // double item2 = reward(states[27], dir.W, states[29]);//should be -.04
        // double item3 = reward(states[46], dir.W, states[44]);//should be -1.04
        // double item4 = reward(states[8], dir.E, states[10]);//should be -.04
        // double item5 = reward(states[62], dir.N, states[62]);//should be -.04

        //neighbor test functions.
        // State nbor1 = states[59].getNeighbor(dir.E);//should be 61
        // State nbor2 = states[59].getNeighbor(dir.W);//should be 59
        // State nbor3 = states[59].getNeighbor(dir.S);//should be 51
        // State nbor4 = states[59].getNeighbor(dir.N);//should be 59
        
        // //print out transition tests
        // System.out.println("\n===== Transition Tests =====");
        // System.out.println(prob1);
        // System.out.println(prob2);
        // System.out.println(prob3);
        // System.out.println(prob4);
        // System.out.println(prob5);

        // //print out reward tests.
        // System.out.println("\n===== Reward Tests =====");
        // System.out.println(item1);
        // System.out.println(item2);
        // System.out.println(item3);
        // System.out.println(item4);
        // System.out.println(item5);

        //print out neighbor tests.
        // System.out.println("\n===== Neighbor Tests =====");
        // System.out.println(nbor1);
        // System.out.println(nbor2);
        // System.out.println(nbor3);
        // System.out.println(nbor4);
    }

    /**
     * This function hardcodes every state with its respective coordinates and 
     * places it in its respective location within the game board.
     */
    public static void generateBoard() {
        Game_Board[4][0][0] = new State(0, 4, 0, 0);
        Game_Board[4][0][1] = new State(1, 4, 0, 1);
        Game_Board[4][1][0] = new State(2, 4, 1, 0);
        Game_Board[4][1][1] = new State(3, 4, 1, 1);
        Game_Board[4][2][0] = new State(4, 4, 2, 0);
        Game_Board[4][2][1] = new State(5, 4, 2, 1);
        Game_Board[4][3][0] = new State(6, 4, 3, 0);
        Game_Board[4][3][1] = new State(7, 4, 3, 1);
        Game_Board[4][4][0] = new State(8, 4, 4, 0);
        Game_Board[4][4][1] = new State(9, 4, 4, 1);
        Game_Board[4][5][0] = new State(10, 4, 5, 0);
        Game_Board[4][5][1] = new State(11, 4, 5, 1);
        Game_Board[4][6][0] = new State(12, 4, 6, 0);
        Game_Board[4][6][1] = new State(13, 4, 6, 1);
        Game_Board[4][7][0] = new State(14, 4, 7, 0);
        Game_Board[4][7][1] = new State(15, 4, 7, 1);
        Game_Board[4][8][0] = new State(16, 4, 8, 0);
        Game_Board[4][8][1] = new State(17, 4, 8, 1);
        Game_Board[4][9][0] = new State(18, 4, 9, 0);
        Game_Board[4][9][1] = new State(19, 4, 9, 1);
        Game_Board[4][10][0] = new State(20, 4, 10, 0);
        Game_Board[4][10][1] = new State(21, 4, 10, 1);
        Game_Board[4][11][0] = new State(22, 4, 11, 0);
        Game_Board[4][11][1] = new State(23, 4, 11, 1);
        Game_Board[4][12][0] = new State(24, 4, 12, 0);
        Game_Board[4][12][1] = new State(25, 4, 12, 1);
        Game_Board[4][13][0] = new State(26, 4, 13, 0);
        Game_Board[4][13][1] = new State(27, 4, 13, 1);
        Game_Board[4][14][0] = new State(28, 4, 14, 0);
        Game_Board[4][14][1] = new State(29, 4, 14, 1);
        Game_Board[3][0][0] = new State(30, 3, 0, 0);
        Game_Board[3][0][1] = new State(31, 3, 0, 1);
        Game_Board[3][1][0] = new State(32, 3, 1, 0);
        Game_Board[3][1][1] = new State(33, 3, 1, 1);
        Game_Board[3][2][0] = new State(34, 3, 2, 0);
        Game_Board[3][2][1] = new State(35, 3, 2, 1);
        Game_Board[3][3][0] = new State(36, 3, 3, 0);
        Game_Board[3][3][1] = new State(37, 3, 3, 1);
        Game_Board[3][4][0] = new State(38, 3, 4, 0);
        Game_Board[3][4][1] = new State(39, 3, 4, 1);
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
    }

    public static void printGameBoard() {
        for (int x = 0; x < 5; x++) {
            if (x == 4) {
                for (int z = 0; z < 2; z++) {
                    for (int y = 0; y < 8; y++) {
                        System.out.print(Game_Board[x][y][z] + "  ");
                    }
                    System.out.println();
                }
                System.out.println();
                for (int z = 0; z < 2; z++) {
                    System.out.print("   ");
                    for (int y = 8; y < 15; y++) {
                        System.out.print(Game_Board[x][y][z] + "  ");
                    }
                    System.out.println();
                }
            } else {
                for (int z = 0; z < 2; z++) {
                    for (int y = 0; y < 15; y++) {
                        if (Game_Board[x][y][z] != null) {
                            System.out.print(Game_Board[x][y][z] + "  ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * This function generates an array of state values where the state number is the states index
     * in the array. this allows easier access for test functions and assigning neighbors.
     */
    public static void genStateArray() {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 15; y++) {
                for (int z = 0; z < 2; z++) {
                    State state = Game_Board[x][y][z];
                    if (state != null) {
                        states[state.getStateNum()] = state;
                    }
                }
            }
        }
    }

    /**
     * this function assignes every state 4 neighbor states, one for each possible direction.
     * terminal cases have themselves as all neighbors, and wall cases have themselves for the
     * neighbor in the direction of the wall.
     */
    public static void genNeighbors() {
        for (State state : states) {
            //if terminal state it can't go anywhere.
            if (terminal(state)) {
                state.setNeighbor(dir.N, state);
                state.setNeighbor(dir.S, state);
                state.setNeighbor(dir.E, state);
                state.setNeighbor(dir.W, state);
            } else {//if not terminal state set neighbors normally
                int x = state.getXCoord();
                int y = state.getYCoord();
                int z = state.getZCoord();
                //set south neighbor
                if ((x < 4) && (Game_Board[x+1][y][z] != null)) {
                    state.setNeighbor(dir.S, Game_Board[x+1][y][z]);
                } else state.setNeighbor(dir.S, state);
                //set north neighbor
                if ((x > 0) && (Game_Board[x-1][y][z] != null)) {
                    state.setNeighbor(dir.N, Game_Board[x-1][y][z]);
                } else state.setNeighbor(dir.N, state);
                //set east neighbor
                if ((y < 14) && (Game_Board[x][y+1][z] != null)) {
                    state.setNeighbor(dir.E, Game_Board[x][y+1][z]);
                } else state.setNeighbor(dir.E, state);
                //set west neighbor
                if ((y > 0) && (Game_Board[x][y-1][z] != null)) {
                    state.setNeighbor(dir.W, Game_Board[x][y-1][z]);
                } else state.setNeighbor(dir.W, state);
            } 
            //set special cases for 63 and 57 going to key state 64
            if (state.getStateNum() == 63) state.setNeighbor(dir.E, states[64]);
            if (state.getStateNum() == 57) state.setNeighbor(dir.N, states[64]);
        }
    }
    /**
     * Calculates the reward for going from state start to state next with specified action
     * if the action will not go from start to next then it returns 0, otherwise it returns
     * the step cost added to whatever reward may be at a special state.
     */
    public static double reward(State state) {
        double reward = 0;
        reward += Step_Cost;
        switch(state.getStateNum()) {
            case 44:
            case 45:
            case 48:
            case 49:
                reward = Neg_Reward;
                break;
            case 28:
                reward = Pos_Reward;
                break;
        }
        return reward;
    }
    
    public static void setRewards() {
        for (State state : states) {
            double reward = reward(state);
            state.setReward(reward);
            state.setOptMove(dir.N);
        }
    }

    /**
     * Return the probability that the specified action takes the agent from the start state
     * to the next state. Has special cases for the key state at state 64.
     */
    public static double transition(State start, dir action, State next) {
        double prob = 0;
        if (terminal(start)) return prob;
        if ((start.getStateNum() % 2 == 0) && (next.getStateNum() == 41)) {
            if (next.getNeighbor(action).equals(states[40])) prob += Forward_Prob;
            if (next.getNeighbor(action.clockwise()).equals(states[40])) prob += Clockwise_Prob;
            if (next.getNeighbor(action.counter()).equals(states[40])) prob += Counter_Prob;
            prob *= Key_Loss_Prob;
        }
        if (next.equals(start.getNeighbor(action))) prob += Forward_Prob;
        if (next.equals(start.getNeighbor(action.clockwise()))) prob += Clockwise_Prob;
        if (next.equals(start.getNeighbor(action.counter()))) prob += Counter_Prob;
        if (next.equals(states[40])) prob *= 1-Key_Loss_Prob;
        return prob;
    }

    /**
     * checks if a state is a terminal state
     */
    public static boolean terminal(State state) {
        int num = state.getStateNum();
        switch(num) {
            case 28:
            case 29:
            case 44:
            case 45:
            case 48:
            case 49:
                return true;
        }
        return false;
    }

    public static void solveMDP(char solType) {
        switch(solType) {
            case 'v':
                //do value iteration!
                boolean cont = true;
                while (cont) {
                    System.out.println("iter: " + iters);
                    double maxChange = valueIter();
                    // printGameBoard();
                    iters++;
                    if (maxChange <= (Max_Error * (1-Discount_Factor)/Discount_Factor)) cont = false;
                } 
                break;
            case 'p':
                //do policy iteration!
                break;
            case 'q':
                //do Q-learning!
                break;
            default:
                System.out.println("invalid solution technique, please enter \"v\", \"p\", or \"q\"");
        }
    }

    public static double valueIter() {
        double maxUChange = 0;
        //iterate through every state
        for (State state : states) {
            double bestDirVal = -Double.MAX_VALUE;
            dir bestDir = null;
            //iterate through each possible action
            for (dir direction: dir.values()) {
                //get each neighbor reachable through current action
                State direct = state.getNeighbor(direction);
                State counter = state.getNeighbor(direction.counter());
                State clockwise = state.getNeighbor(direction.clockwise());
                //calculate total utility of each of those neighbors
                double utility = (transition(state, direction, direct) * direct.getValue());
                if (!direct.equals(counter)) {
                utility += (transition(state, direction, counter) * counter.getValue());
                }
                if (!direct.equals(clockwise) && !counter.equals(clockwise)) { 
                utility += (transition(state, direction, clockwise) * clockwise.getValue());
                }
                if (direct.equals(states[40]) || counter.equals(states[40]) || clockwise.equals(states[40])) {
                    utility += (transition(state, direction, states[41]) * states[41].getValue());
                }
                //if this is the best direction update the current direction and value
                if (utility > bestDirVal) {
                    bestDirVal = utility;
                    bestDir = direction;
                }
            }
            //calculate the actual new utility and change in utility
            double newVal = state.getReward() + (Discount_Factor * bestDirVal);
            double uChange = Math.abs(newVal - state.getValue());
            //if change in utility is the largest yet set new max
            if (uChange > maxUChange) maxUChange = uChange;
            //update state value and optimal move
            state.setValue(newVal);
            state.setOptMove(bestDir);
        }
        //return the maximum change in utility
        return maxUChange;
    }
}