package images.model.functions;

import images.model.functions.base.AbstractMoasic;

/**
 * Mosaic function.
 * 
 * @author Sunwu Choi
 *
 */
public class Mosaic extends AbstractMoasic {

  @Override
  public int[][][] apply(int[][][] image, int option) {
    return mosaic(image, option);
  }

}
