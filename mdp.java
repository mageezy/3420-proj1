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
        Game_Board[4][1][0] = new State(0, 4, 1, 0);
        Game_Board[4][1][1] = new State(1, 4, 1, 1);
        Game_Board[4][2][0] = new State(2, 4, 2, 0);
        Game_Board[4][2][1] = new State(3, 4, 2, 1);
        Game_Board[4][3][0] = new State(4, 4, 3, 0);
        Game_Board[4][3][1] = new State(5, 4, 3, 1);
        Game_Board[4][4][0] = new State(6, 4, 4, 0);
        Game_Board[4][4][1] = new State(7, 4, 4, 1);
        Game_Board[4][5][0] = new State(8, 4, 5, 0);
        Game_Board[4][5][1] = new State(9, 4, 5, 1);
        Game_Board[4][6][0] = new State(10, 4, 6, 0);

        //System.out.println("it do something");
    }

    public static double reward(State start, char action, State next) {
        return 0;
    }

    public static double transition(State start, char action, State next) {
        return 0;
    }

}