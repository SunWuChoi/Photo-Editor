package images.model.functions;

import images.model.functions.base.AbstractPattern;
import images.model.functions.base.DmcList;

/**
 * Pattern function.
 * 
 * @author Sunwu Choi
 *
 */
public class Pattern extends AbstractPattern {

  /**
   * Function for generating a cross stitch pattern.
   * 
   * @param image        image to generate a cross stitch pattern
   * @param numOfSquares number of super pixel per row in the cross stitch
   * @param dmcList      dmc list to pick dmc from
   */
  public String[] generatePattern(int[][][] image, int numOfSquares, DmcList dmcList) {
    nullImageChecker(image);
    validSquareChecker(image, numOfSquares);
    return pattern(image, numOfSquares, dmcList);
  }

  @Override
  public int[][][] apply(int[][][] image, int option) {
    // not used
    return null;
  }

}
