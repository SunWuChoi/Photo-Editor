package images.model.functions;

import images.model.functions.base.AbstractFilter;

/**
 * Blur function.
 * 
 * @author Sunwu Choi
 *
 */
public class Blur extends AbstractFilter {
  private double[][] blur = { 
      { 1.0 / 16, 1.0 / 8, 1.0 / 16 }, 
      { 1.0 / 8,  1.0 / 4, 1.0 / 8 },
      { 1.0 / 16, 1.0 / 8, 1.0 / 16 } };

  @Override
  public int[][][] apply(int[][][] image, int option) {
    return filter(image, blur);
  }
}
