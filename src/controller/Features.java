package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Interface that implements all the features on an image.
 */
public interface Features {

  /**
   * Method to save image.
   *
   * @param path path where image is to be saved.
   * @throws IOException if the image is not saved and path is not found.
   */
  void saveImage(String path) throws IOException;

  /**
   * Method to load an image in the program.
   *
   * @param path path where image is to be saved.
   * @return Loaded instance of Buffered Image.
   */
  BufferedImage loadImage(String path);

  /**
   * Method to generate Histogram for UI.
   *
   * @return instance of Buffered Image.
   * @throws IOException if the image is not saved and path is not found.
   */
  BufferedImage generateHistogram() throws IOException;

  /**
   * Method to execute sepia operation on current image for UI.
   *
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   */
  BufferedImage executeSepiaOperation(int split, Boolean execute);

  /**
   * Method to execute red-component of the current image foe UI.
   *
   * @return instance of Buffered Image.
   */
  BufferedImage executeRedComponent();

  /**
   * Method to execute green-component of the current image for UI.
   *
   * @return instance of Buffered Image.
   */
  BufferedImage executeGreenComponent();

  /**
   * Method to execute green-component of the current image for UI.
   *
   * @return instance of Buffered Image.
   */
  BufferedImage executeBlueComponent();

  /**
   * Method to execute vertical flip operation of the current image for UI.
   *
   * @return instance of Buffered Image.
   */
  BufferedImage executeVerticalFlip();

  /**
   * Method to execute horizontal flip operation of the current image for UI.
   *
   * @return instance of Buffered Image.
   */
  BufferedImage executeHorizontalFlip();

  /**
   * Method to execute greyscale (luma-component) operation on current image for UI.
   *
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   */
  BufferedImage executeGreyscaleOperation(int split, Boolean execute);

  /**
   * Method to execute sharpen operation on current image for UI.
   *
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   */
  BufferedImage executeSharpenOperation(int split, Boolean execute);

  /**
   * Method to execute blur operation on current image for UI.
   *
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   */
  BufferedImage executeBlurOperation(int split, Boolean execute);

  /**
   * Method to execute compress operation on current image for UI.
   *
   * @param compPercentage percentage for compression.
   * @return instance of Buffered Image.
   */
  BufferedImage executeCompressionOperation(String compPercentage);

  /**
   * Method to color correct the image on current image for UI.
   *
   * @param split   split percentage value.
   * @param execute if execute method or not.
   * @return instance of Buffered Image.
   * @throws IOException if the image I/O operation throws error.
   */
  BufferedImage executeColorCorrectOperation(int split, Boolean execute) throws IOException;

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
  BufferedImage executeLevelsAdjustOperation(String b, String m,
                                             String w, int split, Boolean execute);

  /**
   * Method to get the currently precessed image for UI.
   *
   * @return instance of Buffered Image.
   */
  BufferedImage getCurrentImage();

  /**
   * Method to execute brighten operation on current image for UI.
   *
   * @param brightIntensity intensity by which to brighten the image.
   * @return instance of Buffered Image.
   */
  BufferedImage executeBrightenOperation(String brightIntensity);

  /**
   * Method to execute darken operation on current image for UI.
   *
   * @param darkenIntensity intensity by which to darken the image.
   * @return instance of Buffered Image.
   */
  BufferedImage executeDarkenOperation(String darkenIntensity);

  /**
   * Method to get the current split preview image for UI.
   *
   * @return instance of Buffered Image.
   */
  BufferedImage getCurrenSplitImage();
}
