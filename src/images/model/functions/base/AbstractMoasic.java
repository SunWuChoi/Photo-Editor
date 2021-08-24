package images.model.functions.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract class for the mosaic and pixelate function.
 * 
 * @author Sunwu Choi
 *
 */
public abstract class AbstractMoasic extends ModifierBase {
  protected int[][][] mosaic(int[][][] image, int seed) {
    numSeedChecker(image, seed);
    nullImageChecker(image);
    if (seed == image.length * image[0].length) {
      return image;
    }

    List<Coordinate> listofPoints = generatePoints(image, seed);
    int[][][] newImage = new int[image.length][image[0].length][3];

    for (int height = 0; height < image.length; height++) {
      for (int width = 0; width < image[0].length; width++) {

        int closest = new Coordinate(height, width).closest(listofPoints);
        Coordinate seedPixel = listofPoints.get(closest);
        int row = seedPixel.getRow();
        int col = seedPixel.getCol();
        for (int i = 0; i < 3; i++) {
          newImage[height][width][i] = image[row][col][i];
        }
      }
    }

    clamp(newImage);
    return newImage;
  }

  protected int[][][] pixelate(int[][][] image, int numOfSquares) {
    nullImageChecker(image);
    validSquareChecker(image, numOfSquares);

    int squareLength = image[0].length / numOfSquares;
    int[][][] newImage = new int[image.length][image[0].length][3];

    for (int height = 0; height < image.length; height += squareLength) {
      for (int width = 0; width < image[0].length; width += squareLength) {

        for (int row = height; (row < height + squareLength && row < image.length); row++) {
          for (int col = width; (col < width + squareLength && col < image[0].length); col++) {
            newImage[row][col] = image[height][width];
          }
        }
      }
    }

    clamp(newImage);
    return newImage;
  }

  private List<Coordinate> generatePoints(int[][][] image, int seed) {
    Random random = new Random();
    List<Coordinate> list = new ArrayList<>();
    for (int i = 0; i < seed;) {
      Coordinate point = new Coordinate(random.nextInt(image.length),
          random.nextInt(image[0].length));
      if (!list.contains(point)) {
        list.add(point);
        i++;
      }
    }
    return list;
  }

  private void numSeedChecker(int[][][] image, int numSeeds) throws IllegalArgumentException {
    int numPixels = image.length * image[0].length;
    if (numSeeds < 1) {
      throw new IllegalArgumentException("Seed cannot be less than 1\n");
    }
    if (numSeeds > numPixels) {
      throw new IllegalArgumentException("Number of seed exceeded the total pixel count\n");
    }
  }
}
