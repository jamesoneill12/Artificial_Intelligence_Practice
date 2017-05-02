package rochesterMDP;

import java.util.Vector;
import Jama.*;

/*
 * Created on Aug 18, 2004
 *
 * $Log: PolicyEvaluator.java,v $
 * Revision 1.1  2004/08/24 14:41:24  bh
 * Changed the name from PolicyEvaluation to PolicyEvaluator.
 *
 * Revision 1.5  2004/08/23 23:43:52  bh
 * Souce clean.
 *
 * Revision 1.4  2004/08/23 23:34:20  bh
 * Source clean.
 *
 * Revision 1.3  2004/08/23 15:48:59  bh
 * Now it throws an exception is the solution matrix is near singular.
 *
 * Revision 1.2  2004/08/20 00:33:10  bh
 * Set b[][] to zero as well, though not necessary.
 *
 * Revision 1.1  2004/08/19 03:19:19  bh
 * Tested against the textbook 3x4 world.  The transition model is still
 * not ideal.
 *
 */

/**
 *
 * Implement the policy evaluation algorithm for a 2D MDP. Essentially the
 * algorithm is to solve a (large) linear system. The key is to assemble the
 * solution matrix and find a linear algebra package to solve it. A good
 * starting point is <a href="http://math.nist.gov/javanumerics/">
 * http://math.nist.gov/javanumerics/ </a>.  It has very thorough information
 * about numerical packages in Java.  We use JAMA here.
 * <p>
 * A thing worthy to point out is that the solution matrix is very sparse.
 * In fact, only entries near the diagonal are not zeroes.  It's possible (and
 * necessary to large MDPs) to use routines specially designed for sparse
 * linear systems.  But this is something only a miserable graduate student
 * needs to care about.
 *
 * @author bh
 */
public class PolicyEvaluator {

    MarkovDecisionProcess mdp;

    /**
     * Size ~=(rows*cols) of the MDP.
     */
    int size;

    double gamma;

    /**
     * The linear system is Ax=b
     */
    double[][] A;

    double[][] b;

    public PolicyEvaluator(MarkovDecisionProcess mdp)
        throws IllegalStateException {
        this.mdp = mdp;
        size = mdp.getReachableSize();
        gamma = mdp.getGamma();

        if (size == 0) {
            throw new IllegalStateException("MDP is not prepared.");
        }

        A = new double[size][size];
        // JAMA doesn't support a standalone vector class.
        b = new double[size][1];
    }

    public void solve() {

        // Precondition:
        // There is a policy in the mdp already

        for (int i = 0; i < size; ++i){
            b[i][0] = 0.;
            for (int j = 0; j < size; ++j)
                A[i][j] = 0.;
        }

        // Assemble the N*N utility solution matrix, N=rows*cols
        State state = mdp.getStartState();

        while (state != null) {
            int sIndex = state.index;

            A[sIndex][sIndex] = 1.0;
            b[sIndex][0] = mdp.getReward(state);

            Action action = mdp.getAction(state);
            Vector T = mdp.getTransition(state, action);
            int s = T.size();
            for (int i = 0; i < s; ++i) {
                Transition t = (Transition) T.get(i);
                double prob = t.probability;
                State sPrime = t.nextState;
                if (sPrime.terminate) {
                    b[sIndex][0] += gamma * prob * mdp.getUtility(sPrime);
                } else
                    A[sIndex][sPrime.index] -= gamma * prob;

            }

            state = mdp.getNextState();
        }

        // Solve for x in Ax=b
        Matrix mA = new Matrix(A);
        if(mA.cond() > 1e3)
            throw (new ArithmeticException("Singular solution matrix."));

        Matrix mb = new Matrix(b);
        Matrix x = mA.solve(mb);

        // Update the utilities of the mdp
        for (int i = 0; i < size; ++i)
            mdp.setUtility(i, x.get(i, 0));

    }
}
