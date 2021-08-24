package images.model;

import images.model.functions.base.DmcList;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Interface for image modifying functions.
 * 
 * @author Sunwu Choi
 *
 */
public interface IModel {
  /**
   * Blur an image.
   */
  void blur() throws IllegalStateException;

  /**
   * Sharpen an image.
   */
  void sharpen() throws IllegalStateException;

  /**
   * Transform an image to grey.
   */
  void grey() throws IllegalStateException;

  /**
   * Transform an image to sepia.
   */
  void sepia() throws IllegalStateException;

  /**
   * Reduce the color value to a smaller number. The number of color value is
   * color value per channel.
   * 
   * @param maxNumColor maximum number of color per channel
   */
  void reduce(int maxNumColor) throws IllegalStateException, IllegalArgumentException;

  /**
   * Reduce method with dithering. The number of color value is color value per
   * channel.
   * 
   * @param maxNumColor maximum number of color per channel
   */
  void reduceDither(int maxNumColor) throws IllegalStateException, IllegalArgumentException;

  /**
   * Mosaic the image with the seed.
   * 
   * @param seed number of mosaic panels
   */
  void mosaic(int seed) throws IllegalStateException, IllegalArgumentException;

  /**
   * Mosaic the image using pixelation method.
   * 
   * @param numOfSquares number of squares a width will have
   */
  void pixelate(int numOfSquares) throws IllegalStateException, IllegalArgumentException;

  /**
   * Generate a pattern for cross stitch.
   * 
   * @param numOfSquares number of super pixel per row
   */
  void pattern(int numOfSquares) throws IllegalStateException, IllegalArgumentException;

  /**
   * Switch the color of the symbol of the cross stitch pattern.
   * 
   * @param symbol symbol to change color
   * @param dmc    dmc information of the color to be changed
   * @throws IllegalStateException if pattern has not been generated yet
   */
  void switchDmc(char symbol, String dmc) throws IllegalStateException;

  /**
   * Returns a DmcList object which contains the available dmc list.
   * 
   * @return DmcList object containing the available dmc list
   */
  DmcList getAvailableDmcList();

  /**
   * Saves the pattern to a text file.
   * 
   * @param textName file name to save the text file
   * @throws IllegalStateException    throw if there is no pattern generated yet
   * @throws IllegalArgumentException throw if the file name is invalid
   * @throws IOException              throw if the saving process goes wrong
   */
  void exportPattern(String textName)
      throws IllegalStateException, IllegalArgumentException, IOException;

  /**
   * Load an image from a filename.
   * 
   * @param loadName String of the filename
   * @throws IllegalArgumentException throw if the loadName is invalid
   * @throws IOException              throws if the loading process has an error
   */
  void load(String loadName) throws IllegalArgumentException, IOException;

  /**
   * Save the image in the model as the saveName.
   * 
   * @param saveName file name to save the image to
   * @throws IOException throws if there is an error in the save process
   */
  void save(String saveName) throws IllegalStateException, IllegalArgumentException, IOException;

  /**
   * Method that throws IllegalStateException when there is no image loaded.
   * 
   * @throws IllegalStateException throws if the image is not loaded
   */
  void nullImageChecker() throws IllegalStateException;

  /**
   * Method that throws IllegalStateException when there is no pattern generated
   * yet.
   * 
   * @throws IllegalStateException throws if there is no pattern generated
   */
  void nullPatternChecker() throws IllegalStateException;

  /**
   * Method that returns a bufferedImage for the view to display.
   * 
   * @return a buffered image
   * @throws IllegalStateException throws if there is no image to return
   */
  BufferedImage getImage() throws IllegalStateException;

  /**
   * Method that returns a string array that contains the pattern.
   * 
   * @return a string array that contains the pattern
   * @throws IllegalStateException throws if the pattern is not generated
   */
  String[] getPattern() throws IllegalStateException;
}
