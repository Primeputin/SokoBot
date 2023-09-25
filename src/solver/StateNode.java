package solver;

import java.util.ArrayList;
import java.util.Arrays;

public class StateNode {
    private final int cost;
    private final int actualCost;
    private int index;
    private final int previousState;
    private final int[] state;
    private final char move;

    public StateNode(int cost, int actualCost, int index, int previousState, int[] state, char move)
    {
        this.cost = cost;
        this.actualCost = actualCost;
        this.index = index;
        this.previousState = previousState;
        this.state = Arrays.copyOf(state, state.length);
        this.move = move;
    }

    public StateNode(int cost, int actualCost, int previousState, int[] state, char move)
    {
        this.cost = cost;
        this.actualCost = actualCost;
        this.index = -1;
        this.previousState = previousState;
        this.state = Arrays.copyOf(state, state.length);
        this.move = move;
    }


    boolean sameStateToAll(ArrayList<StateNode> comparedStates)
    {
        int count;
        for (StateNode certainState: comparedStates)
        {
            count = 0;
            for (int i = 0; i < certainState.getState().length; i++)
            {
                if (this.state[i] == certainState.getState()[i])
                {
                    count += 1;
                }
            }
            if (count == this.state.length)
            {
                return true;
            }
        }
        return false;
    }

    public int getCost()
    {
        return cost;
    }

    public int getActualCost()
    {
        return actualCost;
    }

    public int getIndex()
    {
        return index;
    }

    public int getPreviousState()
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

    public void setIndex(int index)
    {
        this.index = index;
    }

}

