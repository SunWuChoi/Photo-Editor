package images.view;

import images.controller.IController;
import images.model.functions.base.DmcList;
import images.model.functions.base.DmcList.Dmc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class for representing the view.
 * 
 * @author Sunwu Choi
 *
 */
public class View extends JFrame implements IView {
  private IController controller;
  private DmcList availDmcList;
  private Listener listener;
  private JPanel leftPanel;
  private JPanel batchPanel;
  private JLabel imageLabel;
  private JLabel sizePanel;
  private JPanel boardConstrain;
  private JLabel legendLabel;
  private JTextArea batchText;
  private ImageIcon image;

  /**
   * Default constructor for the view object.
   */
  public View() {
    super("Image editor");
  }

  @Override
  public void setListener(IController controller) {
    this.controller = controller;
    listener = new Listener(controller, this);
    listener.setUp();
  }

  @Override
  public void display() throws IllegalStateException {
    if (listener == null) {
      throw new IllegalStateException("No listener");
    }

    setSize(900, 1000);
    setLocation(200, 100);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    setupLeftPanel();
    setupMenuItems();
    setupBatchPanel();

    mainPanel.add(leftPanel, BorderLayout.CENTER);
    mainPanel.add(batchPanel, BorderLayout.LINE_END);

    setContentPane(mainPanel);
    setVisible(true);
  }

  @Override
  public void updateImage(BufferedImage inputImage) throws IllegalStateException {
    try {
      ImageIcon img = new ImageIcon(inputImage);
      img.getImage().flush();
      image.setImage(img.getImage());
      imageLabel.setIcon(image);
      imageLabel.setHorizontalAlignment(JLabel.CENTER);
      imageLabel.setVerticalAlignment(JLabel.CENTER);
      imageLabel.repaint();
      imageLabel.revalidate();
    } catch (IllegalStateException e) {
      printMessage(e.getMessage(), true);
    }
  }

