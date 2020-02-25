import java.lang.Math;

/**
 * Our MDP class encompasses the entire project 1, this is the main function with which all
 * functions and objects will be created and called from.
 */
public class mdp {

    //===============  Global Variables  ===============

    //specifiable here or at command line
    public static double Discount_Factor = 1;
    public static double Max_Error = 1.0E-6;
    public static double Pos_Reward = 1;
    public static double Neg_Reward = -1;
    public static double Step_Cost = -.04;
    public static double Key_Loss_Prob = .5;
    public static char Sol_Type = 'p';

    //specifiable only here
    public static double Forward_Prob = .8;
    public static double Clockwise_Prob = .1;
    public static double Counter_Prob = .1;

    //not to be changed
    private static State[][][] Game_Board= new State[5][15][2];//odds z = 1, evens z = 0
    private static State[] States = new State[65];
    private static double[][] Matrix_Array;//for policy iteration
    private static double[][] Matrix_Rewards;//for policy iteration
    private static int Iters = 0;
    private static long totalTime = 0;
    private static final String Help_Message = 
    "======================== Help Message ========================\n"
    + "When using arguments specified in function run without any arguments\n"
    + "When inputting arguments from command line follow following format:\n"
    + "   java mdp Discount[double] Max_Error[double] key_loss_probability[double]\n"
    + "   pos_reward[double] neg_reward[double] step_cost[double] solution_technique[char]\n"
    + "\n ---------- common values / value options ----------\n"
    + "Discount: between 1 and 0, otherwise values go to infinity\n"
    + "Max_Error: often close to 0, higher value will mean fewer iterations\n"
    + "Key_loss_probability: usually .5, must be between 1 and 0\n"
    + "pos_reward: anything positive, bigger value will overshadow negative reward and step costs\n"
    + "neg_reward: anything negative, bigger value will overshadow positive reward\n"
    + "step_cost: generally negative, if positive terminal states will be avoided\n"
    + "solution_technique: 'v' for value iteration, 'p' for policy iteration, 'q' for Q-learning\n"
    + "===============================================================";

    //for debugging
    private static double[][][] Transitions = new double[65][65][4];

    /**
     * A Direction Enum to make specifiec functions for retrieving the clockwise and counter
     * clockwise directions of a direction easily, and for mapping each direction to a value
     * that can be used for population debugging arrays.
     */
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

    //---------- Start of Functions ---------

    /**
     * Generates a game board, populates an array of every state, initializes every states
     * neighbors, and then runs test cases on reward function, transition function, and 
     * neghbor assignments.
     * @param args
     */
    public static void main(String [] args) {
        //if help is entered output help message and then end the program
        if ((args.length > 0) && (args[0].equals("help"))) {
            System.out.println(Help_Message);
        } else {//if help is not called run the program
            if (args.length > 0) {//if arguments are input on command line change parameters
                try {
                    Discount_Factor = Double.parseDouble(args[0]);
                    Max_Error = Double.parseDouble(args[1]);
                    Key_Loss_Prob = Double.parseDouble(args[2]);
                    Pos_Reward = Double.parseDouble(args[3]);
                    Neg_Reward = Double.parseDouble(args[4]);
                    Step_Cost = Double.parseDouble(args[5]);
                    Sol_Type = args[6].charAt(0);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Bad input");
                    System.out.println("call \"java mdp help\" for a help message");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("ERROR: Too many arguments entered");
                    System.out.println("call \"java mdp help\" for a help message");
                }
            }
            generateBoard();
            genStateArray();
            genNeighbors();
            setRewards();

            // genTransitionArray();//for debugging purposes
            // testFunctions();//for debugging purposes

            long startTime = System.currentTimeMillis();
            int worked = solveMDP(Sol_Type);
            long endTime = System.currentTimeMillis();
            totalTime = endTime - startTime;
            if (worked == 0) {
                printParams();
                printGameBoard();
            }
        }
    }


