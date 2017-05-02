package MDP;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.*;

/**
 * Created by 1 on 23/04/2017.
 *
 * Description - The algorithm can be split into three parts
 * Gameboard - The initialization of the matrix and initializing the transition probabilities and
 * also the pitfalls and destiniation and the associated rewards.
 *
 * Player - transitions through th
 */
public class MDP {

    int state = 0;
    static final double discountF = 0.9;
    static int num_steps = 10000;

    static int width = 10;
    static int height = 10;
    static int xPosition = 0;
    static int yPosition = 0;

    static double[][] states = new double[width][height];
    static double[][][] transitionP = new double[width][height][4];
    static double[][][] cumTransition = new double[width][height][4];

    double[][][] rewards = new double[width][height][4];
    static int[][] optimalPolicy = new int[width][height];

    static final int ACTION_UP = 0;
    static final int ACTION_RIGHT = 1;
    static final int ACTION_DOWN = 2;
    static final int ACTION_LEFT = 3;
    //static final int ACTION_STAY = 4; //stay is not an action per se.
    static final int numActions = 4;

    // 0=up, 1=right, 2=down, 3=left

    public String[] actions = {"up","right","down","left"};

    // agent cannot go down if y = 0
    // agent cannot go left if x = 0
    // agent cannot go up if y = 10
    // agent cannot go right if x = 10

    Random generator = new Random();

    public MDP(){

        initArray();
        initProbs();
        //setGoal(width-1,height-1);
        //setTrap(width-4,height-1);
        //setTrap(width-1,height-3);
    }

    public MDP(int xlength, int ylength){

        width = xlength;
        height = ylength;
        states = new double[width][height];
        transitionP = new double[width][height][4];
        cumTransition = new double[width][height][4];
        rewards = new double[width][height][4];
        optimalPolicy = new int[width][height];

        initArray();
        initProbs();

        //setGoal(width-1,height-1,20);
        //setTrap(width-1,height-1);
        //setTrap(width-1,height-2);

    }

    public MDP(int xlength, int ylength,int xPosition, int yPosition){

        width = xlength;
        height = ylength;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        states = new double[width][height];
        transitionP = new double[width][height][4];
        cumTransition = new double[width][height][4];
        rewards = new double[width][height][4];
        optimalPolicy = new int[width][height];

        initArray();
        initProbs();

        //setGoal(width-1,height-1,20);
        //setTrap(width-1,height-1);
        //setTrap(width-1,height-2);

    }

    private double[][][] initProbs(){

        double[][][] transitions = new double[width][height][4];
        double sum = 0;

        for(int x=0; x < width; x++) {
            // 0=up, 1=right, 2=down, 3=left
            for (int y = 0; y < height; y++) {

                double cumProbs = DoubleStream.of(transitions[x][y]).sum();
                for (int i = 0; i < 4; i++) {

                    // cant go left or right on sides
                    if (x == 0 && i == 3) {
                        transitions[x][y][i] = 0;
                    } else if (x == width - 1 && i == 1) {
                        transitions[x][y][i] = 0;
                    }
                    // cant go up or down on top and bottom of board
                    else if (y == height - 1 && i == 0) {
                        transitions[x][y][i] = 0;
                    } else if (y == height - 1 && i == 2) {
                        transitions[x][y][i] = 0;
                    } else {
                        transitions[x][y][i] = generator.nextDouble();
                    }

                }
            }
        }

        transitions = normalizeProbs(transitions);

        MDP.cumTransition = getcumTransition(transitions);
        MDP.transitionP = transitions;
        return(transitions);
    }

    private static void initArray(){

        double[][] states = new double[width][height];
        for(double[] row: states){
            Arrays.fill(row,0);
        }

        states[width-1][height-1] = 10;
        states[width-2][height-3] = -20;

        MDP.states = states;
    }

    private double[][][] getcumTransition(double[][][] transitions){
        for(int x=0; x < width; x++) {

            for (int y = 0; y < height; y++) {
                for (int j = 0; j < 4; j++) {
                    if (j == 0) {
                        cumTransition[x][y][j] = transitions[x][y][j];
                    } else {
                        cumTransition[x][y][j] = transitions[x][y][j] + cumTransition[x][y][j - 1];
                    }
                }
            }
        }
        return (cumTransition);
    }

