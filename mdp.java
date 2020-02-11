public class mdp {

//have 3d array of state objects to generate transition function
//have 1d array of state values to reference by state number
//780 line hard code transition function to assign all answers to possible transitions
//we can write a transition function to actually figure this out with our positional array
//reward function is is also a refferencable 3d array that is usually just the step cost unless it is a +1 or -1
//write function to build 3d array of transition function answers instead of hard coding
//add a get coords function of the node by node number
//also add its North, West, East, South nodes as properties when building the nodes for easy reference in building transition function table
//DO NOT NEED to make actual value iteration calculations. Just the initial step stuff


    public static double Discount_Factor = .99;
    public static double Max_Error = 0;
    public static double Pos_Reward = 1;
    public static double Neg_Reward = -1;
    public static double Step_Cost = -.04;
    public static double Forward_Prob = .8;
    public static double Clockwise_Prob = .1;
    public static double Counter_Prob = .1;
    private static double[][][] Rewards = new double[65][65][4];
    private static double[][][] Transition = new double[65][65][4];
    private static State[][][] Game_Board= new State[5][15][2];//odds z = 1, evens z = 0

    public static void main(String [] args) {
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

        //System.out.println("it do something");
    }

    public static double reward(State start, char action, State next) {
        return 0;
    }

    public static double transition(State start, char action, State next) {
        return 0;
    }

}