    //---------- Generating necessary state objects and the data they need ----------


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
                        States[state.getStateNum()] = state;
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
        for (State state : States) {
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
            if (state.getStateNum() == 63) state.setNeighbor(dir.E, States[64]);
            if (state.getStateNum() == 57) state.setNeighbor(dir.N, States[64]);
        }
    }


    // ----------  Functions to print the gameboard and the parameters ----------


    /**
     * Prints out a string representation of the game board to the console. Each state is formatted
     * by the state .toString method. prints out the states in the shape of the gameboard, with a break
     * after the 7th column of states in the bottom row to make it more readable.
     */
    public static void printGameBoard() {
        //iterate through each row
        for (int x = 0; x < 5; x++) {
            //if its the bottom row it is real wide so split it in 2
            if (x == 4) {
                //print out the first 8 pairs in row 4
                for (int z = 0; z < 2; z++) {
                    for (int y = 0; y < 8; y++) {
                        System.out.print(Game_Board[x][y][z] + "  ");
                    }
                    System.out.println();
                }
                System.out.println();
                //print out the next 7 pairs in row 4
                for (int z = 0; z < 2; z++) {
                    System.out.print("   ");
                    for (int y = 8; y < 15; y++) {
                        System.out.print(Game_Board[x][y][z] + "  ");
                    }
                    System.out.println();
                }
            } else {//the other rows have fewer states so no break needed
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
     * prints out all the parameters used in creating a solution for this MDP.
     */
    public static void printParams() {
        System.out.println("\n========== Parameters ==========");
        System.out.println(" Discount Factor: " + Discount_Factor);
        System.out.println(" Maximum State Error: " + Max_Error);
        System.out.println(" Key Loss Probability: " + Key_Loss_Prob);
        System.out.println(" Positive Terminal State Reward: " + Pos_Reward);
        System.out.println(" Negative Terminal State Reward: " + Neg_Reward);
        System.out.println(" Step Cost: " + Step_Cost);
        System.out.println(" Solution Technique: " + Sol_Type + "\n");
        System.out.println(" Iterations: " + Iters);
        System.out.println(" Time: " + totalTime + " milliseconds");
        System.out.println();
    }


    //---------- Functions to calculate and set Rewards ----------


    /**
     * Calculates the reward of the input state. Each state has a reward of the step cost
     * unless the state is one of the terminal states. there is a switch statement to
     * take care of all special cases.
     */
    public static double reward(State state) {
        double reward = 0;
        reward += Step_Cost;
        switch(state.getStateNum()) {
            case 44://any of the negative terminal states
            case 45:
            case 48:
            case 49:
                reward = Neg_Reward;
                break;
            case 28://the only positive terminal state
                reward = Pos_Reward;
                break;
        }
        return reward;
    }
    
    /**
     * Set the reward variable in each state to it's reward so the reward for a state is easy to access.
     */
    public static void setRewards() {
        //iterate through every initialized state
        for (State state : States) {
            double reward = reward(state);
            state.setReward(reward);
            state.setOptMove(dir.N);
        }
    }


    //---------- Functions for moving from one state to another ----------


    /**
     * Return the probability that the specified action takes the agent from the start state
     * to the next state. Jas a special case for terminal states, which have a 0% chance of moving
     * anywhere. Has a special case for even states trying to move to state 41, which is possible
     * because of the key loss probability. Has a special case for moving to state 40, where the
     * probability is decreased because of the key loss probability
     */
    public static double transition(State start, dir action, State next) {
        double prob = 0;
        //if terminal return 0
        if (terminal(start)) return prob;
        //if the starting state is an even state and it is going to 41
        if ((start.getStateNum() % 2 == 0) && (next.getStateNum() == 41)) {
            //calculate the chance of the starting state to go to 40
            if (start.getNeighbor(action).equals(States[40])) prob += Forward_Prob;
            if (start.getNeighbor(action.clockwise()).equals(States[40])) prob += Clockwise_Prob;
            if (start.getNeighbor(action.counter()).equals(States[40])) prob += Counter_Prob;
            prob *= Key_Loss_Prob;//multiply by the key loss prob
        }
        //calculate prob of starting state going to next state with all directions
        if (next.equals(start.getNeighbor(action))) prob += Forward_Prob;
        if (next.equals(start.getNeighbor(action.clockwise()))) prob += Clockwise_Prob;
        if (next.equals(start.getNeighbor(action.counter()))) prob += Counter_Prob;
        //if going to state 40 multiply by 1-key loss probability
        if (next.equals(States[40])) prob *= (1-Key_Loss_Prob);
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


    //--------- Functions to solve the MDP ---------


    /**
     * takes a character input to determine which solution technique should be used in solving
     * the MDP. so far only value iteration has been implemented. calls valueIter function to do
     * the main work in value iteration
     * @param solType
     */
    public static int solveMDP(char solType) {
        boolean cont;
        switch(solType) {
            case 'v':
                cont = true;
                while (cont) {//do value iteration until the stop criterion is reached
                    double maxChange = valueIter();
                    // printGameBoard();
                    Iters++;
                    //check stop criterion
                    if (maxChange <= (Max_Error * (1-Discount_Factor)/Discount_Factor)) cont = false;
                } 
                return 0;
            case 'p':
                cont = true;
                while (cont) {
                    genPolicyMatrix();
                    updatePolicyMatrix();
                    solvePolicyMatrix();
                    boolean changed = simpleValueIter();
                    Iters++;
                    if (!changed) {
                        cont = false;
                    }
                }
                return 0;
            case 'q':
                //do Q-learning!
                return 0;
            default:
                System.out.println("ERROR: invalid solution technique");
                System.out.println("call \"java mdp help\" for a help message");
                return 1;
        }
    }


    //---------- Value Iteration ----------


    /**
     * executes an iteration of value iteration solution technique. iterates through every
     * state and updates the current value of the state based off of the values of the states
     * it can reach through its best possible action. calculates the maximum change in a value of
     * a state throughout the iteration and stores this value to check stop criterion.
     * @return the maximum change in value (utility) of a state
     */
    public static double valueIter() {
        double maxUChange = 0;//to store maximum value change
        //iterate through every state
        for (State state : States) {
            double bestDirVal = -Double.MAX_VALUE;//so that it definitely gets updated
            dir bestDir = null;
            //iterate through each possible action
            for (dir direction: dir.values()) {
                //get each neighbor reachable through current action
                State direct = state.getNeighbor(direction);
                State counter = state.getNeighbor(direction.counter());
                State clockwise = state.getNeighbor(direction.clockwise());
                //calculate total utility of each of those neighbors
                double utility = (transition(state, direction, direct) * direct.getValue());
                //make sure counter neighbor hasn't already been checked
                if (!direct.equals(counter)) {
                utility += (transition(state, direction, counter) * counter.getValue());
                }
                //make sure clockwise neighbor hasn't already been checked
                if (!direct.equals(clockwise) && !counter.equals(clockwise)) { 
                utility += (transition(state, direction, clockwise) * clockwise.getValue());
                }
                //if one of the possible states is 40, then the key loss prob means 41 is also possible
                if (direct.equals(States[40]) || counter.equals(States[40]) || clockwise.equals(States[40])) {
                    utility += (transition(state, direction, States[41]) * States[41].getValue());
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


    //---------- Policy Iteration ----------


    /**
     * generate the initial array of coefficients and rewards to be used for 
     * the policy iteration solution technique.
     */
    public static void genPolicyMatrix() {
        Matrix_Array = new double[65][65];
        Matrix_Rewards = new double[65][1];
        for (int x = 0; x < 65; x++) {
            //fill in the array of rewards to be made into a matrix
            Matrix_Rewards[x][0] = States[x].getReward();
            for (int y = 0; y < 65; y++) {
                //fill in the array of coefficients to be made into a matrix
                if (x==y) Matrix_Array[x][y] = 1;
                else Matrix_Array[x][y] = 0;
            }
        }
    }

    /**
     * subtract transition values from all necessary states in the policy matrix.
     */
    public static void updatePolicyMatrix() {
        for (State start: States) {
            int x = start.getStateNum();
            dir policy = start.getOptMove();

            State direct = start.getNeighbor(policy);
            State clockwise = start.getNeighbor(policy.clockwise());
            State counter = start.getNeighbor(policy.counter());

            int y = direct.getStateNum();
            Matrix_Array[x][y] -= Discount_Factor * transition(start, policy, direct);
            if (!clockwise.equals(direct)) {
                y = clockwise.getStateNum();
                Matrix_Array[x][y] -= Discount_Factor * transition(start, policy, clockwise);
            }
            if (!counter.equals(direct) && !counter.equals(clockwise)) {
                y = counter.getStateNum();
                Matrix_Array[x][y] -= Discount_Factor * transition(start, policy, counter);
            }
            if (direct.equals(States[40]) || counter.equals(States[40]) || clockwise.equals(States[40])) {
                Matrix_Array[x][41] -= Discount_Factor * transition(start, policy, States[41]);
            }
        }
    }

    public static void solvePolicyMatrix() {
        Jama.Matrix Jama_Matrix_Array = new Jama.Matrix(Matrix_Array);
        Jama.Matrix Jama_Matrix_Rewards = new Jama.Matrix(Matrix_Rewards);
        Jama.Matrix solved = Jama_Matrix_Array.solve(Jama_Matrix_Rewards);


        for(int x = 0; x < 65; x++){
            States[x].setValue(solved.getArray()[x][0]);
        }
    }

    //can this be more optimized?


    public static boolean simpleValueIter() {
        boolean changed = false;//to check if the policy changes between iterations
        //iterate through every state
        for (State state : States) {
            double bestDirVal = -Double.MAX_VALUE;//so that it definitely gets updated
            dir bestDir = null;
            //iterate through each possible action
            for (dir direction: dir.values()) {
                //get each neighbor reachable through current action
                State direct = state.getNeighbor(direction);
                State counter = state.getNeighbor(direction.counter());
                State clockwise = state.getNeighbor(direction.clockwise());
                //calculate total utility of each of those neighbors
                double utility = (transition(state, direction, direct) * direct.getValue());
                //make sure counter neighbor hasn't already been checked
                if (!direct.equals(counter)) {
                utility += (transition(state, direction, counter) * counter.getValue());
                }
                //make sure clockwise neighbor hasn't already been checked
                if (!direct.equals(clockwise) && !counter.equals(clockwise)) { 
                utility += (transition(state, direction, clockwise) * clockwise.getValue());
                }
                //if one of the possible states is 40, then the key loss prob means 41 is also possible
                if (direct.equals(States[40]) || counter.equals(States[40]) || clockwise.equals(States[40])) {
                    utility += (transition(state, direction, States[41]) * States[41].getValue());
                }
                //if this is the best direction update the current direction and value
                if (utility > bestDirVal) {
                    bestDirVal = utility;
                    bestDir = direction;
                }
            }
            //calculate the actual new utility and change in utility
            double newVal = state.getReward() + bestDirVal;
            //update state value and optimal move
            if (state.getOptMove() != bestDir) changed = true;
            state.setValue(newVal);
            state.setOptMove(bestDir);
        }
        //return if the policy changed
        return changed;
    }


    //---------- Debugging Functions ----------


    /**
     * this function populates the array of transition values for every state to every other state
     * for every action. **used only for debugging**
     */
    public static void genTransitionArray() {
        for (State start: States) {
            for (State next: States) {
                for (dir direction: dir.values()) {
                    int startNum = start.getStateNum();
                    int nextNum = next.getStateNum();
                    Transitions[startNum][nextNum][direction.val] = transition(start, direction, next);
                    if (nextNum == 40) {
                        Transitions[startNum][41][direction.val] = transition(start, direction, States[41]);
                    }
                }
            }
        }
    }

    /**
     * Function that calls lots of tests on various objects and data structures used in this
     * program. Used purely to make sure different things are working, has no function in
     * the program.
     */
    public static void testFunctions() {
        //test functions for the transition function.
        double prob1 = transition(States[62], dir.W, States[62]);//should be .1
        double prob2 = transition(States[63], dir.E, States[64]);//should be .8
        double prob3 = transition(States[44], dir.E, States[46]);//should be 0
        double prob4 = transition(States[30], dir.N, States[40]);//should be .4
        double prob5 = transition(States[30], dir.N, States[41]);//should be .4

        // //test functions for the reward function.
        double item1 = reward(States[28]);//should be 1
        double item2 = reward(States[29]);//should be -.04
        double item3 = reward(States[44]);//should be -1
        double item4 = reward(States[10]);//should be -.04
        double item5 = reward(States[62]);//should be -.04

        //neighbor test functions.
        State nbor1 = States[59].getNeighbor(dir.E);//should be 61
        State nbor2 = States[59].getNeighbor(dir.W);//should be 59
        State nbor3 = States[59].getNeighbor(dir.S);//should be 51
        State nbor4 = States[59].getNeighbor(dir.N);//should be 59
        
        //print out transition tests
        System.out.println("\n===== Transition Tests =====");
        System.out.println(prob1);
        System.out.println(prob2);
        System.out.println(prob3);
        System.out.println(prob4);
        System.out.println(prob5);

        //print out reward tests.
        System.out.println("\n===== Reward Tests =====");
        System.out.println(item1);
        System.out.println(item2);
        System.out.println(item3);
        System.out.println(item4);
        System.out.println(item5);

        //print out neighbor tests.
        System.out.println("\n===== Neighbor Tests =====");
        System.out.println(nbor1);
        System.out.println(nbor2);
        System.out.println(nbor3);
        System.out.println(nbor4);
    }
}