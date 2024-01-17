package view;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ComponentOrientation;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.SpinnerModel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

/**
 * Image Processing GUI Implementation class to display GUI.
 */
public class ImageGUIViewImpl extends JFrame implements ImageGUIView {
  private JPanel operationTextPanel;
  private JPanel compressionPanel;
  private JPanel levelsPanel;
  private JPanel brightenPanel;
  private JPanel darkenPanel;
  private JPanel splitPercentagePanel;
  private JComboBox<String> operationsCombobox;
  private JCheckBox splitCheckBox;
  private JSlider splitSlider;
  private JLabel percentageLabel;
  private final String[] operationOptions = {"Visualize Red Component", "Visualize Green Component"
                                              , "Visualize Blue Component"
                                              , "Flip Horizontal", "Flip Vertical"
                                              , "Blur", "Sharpen", "Greyscale", "Sepia", "Brighten"
                                              , "Darken"
                                              , "Compression", "Color Correction", "Levels Adjust"
  };

  private final String[] splitOptions = {"Sharpen", "Blur", "Sepia", "Greyscale"
                                          , "Color Correction", "Levels Adjust"
  };

  private final String[] requireInputsOptions = {"Compression"
                                                  , "Levels Adjust", "Brighten", "Darken"
  };
  private JSpinner levelsBlackSpinner;
  private JSpinner brightenSpinner;
  private JSpinner darkenSpinner;
  private JSpinner levelsMidSpinner;
  private JSpinner levelsWhiteSpinner;
  private JSpinner compressionSpinner;
  private JButton loadButton;
  private JButton saveButton;
  private JLabel checkBoxLabel;
  private JButton executeButton;
  private JLabel histImageLabel;
  private JLabel modifiedImageLabel;
  private Boolean isImageSaved = true;

  /**
   * Constructor that displays GUI window on screen.
   */
  public ImageGUIViewImpl() {
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }


