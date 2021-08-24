package images.view;

import images.controller.IController;
import images.model.functions.base.DmcList;

import java.awt.image.BufferedImage;

/**
 * Interface that represents a view.
 * 
 * @author Sunwu Choi
 *
 */
public interface IView {
  /**
   * Sets an action listener.
   * 
   * @param controller action listener for this view
   */
  void setListener(IController controller);

  /**
   * Display the view.
   * 
   * @throws IllegalStateException throw if the listener for the view is not set
   */
  void display() throws IllegalStateException;

  /**
   * Update the image in the view.
   * 
   * @param image the image to update
   * @throws IllegalStateException throws if there is no image to update
   */
  void updateImage(BufferedImage image) throws IllegalStateException;
  
  /**
   * Update the cross stitch pattern display.
   * 
   * @param pattern pattern to display
   * @param availDmcList  list of dmc that is available to use
   */
  void updateCrossStitch(String[] pattern, DmcList availDmcList);
}
