
package model;


/**
 * Mock Image Model used to Test ImageModel Implementations.
 */
public class MockImage implements ImageModelNew {
  private String logger;
  private int[][][] imageMatrix;

  /**
   * Constructor to initialise MockImage Parameters.
   */
  public MockImage() {
    imageMatrix = new int[][][]{{{0, 0, 0}, {0, 0, 0}}};
    logger = "";
  }


  /**
   * Get logger from this Model.
   *
   * @return logger to be printed
   */
  public String getLogger() {
    return this.logger;
  }


  /**
   * Method to get the image name.
   *
   * @return image name.
   */
  @Override
  public String getImageAliasName() {
    imageMatrix = new int[][][]{{{0, 0, 0}, {0, 0, 0}}};
    this.logger += "Alias Name received.";
    return "curr";
  }

  /*
  @Override
  public void display() {
  }*/

  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias       image name of the new-generated image.
   * @param compressionRatio compression percentage to compress the image upto that extent.
   * @return itself.
   */
  @Override
  public ImageModelNew compress(String imageAlias, Double compressionRatio) {
    this.logger += "compress called.";
    return this;
  }

  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias image name of the new-generated image.
   * @param b          Black value of the image.
   * @param m          Middle value of the image.
   * @param w          White image of the image.
   * @param per        percent to split image.
   * @return itself.
   */
  @Override
  public ImageModelNew levelAdjust(String imageAlias, int b, int m, int w, double per) {
    this.logger += "levels-adjust called.";
    return this;
  }

  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias Alias name for new image
   * @param per        percentage to split image.
   * @return itself.
   */
  @Override
  public ImageModelNew colorCorrect(String imageAlias, double per) {
    this.logger += "color-correct called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @return itself.
   */
  @Override
  public MockImage generateHistogram(String imageAlias) {
    this.logger += "histogram called.";
    return this;
  }

  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @return itself.
   */
  @Override
  public ImageModelNew generateRed(String imageAlias) {
    this.logger += "red-component called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @return itself.
   */
  @Override
  public ImageModelNew generateGreen(String imageAlias) {
    this.logger += "green-component called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @return itself.
   */
  @Override
  public ImageModelNew generateBlue(String imageAlias) {
    this.logger += "blue-component called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @param split      percentage to split image.
   * @return itself.
   */

  @Override
  public ImageModelNew blur(String imageAlias, double split) {
    this.logger += "blur called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @param split      percentage to split image.
   * @return itself.
   */

  @Override
  public ImageModelNew sharpen(String imageAlias, double split) {
    this.logger += "sharpen called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param value      bright by value.
   * @param imageAlias alias name for the new image.
   * @return itself.
   */

  @Override
  public ImageModelNew bright(int value, String imageAlias) {
    this.logger += "brighten called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @param per        percentage to split image.
   * @return itself.
   */

  @Override
  public ImageModelNew greyscale(String imageAlias, double per) {
    this.logger += "luma called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @param split      percentage to split image.
   * @return itself.
   */

  @Override
  public ImageModelNew sepia(String imageAlias, double split) {
    this.logger += "sepia called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @return itself.
   */

  @Override
  public ImageModelNew intensity(String imageAlias) {
    this.logger += "intensity-component called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @return itself.
   */

  @Override
  public ImageModelNew maxValue(String imageAlias) {
    this.logger += "value-component called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @return itself.
   */

  @Override
  public ImageModelNew verticalFlipImage(String imageAlias) {
    this.logger += "vertical-flip called.";
    return this;
  }


  /**
   * Save logger corresponding to this Model.
   *
   * @param imageAlias alias name for the new image.
   * @return itself.
   */

  @Override
  public ImageModelNew horizontalFlipImage(String imageAlias) {
    this.logger += "horizontal-flip called.";
    return this;
  }

  /**
   * Save logger corresponding to this Model.
   *
   * @param green      Green Channel Image.
   * @param blue       Blue Channel Image.
   * @param imageAlias alias name for the new image.
   * @return itself.
   */
  @Override
  public ImageModelNew merge3components(ImageModelNew green, ImageModelNew blue,
                                        String imageAlias) {
    this.logger += "rgb-combine called.";
    return this;
  }


  /**
   * Method to get a particular pixel of an image.
   *
   * @param i       ith pixel value of a particular image channel.
   * @param j       jth pixel value of a particular image channel.
   * @param channel red, green, or blue channel.
   * @return nothing.
   */

  @Override
  public int getPixelValue(int i, int j, int channel) {
    return imageMatrix[i][j][channel];
  }


  /**
   * Method to get the width of an image.
   *
   * @return nothing.
   */

  @Override
  public int getImageWidth() {
    return imageMatrix.length;
  }


  /**
   * Method to get the height of an image.
   *
   * @return nothing.
   */

  @Override
  public int getImageHeight() {
    return imageMatrix[0].length;
  }

}