  /**
   * Method to show GUI Window.
   */
  @Override
  public void displayGUI() {
    setTitle("Project");
    setSize(1250, 700);
    setLocation(200, 200);
    JPanel mainPanel = new JPanel(new BorderLayout());
    add(mainPanel);

    JPanel wholePanel = new JPanel();
    wholePanel.setLayout(new BoxLayout(wholePanel, BoxLayout.Y_AXIS));
    JScrollPane topScrollPane = new JScrollPane(wholePanel);
    mainPanel.add(topScrollPane, BorderLayout.CENTER);

    //TOP PANEL FROM HERE
    JPanel topPanel = new JPanel();
    TitledBorder title = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.black), "Image Display Area");
    title.setTitleJustification(TitledBorder.CENTER);
    topPanel.setBorder(title);
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
    topScrollPane = new JScrollPane(topPanel);
    topScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    wholePanel.add(topPanel, BorderLayout.CENTER);

    JPanel imageTopPanel = new JPanel();
    topPanel.add(imageTopPanel);

    modifiedImageLabel = new JLabel();
    JScrollPane modifiedImageScrollPane = new JScrollPane(modifiedImageLabel);
    modifiedImageScrollPane.setBackground(Color.LIGHT_GRAY);
    modifiedImageScrollPane.setBorder(BorderFactory.createTitledBorder("Processed Image"));
    modifiedImageScrollPane.setPreferredSize(new Dimension(800, 400));
    imageTopPanel.add(modifiedImageScrollPane);

    histImageLabel = new JLabel();
    JScrollPane histImageScrollPane = new JScrollPane(histImageLabel);
    histImageLabel.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 0));
    histImageScrollPane.setBackground(Color.LIGHT_GRAY);
    histImageScrollPane.setBorder(BorderFactory.createTitledBorder("Histogram Of Processed Image"));
    histImageScrollPane.setPreferredSize(new Dimension(400, 400));
    imageTopPanel.add(histImageScrollPane);


    //----------------------------------------------------------
    //BOTTOM PANEL FROM HERE
    JPanel bottomPanel = new JPanel();
    TitledBorder title2 = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.black), "Operation Select Area");
    title2.setTitleJustification(TitledBorder.CENTER);
    bottomPanel.setBorder(title2);
    wholePanel.add(bottomPanel, BorderLayout.CENTER);


    JPanel comboboxPanel = new JPanel();
    bottomPanel.add(comboboxPanel);
    JLabel comboboxLabel = new JLabel("Select Operation:");
    comboboxPanel.add(comboboxLabel);
    operationsCombobox = new JComboBox<String>();

    for (String operationOption : operationOptions) {
      operationsCombobox.addItem(operationOption);
    }
    operationsCombobox.setToolTipText("Select an operation and click apply.");
    operationsCombobox.setActionCommand("Selected Image Operation");
    comboboxPanel.add(operationsCombobox);
    operationsCombobox.setEnabled(false);
    checkBoxLabel = new JLabel("Split View:");
    comboboxPanel.add(checkBoxLabel);
    splitCheckBox = new JCheckBox();
    splitCheckBox.setSelected(false);
    splitCheckBox.setActionCommand("Split Selected");
    checkBoxLabel.setVisible(false);
    splitCheckBox.setVisible(false);

    comboboxPanel.add(splitCheckBox);
    splitPercentagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    splitPercentagePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    JLabel splitPercentLabel = new JLabel("Split(%):");
    splitPercentagePanel.add(splitPercentLabel);
    splitSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    splitSlider.setMajorTickSpacing(20);
    splitSlider.setMinorTickSpacing(5);
    splitSlider.setPaintTicks(true);
    splitSlider.setPaintLabels(true);


    splitPercentagePanel.setVisible(false);
    splitPercentagePanel.add(splitSlider);
    percentageLabel = new JLabel(splitSlider.getValue() + "%");
    splitPercentagePanel.add(percentageLabel);
    comboboxPanel.add(splitPercentagePanel);


    operationTextPanel = new JPanel();
    operationTextPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    bottomPanel.add(operationTextPanel);
    boolean isSplitSupported = Arrays.asList(splitOptions)
            .contains(operationsCombobox.getSelectedItem());
    boolean isInputRequired = Arrays.asList(requireInputsOptions)
            .contains(operationsCombobox.getSelectedItem());
    operationTextPanel.setVisible(isInputRequired);


    compressionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    operationTextPanel.add(compressionPanel);
    JLabel inputLabel = new JLabel("Compression Percentage");
    compressionPanel.add(inputLabel);
    SpinnerModel compressionModel = new SpinnerNumberModel(0, 0, 100, 1);
    compressionSpinner = new JSpinner(compressionModel);
    compressionSpinner.setToolTipText("Set the compression ratio.");
    compressionSpinner.setMaximumSize(new Dimension(75, 20));
    compressionPanel.add(compressionSpinner);

    levelsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    operationTextPanel.add(levelsPanel);
    JLabel levelsLabel = new JLabel("Level Values:");
    JLabel levelsBlackLabel = new JLabel("Black:");
    JLabel levelsWhiteLabel = new JLabel("White:");
    JLabel levelsMidLabel = new JLabel("Mid-Value:");
    levelsPanel.add(levelsLabel);
    SpinnerModel levelsBlackModel = new SpinnerNumberModel(0, 0, 255, 1);
    SpinnerModel levelsWhiteModel = new SpinnerNumberModel(255, 0, 255, 1);
    SpinnerModel levelsMidModel = new SpinnerNumberModel(128, 0, 255, 1);
    levelsBlackSpinner = new JSpinner(levelsBlackModel);
    levelsWhiteSpinner = new JSpinner(levelsWhiteModel);
    levelsMidSpinner = new JSpinner(levelsMidModel);
    levelsBlackSpinner.setToolTipText("This value should be less than Mid Value");
    levelsMidSpinner.setToolTipText("This value should be less than White Value");
    levelsWhiteSpinner.setToolTipText("This value should be less than 255.");
    levelsBlackSpinner.setMaximumSize(new Dimension(65, 30));
    levelsWhiteSpinner.setMaximumSize(new Dimension(65, 30));
    levelsMidSpinner.setMaximumSize(new Dimension(65, 30));
    levelsPanel.add(levelsBlackLabel);
    levelsPanel.add(levelsBlackSpinner);
    levelsPanel.add(levelsMidLabel);
    levelsPanel.add(levelsMidSpinner);
    levelsPanel.add(levelsWhiteLabel);
    levelsPanel.add(levelsWhiteSpinner);

    brightenPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    operationTextPanel.add(brightenPanel);
    JLabel brightLabel = new JLabel("Intensity:");
    SpinnerModel brightenModel = new SpinnerNumberModel(0, 0, 200, 1);
    brightenSpinner = new JSpinner(brightenModel);
    brightenSpinner.setToolTipText("Should be Positive value");
    brightenSpinner.setMaximumSize(new Dimension(65, 30));
    brightenPanel.add(brightLabel);
    brightenPanel.add(brightenSpinner);

    darkenPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    operationTextPanel.add(darkenPanel);
    JLabel darkLabel = new JLabel("Intensity:");
    SpinnerModel darkenModel = new SpinnerNumberModel(0, 0, 200, 1);
    darkenSpinner = new JSpinner(darkenModel);
    darkenSpinner.setToolTipText("Should be Positive value");
    darkenSpinner.setMaximumSize(new Dimension(65, 30));
    darkenPanel.add(darkLabel);
    darkenPanel.add(darkenSpinner);

    //EXECUTION PANEL FROM HERE
    JPanel bottomExecutionPanel = new JPanel();
    TitledBorder title3 = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.black), "Execution Area");
    title3.setTitleJustification(TitledBorder.CENTER);
    bottomExecutionPanel.setBorder(title3);
    wholePanel.add(bottomExecutionPanel, BorderLayout.CENTER);

    loadButton = new JButton("Load");
    loadButton.setPreferredSize(new Dimension(200, 30));
    loadButton.setActionCommand("Load Image");

    saveButton = new JButton("Save");
    saveButton.setPreferredSize(new Dimension(200, 30));
    saveButton.setVisible(false);
    saveButton.setActionCommand("Save Image");

    executeButton = new JButton("Execute Operation");
    executeButton.setPreferredSize(new Dimension(400, 30));
    executeButton.setVisible(false);
    executeButton.setActionCommand("Execute Operation");

    bottomExecutionPanel.add(executeButton);
    bottomExecutionPanel.add(loadButton);
    bottomExecutionPanel.add(saveButton);

    setVisible(true);
  }

  /**
   * Private method called when Image Operation dropdown changed to change specific UI panels.
   */
  private void checkImageOperation() {
    boolean isSplitSupported = Arrays.asList(splitOptions)
            .contains(operationsCombobox.getSelectedItem());
    boolean isInputRequired = Arrays.asList(requireInputsOptions)
            .contains(operationsCombobox.getSelectedItem());
    if (!isSplitSupported) {
      splitCheckBox.setSelected(false);
    } else {
      splitCheckBox.setSelected(false);
      splitSlider.setValue(0);
    }
    checkBoxLabel.setVisible(isSplitSupported);
    splitCheckBox.setVisible(isSplitSupported);
    splitPercentagePanel.setVisible(isSplitSupported && splitCheckBox.isSelected());
    if (isInputRequired) {
      if (operationsCombobox.getSelectedItem().toString().equals("Compression")) {
        levelsPanel.setVisible(false);
        compressionPanel.setVisible(true);
        brightenPanel.setVisible(false);
        darkenPanel.setVisible(false);
      } else if (operationsCombobox.getSelectedItem().toString().equals("Brighten")) {
        levelsPanel.setVisible(false);
        compressionPanel.setVisible(false);
        brightenPanel.setVisible(true);
        darkenPanel.setVisible(false);
      } else if (operationsCombobox.getSelectedItem().toString().equals("Darken")) {
        levelsPanel.setVisible(false);
        compressionPanel.setVisible(false);
        brightenPanel.setVisible(false);
        darkenPanel.setVisible(true);
      } else {
        levelsPanel.setVisible(true);
        compressionPanel.setVisible(false);
        brightenPanel.setVisible(false);
        darkenPanel.setVisible(false);
      }
    } else {
      levelsPanel.setVisible(false);
      compressionPanel.setVisible(false);
      brightenPanel.setVisible(false);
      darkenPanel.setVisible(false);
    }
    operationTextPanel.setVisible(isInputRequired);
  }

  /**
   * Private method triggered when checkbox change.
   *
   * @param e        Event Triggered.
   * @param features Features to use on event.
   * @throws IOException If Image not found.
   */
  private void splitCheckChange(ItemEvent e, Features features) throws IOException {
    String who = ((JCheckBox) e.getItemSelectable()).getActionCommand();
    if (Objects.equals(who, "Split Selected")) {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        splitPercentagePanel.setVisible(true);
        splitSlider.setValue(50);
        executePressed(features, splitSlider.getValue(), false);
        modifiedImageLabel.setIcon(new ImageIcon(features.getCurrenSplitImage()));
      } else {
        splitPercentagePanel.setVisible(false);
        splitSlider.setValue(100);
        modifiedImageLabel.setIcon(new ImageIcon(features.getCurrentImage()));
      }
    }
  }

  /**
   * Private Method triggered when split slider value change.
   *
   * @param e    Event Triggered.
   * @param feat Features to use on event.
   * @throws IOException If Image not found.
   */
  private void slideStateChange(ChangeEvent e, Features feat) throws IOException {
    if (e.getSource() instanceof JSlider) {
      JSlider sourceVal = (JSlider) e.getSource();
      if (!sourceVal.getValueIsAdjusting()) {
        percentageLabel.setText(sourceVal.getValue() + "%");
        executePressed(feat, sourceVal.getValue(), false);
      }
    }
  }


  /**
   * Method to call different callbacks in execute button press.
   *
   * @param feat    Features to use on event.
   * @param split   split value.
   * @param execute boolean to view split.
   * @throws IOException If Image not found.
   */
  private void executePressed(Features feat, int split, boolean execute) throws IOException {
    String currOperation = (String) operationsCombobox.getSelectedItem();
    try {
      switch (currOperation) {
        case "Visualize Red Component":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeRedComponent()));
          break;
        case "Visualize Green Component":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeGreenComponent()));
          break;
        case "Visualize Blue Component":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeBlueComponent()));
          break;
        case "Flip Horizontal":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeHorizontalFlip()));
          break;
        case "Flip Vertical":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeVerticalFlip()));
          break;
        case "Blur":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeBlurOperation(split, execute)));
          break;
        case "Sharpen":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeSharpenOperation(split, execute)));
          break;
        case "Greyscale":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeGreyscaleOperation(split, execute)));
          break;
        case "Sepia":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeSepiaOperation(split, execute)));
          break;
        case "Compression":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeCompressionOperation(
                  String.valueOf(compressionSpinner.getValue()))));
          break;
        case "Color Correction":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeColorCorrectOperation(
                  split, execute)));
          break;
        case "Levels Adjust":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeLevelsAdjustOperation(
                  String.valueOf(levelsBlackSpinner.getValue()), String.valueOf(
                          levelsMidSpinner.getValue()), String.valueOf(
                          levelsWhiteSpinner.getValue()), split, execute)));
          break;
        case "Brighten":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeBrightenOperation(
                  String.valueOf(brightenSpinner.getValue()))));
          break;
        case "Darken":
          modifiedImageLabel.setIcon(new ImageIcon(feat.executeDarkenOperation(
                  String.valueOf(darkenSpinner.getValue()))));
          break;
        default:
          break;
      }
      feat.generateHistogram();
      histImageLabel.setIcon(new ImageIcon(feat.generateHistogram()));
    } catch (Exception e) {
      displayMessagePopUp(e.getMessage());
    }

  }

  /**
   * Method to display popup.
   *
   * @param displayMessage Message to display in popup.
   */
  private void displayMessagePopUp(String displayMessage) {
    JOptionPane.showMessageDialog(null, displayMessage, "Information",
            JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Method to display popup.
   *
   * @param displayMessage Message to display in popup.
   * @return Yes or No.
   */
  private int loadBeforeSave(String displayMessage) {
    return JOptionPane.showConfirmDialog(null, displayMessage, "Information",
            JOptionPane.YES_NO_OPTION);
  }

  /**
   * Method to load the image in the on load click press.
   *
   * @param features features to be used by GUI.
   * @throws IOException If file not found.
   */
  private void onLoad(Features features) throws IOException {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Input Image", "jpg", "png", "ppm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(null);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String path = f.getAbsolutePath();
      BufferedImage img = features.loadImage(path);
      operationsCombobox.setEnabled(true);
      modifiedImageLabel.setIcon(new ImageIcon(img));
      histImageLabel.setIcon(new ImageIcon(features.generateHistogram()));
      isImageSaved = true;
      executeButton.setVisible(true);
      saveButton.setVisible(true);
    }

  }

  /**
   * Method to add action listeners to GUI as per the features.
   *
   * @param features instance for features interface that need to use in UI.
   */
  @Override
  public void addFeatures(Features features) {
    operationsCombobox.addActionListener(evt -> checkImageOperation());
    loadButton.addActionListener(evt -> {
      try {
        if (!isImageSaved) {
          if (loadBeforeSave("Current Image is not saved." +
                  " Do you want to load new Image?") == JOptionPane.YES_OPTION) {
            onLoad(features);
          }
        } else {
          onLoad(features);
        }
      } catch (IOException e) {
        displayMessagePopUp("Oops, Image Load Issue." + e);
      }
    });
    saveButton.addActionListener(evt -> {
      try {
        final JFileChooser fchooser = new JFileChooser(".");
        fchooser.setFileFilter(new FileNameExtensionFilter(
                "JPG", "jpg"));
        fchooser.setFileFilter(new FileNameExtensionFilter(
                "PNG", "png"));
        fchooser.setFileFilter(new FileNameExtensionFilter(
                "PPM", "ppm"));
        if (fchooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          String path = String.format(String.format(f.getAbsolutePath() + "."
                  + fchooser.getFileFilter().getDescription()));
          features.saveImage(path);
          isImageSaved = true;
          displayMessagePopUp("Image Saved Successfully.");
        }

      } catch (Exception e) {
        displayMessagePopUp("Oops, Image Not Saved." + e);
      }
    });
    executeButton.addActionListener(evt -> {
      try {
        isImageSaved = false;
        executePressed(features, 0, true);
      } catch (IOException e) {
        displayMessagePopUp("Oops, Image Not Executed Successfully." + e);
      }
    });
    splitSlider.addChangeListener(evt -> {
      try {
        slideStateChange(evt, features);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    splitCheckBox.addItemListener(evt -> {
      try {
        splitCheckChange(evt, features);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    revalidate();
    repaint();
  }


}
