package images.model.functions;

import images.model.functions.base.AbstractFilter;

/**
 * Sharpen function.
 * 
 * @author Sunwu Choi
 *
 */
public class Sharpen extends AbstractFilter {
  private double[][] sharpen = {
      { -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8 },
      { -1.0 / 8,  1.0 / 4,  1.0 / 4,  1.0 / 4, -1.0 / 8 }, 
      { -1.0 / 8,  1.0 / 4,  1.0,      1.0 / 4, -1.0 / 8 },
      { -1.0 / 8,  1.0 / 4,  1.0 / 4,  1.0 / 4, -1.0 / 8 }, 
      { -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8 }};
  
  @Override
  public int[][][] apply(int[][][] image, int option) {
    return filter(image, sharpen);
  }
}
