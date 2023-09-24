package solver;

import java.util.*;

public class SokoBot {
  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {

    int x = 0;
    int y = 0;
    ArrayList<StateNode> states = new ArrayList<>(); // list of states visited
    Map<Integer, Integer> targets = new HashMap<Integer, Integer>(); // list of targets
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
          targets.put(i, j);
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
    frontier.add(new StateNode(0, 0, -1, currentState, ' ')); // starting state added to frontier

    System.out.println("height: " + height + " width: " + width);
    System.out.println("x: " + x + " y: " + y);
    System.out.println(actions(width, height, x, y, mapData, itemsData));

    for (char action: actions(width, height, x, y, mapData, itemsData))
    {
      System.out.println(succeed(0, 0, currentState, action, targets).getCost());
    }
    return "lrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlr";
  }

  public static int heuristic(int[] state, Map<Integer, Integer> targets)
  {
//     heuristic value = minimum manhattan distance between each box and a target
    int distance;
    int sum = 0;
    int value;
    for (int i = 2; i < state.length; i+=2)
    {
      distance = Integer.MAX_VALUE;
      for (Map.Entry<Integer, Integer> set: targets.entrySet())
      {
        value = Math.abs(state[i] - set.getKey()) + Math.abs(state[i + 1] - set.getValue());
        if (distance > value)
        {
          distance = value;
        }
      }
      sum += distance;
    }
    return sum;
  }

  public static StateNode succeed(int actualCost, int previousState, int[] state, char move, Map<Integer, Integer> targets)
  {
    int newX = succX(state[0], move);
    int newY = succY(state[1], move);
    int[] newState = Arrays.copyOf(state, state.length);
    newState[0] = newX;
    newState[1] = newY;
    int cost = 0; // just move in a free space
    boolean checkCost = false;

    for (Map.Entry<Integer, Integer> set: targets.entrySet())
    {
      for (int i = 2; i < state.length; i+=2)
      {
        if (newX == state[i] && newY == state[i + 1])
        {
          newState[i] = succX(state[i], move);
          newState[i + 1] = succY(state[i + 1], move);
          // if move box out of target, cost = 2
          if (newX == set.getKey() && newY == set.getValue())
          {
            cost = 2;
          }
          else
          {
            cost = 1; // if move box, cost = 1
          }
          checkCost = true;
          break;

        }
        if (checkCost)
        {
          break;
        }
      }

    }
    return new StateNode(cost + actualCost + heuristic(state, targets), actualCost, previousState, newState, move);

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

  public static ArrayList<Character> actions(int width, int height, int x, int y, char[][] mapData, char[][] itemsData)
  {

    // need to implement where it checks if the action will go to a visited state

    ArrayList<Character> moves = new ArrayList<>();
    if (mapData[y][x + 1] != '#')
    {
      if (itemsData[y][x + 1] == '$')
      {
        if (x + 2 < width)
        {
          if (mapData[y][x + 2] != '#' && itemsData[y][x + 2] != '$')
          {
            moves.add('r');
          }
        }
        else
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
        if (x - 2 >= 0)
        {
          if (mapData[y][x - 2] != '#' && itemsData[y][x - 2] != '$')
          {
            moves.add('l');
          }
        }
        else
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
        if (y + 2 < height)
        {
          if (mapData[y + 2][x] != '#' && itemsData[y + 2][x] != '$')
          {
            moves.add('d');
          }
        }
        else
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
        if (y - 2 >= 0)
        {
          if (mapData[y - 2][x] != '#' && itemsData[y - 2][x] != '$')
          {
            moves.add('u');
          }
        }
        else
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
