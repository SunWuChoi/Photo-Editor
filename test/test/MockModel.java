package test;

import images.model.IModel;
import images.model.functions.base.DmcList;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Mock model class for test purpose.
 * 
 * @author Sunwu Choi
 *
 */
public class MockModel implements IModel {

  @Override
  public void blur() throws IllegalStateException {
    System.out.format("blur called");
  }

  @Override
  public void sharpen() throws IllegalStateException {
    System.out.format("sharpen called");
  }

  @Override
  public void grey() throws IllegalStateException {
    System.out.format("grey called");
  }

  @Override
  public void sepia() throws IllegalStateException {
    System.out.format("sepia called");
  }

  @Override
  public void reduce(int maxNumColor) throws IllegalStateException, IllegalArgumentException {
    System.out.format("reduce called with " + maxNumColor);
  }

  @Override
  public void reduceDither(int maxNumColor) throws IllegalStateException, IllegalArgumentException {
    System.out.format("dither called with " + maxNumColor);
  }

  @Override
  public void mosaic(int seed) throws IllegalStateException, IllegalArgumentException {
    System.out.format("mosaic called with " + seed);
  }

  @Override
  public void pixelate(int numOfSquares) throws IllegalStateException, IllegalArgumentException {
    System.out.format("pixelate called with " + numOfSquares);
  }

  @Override
  public void pattern(int numOfSquares) throws IllegalStateException, IllegalArgumentException {
    System.out.format("pattern called with " + numOfSquares);
  }

  @Override
  public void switchDmc(char symbol, String dmc) throws IllegalStateException {
    System.out.format("switchDmc called with " + symbol + " " + dmc);
  }

  @Override
  public DmcList getAvailableDmcList() {
    // Not called in controller
    return null;
  }

  @Override
  public void exportPattern(String textName)
      throws IllegalStateException, IllegalArgumentException, IOException {
    System.out.format("export called with " + textName);
  }

  @Override
  public void load(String loadName) throws IllegalArgumentException, IOException {
    System.out.format("load called with " + loadName);
  }

  @Override
  public void save(String saveName)
      throws IllegalStateException, IllegalArgumentException, IOException {
    System.out.format("save called with " + saveName);
  }

  @Override
  public void nullImageChecker() throws IllegalStateException {
    System.out.format("check called");
  }

  @Override
  public void nullPatternChecker() throws IllegalStateException {
    System.out.format("checkp called");
  }

  @Override
  public BufferedImage getImage() throws IllegalStateException {
    // not used in controller test
    return null;
  }

  @Override
  public String[] getPattern() throws IllegalStateException {
    // not used in controller test
    return null;
  }

}
