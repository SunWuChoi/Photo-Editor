package images.model.functions;

import images.model.functions.base.AbstractReduce;

/**
 * Reduce function.
 * 
 * @author Sunwu Choi
 *
 */
public class Reduce extends AbstractReduce {

  @Override
  public int[][][] apply(int[][][] image, int option) {
    return reduce(image, option);
  }

}
