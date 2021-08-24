package images.model.functions.base;

/**
 * Abstract class for the reduce function.
 * 
 * @author Sunwu Choi
 *
 */
public abstract class AbstractReduce extends ModifierBase {
  /**
   * Reduce method that performs dithering.
   * 
   * @param image        image to reduce color
   * @param maxNumColors max number of colors to reduce to, per color channel
   * @return an image after reduce with dither has been applied and clamped
   * @throws IllegalArgumentException when image is null
   */
  protected int[][][] reduceDither(int[][][] image, int maxNumColors)
      throws IllegalArgumentException {
    nullImageChecker(image);
    validReduceChecker(maxNumColors);

    int[][][] newImage = new int[image.length][image[0].length][3];

    double step = 256.0 / (maxNumColors - 1);
    int error;

    for (int height = 0; height < image.length; height++) {
      for (int width = 0; width < image[0].length; width++) {
        for (int i = 0; i < 3; i++) {

          newImage[height][width][i] = (int) (Math.round(image[height][width][i] / step) * step);

          // Floyd-SteinBerg dithering
          error = image[height][width][i] - newImage[height][width][i];

          if (width + 1 < image[0].length) {
            image[height][width + 1][i] += error * 7.0 / 16;
          }
          if (width - 1 >= 0 && height + 1 < image.length) {
            image[height + 1][width - 1][i] += error * 3.0 / 16;
          }
          if (height + 1 < image.length) {
            image[height + 1][width][i] += error * 5.0 / 16;
          }
          if (width + 1 < image[0].length && height + 1 < image.length) {
            image[height + 1][width + 1][i] += error * 1.0 / 16;
          }
        }
      }
    }

    clamp(newImage);
    return newImage;
  }

  /**
   * Reduce method that does not perform dithering.
   * 
   * @param image        image to reduce color
   * @param maxNumColors max number of colors to reduce to, per color channel
   * @return an image after reduce and clamped
   * @throws IllegalArgumentException when image is null
   */
  protected int[][][] reduce(int[][][] image, int maxNumColors) throws IllegalArgumentException {
    nullImageChecker(image);
    validReduceChecker(maxNumColors);

    int[][][] newImage = new int[image.length][image[0].length][3];

    double step = 256.0 / (maxNumColors - 1);

    for (int height = 0; height < image.length; height++) {
      for (int width = 0; width < image[0].length; width++) {
        for (int i = 0; i < 3; i++) {

          newImage[height][width][i] = (int) (Math.round(image[height][width][i] / step) * step);

        }
      }
    }

    clamp(newImage);
    return newImage;
  }

  /**
   * Throws exception if the colors is 0 or over 255.
   * 
   * @param colors number of colors to check
   * @throws IllegalArgumentException throw if the number of colors is invalid
   */
  private void validReduceChecker(int colors) throws IllegalArgumentException {
    if (colors < 1) {
      throw new IllegalArgumentException("Color per channel cannot be less than 1\n");
    } else if (colors > 255) {
      throw new IllegalArgumentException("Color per channel cannot exceed 255\n");
    }
  }
}
