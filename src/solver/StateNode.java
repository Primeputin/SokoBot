package solver;

import java.util.Arrays;

public class StateNode {
    int cost;
    int actualCost;
    int previousState;
    int[] state;
    char move;

    public StateNode(int cost, int actualCost, int previousState, int[] state, char move)
    {
        this.cost = cost;
        this.actualCost = actualCost;
        this.previousState = previousState;
        this.state = Arrays.copyOf(state, state.length);
        this.move = move;
    }

    public StateNode(int actualCost, int previousState, int[] state, char move)
    {
        this.actualCost = actualCost;
        this.previousState = previousState;
        this.state = Arrays.copyOf(state, state.length);
        this.move = move;
    }

    public int getCost()
    {
        return cost;
    }

    public int getActualCost()
    {
        return actualCost;
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

    public void setCost(int cost)
    {
        this.cost = cost;
    }
}
