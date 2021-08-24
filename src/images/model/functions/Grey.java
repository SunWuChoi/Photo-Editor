package images.model.functions;

import images.model.functions.base.AbstractTransform;

/**
 * Grey function.
 * 
 * @author Sunwu Choi
 *
 */
public class Grey extends AbstractTransform {
  private double[][] grey = { 
      { 0.2126, 0.7152, 0.0722 }, 
      { 0.2126, 0.7152, 0.0722 },
      { 0.2126, 0.7152, 0.0722 } };

  @Override
  public int[][][] apply(int[][][] image, int option) {
    return transform(image, grey);
  }

}
