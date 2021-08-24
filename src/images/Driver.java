package images;

import images.controller.Controller;
import images.controller.IController;
import images.model.IModel;
import images.model.Model;
import images.view.IView;
import images.view.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Driver class for the image modifier.
 * 
 * @author Sunwu Choi
 *
 */
public class Driver {
  /**
   * Main method for the driver class.
   * 
   * @param args arguments
   */
  public static void main(String[] args) {
    IModel model = new Model();
    IController controller;
    // real time user input mode
    if (args[0].equals("-interactive")) {
      IView view = new View();
      controller = new Controller(model, view);
      view.setListener(controller);
      view.display();
    } else if (args[0].equals("-script")) {
      if (args.length < 2) {
        System.out.println("No batch file argument found");
        return;
      }
      controller = new Controller(model);
      // script mode
      // try to access the batch file, error if there is no such file
      try {
        controller.start(new FileInputStream(args[1]));
      } catch (IOException e) {
        // if batch file is not found
        System.out.format("%s", e.getMessage());
      } catch (NoSuchElementException e) {
        System.out.format("%s", e.getMessage());
      }
    } else {
      System.out.format("Invalid argument length\n");
    }
  }
}
