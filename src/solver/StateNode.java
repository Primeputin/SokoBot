package solver;

import java.util.Arrays;

public class StateNode {
    private final int cost;
    private final int actualCost;
    private String previousState;
    private final int[] state;
    private final char move;
    private String stringState;

    public StateNode(int cost, int actualCost, String previousState, int[] state, char move)
    {
        this.cost = cost;
        this.actualCost = actualCost;
        this.previousState = previousState;
        this.state = Arrays.copyOf(state, state.length);
        this.move = move;
        for (int i: state)
        {
            stringState += Integer.toString(i);
        }
    }

    public int getCost()
    {
        return cost;
    }

    public int getActualCost()
    {
        return actualCost;
    }

    public String getPreviousState()
    {
        return previousState;
    }

    public int[] getState()
    {
        return state;
    }

    public char getMove()
    {
        return move;
    }

    public String getStringState()
    {
        return stringState;
    }

}