    private double[][][] normalizeProbs(double[][][] transitions){
        for(int x=0; x < width; x++) {
            // 0=up, 1=right, 2=down, 3=left
            for (int y = 0; y < height; y++) {
                double cumProbs = DoubleStream.of(transitions[x][y]).sum();

                for (int i = 0; i < 4; i++) {
                    transitions[x][y][i] /=cumProbs;
                }

            }
        }
        return (transitions);
    }

    // This function samples from the transition probabilities to find
    // the next tranition and returns the state

    public void getTransition(int x, int y, int action){

        double randomVal = generator.nextDouble();
        for(int i =0; i<MDP.transitionP[x][y].length; i++) {

            // 0=up, 1=right, 2=down, 3=left
            // If the uniformly random value exceeds the cumulative probability
            // take that transition index and move to this space accordingly.

            if (randomVal > MDP.cumTransition[x][y][i]){
                states = move(states,i);
            }
            break;
        }
    }

    public double ValueIteration(){
        return 0.0;
    }

    public double[][] move(double s[][],int action){

        //if (action == ACTION_STAY)
        //    return s;

        // If the movement is not possible, it stays where it was.
        // This is a hidden rule and should be revealed to the outside
        // world through some interface(s).
        // If there's a hole, it'll return null.  All taken care of
        // by grid[][].

        switch (action) {
            case ACTION_UP:
                xPosition+= 1;
                if(xPosition > width-1)
                    xPosition = xPosition-1;
                break;
            case ACTION_RIGHT:
                yPosition += 1;
                if(yPosition > height-1)
                    yPosition = yPosition-1;
                break;
            case ACTION_DOWN:
                xPosition -= 1;
                if(xPosition < 0)
                    xPosition = 0;
                break;
            case ACTION_LEFT:
                height -= 1;
                if(height < 0)
                    height = 0;
                break;
        }
        // if the destination is a hole, stay where it was.
        if(MDP.states[xPosition][yPosition] == 0)
            return s;
        else
            return s;
    }

    public void run(){
        move(states,0);
    }

    public void printStates(){System.out.print(Arrays.deepToString(MDP.states));}
    public void printcumTransition(){
        System.out.println(Arrays.deepToString(MDP.cumTransition));
    }
    public void printProbs(){
        System.out.println(Arrays.deepToString(MDP.transitionP));
    }

    public static void transition(int state, String action){}
    public static void reward(){}

    public void setGoal(int x,int y) {
        this.states[x][y] = 10;
    }


    public void setGoal(int x,int y, int val) {
        this.states[x][y] = val;
    }

    public void setTrap(int x,int y) {
        this.states[x][y] = -20;
    }

    public void setTrap(int x,int y, int val) {
        this.states[x][y] = -val;
    }

    double randomGenerator() {
        return generator.nextDouble()*0.5;
    }

    double getSum(double[] m){
        int i = 0;
        int sum = 0;// Create a separate integer to serve as your array indexer.
        while(i < 4) {   // The indexer needs to be less than 10, not A itself.
            sum += m[i];   // either sum = sum + ... or sum += ..., but not both
            i++;           // You need to increment the index at the end of the loop.
        }
        return sum;
    }

    double[][] valueIteration(){double[][] j = new double[width][height]; return j;}
    double[][] optimalPolicy(){double[][] j = new double[width][height]; return j;}

    double getStartState(){
        return (MDP.states[0][0]);
    }

    double getNextState(){
        return (MDP.states[xPosition][yPosition]);
    }

    double getStartAction(){
        return (MDP.states[0][0]);
    }

    double getNextAction(){
        // up 0, right 1, down 2, left 3
        return ((int) generator.nextFloat()*4);
    }


    public static void main(String[] args){

        MDP gameboard = new MDP(4,4);
        //gameboard.printProbs();
        //gameboard.printcumTransition();
        gameboard.printStates();
        gameboard.run();

    }
}
