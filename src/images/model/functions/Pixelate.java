package images.model.functions;

import images.model.functions.base.AbstractMoasic;

/**
 * Pixelate function.
 * 
 * @author Sunwu Choi
 *
 */
public class Pixelate extends AbstractMoasic {

  @Override
  public int[][][] apply(int[][][] image, int option) {
    return pixelate(image, option);
  }

}
