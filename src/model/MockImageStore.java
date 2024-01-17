package model;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Mock class to test the ImageModelStore.
 */
public class MockImageStore implements ImageModelStore {

  private Map<String, ImageModelNew> loadedImageMap;
  private String log = "";

  /**
   * Constructor for MockImageStore.
   *
   * @param alias aliasname of image.
   * @param mock  the image to be stored initially.
   */
  public MockImageStore(String alias, ImageModelNew mock) {
    this.loadedImageMap = new HashMap<>();
    this.loadedImageMap.put(alias, mock);
  }

  /**
   * Get logger from this Model.
   *
   * @return logger to be printed.
   */
  public String getLogger() {
    return this.log;
  }

  /**
   * Return Image for given alias.
   *
   * @param aliasName aliasname of image.
   * @return Model with given aliasname.
   */
  @Override
  public ImageModelNew getImageForAlias(String aliasName) {
    log += "getImageForAlias called.";
    return loadedImageMap.get(aliasName);
  }

  /**
   * Private method to load an image.
   *
   * @param image     Image Model instance that is to be loaded.
   * @param aliasName Image name of loaded image.
   */
  @Override
  public void loadImage(ImageModelNew image, String aliasName) {
    log += "loadImage called.";
  }

  /**
   * Method that helps the image to get loaded in the application.
   *
   * @param words operation words in an array.
   */
  @Override
  public void loadHelper(String[] words) {
    log += "loadHelper called.";
  }

  /**
   * Method that helps controller to save the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void saveHelper(String[] words) {
    log += "saveHelper called.";
  }

  /**
   * Method to get the processed image from Map.
   *
   * @param aliasName Name of the image to get.
   * @return The buffered image instance of the getting image.
   */
  @Override
  public BufferedImage getBufferedImage(String aliasName) {
    log += "getBufferedImage called.";
    return null;
  }
}
