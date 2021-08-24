package images.model.functions;

import images.model.functions.base.AbstractTransform;

/**
 * Sepia function.
 * 
 * @author Sunwu Choi
 *
 */
public class Sepia extends AbstractTransform {
  private double[][] sepia = { 
      { 0.393, 0.769, 0.189 }, 
      { 0.349, 0.686, 0.168 },
      { 0.272, 0.534, 0.131 } };

  @Override
  public int[][][] apply(int[][][] image, int option) {
    return transform(image, sepia);
  }

}
