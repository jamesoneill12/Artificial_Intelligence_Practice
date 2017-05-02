package rochesterMDP;

/*
 * Created on Aug 16, 2004
 *
 * $Log: Transition.java,v $
 * Revision 1.1  2004/08/19 03:19:19  bh
 * Tested against the textbook 3x4 world.  The transition model is still
 * not ideal.
 *
 */

/**
 * @author bh
 *
 */
public class Transition {
    double probability;
    State nextState;

    public Transition(double probability, State state) {
        this.probability = probability;
        this.nextState = state;
    }
}

