/**
 * Our MDP class encompasses the entire project 1, this is the main function with which all
 * functions and objects will be created and called from.
 */
public class mdp {

//TODO
//make terminal states terminal!!
//maybe make 1d array of state values to reference by state number
//maybe write function to build 3d array of transition function answers and reward answers
//instead of calling the functions every time.

    //==========  Global Variables  ==========

    public static double Discount_Factor = .99;
    public static double Max_Error = 0;
    public static double Pos_Reward = 1;
    public static double Neg_Reward = -1;
    public static double Step_Cost = -.04;
    public static double Forward_Prob = .8;
    public static double Clockwise_Prob = .1;
    public static double Counter_Prob = .1;
    //private static double[][][] Rewards = new double[65][65][4];
    //private static double[][][] Transition = new double[65][65][4];
    private static State[][][] Game_Board= new State[5][15][2];//odds z = 1, evens z = 0

    //enums to keep directions mapped to one specific value.
    public enum dir {
        N(0),
        S(1),
        E(2),
        W(3);

        public final int val;
        dir(int val) { this.val = val; }
    }

    /**
     * 
     * @param args
     */
    public static void main(String [] args) {
        generateBoard();

        //test functions for the transition function.
        double prob1 = transition(Game_Board[0][0][0], dir.E, Game_Board[0][1][0]);//should be .8
        double prob2 = transition(Game_Board[0][0][0], dir.S, Game_Board[0][1][0]);//should be .1
        double prob3 = transition(Game_Board[0][2][1], dir.E, Game_Board[0][3][0]);//should be .8
        double prob4 = transition(Game_Board[1][3][1], dir.E, Game_Board[0][3][0]);//should be .1
        double prob5 = transition(Game_Board[0][3][0], dir.E, Game_Board[1][2][1]);//should be 0

        //test functions for the reward function.
        double item1 = reward(Game_Board[4][13][0], dir.E, Game_Board[4][14][0]);//should be .96
        double item2 = reward(Game_Board[4][13][0], dir.W, Game_Board[4][14][0]);//should be 0
        double item3 = reward(Game_Board[2][3][0], dir.E, Game_Board[2][4][0]);//should be -1.04
        double item4 = reward(Game_Board[1][3][1], dir.E, Game_Board[0][3][0]);//should be -.04
        double item5 = reward(Game_Board[2][3][0], dir.E, Game_Board[1][3][0]);//should be -.04
        
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

    /**
     * Calculates the reward for going from state start to state next with specified action
     * if the action will not go from start to next then it returns 0, otherwise it returns
     * the step cost added to whatever reward may be at a special stop.
     */
    public static double reward(State start, dir action, State next) {
        double reward = 0;
        if (transition(start, action, next) > 0) {
            reward += Step_Cost;
            int nextStateNum = next.getStateNum();
            switch(nextStateNum) {
                case 44:
                case 45:
                case 48:
                case 49:
                    reward += Neg_Reward;
                    break;
                case 28:
                    reward += Pos_Reward;
                    break;
            }
        }
        return reward;
    }

    /**
     * Return the probability that the specified action takes the agent from the start state
     * to the next state. Has special cases for the key state at state 64.
     */
    public static double transition(State start, dir action, State next) {
        //if start is 63 or 57 then it can reach 64 even though z coord is different
        if ((start.getStateNum() == 63 || start.getStateNum() == 57) 
        && next.getStateNum() == 64) {
            switch(action) {//switch on action to check prob of getting from 63 or 57 to 64
                case N://if going north check if next is above start
                    if (start.getXCoord() - 1 == next.getXCoord()) return Forward_Prob;
                    else if (start.getYCoord() + 1 == next.getYCoord()) return Clockwise_Prob;
                    else if (start.getYCoord() - 1 == next.getYCoord()) return Counter_Prob;
                    else return 0;
                case S://if going south check if next is below start
                    if (start.getXCoord() + 1 == next.getXCoord()) return Forward_Prob;
                    else if (start.getYCoord() + 1 == next.getYCoord()) return Counter_Prob;
                    else if (start.getYCoord() - 1 == next.getYCoord()) return Clockwise_Prob;
                    else return 0;
                case E://if going east check if next is to the right of start
                    if (start.getYCoord() + 1 == next.getYCoord()) return Forward_Prob;
                    else if (start.getXCoord() + 1 == next.getXCoord()) return Counter_Prob;
                    else if (start.getXCoord() - 1 == next.getXCoord()) return Clockwise_Prob;
                    else return 0;
                case W://if going west check if next is to the left of start
                    if (start.getYCoord() - 1 == next.getYCoord()) return Forward_Prob;
                    else if (start.getXCoord() + 1 == next.getXCoord()) return Clockwise_Prob;
                    else if (start.getXCoord() - 1 == next.getXCoord()) return Counter_Prob;
                    else return 0;
                default:
                    System.out.println("invalid action");
            }

        }//check prob of reaching next from start for all non-special cases
        switch(action) {//same switch statement as above with z coord check
            case N:
                if (start.getZCoord() == next.getZCoord()) {//check if z coords are the same
                    if (start.getXCoord() - 1 == next.getXCoord()) return Forward_Prob;
                    else if (start.getYCoord() + 1 == next.getYCoord()) return Clockwise_Prob;
                    else if (start.getYCoord() - 1 == next.getYCoord()) return Counter_Prob;
                }
                return 0;
            case S:
                if (start.getZCoord() == next.getZCoord()) {
                    if (start.getXCoord() + 1 == next.getXCoord()) return Forward_Prob;
                    else if (start.getYCoord() + 1 == next.getYCoord()) return Counter_Prob;
                    else if (start.getYCoord() - 1 == next.getYCoord()) return Clockwise_Prob;
                }
                return 0;
            case E:
                if (start.getZCoord() == next.getZCoord()) {
                    if (start.getYCoord() + 1 == next.getYCoord()) return Forward_Prob;
                    else if (start.getXCoord() + 1 == next.getXCoord()) return Counter_Prob;
                    else if (start.getXCoord() - 1 == next.getXCoord()) return Clockwise_Prob;
                }
                return 0;
            case W:
                if (start.getZCoord() == next.getZCoord()) {
                    if (start.getYCoord() - 1 == next.getYCoord()) return Forward_Prob;
                    else if (start.getXCoord() + 1 == next.getXCoord()) return Clockwise_Prob;
                    else if (start.getXCoord() - 1 == next.getXCoord()) return Counter_Prob;
                }
                return 0;
            default:
                System.out.println("invalid action");
        }
    return 0;
    }

}