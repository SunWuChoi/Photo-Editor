package images.model.functions.base;

/**
 * Interface for the modifying functions.
 * 
 * @author Sunwu Choi
 *
 */
public interface IModifier {
  /**
   * Modify an image, if the function needs an additional argument, apply option.
   * 
   * @param image  image to modify
   * @param option additional argument
   * @return image after modification
   */
  int[][][] apply(int[][][] image, int option);
}
