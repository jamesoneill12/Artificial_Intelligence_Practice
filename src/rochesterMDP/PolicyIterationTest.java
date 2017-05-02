package rochesterMDP;

/*
 * Created on Aug 18, 2004
 */
import cs.decision.*;
import java.io.FileNotFoundException;

/**
 * @author bh
 *
 */
public class PolicyIterationTest {

    MarkovDecisionProcess mdp;

    /**
     * Constructor for PolicyIterationTest.
     * @param // FIXME: 01/05/2017
     */
    public static void main(String[] args) {
        try {
            MDPFileParser parser = new MDPFileParser("C:/Users/1/James/Java/src/rochesterMDP/textbook.txt");
            MarkovDecisionProcess mdp = parser.parse();

            PolicyIteration pi = new PolicyIteration(mdp);

            pi.solve();
            mdp.dumpHTML(false);

        } catch (FileNotFoundException e){
            System.out.println(e);
        }
    }



}
