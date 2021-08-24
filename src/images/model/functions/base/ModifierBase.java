package images.model.functions.base;

/**
 * Base abstract class for the modifier.
 * 
 * @author Sunwu Choi
 *
 */
public abstract class ModifierBase implements IModifier {

  /**
   * Clamp method makes sure that every pixel's color channel in this image is in
   * range of 0 ~ 255.
   * 
   * @param image the image to clamp
   */
  protected void clamp(int[][][] image) throws IllegalArgumentException {
    nullImageChecker(image);
    for (int height = 0; height < image.length; height++) {
      for (int width = 0; width < image[0].length; width++) {
        for (int i = 0; i < 3; i++) {
          if (image[height][width][i] > 255) {
            image[height][width][i] = 255;
          } else if (image[height][width][i] < 0) {
            image[height][width][i] = 0;
          }
        }
      }
    }
  }

  /**
   * Throws exception if the image is null.
   * 
   * @param image image to check null
   * @throws IllegalArgumentException throw if the image is null
   */
  protected void nullImageChecker(int[][][] image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Null image found\n");
    }
  }

  /**
   * Throws exception if the kernel is null.
   * 
   * @param kernel kernel to check null
   * @throws IllegalArgumentException throw if the kernel is null
   */
  protected void nullKernelChecker(double[][] kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel not found\n");
    }
  }

  /**
   * Throws exception if the numOfSquares is invalid.
   * 
   * @param numOfSquares numOfSquares to check
   * @throws IllegalArgumentException throw if the numOfSquares is invalid
   */
  protected void validSquareChecker(int[][][] image, int numOfSquares)
      throws IllegalArgumentException {
    if (numOfSquares < 1) {
      throw new IllegalArgumentException("Super pixel cannot be less than 1\n");
    } else if (numOfSquares > 150) {
      throw new IllegalArgumentException("Super pixel cannot exceed 150 pixels\n");
    }
  }
}
