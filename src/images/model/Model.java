package images.model;

import images.model.functions.Blur;
import images.model.functions.Grey;
import images.model.functions.Mosaic;
import images.model.functions.Pattern;
import images.model.functions.Pixelate;
import images.model.functions.Reduce;
import images.model.functions.ReduceDither;
import images.model.functions.Sepia;
import images.model.functions.Sharpen;
import images.model.functions.base.DmcList;
import images.util.ImageUtilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class that has image modifying static methods.
 * 
 * @author Sunwu Choi
 *
 */
public class Model implements IModel {
  private int[][][] image;
  private String patternHead;
  private String patternBody;
  private String patternLegend;
  private DmcList availableDmcList;

  /**
   * Constructor for the model, sets the image as null.
   */
  public Model() {
    image = null;
    patternHead = null;
    patternBody = null;
    patternLegend = null;
    availableDmcList = new DmcList();
  }

  @Override
  public void blur() throws IllegalStateException {
    nullImageChecker();
    image = new Blur().apply(image, 0);
  }

  @Override
  public void sharpen() throws IllegalStateException {
    nullImageChecker();
    image = new Sharpen().apply(image, 0);
  }

  @Override
  public void grey() throws IllegalStateException {
    nullImageChecker();
    image = new Grey().apply(image, 0);
  }

  @Override
  public void sepia() throws IllegalStateException {
    nullImageChecker();
    image = new Sepia().apply(image, 0);
  }

  @Override
  public void reduce(int maxNumColors) throws IllegalStateException, IllegalArgumentException {
    nullImageChecker();
    image = new Reduce().apply(image, maxNumColors);
  }

  @Override
  public void reduceDither(int maxNumColors)
      throws IllegalStateException, IllegalArgumentException {
    nullImageChecker();
    image = new ReduceDither().apply(image, maxNumColors);
  }

  @Override
  public void mosaic(int seed) throws IllegalStateException, IllegalArgumentException {
    nullImageChecker();
    image = new Mosaic().apply(image, seed);
  }

  @Override
  public void pixelate(int numOfSquares) throws IllegalStateException, IllegalArgumentException {
    nullImageChecker();
    image = new Pixelate().apply(image, numOfSquares);
  }

  @Override
  public void pattern(int numOfSquares) throws IllegalStateException, IllegalArgumentException {
    nullImageChecker();
    int[][][] before = image;

    pixelate(numOfSquares);
    String[] pattern = new Pattern().generatePattern(image, numOfSquares, availableDmcList);
    image = before;
    
    patternHead = pattern[0];
    patternBody = pattern[1];
    patternLegend = pattern[2];
  }

  @Override
  public void switchDmc(char symbol, String dmc) throws IllegalStateException {
    nullPatternChecker();

    String[] tokens = dmc.split(",");
    StringBuilder newDmc = new StringBuilder();

    for (int i = 0; i < tokens.length; i++) {
      newDmc.append(tokens[i]);
      newDmc.append(" ");
    }
    String newDmcString = newDmc.toString().strip();

    String[] lines = patternLegend.split("\n");
    for (int i = 1; i < lines.length; i++) {
      if (lines[i].charAt(0) == symbol) {
        lines[i] = symbol + " " + newDmcString;
      }
    }

    StringBuilder newLegend = new StringBuilder();
    for (int i = 0; i < lines.length; i++) {
      newLegend.append(lines[i]);
      newLegend.append("\n");
    }

    patternLegend = newLegend.toString();
  }

  @Override
  public DmcList getAvailableDmcList() {
    return this.availableDmcList;
  }

  @Override
  public void exportPattern(String textName)
      throws IllegalStateException, IllegalArgumentException, IOException {
    nullPatternChecker();
    String crossStitch = patternHead + "\n" + patternBody + "\n" + patternLegend;
    byte[] strToBytes = crossStitch.getBytes();
    FileOutputStream outputStream;
    outputStream = new FileOutputStream(textName);
    outputStream.write(strToBytes);
    outputStream.close();
  }

  @Override
  public void load(String loadName) throws IllegalArgumentException, IOException {
    if (loadName == null || loadName.isBlank()) {
      throw new IllegalArgumentException("Null or blank file name found");
    }
    String absPath = new File(loadName).getAbsolutePath();
    image = ImageUtilities.readImage(absPath);
  }

  @Override
  public void save(String saveName) throws IOException {
    nullImageChecker();
    if (saveName == null || saveName.isBlank()) {
      throw new IllegalArgumentException("Null or blank file name found\n");
    }

    ImageUtilities.writeImage(image, image[0].length, image.length, saveName);

  }

  @Override
  public void nullImageChecker() throws IllegalStateException {
    if (image == null) {
      throw new IllegalStateException("No image loaded\n");
    }
  }

  @Override
  public void nullPatternChecker() throws IllegalStateException {
    if (patternBody == null) {
      throw new IllegalStateException("No pattern generated yet\n");
    }
  }

  @Override
  public BufferedImage getImage() throws IllegalStateException {
    if (image == null) {
      throw new IllegalStateException("No image loaded");
    }
    try {
      return ImageUtilities.getBufferedImage(image, image[0].length, image.length);
    } catch (IllegalStateException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public String[] getPattern() throws IllegalStateException {
    if (patternHead == null || patternBody == null || patternLegend == null) {
      throw new IllegalStateException("No pattern generated yet");
    }
    String[] patterns = { patternHead, patternBody, patternLegend };
    return patterns;
  }
}
