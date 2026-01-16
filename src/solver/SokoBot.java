package solver;

import java.util.*;

public class SokoBot {
  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {

    int x = 0;
    int y = 0;
    HashMap<String, StateNode> states = new HashMap<>(5000);
    ArrayList<Integer> targets = new ArrayList<>(); // list of targets
    // priority queue based on the cost of the state
    PriorityQueue<StateNode> frontier = new PriorityQueue<>(new Comparator<StateNode>() {
      @Override
      public int compare(StateNode state1, StateNode state2) {
        return Integer.compare(state1.getCost(), state2.getCost());
      }
    });

    ArrayList<Integer> boxes = new ArrayList<>(); // list of current boxes' position

    // Finding the position of the player, boxes, and targets
    for (int i = 0; i < width; i++)
    {
      for (int j = 0; j < height; j++)
      {
        if (itemsData[j][i] == '@')
        {
          x = i;
          y = j;
        }
        else if (itemsData[j][i] == '$')
        {
          boxes.add(i);
          boxes.add(j);
        }

        if (mapData[j][i] == '.')
        {
          targets.add(i);
          targets.add(j);
        }

      }
    }
    // Adding the starting state to the visited states
    int[] currentState = new int[boxes.size() + 2];
    currentState[0] = x;
    currentState[1] = y;
    for (int i = 2; i < currentState.length; i++)
    {
      currentState[i] =  boxes.get(i - 2);
    }

    frontier.add(new StateNode(0, 0, "", currentState, ' ')); // starting state added to frontier
    StateNode removed;
    StateNode nextState;
    while (!frontier.isEmpty())
    {
      removed = frontier.poll();
      if (isEnd(removed.getState(), targets))
      {
        return returnSolution(states, removed);
      }

      states.put(removed.getStringState(), removed);

      // apply the current state to the item data
      // it doesn't matter if the target data got overwritten since it's already saved before in another list
      for (int i = 0; i < boxes.size(); i+=2) // clearing the last applied state
      {
        itemsData[boxes.get(i + 1)][boxes.get(i)] = ' ';
      }
      for (int i = 0; i < boxes.size(); i+=2) // applying the current state
      {
        itemsData[removed.getState()[i + 3]][removed.getState()[i + 2]] = '$';
        itemsData[removed.getState()[1]][removed.getState()[0]] = '@';
        boxes.set(i + 1 , removed.getState()[i + 3]); // storing for the next state to be cleared
        boxes.set(i, removed.getState()[i + 2]);
      }

      for (char action: actions(width, height, removed.getState()[0], removed.getState()[1], mapData, itemsData, targets))
      {
        nextState = succeed(removed.getActualCost(), removed.getStringState(), removed.getState(), action, targets);
        // if not visited, add to the frontier
        if (!states.containsKey(nextState.getStringState()))
        {
          frontier.add(nextState);
        }
      }

    }



//    System.out.println("height: " + height + " width: " + width);
//    System.out.println("x: " + x + " y: " + y);
//    System.out.println(actions(width, height, x, y, mapData, itemsData));
    return "lrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlr";
  }

  public static String returnSolution(HashMap<String, StateNode> states, StateNode goal)
  {
    StateNode temp = goal;
    String todo = "";
    while (temp.getPreviousState() != "")
    {
      todo = temp.getMove() + todo;
      temp = states.get(temp.getPreviousState());
    }
    return todo;
  }

  public static boolean isEnd(int[] state, ArrayList<Integer> targets)
  {
    boolean found;
    for (int i = 2; i < state.length; i+=2)
    {
      found = false;
      for (int j = 0; j < targets.size(); j+=2)
      {

        if (targets.get(j) == state[i] && targets.get(j + 1) == state[i + 1])
        {
          found = true;
        }
      }

      if (!found)
      {
        return false;
      }
    }
    return true;
  }
  public static int heuristic(int[] state, ArrayList<Integer> targets)
  {
//     heuristic value = minimum manhattan distance between each box and a target
    int distance;
    int sum = 0;
    int value;
    for (int i = 2; i < state.length; i+=2)
    {
      distance = Integer.MAX_VALUE;
      for (int j = 0; j < targets.size(); j+=2)
      {
        value = Math.abs(state[i] - targets.get(j)) + Math.abs(state[i + 1] - targets.get(j + 1));
        if (distance > value)
        {
          distance = value;
        }
      }
      sum += distance;
    }
    return sum;
  }

