package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import images.controller.Controller;
import images.controller.IController;
import images.model.IModel;
import images.model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * Test class for the controller.
 * 
 * @author Sunwu Choi
 *
 */
public class ControllerTest {
  private ByteArrayOutputStream outContent;
  private PrintStream originalOut;

  private IController controller;
  private InputStream input;

  @Before
  public void setUp() {
    IModel model = new Model();
    controller = new Controller(model);

    outContent = new ByteArrayOutputStream();
    originalOut = System.out;

    System.setOut(new PrintStream(outContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void quitTest() {
    String text = "q";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (Exception e) {
      fail();
    }

    text = "quit";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void loadTest() {
    // testing load filename
    String text = "load Filename";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IOException e) {
      System.out.format("%s", e.getMessage());
    }
    assertTrue(outContent.toString().contains("The system cannot find the file specified"));
    outContent.reset();

    // testing load
    text = "load";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      System.out.format("%s", e.getClass());
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.NoSuchElementException", outContent.toString());
    outContent.reset();
  }

  @Test
  public void saveTest() {
    String text;

    // testing save filename
    File file = new File("test/goat_save_test.png");
    if (file.exists()) {
      assertTrue(file.delete());
    }
    text = "load goat.png save test/goat_save_test.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (Exception e) {
      fail();
    }
    assertTrue(file.exists());

    // testing save
    text = "load goat.png save";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      System.out.format("%s", e.getClass());
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.NoSuchElementException", outContent.toString());
  }

  @Test
  public void noImageLoadedTest() {
    // testing methods when no image is loaded
    String text = "blur";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      System.out.format("%s", e.getMessage());
    } catch (IOException e) {
      fail();
    }
    assertEquals("No image loaded\n", outContent.toString());
    outContent.reset();

    text = "sharpen";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      System.out.format("%s", e.getMessage());
    } catch (IOException e) {
      fail();
    }
    assertEquals("No image loaded\n", outContent.toString());
    outContent.reset();

    text = "grey";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      System.out.format("%s", e.getMessage());
    } catch (IOException e) {
      fail();
    }
    assertEquals("No image loaded\n", outContent.toString());
    outContent.reset();