  @Override
  public void updateCrossStitch(String[] pattern, DmcList availDmcList) {
    try {
      this.availDmcList = availDmcList;
      boardConstrain.removeAll();

      String size = pattern[0];
      String body = pattern[1];
      String legend = pattern[2];

      Map<Character, Integer[]> colorMap = getRgb(legend);

      sizePanel.setText(size);

      StringTokenizer sizeToken = new StringTokenizer(size);
      int col = Integer.valueOf(sizeToken.nextToken());
      sizeToken.nextToken();
      int row = Integer.valueOf(sizeToken.nextToken());
      int width = 500 / col;
      Character[][] patternArray = getPattern(body);

      JPanel gridBoard = new JPanel(new GridLayout(row, col));
      boardConstrain.add(gridBoard);

      JButton[][] patternBoard = new JButton[col][row];
      Insets buttonMargin = new Insets(0, 0, 0, 0);
      for (int i = 0; i < row; i++) {
        for (int j = 0; j < col; j++) {
          JButton b = new JButton();
          b.setMargin(buttonMargin);
          ImageIcon icon = new ImageIcon(
              new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB));
          b.setIcon(icon);

          Character alphabet = patternArray[j][i];
          Integer[] rgb = colorMap.get(alphabet);

          if (rgb[0] == -1) {
            b.setBackground(new Color(238, 238, 238));
            b.setBorder(BorderFactory.createEmptyBorder());
            b.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                selectAvailableDmc(alphabet);
              }
            });
            patternBoard[j][i] = b;
            gridBoard.add(b);
          } else {
            b.setFont(new Font("Arial", Font.PLAIN, width / 2));
            b.setHorizontalTextPosition(SwingConstants.CENTER);
            b.setVerticalTextPosition(SwingConstants.CENTER);

            b.setText(alphabet.toString().strip());
            b.setBackground(new Color(rgb[0], rgb[1], rgb[2]));
            b.setBorder(BorderFactory.createEmptyBorder());
            b.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                selectAvailableDmc(alphabet);
              }
            });
            patternBoard[j][i] = b;
            gridBoard.add(b);
          }
        }
      }

      legendLabel.setText("<html>"
          + legend.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>")
          + "</html>");

      leftPanel.repaint();
      leftPanel.revalidate();
    } catch (IllegalStateException e) {
      printMessage(e.getMessage(), true);
    } catch (NullPointerException e) {
      printMessage("No pattern passed", true);
    }
  }

  private void selectAvailableDmc(Character symbol) {
    JFrame selectColorFrame = new JFrame();
    JPanel colorPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(colorPanel);
    colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
    selectColorFrame.add(scrollPane);
    selectColorFrame.setSize(300, 600);

    JButton blank = new JButton("Blank");
    blank.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchDmc(symbol, "Blank");
        selectColorFrame.setVisible(false);
      }
    });
    colorPanel.add(blank);

    List<Dmc> dmcList = availDmcList.getArrayList();

    for (int i = 0; i < dmcList.size(); i++) {
      Dmc currentDmc = dmcList.get(i);
      JButton b = new JButton(currentDmc.getName());
      b.setBackground(new Color(currentDmc.getRed(), currentDmc.getGreen(), currentDmc.getBlue()));
      b.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String newDmc = currentDmc.getFloss() + ",";
          String[] nameToken = currentDmc.getName().split(" ");
          for (String token : nameToken) {
            newDmc = newDmc + token + ",";
          }
          newDmc = newDmc + currentDmc.getRed() + "," + currentDmc.getGreen() + ","
              + currentDmc.getBlue();
          switchDmc(symbol, newDmc);
          selectColorFrame.setVisible(false);
        }
      });
      colorPanel.add(b);
    }
    selectColorFrame.setLocation(300, 100);
    selectColorFrame.setVisible(true);
  }

  private void switchDmc(char symbol, String dmc) {
    try {
      String command = "switch " + Character.toString(symbol) + " " + dmc;
      controller.start(new ByteArrayInputStream(command.getBytes()));
    } catch (Exception e) {
      printMessage(e.getMessage(), true);
    }
  }

  private Character[][] getPattern(String body) {
    String[] lines = body.split("\\r?\\n");
    String[] alphabets = lines[0].split(" ");
    Character[][] pattern = new Character[alphabets.length][lines.length];

    for (int i = 0; i < lines.length; i++) {
      alphabets = lines[i].split(" ");
      for (int j = 0; j < alphabets.length; j++) {
        pattern[j][i] = alphabets[j].charAt(0);
      }
    }

    return pattern;
  }

  private Map<Character, Integer[]> getRgb(String legend) {
    Map<Character, Integer[]> map = new HashMap<Character, Integer[]>();
    String[] lines = legend.split("\n");
    for (int i = 1; i < lines.length; i++) {
      String[] tokens = lines[i].split(" ");
      if (tokens[1].equals("Blank")) {
        map.put(lines[i].charAt(0), new Integer[] { -1, -1, -1 });
      } else {
        int length = tokens.length;
        int r = Integer.valueOf(tokens[length - 3]);
        int g = Integer.valueOf(tokens[length - 2]);
        int b = Integer.valueOf(tokens[length - 1]);
        Integer[] rgb = new Integer[] { r, g, b };
        map.put(lines[i].charAt(0), rgb);
      }
    }

    return map;
  }

  private void printMessage(String message, boolean isError) {
    if (isError) {
      JOptionPane.showMessageDialog(View.this, message, "Error", JOptionPane.ERROR_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(View.this, message, "Message", JOptionPane.PLAIN_MESSAGE);
    }
  }

  private void setupLeftPanel() {
    image = new ImageIcon();
    leftPanel = new JPanel();
    leftPanel.setLayout(new BorderLayout());
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image Section"));
    imagePanel.setLayout(new GridLayout(1, 0, 0, 0));
    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(image);
    imagePanel.add(imageScrollPane);
    imagePanel.setPreferredSize(new Dimension(400, 400));
    leftPanel.add(imagePanel, BorderLayout.PAGE_START);

    JPanel crossStitchPanelParent = new JPanel();
    crossStitchPanelParent.setLayout(new GridLayout(1, 0, 0, 0));
    JPanel crossStitchPanel = new JPanel();
    JScrollPane patternScrollPane = new JScrollPane(crossStitchPanel);
    crossStitchPanelParent.add(patternScrollPane);

    crossStitchPanel.setLayout(new BoxLayout(crossStitchPanel, BoxLayout.Y_AXIS));
    sizePanel = new JLabel("Cross Stitch pattern size section");
    crossStitchPanel.add(sizePanel);

    boardConstrain = new JPanel(new GridBagLayout());
    crossStitchPanel.add(boardConstrain);

    legendLabel = new JLabel("Cross Stitch pattern legend section");
    crossStitchPanel.add(legendLabel);

    crossStitchPanelParent.setBorder(BorderFactory.createTitledBorder("Cross Stitch Section"));
    leftPanel.add(crossStitchPanelParent, BorderLayout.CENTER);
  }

  private void setupMenuItems() {
    // file menu
    JMenu fileMenu = new JMenu("File");

    JMenuItem load = new JMenuItem("Open file");
    load.addActionListener(listener);
    fileMenu.add(load);

    JMenuItem save = new JMenuItem("Save file");
    save.addActionListener(listener);
    fileMenu.add(save);
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(fileMenu);

    // edit menu
    JMenu editMenu = new JMenu("Edit");

    JMenuItem blur = new JMenuItem("Blur");
    blur.addActionListener(listener);
    editMenu.add(blur);

    JMenuItem sharpen = new JMenuItem("Sharpen");
    sharpen.addActionListener(listener);
    editMenu.add(sharpen);

    JMenuItem grey = new JMenuItem("Grey");
    grey.addActionListener(listener);
    editMenu.add(grey);

    JMenuItem sepia = new JMenuItem("Sepia");
    sepia.addActionListener(listener);
    editMenu.add(sepia);

    JMenuItem reduce = new JMenuItem("Reduce");
    reduce.addActionListener(listener);
    editMenu.add(reduce);

    JMenuItem dither = new JMenuItem("Dither");
    dither.addActionListener(listener);
    editMenu.add(dither);

    JMenuItem mosaic = new JMenuItem("Mosaic");
    mosaic.addActionListener(listener);
    editMenu.add(mosaic);

    JMenuItem pixelate = new JMenuItem("Pixelate");
    pixelate.addActionListener(listener);
    editMenu.add(pixelate);

    menuBar.add(editMenu);

    // generate menu
    JMenu generateMenu = new JMenu("Generate");

    JMenuItem crossStitch = new JMenuItem("Cross Stitch");
    crossStitch.addActionListener(listener);
    generateMenu.add(crossStitch);

    JMenuItem saveCrossStitch = new JMenuItem("Save Cross Stitch");
    saveCrossStitch.addActionListener(listener);
    generateMenu.add(saveCrossStitch);

    menuBar.add(generateMenu);

    // batch menu
    JMenu batchMenu = new JMenu("Batch");

    JMenuItem runBatch = new JMenuItem("Execute batch");
    runBatch.addActionListener(listener);
    batchMenu.add(runBatch);

    menuBar.add(batchMenu);

    super.setJMenuBar(menuBar);
  }

  private void setupBatchPanel() {
    // batch panel
    batchPanel = new JPanel();
    batchPanel.setPreferredSize(new Dimension(145, 450));
    batchPanel.setLayout(new BoxLayout(batchPanel, BoxLayout.Y_AXIS));

    // batch text
    batchText = new JTextArea(1, 5);
    JScrollPane scrollPane = new JScrollPane(batchText);
    batchText.setLineWrap(true);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Batch Section"));
    batchPanel.add(scrollPane);

    // Execute button
    JButton execute = new JButton("Execute Batch");
    execute.setActionCommand("Execute batch");
    execute.addActionListener(listener);
    batchPanel.add(execute);
  }

  private class Listener implements ActionListener {
    private Map<String, Runnable> eventMap;
    private IController controller;

    public Listener(IController controller, IView view) {
      if (controller == null) {
        throw new IllegalArgumentException("Null controller passed to the listener");
      }
      this.controller = controller;
    }

    public void setUp() {
      eventMap = new HashMap<>();
      eventMap.put("Open file", this::open);
      eventMap.put("Save file", this::save);
      eventMap.put("Blur", this::blur);
      eventMap.put("Sharpen", this::sharpen);
      eventMap.put("Grey", this::grey);
      eventMap.put("Sepia", this::sepia);
      eventMap.put("Reduce", this::reduce);
      eventMap.put("Dither", this::dither);
      eventMap.put("Mosaic", this::mosaic);
      eventMap.put("Pixelate", this::pixelate);
      eventMap.put("Execute batch", this::batch);
      eventMap.put("Cross Stitch", this::pattern);
      eventMap.put("Save Cross Stitch", this::exportPattern);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (eventMap.containsKey(e.getActionCommand())) {
        eventMap.get(e.getActionCommand()).run();
      }
    }

    private void open() {
      final JFileChooser fileChooser = new JFileChooser(".");
      fileChooser.setFileFilter(
          new FileNameExtensionFilter("JPG, GIF, & PNG Images", "jpg", "gif", "png"));
      int option = fileChooser.showOpenDialog(View.this);
      if (option == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        String command = "load " + file.getAbsolutePath();
        try {
          printMessage("Opening file", false);
          controller.start(new ByteArrayInputStream(command.getBytes()));
        } catch (IOException e) {
          printMessage(e.getMessage(), true);
        }
      }
    }

    private void save() {
      try {
        controller.start(new ByteArrayInputStream("check".getBytes()));
        final JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter(".PNG (*.png)", "png"));
        if (fileChooser.showSaveDialog(View.this) == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          String command = "save " + file.getAbsolutePath();
          printMessage("Saving file", false);
          controller.start(new ByteArrayInputStream(command.getBytes()));
        }
      } catch (IllegalStateException e) {
        printMessage(e.getMessage(), true);
      } catch (IOException e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void blur() {
      try {
        printMessage("Processing image", false);
        String command = "blur";
        controller.start(new ByteArrayInputStream(command.getBytes()));
      } catch (Exception e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void sharpen() {
      try {
        printMessage("Processing image", false);
        String command = "sharpen";
        controller.start(new ByteArrayInputStream(command.getBytes()));
      } catch (Exception e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void grey() {
      try {
        printMessage("Processing image", false);
        String command = "grey";
        controller.start(new ByteArrayInputStream(command.getBytes()));
      } catch (Exception e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void sepia() {
      try {
        printMessage("Processing image", false);
        String command = "sepia";
        controller.start(new ByteArrayInputStream(command.getBytes()));
      } catch (Exception e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void reduce() {
      try {
        controller.start(new ByteArrayInputStream("check".getBytes()));
        String ask = JOptionPane
            .showInputDialog("Enter number of color per channel for reduce operation");
        int reduceColorNum;
        if (ask == null) {
          return;
        } else {
          reduceColorNum = Integer.valueOf(ask);
        }
        printMessage("Processing image", false);
        String command = "reduce " + reduceColorNum;
        controller.start(new ByteArrayInputStream(command.getBytes()));
      } catch (NumberFormatException e) {
        printMessage("Enter numbers only", true);
      } catch (IllegalStateException | IllegalArgumentException | IOException e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void dither() {
      try {
        controller.start(new ByteArrayInputStream("check".getBytes()));
        String ask = JOptionPane
            .showInputDialog("Enter number of color per channel for dither operation");
        int reduceColorNum;
        if (ask == null) {
          return;
        } else {
          reduceColorNum = Integer.valueOf(ask);
        }
        printMessage("Processing image", false);
        String command = "dither " + reduceColorNum;
        controller.start(new ByteArrayInputStream(command.getBytes()));
      } catch (NumberFormatException e) {
        printMessage("Enter numbers only", true);
      } catch (IllegalStateException | IllegalArgumentException | IOException e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void mosaic() {
      try {
        controller.start(new ByteArrayInputStream("check".getBytes()));
        String ask = JOptionPane.showInputDialog("Enter seed for mosaic operation");
        int seed;
        if (ask == null) {
          return;
        } else {
          seed = Integer.valueOf(ask);
        }
        printMessage("Processing image", false);
        String command = "mosaic " + seed;
        controller.start(new ByteArrayInputStream(command.getBytes()));
      } catch (NumberFormatException e) {
        printMessage("Enter numbers only", true);
      } catch (IllegalStateException | IllegalArgumentException | IOException e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void pixelate() {
      try {
        controller.start(new ByteArrayInputStream("check".getBytes()));
        String ask = JOptionPane
            .showInputDialog("Enter number of super pixel per row for pixelate operation");
        int seed;
        if (ask == null) {
          return;
        } else {
          seed = Integer.valueOf(ask);
        }
        printMessage("Processing image", false);
        String command = "pixelate " + seed;
        controller.start(new ByteArrayInputStream(command.getBytes()));
      } catch (NumberFormatException e) {
        printMessage("Enter numbers only", true);
      } catch (IllegalStateException | IllegalArgumentException | IOException e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void batch() {
      try {
        printMessage("Processing batch", false);
        String command = batchText.getText();
        controller.start(new ByteArrayInputStream(command.getBytes()));
      } catch (NoSuchElementException e) {
        printMessage("Function with no argument found in batch", true);
      } catch (Exception e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void pattern() {
      try {
        controller.start(new ByteArrayInputStream("check".getBytes()));
        String ask = JOptionPane
            .showInputDialog("Enter number of super pixel per row for cross stitch");
        int seed;
        if (ask == null) {
          return;
        } else {
          seed = Integer.valueOf(ask);
        }
        printMessage("Generating Cross Stitch Pattern", false);
        String command = "pattern " + seed;
        controller.start(new ByteArrayInputStream(command.getBytes()));
      } catch (NumberFormatException e) {
        printMessage("Enter numbers only", true);
      } catch (IllegalStateException | IllegalArgumentException | IOException e) {
        printMessage(e.getMessage(), true);
      }
    }

    private void exportPattern() {
      try {
        controller.start(new ByteArrayInputStream("checkp".getBytes()));
        final JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Documents (*.txt)", "txt"));
        if (fileChooser.showSaveDialog(View.this) == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          printMessage("Saving Cross Stitch Pattern", false);
          String command = "export " + file.getAbsolutePath();
          controller.start(new ByteArrayInputStream(command.getBytes()));
        }
      } catch (IllegalStateException | IllegalArgumentException | IOException e) {
        printMessage(e.getMessage(), true);
      }
    }
  }
}
