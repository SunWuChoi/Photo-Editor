package images.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

/**
 * Interface for controller.
 * 
 * @author Sunwu Choi
 *
 */
public interface IController {
  /**
   * Primary method that runs the controller.
   * 
   * @param in InputStream to run
   * @throws IOException              throw if file load or save throws an
   *                                  exception
   * @throws IllegalStateException    throw if an invalid state is detected
   * @throws NoSuchElementException   throw if batch command argument is missing
   * @throws IllegalArgumentException throw if illegal argument is passed
   */
  void start(InputStream in)
      throws IllegalArgumentException, NoSuchElementException, IllegalStateException, IOException;
}
