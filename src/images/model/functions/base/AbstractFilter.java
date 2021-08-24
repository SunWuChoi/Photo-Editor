package images.model.functions.base;

/**
 * Abstract class for the filter related functions.
 * 
 * @author Sunwu Choi
 *
 */
public abstract class AbstractFilter extends ModifierBase {
  /**
   * Filter method that applies the kernel to the input image.
   * 
   * @param image  image to apply filter
   * @param kernel kernel to apply the filtering
   * @return an image after filtering is applied and clamped
   * @throws IllegalArgumentException when image is null
   */
  protected int[][][] filter(int[][][] image, double[][] kernel) throws IllegalArgumentException {
    nullImageChecker(image);
    nullKernelChecker(kernel);

    int[][][] newImage = new int[image.length][image[0].length][3];

    int kernelLength = kernel.length;
    int radius = kernelLength / 2;
    double change;

    // iterate through the image height and width and colors
    for (int height = 0; height < image.length; height++) {
      for (int width = 0; width < image[0].length; width++) {
        for (int i = 0; i < 3; i++) {

          // iterate the kernel n x n size
          for (int kernelHeight = 0; kernelHeight < kernelLength; kernelHeight++) {
            for (int kernelWidth = 0; kernelWidth < kernelLength; kernelWidth++) {
              if (height - radius + kernelHeight >= 0
                  && height - radius + kernelHeight < image.length
                  && width - radius + kernelWidth >= 0
                  && width - radius + kernelWidth < image[0].length) {

                change = kernel[kernelHeight][kernelWidth]
                    * image[height - radius + kernelHeight][width - radius + kernelWidth][i];
                newImage[height][width][i] += change;

              } else if (height - radius + kernelHeight < 0 && width - radius + kernelWidth < 0) {
                change = kernel[kernelHeight][kernelWidth] * image[0][0][i];
                newImage[height][width][i] += change;
              } else if (height - radius + kernelHeight >= image.length
                  && width - radius + kernelWidth < 0) {
                change = kernel[kernelHeight][kernelWidth] * image[image.length - 1][0][i];
                newImage[height][width][i] += change;
              } else if (height - radius + kernelHeight < 0
                  && width - radius + kernelWidth >= image[0].length) {
                change = kernel[kernelHeight][kernelWidth] * image[0][image[0].length - 1][i];
                newImage[height][width][i] += change;
              } else if (height - radius + kernelHeight >= image.length
                  && width - radius + kernelWidth >= image[0].length) {
                change = kernel[kernelHeight][kernelWidth]
                    * image[image.length - 1][image[0].length - 1][i];
                newImage[height][width][i] += change;
              } else if (height - radius + kernelHeight < 0) {
                change = kernel[kernelHeight][kernelWidth]
                    * image[0][width - radius + kernelWidth][i];
                newImage[height][width][i] += change;
              } else if (height - radius + kernelHeight >= image.length) {
                change = kernel[kernelHeight][kernelWidth]
                    * image[image.length - 1][width - radius + kernelWidth][i];
                newImage[height][width][i] += change;
              } else if (width - radius + kernelWidth < 0) {
                change = kernel[kernelHeight][kernelWidth]
                    * image[height - radius + kernelHeight][0][i];
                newImage[height][width][i] += change;
              } else if (width - radius + kernelWidth >= image[0].length) {
                change = kernel[kernelHeight][kernelWidth]
                    * image[height - radius + kernelHeight][image[0].length - 1][i];
                newImage[height][width][i] += change;
              }
            }
          }
        }
      }
    }
    clamp(newImage);
    return newImage;
  }
}
