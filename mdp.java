public class mdp {

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

    public static void main(String [] args) {
        State rando = new State(1, 'N', 1, 1, 1);
        //System.out.println("it do something");
    }

    public static double reward(State start, char action, State next) {
        return 0;
    }

    public static double transition(State start, char action, State next) {
        return 0;
    }

}