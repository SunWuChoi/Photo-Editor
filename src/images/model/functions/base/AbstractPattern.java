package images.model.functions.base;

import images.model.functions.base.DmcList.Dmc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for the pattern function.
 * 
 * @author Sunwu Choi
 *
 */
public abstract class AbstractPattern extends ModifierBase {
  protected String[] pattern(int[][][] image, int numOfSquares, DmcList dmcList) {
    nullImageChecker(image);
    validSquareChecker(image, numOfSquares);

    int squareLength = image[0].length / numOfSquares;

    int r = 0;
    int c = 0;
    for (int row = 0; row < image.length; row += squareLength) {
      r++;
      c = 0;
      for (int col = 0; col < image[0].length; col += squareLength) {
        c++;
      }
    }
    int[][] patternArray = new int[r][c];

    String[] pattern = new String[3];
    List<Dmc> usedDmc = new ArrayList<Dmc>();

    r = 0;
    for (int row = 0; row < image.length; row += squareLength) {
      c = 0;
      for (int col = 0; col < image[0].length; col += squareLength) {

        Dmc closest = dmcList.getClosestDmc(image[row][col]);
        if (!usedDmc.contains(closest)) {
          usedDmc.add(closest);
        }
        patternArray[r][c] = closest.getFloss();

        c++;
      }
      r++;
    }

    pattern[0] = c + " x " + r + "\n";
    usedDmc.sort(new SortByFloss());

    int alphabet = 65;
    Map<Character, Integer> flossMap = new HashMap<Character, Integer>();

    Map<Character, Integer> flosstoRedMap = new HashMap<Character, Integer>();
    Map<Character, Integer> flosstoGreenMap = new HashMap<Character, Integer>();
    Map<Character, Integer> flosstoBlueMap = new HashMap<Character, Integer>();

    Map<Integer, Character> alphabetMap = new HashMap<Integer, Character>();
    Map<Character, String> nameMap = new HashMap<Character, String>();
    for (Dmc d : usedDmc) {
      flossMap.put((char) alphabet, d.getFloss());
      flosstoRedMap.put((char) alphabet, d.getRed());
      flosstoGreenMap.put((char) alphabet, d.getGreen());
      flosstoBlueMap.put((char) alphabet, d.getBlue());
      nameMap.put((char) alphabet, d.getName());
      alphabetMap.put(d.getFloss(), (char) alphabet);
      alphabet++;
    }

    StringBuilder body = new StringBuilder();
    for (int row = 0; row < patternArray.length; row++) {
      for (int col = 0; col < patternArray[0].length; col++) {
        body.append(alphabetMap.get(patternArray[row][col]) + " ");
      }
      body.append("\n");
    }

    pattern[1] = body.toString();

    StringBuilder legend = new StringBuilder("LEGEND\n");
    for (Character key : flossMap.keySet()) {
      legend.append(
          key + " " + flossMap.get(key) + " " + nameMap.get(key) + " " + flosstoRedMap.get(key)
              + " " + flosstoGreenMap.get(key) + " " + flosstoBlueMap.get(key) + " " + "\n");
    }
    pattern[2] = legend.toString();
    return pattern;
  }

  private class SortByFloss implements Comparator<Dmc> {

    @Override
    public int compare(Dmc o1, Dmc o2) {
      return o1.getFloss() - o2.getFloss();
    }

  }

}