    text = "sepia";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      System.out.format("%s", e.getMessage());
    } catch (IOException e) {
      fail();
    }
    assertEquals("No image loaded\n", outContent.toString());
    outContent.reset();

    text = "reduce 1";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      System.out.format("%s", e.getMessage());
    } catch (IOException e) {
      fail();
    }
    assertEquals("No image loaded\n", outContent.toString());
    outContent.reset();

    text = "dither 1";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      System.out.format("%s", e.getMessage());
    } catch (IOException e) {
      fail();
    }
    assertEquals("No image loaded\n", outContent.toString());
    outContent.reset();

    text = "mosaic 100";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      System.out.format("%s", e.getMessage());
    } catch (IOException e) {
      fail();
    }
    assertEquals("No image loaded\n", outContent.toString());
    outContent.reset();

    text = "pixelate 100";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      System.out.format("%s", e.getMessage());
    } catch (IOException e) {
      fail();
    }
    assertEquals("No image loaded\n", outContent.toString());
    outContent.reset();

    text = "pattern 10";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      System.out.format("%s", e.getMessage());
    } catch (IOException e) {
      fail();
    }
    assertEquals("No image loaded\n", outContent.toString());
    outContent.reset();
  }

  @Test
  public void filterTest() {
    // testing blur
    File file = new File("test/goat_blur_test.png");
    if (file.exists()) {
      assertTrue(file.delete());
    }
    String text = "load goat.png blur save test/goat_blur_test.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertTrue(file.exists());
    outContent.reset();

    // testing sharpen
    file = new File("test/goat_sharpen_test.png");
    if (file.exists()) {
      assertTrue(file.delete());
    }
    text = "load goat.png sharpen save test/goat_sharpen_test.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertTrue(file.exists());
    outContent.reset();
  }

  @Test
  public void transformTest() {
    // testing grey
    File file = new File("test/goat_grey_test.png");
    if (file.exists()) {
      assertTrue(file.delete());
    }
    String text = "load goat.png grey save test/goat_grey_test.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertTrue(file.exists());
    outContent.reset();

    // testing sepia
    file = new File("test/goat_sepia_test.png");
    if (file.exists()) {
      assertTrue(file.delete());
    }
    text = "load goat.png sepia save test/goat_sepia_test.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertTrue(file.exists());
    outContent.reset();
  }

  @Test
  public void reduceTest() {
    // testing reduce normal condition
    File file = new File("test/goat_reduce8_test.png");
    if (file.exists()) {
      assertTrue(file.delete());
    }
    String text = "load goat.png reduce 8 save test/goat_reduce8_test.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertTrue(file.exists());
    outContent.reset();

    // testing reduce 0
    text = "load goat.png reduce 0";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalArgumentException e) {
      System.out.format("%s", e.getMessage());
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("Color per channel cannot be less than 1\n", outContent.toString());
    outContent.reset();

    // testing reduce 256
    text = "load goat.png reduce 256";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalArgumentException e) {
      System.out.format("%s", e.getMessage());
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("Color per channel cannot exceed 255\n", outContent.toString());
    outContent.reset();

    // testing reduce a
    text = "load goat.png reduce a";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (IllegalArgumentException e) {
      fail();
    } catch (InputMismatchException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.InputMismatchException", outContent.toString());
    outContent.reset();

    // testing reduce
    text = "load goat.png reduce";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.NoSuchElementException", outContent.toString());
    outContent.reset();

    // testing dither normal condition
    file = new File("test/goat_dither8_test.png");
    if (file.exists()) {
      assertTrue(file.delete());
    }
    text = "load goat.png dither 8 save test/goat_dither8_test.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertTrue(file.exists());
    outContent.reset();

    // testing dither 0
    text = "load goat.png dither 0";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      System.out.format("%s", e.getMessage());
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("Color per channel cannot be less than 1\n", outContent.toString());
    outContent.reset();

    // testing dither 256
    text = "load goat.png dither 256";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      System.out.format("%s", e.getMessage());
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("Color per channel cannot exceed 255\n", outContent.toString());
    outContent.reset();

    // testing dither a
    text = "load goat.png dither a";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (InputMismatchException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.InputMismatchException", outContent.toString());
    outContent.reset();

    // testing dither
    text = "load goat.png dither";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.NoSuchElementException", outContent.toString());
    outContent.reset();
  }

  @Test
  public void mosaicAndPixelateTest() {
    // testing mosaic normal condition
    File file = new File("test/goat_mosaic10_test.png");
    if (file.exists()) {
      assertTrue(file.delete());
    }
    String text = "load goat.png mosaic 10 save test/goat_mosaic10_test.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertTrue(file.exists());
    outContent.reset();

    // testing mosaic 0
    text = "load goat.png mosaic 0";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      System.out.format("%s", e.getMessage());
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("Seed cannot be less than 1\n", outContent.toString());
    outContent.reset();

    // testing mosaic 99999999
    text = "load goat.png mosaic 99999999";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      System.out.format("%s", e.getMessage());
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("Number of seed exceeded the total pixel count\n", outContent.toString());
    outContent.reset();

    // testing mosaic a
    text = "load goat.png mosaic a";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (InputMismatchException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.InputMismatchException", outContent.toString());
    outContent.reset();

    // testing mosaic
    text = "load goat.png mosaic";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.NoSuchElementException", outContent.toString());
    outContent.reset();

    // testing pixelate normal condition
    file = new File("test/goat_pixelate10_test.png");
    if (file.exists()) {
      assertTrue(file.delete());
    }
    text = "load goat.png pixelate 10 save test/goat_pixelate10_test.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertTrue(file.exists());
    outContent.reset();

    // testing pixelate 0
    text = "load goat.png pixelate 0";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      System.out.format("%s", e.getMessage());
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("Super pixel cannot be less than 1\n", outContent.toString());
    outContent.reset();

    // testing pixelate 99999999
    text = "load goat.png pixelate 99999999";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      System.out.format("%s", e.getMessage());
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("Super pixel cannot exceed 150 pixels\n", outContent.toString());
    outContent.reset();

    // testing pixelate a
    text = "load goat.png pixelate a";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (InputMismatchException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.InputMismatchException", outContent.toString());
    outContent.reset();

    // testing pixelate
    text = "load goat.png pixelate";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.NoSuchElementException", outContent.toString());
    outContent.reset();
  }

  @Test
  public void patternTest() {
    // testing pattern normal condition
    File file = new File("test/goat_pattern10_test.txt");
    if (file.exists()) {
      assertTrue(file.delete());
    }
    String text = "load goat.png pattern 10\n export test/goat_pattern10_test.txt";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertTrue(file.exists());

    // testing pattern a
    text = "load goat.png pattern a";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (InputMismatchException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.InputMismatchException", outContent.toString());
    outContent.reset();

    // testing pattern
    text = "load goat.png pattern";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.NoSuchElementException", outContent.toString());
    outContent.reset();

    // testing export
    text = "export";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      System.out.format("%s", e.getClass());
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.util.NoSuchElementException", outContent.toString());
    outContent.reset();

    // testing checkp
    text = "checkp";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller = new Controller(new Model());
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      System.out.format("%s", e.getClass());
    } catch (IOException e) {
      fail();
    }
    assertEquals("class java.lang.IllegalStateException", outContent.toString());
    outContent.reset();
  }

  @Test
  public void mockModelTest() {
    // setting up the mock model controller
    controller = new Controller(new MockModel());

    // testing load
    String text = "load goat.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("load called with goat.png", outContent.toString());
    outContent.reset();

    // testing save
    text = "save goat.png";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("save called with goat.png", outContent.toString());
    outContent.reset();

    // testing blur
    text = "blur";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("blur called", outContent.toString());
    outContent.reset();

    // testing sharpen
    text = "sharpen";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("sharpen called", outContent.toString());
    outContent.reset();

    // testing grey
    text = "grey";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("grey called", outContent.toString());
    outContent.reset();

    // testing sepia
    text = "sepia";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("sepia called", outContent.toString());
    outContent.reset();

    // testing reduce 10
    text = "reduce 10";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("reduce called with 10", outContent.toString());
    outContent.reset();

    // testing dither 10
    text = "dither 10";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("dither called with 10", outContent.toString());
    outContent.reset();

    // testing mosaic 10
    text = "mosaic 10";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("mosaic called with 10", outContent.toString());
    outContent.reset();

    // testing pixelate 10
    text = "pixelate 10";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("pixelate called with 10", outContent.toString());
    outContent.reset();

    // testing pattern 10
    text = "pattern 10";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("pattern called with 10", outContent.toString());
    outContent.reset();

    // testing switch
    text = "switch a string";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("switchDmc called with a string", outContent.toString());
    outContent.reset();

    // testing export
    text = "export pattern.txt";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("export called with pattern.txt", outContent.toString());
    outContent.reset();

    // testing check
    text = "check";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("check called", outContent.toString());
    outContent.reset();

    // testing checkp
    text = "checkp";
    input = new ByteArrayInputStream(text.getBytes());
    try {
      controller.start(input);
    } catch (NoSuchElementException e) {
      fail();
    } catch (IllegalArgumentException e) {
      fail();
    } catch (IllegalStateException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
    assertEquals("checkp called", outContent.toString());
    outContent.reset();
  }
}
