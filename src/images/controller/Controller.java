package images.controller;

import images.model.IModel;
import images.view.IView;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Controller class for the image editor.
 * 
 * @author Sunwu Choi
 *
 */
public class Controller implements IController {
  private IModel model;
  private IView view;

  /**
   * Constructor for this controller class.
   * 
   * @param model model to control
   */
  public Controller(IModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Null model inserted\n");
    }
    this.model = model;
    this.view = null;
  }

  /**
   * Constructor for this controller with view.
   * 
   * @param model model to control
   * @param view  view to control
   */
  public Controller(IModel model, IView view) {
    if (model == null) {
      throw new IllegalArgumentException("Null model inserted\n");
    }
    if (view == null) {
      throw new IllegalArgumentException("Null view inserted\n");
    }
    this.model = model;
    this.view = view;
  }

  @Override
  public void start(InputStream in)
      throws IllegalArgumentException, NoSuchElementException, IllegalStateException, IOException {
    if (in == null) {
      throw new IllegalArgumentException("Null input stream inserted\n");
    }
    try (Scanner scan = new Scanner(in)) {
      while (scan.hasNext()) {
        switch (scan.next().toLowerCase()) {
          case "q":
            return;
          case "quit":
            return;
          case "load":
            model.load(scan.next());
            updateViewImage();
            break;
          case "save":
            model.save(scan.next());
            break;
          case "blur":
            model.blur();
            updateViewImage();
            break;
          case "sharpen":
            model.sharpen();
            updateViewImage();
            break;
          case "grey":
            model.grey();
            updateViewImage();
            break;
          case "sepia":
            model.sepia();
            updateViewImage();
            break;
          case "reduce":
            model.reduce(scan.nextInt());
            updateViewImage();
            break;
          case "dither":
            model.reduceDither(scan.nextInt());
            updateViewImage();
            break;
          case "mosaic":
            model.mosaic(scan.nextInt());
            updateViewImage();
            break;
          case "pixelate":
            model.pixelate(scan.nextInt());
            updateViewImage();
            break;
          case "pattern":
            model.pattern(scan.nextInt());
            updateCrossStitch();
            break;
          case "switch":
            model.switchDmc(scan.next().charAt(0), scan.next());
            updateCrossStitch();
            break;
          case "export":
            model.exportPattern(scan.next());
            break;
          case "check":
            model.nullImageChecker();
            break;
          case "checkp":
            model.nullPatternChecker();
            break;
          default:
            throw new IllegalArgumentException("Invalid command inserted");
        }
      }
    }
  }

  private void updateViewImage() {
    if (view != null) {
      try {
        model.getImage();
        view.updateImage(model.getImage());
      } catch (IllegalStateException e) {
        // no image loaded
      }
    }
  }

  private void updateCrossStitch() {
    if (view != null) {
      try {
        model.getPattern();
        view.updateCrossStitch(model.getPattern(), model.getAvailableDmcList());
      } catch (IllegalStateException e) {
        // no image loaded
      }
    }
  }

}
