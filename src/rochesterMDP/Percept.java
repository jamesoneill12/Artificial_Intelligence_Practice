package rochesterMDP;

/**
 * Created by 1 on 01/05/2017.
 */
/*
 * Created on Aug 24, 2004
 *
 * $Log: Percept.java,v $
 * Revision 1.1  2004/08/25 02:23:04  bh
 * Tested against the optimal policy of the 3x4 world.
 *
 */

/**
 *
 * @author bh
 */
public class Percept {
    State state;
    double reward;

    public Percept(State s, double r) {
        state = s;
        reward = r;
    }
}
