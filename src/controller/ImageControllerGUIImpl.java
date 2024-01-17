package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import model.ImageModelStore;
import view.ImageGUIView;

/**
 * New Controller for UI.
 */
public class ImageControllerGUIImpl implements Features {
  private ControllerHelper helper;
  private ImageModelStore modelStore;
  private final String currImageName = "curr";
  private final String currImageHist = "curr-hist";
  private final String splitImage = "split-img";

  /**
   * Constructor that connects the view and the model to the controller and displays the GUI.
   *
   * @param onScreenView view of the program GUI.
   * @param modelStore   model of the image for the program GUI.
   */
  public ImageControllerGUIImpl(ImageGUIView onScreenView, ImageModelStore modelStore) {
    this.helper = new ControllerHelperImpl(modelStore);
    this.modelStore = modelStore;
    onScreenView.displayGUI();
    onScreenView.addFeatures(this);
  }

  /**
   * Constructor that connects the mock model to the controller and displays the GUI.
   *
   * @param modelStore model of the image for the program GUI.
   */
  public ImageControllerGUIImpl(ImageModelStore modelStore) {
    this.helper = new ControllerHelperImpl(modelStore);
    this.modelStore = modelStore;
  }

  /**
   * Method to save image.
   *
   * @param path path where image is to be saved.
   * @throws IOException if the image is not saved and path is not found.
   */
  @Override
  public void saveImage(String path) throws IOException {
    String[] words = {"save", path, currImageName};
    helper.saveHelper(words);
  }

  /**
   * Method to load an image in the program.
   *
   * @param path path where image is to be saved.
   * @return Loaded instance of Buffered Image.
   */
  @Override
  public BufferedImage loadImage(String path) {
    String[] words = {"load", path, currImageName};
    helper.loadHelper(words);
    return modelStore.getBufferedImage(currImageName);
  }

  /**
   * Method to generate Histogram for UI.
   *
   * @return instance of Buffered Image.
   * @throws IOException if the image is not saved and path is not found.
   */
  @Override
  public BufferedImage generateHistogram() throws IOException {
    if (modelStore.getImageForAlias(currImageName) != null) {
      String[] words = {"histogram", currImageName, currImageHist};
      helper.histogramHelper(words);
      return modelStore.getBufferedImage(currImageHist);
    } else {
      return null;
    }
  }


