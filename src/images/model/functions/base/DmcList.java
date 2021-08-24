package images.model.functions.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Class for a list that contains DMC.
 * 
 * @author Sunwu Choi
 *
 */
public class DmcList {
  private List<Dmc> dmcList;

  /**
   * DMC list constructor.
   */
  public DmcList() {
    dmcList = new ArrayList<Dmc>();
    init();
  }
  
  public List<Dmc> getArrayList() {
    return this.dmcList;
  }

  /**
   * Function that finds the closest DMC color and returns that DMC.
   * 
   * @param color rgb value
   * @return the closest DMC color to that input rgb
   */
  public Dmc getClosestDmc(int[] color) {
    double min = dmcList.get(0).redmean(color);
    int minColorIndex = 0;
    double diff;

    for (int i = 1; i < dmcList.size(); i++) {
      diff = dmcList.get(i).redmean(color);
      if (diff < min) {
        min = diff;
        minColorIndex = i;
      }
    }

    return dmcList.get(minColorIndex);
  }

  private void init() {
    String dmcTextFile =
        "209,Lavender Dark,163,123,167\n"
        + "210,Lavender Medium,195,159,195\n"
        + "320,Pistachio Green Medium,105,136,90\n"
        + "333,Blue Violet Very Dark,92,84,120\n"
        + "340,Blue Violet Medium,173,167,199\n"
        + "435,Brown Very Light,184,119,72\n"
        + "436,Tan,203,144,81\n"
        + "498,Red Dark,167,19,43\n"
        + "552,Violet Medium,128,58,107\n"
        + "602,Cranberry Medium,226,72,116\n"
        + "603,Cranberry,255,164,190\n"
        + "605,Cranberry Very Light,255,192,205\n"
        + "699,Green,5,101,23\n"
        + "712,Cream,255,251,239\n"
        + "726,Topaz Light,253,215,85\n"
        + "738,Tan Very Light,236,204,158\n"
        + "739,Tan Ultra Very Light,248,228,200\n"
        + "741,Tangerine Medium,255,163,43\n"
        + "742,Tangerine Light,255,191,87\n"
        + "744,Yellow Pale,255,231,147\n"
        + "783,Topaz Medium,206,145,36\n"
        + "796,Royal Blue Dark,17,65,109\n"
        + "809,Delft Blue,148,168,198\n"
        + "815,Garnet Medium,135,7,31\n"
        + "817,Coral Red Very Dark,187,5,31\n"
        + "827,Blue Very Light,189,221,237\n"
        + "838,Beige Brown Very Dark,89,73,55\n"
        + "930,Antique Blue Dark,69,92,113\n"
        + "931,Antique Blue Medium,106,133,158\n"
        + "995,Electric Blue Dark,38,150,182\n"
        + "996,Electric Blue Medium,48,194,236\n"
        + "3051,Green Gray Dark,95,102,72\n"
        + "3326,Rose Light,251,173,180\n"
        + "3347,Yellow Green Medium,113,147,92\n"
        + "3348,Yellow Green Light,204,217,177\n"
        + "3371,Black Brown,30,17,8";

    Scanner scan = new Scanner(dmcTextFile);

    while (scan.hasNextLine()) {
      String line = scan.nextLine();
      StringTokenizer tokens = new StringTokenizer(line, ",");
      int floss = Integer.parseInt(tokens.nextToken());
      String name = tokens.nextToken();
      int red = Integer.parseInt(tokens.nextToken());
      int green = Integer.parseInt(tokens.nextToken());
      int blue = Integer.parseInt(tokens.nextToken());
      dmcList.add(new Dmc(floss, name, red, green, blue));
    }
    scan.close();

  }

  /**
   * Class representing DMC.
   * 
   * @author Sunwu Choi
   *
   */
  public class Dmc {
    private String name;
    private int floss;
    private int red;
    private int green;
    private int blue;

    /**
     * Constructor for the DMC class.
     * 
     * @param floss floss number
     * @param name  name of this DMC
     * @param r     red color value
     * @param g     green color value
     * @param b     blue color value
     */
    public Dmc(int floss, String name, int r, int g, int b) {
      this.floss = floss;
      this.name = name;
      this.red = r;
      this.green = g;
      this.blue = b;
    }

    public String getName() {
      return this.name;
    }

    public int getFloss() {
      return this.floss;
    }
    
    public int getRed() {
      return this.red;
    }
    
    public int getGreen() {
      return this.green;
    }
    
    public int getBlue() {
      return this.blue;
    }

    /**
     * Redmean function.
     * 
     * @param color rgb to calculate the redmean
     * @return the redmean value
     */
    public double redmean(int[] color) {
      double r = (this.red + color[0]) / 2.0;
      double diffRSquare = Math.pow(this.red - color[0], 2);
      double diffGSquare = Math.pow(this.green - color[1], 2);
      double diffBSquare = Math.pow(this.blue - color[2], 2);

      double firstTerm = (2 + r / 256.0) * diffRSquare;
      double secondTerm = 4 * diffGSquare;
      double thirdTerm = (2 + (255 - r) / 256.0) * diffBSquare;

      return Math.sqrt(firstTerm + secondTerm + thirdTerm);
    }
  }
}
