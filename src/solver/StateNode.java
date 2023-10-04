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
        sortBox(state);
        this.move = move;
        for (int i: state)
        {
            stringState += Integer.toString(i);
        }
    }
    private void sortBox(int[] state)
    {
        int min;
        int miny;
        int minIndex;
        for (int i = 2; i < state.length; i+=2)
        {
            min = state[i];
            miny = state[i + 1];
            minIndex = i;
            for (int j = i + 2; j < state.length; j+=2)
            {
                if (min == state[j])
                {
                    if (miny > state[j + 1])
                    {
                        min = state[j];
                        miny = state[j + 1];
                        minIndex = j;
                    }
                }
                else if (min > state[j])
                {
                    min = state[j];
                    miny = state[j + 1];
                    minIndex = j;
                }
            }
            state[minIndex] = state[i];
            state[minIndex + 1] = state[i + 1];
            state[i] = min;
            state[i + 1] = miny;
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