  public static StateNode succeed(int actualCost, String previousState, int[] state, char move, ArrayList<Integer> targets)
  {
    int newX = succX(state[0], move);
    int newY = succY(state[1], move);
    int[] newState = Arrays.copyOf(state, state.length);
    newState[0] = newX;
    newState[1] = newY;
    int cost = 1; // just move in a free space
    boolean movedBox = false;

    for (int i = 0; i < targets.size(); i+=2)
    {
      for (int j = 2; j < state.length; j+=2)
      {
        if (newX == state[j] && newY == state[j + 1])
        {
          newState[j] = succX(state[j], move);
          newState[j + 1] = succY(state[j + 1], move);

          movedBox = true;
          break;

        }
      }
      if (movedBox)
      {
        break;
      }

    }

    return new StateNode(cost + actualCost + heuristic(newState, targets), actualCost + cost, previousState, newState, move);

  }

  public static int succX(int x , char move)
  {
      return switch (move) {
          case 'r' -> x + 1;
          case 'l' -> x - 1;
          default -> x;
      };
  }

  public static int succY(int y, char move)
  {
      return switch (move) {
          case 'd' -> y + 1;
          case 'u' -> y - 1;
          default -> y;
      };
  }

  public static boolean isInTargets(int x, int y, ArrayList<Integer> targets)
  {
    for (int i = 0; i < targets.size(); i+=2)
    {
      if (x == targets.get(i) && y == targets.get(i + 1))
      {
        return true;
      }
    }
    return false;
  }

  public static boolean isCorner(int x, int y, char[][] mapData, ArrayList<Integer> targets)
  {
    boolean horizontal = false;
    boolean vertical = false;

    if (isInTargets(x + 1, y, targets) || isInTargets(x - 1, y, targets) || isInTargets(x, y -1, targets) || isInTargets(x, y + 1, targets) || isInTargets(x, y, targets))
    {
      return false;
    }
    if (mapData[y][x + 1] == '#' || mapData[y][x - 1] == '#')
    {
      horizontal = true;
    }
    if (mapData[y + 1][x] == '#' || mapData[y - 1][x] == '#')
    {
      vertical = true;
    }
    return horizontal && vertical;
  }

  public static ArrayList<Character> actions(int width, int height, int x, int y, char[][] mapData, char[][] itemsData, ArrayList<Integer> targets)
  {

    // need to implement where it checks if the action will go to a visited state

    ArrayList<Character> moves = new ArrayList<>();
    if (mapData[y][x + 1] != '#')
    {
      if (itemsData[y][x + 1] == '$')
      {
          if (mapData[y][x + 2] != '#' && itemsData[y][x + 2] != '$' && !isCorner(x + 2, y, mapData, targets))
          {
              moves.add('r');
          }
      }
      else
      {
        moves.add('r');
      }
    }

    if (mapData[y][x - 1] != '#')
    {
      if (itemsData[y][x - 1] == '$')
      {
          if (mapData[y][x - 2] != '#' && itemsData[y][x - 2] != '$' && !isCorner(x - 2, y, mapData, targets))
          {
              moves.add('l');
          }

      }
      else
      {
        moves.add('l');
      }
    }

    if (mapData[y + 1][x] != '#')
    {
      if (itemsData[y + 1][x] == '$')
      {

          if (mapData[y + 2][x] != '#' && itemsData[y + 2][x] != '$' && !isCorner(x, y + 2, mapData, targets))
          {
              moves.add('d');

          }
      }
      else
      {
        moves.add('d');
      }
    }

    if (mapData[y - 1][x] != '#')
    {
      if (itemsData[y - 1][x] == '$')
      {

          if (mapData[y - 2][x] != '#' && itemsData[y - 2][x] != '$' && !isCorner(x, y - 2, mapData, targets))
          {
              moves.add('u');
          }

      }
      else
      {
        moves.add('u');
      }
    }
    return moves;

  }



}