  /**
   * Method to execute sepia operation on current image for UI.
   *
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeSepiaOperation(int split, Boolean execute)
          throws IllegalArgumentException {
    if (execute) {
      String[] words = {"sepia", currImageName, currImageName};
      helper.sepiaHelper(words);
      return modelStore.getBufferedImage(currImageName);
    }
    String[] words = {"sepia", currImageName, splitImage, "split", split + ""};
    helper.sepiaHelper(words);
    return modelStore.getBufferedImage(splitImage);
  }

  /**
   * Method to execute red-component of the current image foe UI.
   *
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeRedComponent() {
    String[] words = {"red-component", currImageName, currImageName};
    helper.redComponentHelper(words);
    return modelStore.getBufferedImage(currImageName);
  }

  /**
   * Method to execute green-component of the current image for UI.
   *
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeGreenComponent() {
    String[] words = {"green-component", currImageName, currImageName};
    helper.greenComponentHelper(words);
    return modelStore.getBufferedImage(currImageName);
  }

  /**
   * Method to execute green-component of the current image for UI.
   *
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeBlueComponent() {
    String[] words = {"blue-component", currImageName, currImageName};
    helper.blueComponentHelper(words);
    return modelStore.getBufferedImage(currImageName);
  }

  /**
   * Method to execute horizontal flip operation of the current image for UI.
   *
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeHorizontalFlip() {
    String[] words = {"horizontal-flip", currImageName, currImageName};
    helper.horizontalFlipHelper(words);
    return modelStore.getBufferedImage(currImageName);
  }

  /**
   * Method to execute vertical flip operation of the current image for UI.
   *
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeVerticalFlip() {
    String[] words = {"vertical-flip", currImageName, currImageName};
    helper.verticalFlipHelper(words);
    return modelStore.getBufferedImage(currImageName);
  }

  /**
   * Method to execute blur operation on current image for UI.
   *
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeBlurOperation(int split, Boolean execute)
          throws IllegalArgumentException {
    if (execute) {
      String[] words = {"blur", currImageName, currImageName};
      helper.blurHelper(words);
      return modelStore.getBufferedImage(currImageName);
    }
    String[] words = {"blur", currImageName, splitImage, "split", split + ""};
    helper.blurHelper(words);
    return modelStore.getBufferedImage(splitImage);
  }

  /**
   * Method to execute sharpen operation on current image for UI.
   *
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeSharpenOperation(int split, Boolean execute)
          throws IllegalArgumentException {
    if (execute) {
      String[] words = {"sharpen", currImageName, currImageName};
      helper.sharpenHelper(words);
      return modelStore.getBufferedImage(currImageName);
    }
    String[] words = {"sharpen", currImageName, splitImage, "split", split + ""};
    helper.sharpenHelper(words);
    return modelStore.getBufferedImage(splitImage);
  }

  /**
   * Method to execute greyscale (luma-component) operation on current image for UI.
   *
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeGreyscaleOperation(int split, Boolean execute)
          throws IllegalArgumentException {
    if (execute) {
      String[] words = {"luma-component", currImageName, currImageName};
      helper.greyscaleHelper(words);
      return modelStore.getBufferedImage(currImageName);
    }
    String[] words = {"luma-component", currImageName, splitImage, "split", split + ""};
    helper.greyscaleHelper(words);
    return modelStore.getBufferedImage(splitImage);
  }

  /**
   * Method to execute compress operation on current image for UI.
   *
   * @param compPercentage percentage for compression.
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeCompressionOperation(String compPercentage)
          throws IllegalArgumentException {
    String[] words = {"compress", compPercentage, currImageName, currImageName};
    helper.compressHelper(words);
    return modelStore.getBufferedImage(currImageName);
  }

  /**
   * Method to execute brighten operation on current image for UI.
   *
   * @param brightIntensity intensity by which to brighten the image.
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeBrightenOperation(String brightIntensity) {
    String[] words = {"brighten", brightIntensity, currImageName, currImageName};
    helper.brightenHelper(words);
    return modelStore.getBufferedImage(currImageName);
  }

  /**
   * Method to execute darken operation on current image for UI.
   *
   * @param darkenIntensity intensity by which to darken the image.
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeDarkenOperation(String darkenIntensity) {
    String[] words = {"brighten", "-" + darkenIntensity, currImageName, currImageName};
    helper.brightenHelper(words);
    return modelStore.getBufferedImage(currImageName);
  }

  /**
   * Method to color correct the image on current image for UI.
   *
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   * @throws IOException if the image I/O operation throws error.
   */
  @Override
  public BufferedImage executeColorCorrectOperation(int split, Boolean execute)
          throws IOException, IllegalArgumentException {
    if (execute) {
      String[] words = {"color-correct", currImageName, currImageName};
      helper.colorCorrectHelper(words);
      return modelStore.getBufferedImage(currImageName);
    }
    String[] words = {"color-correct", currImageName, splitImage, "split", split + ""};
    helper.colorCorrectHelper(words);
    return modelStore.getBufferedImage(splitImage);
  }

  /**
   * Method to execute levels adjust operation on current image for UI.
   *
   * @param b       black value.
   * @param m       mid value.
   * @param w       white value.
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage executeLevelsAdjustOperation(String b, String m, String w,
                                                    int split, Boolean execute)
          throws IllegalArgumentException {
    if (execute) {
      String[] words = {"levels-adjust", b, m, w, currImageName, currImageName};
      helper.levelsAdjustHelper(words);
      return modelStore.getBufferedImage(currImageName);
    }
    String[] words = {"levels-adjust", b, m, w, currImageName, splitImage, "split", split + ""};
    helper.levelsAdjustHelper(words);
    return modelStore.getBufferedImage(splitImage);
  }

  /**
   * Method to get the currently precessed image for UI.
   *
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage getCurrentImage() {
    return modelStore.getBufferedImage(currImageName);
  }

  /**
   * Method to get the current split preview image for UI.
   *
   * @return instance of Buffered Image.
   */
  @Override
  public BufferedImage getCurrenSplitImage() {
    return modelStore.getBufferedImage(splitImage);
  }

}