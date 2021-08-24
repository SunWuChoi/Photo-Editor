package images.model.functions.base;

/**
 * Abstract class for the transform function.
 * 
 * @author Sunwu Choi
 *
 */
public abstract class AbstractTransform extends ModifierBase {
  /**
   * Transforms an image with the provided tone information.
   * 
   * @param image image to transform
   * @param tone  tone value to change the colors
   * @return an image after transformation is applied and clamped
   * @throws IllegalArgumentException when image is null
   */
  protected int[][][] transform(int[][][] image, double[][] tone) throws IllegalArgumentException {
    nullImageChecker(image);
    nullKernelChecker(tone);

    int[][][] newImage = new int[image.length][image[0].length][3];

    for (int height = 0; height < image.length; height++) {
      for (int width = 0; width < image[0].length; width++) {
        for (int i = 0; i < 3; i++) {
          newImage[height][width][i] += image[height][width][0] * tone[i][0]
              + image[height][width][1] * tone[i][1] + image[height][width][2] * tone[i][2];
        }
      }
    }

    clamp(newImage);
    return newImage;
  }
}
