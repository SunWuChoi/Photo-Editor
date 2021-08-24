package images.model.functions;

import images.model.functions.base.AbstractReduce;

/**
 * Dither function.
 * 
 * @author Sunwu Choi
 *
 */
public class ReduceDither extends AbstractReduce {

  @Override
  public int[][][] apply(int[][][] image, int option) {
    return reduceDither(image, option);
  }

}
