package HMM;

import Parsers.FileParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by 1 on 01/05/2017.
 * Each sentence is a sequence of words with a fixed length of 20
 *
 */
public class HMM {

    static int sequenceLength = 100;
    static int numSentences = 20;
    static double[][] sequences = new double[sequenceLength][numSentences];

    HashMap<String, Integer> cache = new HashMap<String, Integer>();
    List<String> sentences = new ArrayList<String>();
    FileParser fp = new FileParser();

    static double[][][] transitionMat = new double[sequenceLength][numSentences][2];
    static double[][] emissionMat = new double[sequenceLength][sequenceLength];
    static double[] initProbs = new double[sequenceLength];


    public HMM(String filename){
        sentences = fp.getDocument(filename);
        run();
    }

    public HMM(String filename,int sequenceLength, int numSentences){
        sentences = fp.getDocument(filename);
        this.sequenceLength = sequenceLength;
        this.numSentences = numSentences;
        run();
    }

    public void run(){}

    private double[] viterbiL(double[] sequence){

        double[][] mat = new double[sequenceLength][sequenceLength];
        int[][] matTb = new int[sequenceLength][sequenceLength];

        // Fill first column

        for(double[] row:emissionMat){
            Arrays.fill(row,0);
        }


        return(sequence);
    }
}
