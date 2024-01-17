package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Helper Class Interface for Controller Logic.
 */
public interface ControllerHelper {


  /**
   * Method that helps controller to brighten the image.
   *
   * @param words operation words in an array.
   */
  void brightenHelper(String[] words);

  /**
   * Method that helps controller to vertically flip the image.
   *
   * @param words operation words in an array.
   */
  void verticalFlipHelper(String[] words);

  /**
   * Method that helps controller to horizontally flip the image.
   *
   * @param words operation words in an array.
   */
  void horizontalFlipHelper(String[] words);

  /**
   * Method that helps controller to save the image.
   *
   * @param words operation words in an array.
   * @throws IOException If the save path is not present.
   */
  void saveHelper(String[] words) throws IOException;

  /**
   * Method that helps controller to split 3 channels of the image.
   *
   * @param words operation words in an array.
   */
  void rgbSplitHelper(String[] words);

  /**
   * Method that helps controller to combine 3 channels to single instance of the image.
   *
   * @param words operation words in an array.
   */
  void rgbCombineHelper(String[] words);

  /**
   * Method that helps controller to blur the image.
   *
   * @param words operation words in an array.
   */
  void blurHelper(String[] words);

  /**
   * Method that helps controller to sharpen the image.
   *
   * @param words operation words in an array.
   */
  void sharpenHelper(String[] words);

  /**
   * Method that helps controller to greyscale the image.
   *
   * @param words operation words in an array.
   */
  void greyscaleHelper(String[] words);

  /**
   * Method that helps controller to sepia the image.
   *
   * @param words operation words in an array.
   */
  void sepiaHelper(String[] words);

  /**
   * Method that helps controller to extract red channel of the image.
   *
   * @param words operation words in an array.
   */
  void redComponentHelper(String[] words);

  /**
   * Method that helps controller to extract green channel of the image.
   *
   * @param words operation words in an array.
   */
  void greenComponentHelper(String[] words);

  /**
   * Method that helps controller to extract blue channel of the image.
   *
   * @param words operation words in an array.
   */
  void blueComponentHelper(String[] words);

  /**
   * Method that helps controller to intensify the image.
   *
   * @param words operation words in an array.
   */
  void intensityHelper(String[] words);

  /**
   * Method that helps controller to set the channel to max value of the image.
   *
   * @param words operation words in an array.
   */
  void valueHelper(String[] words);

  /**
   * Method to help the controller run commands form a file.
   *
   * @param words operation words in an array.
   * @return the String of every line from the file to execute further.
   * @throws FileNotFoundException if the run file path is not found.
   */
  ArrayList<String> runHelper(String[] words) throws FileNotFoundException;

  /**
   * Method to help the controller run compress function on an image.
   *
   * @param words operation words in an array.
   */
  void compressHelper(String[] words);

  /**
   * Method to help the controller run levels adjust function on an image.
   *
   * @param words operation words in an array.
   */
  void levelsAdjustHelper(String[] words);

  /**
   * Method to help the controller run generate histogram function on an image.
   *
   * @param words operation words in an array.
   * @throws IOException when.
   */
  void histogramHelper(String[] words) throws IOException;

  /**
   * Method to help the controller run coloe correction function on an image.
   *
   * @param words operation words in an array.
   * @throws IOException when.
   */
  void colorCorrectHelper(String[] words) throws IOException;

  /**
   * Method that helps the image to get loaded in the application.
   *
   * @param words operation words in an array.
   */
  void loadHelper(String[] words);
}
