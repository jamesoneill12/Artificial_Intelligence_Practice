package rochesterMDP;

/*
 * Created on Aug 17, 2004
 *
 */
import java.io.FileNotFoundException;

/**
 * @author bh
 *
 */
public class ValueIterationTest {

    public static void main(String args[]) {
        try {
            MDPFileParser parser = new MDPFileParser("textbook.txt");
            MarkovDecisionProcess mdp = parser.parse();

            ValueIteration vi = new ValueIteration(mdp);
            vi.setError(1e-4);
            vi.solve();
            mdp.dumpHTML(false);

        } catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

}
