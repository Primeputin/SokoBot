package solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SokoBot {
  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    /*
     * YOU NEED TO REWRITE THE IMPLEMENTATION OF THIS METHOD TO MAKE THE BOT SMARTER
     */
    int x = 0;
    int y = 0;
    Map<Integer, Integer> boxes = new HashMap<Integer, Integer>();
    Map<Integer, Integer> targets = new HashMap<Integer, Integer>();

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
          boxes.put(i, j);
        }

        if (mapData[j][i] == '.')
        {
          targets.put(i, j);
        }

      }
    }
    System.out.println("height: " + height + " width: " + width);
    System.out.println("x: " + x + " y: " + y);
    System.out.println(actions(width, height, x, y, mapData, itemsData));
    return "lrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlr";
  }
  public static ArrayList<Character> actions(int width, int height, int x, int y, char[][] mapData, char[][] itemsData)
  {
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
