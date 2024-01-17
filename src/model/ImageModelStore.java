package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Interface that is used by controller ad model for storing the instances of Images.
 */
public interface ImageModelStore {
  /**
   * Method to get the image as per the alias-name.
   *
   * @param aliasName image name.
   * @return the Image instance with name alias-name.
   */
  ImageModelNew getImageForAlias(String aliasName);

  /**
   * Private method to load an image.
   *
   * @param image     Image Model instance that is to be loaded.
   * @param aliasName Image name of loaded image.
   */
  void loadImage(ImageModelNew image, String aliasName);

  /**
   * Method that helps the image to get loaded in the application.
   *
   * @param words operation words in an array.
   */
  void loadHelper(String[] words);

  /**
   * Method that helps controller to save the image.
   *
   * @param words operation words in an array.
   * @throws IOException If the save path is not present.
   */
  void saveHelper(String[] words) throws IOException;

  /**
   * Method to get the processed image from Map.
   *
   * @param aliasName Name of the image to get.
   * @return The buffered image instance of the getting image.
   */
  BufferedImage getBufferedImage(String aliasName);
}
