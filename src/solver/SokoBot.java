package solver;

import java.util.*;

public class SokoBot {
  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {

    int x = 0;
    int y = 0;
    ArrayList<int[]> states = new ArrayList<>();
    Map<Integer, Integer> targets = new HashMap<Integer, Integer>();
    PriorityQueue<Integer> minCosts = new PriorityQueue<>();
    ArrayList<Integer> boxes = new ArrayList<>();

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
    int[] startState = new int[boxes.size()];
    startState[0] = x;
    startState[1] = y;
    for (int i = 2; i <  boxes.size(); i++)
    {
      startState[i] =  boxes.get(i);
    }
    states.add(startState);


    System.out.println("height: " + height + " width: " + width);
    System.out.println("x: " + x + " y: " + y);
    System.out.println(actions(width, height, x, y, mapData, itemsData));

    for (char action: actions(width, height, x, y, mapData, itemsData))
    {
      System.out.println(cost(succX(x, action), succY(y, action), boxes));
    }
    return "lrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlr";
  }

  public static int cost(int x, int y, ArrayList<Integer> boxes)
  {
    // if move box out of target, cost = 2
    // if move box, cost = 1
    // if just moving, cost = 0

    // heuristic value = minimum manhattan distance between each box and a target
    return 0;
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
