package model;

/**
 * Interface to represent an Image Object Model.
 */
public interface ImageModel {

  /**
   * Method to get the name of the image.
   *
   * @return Returns the name of the image.
   */
  String getImageAliasName();

  /**
   * Generates the red component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel generateRed(String imageAlias);

  /**
   * Generates the green component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel generateGreen(String imageAlias);

  /**
   * Generates the blue component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel generateBlue(String imageAlias);

  /**
   * Generates a new blurred Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel blur(String imageAlias);

  /**
   * Generates a new sharper Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel sharpen(String imageAlias);

  /**
   * Generates a new brighter or darker Image of this image based on the value.
   *
   * @param value      bright by value.
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel bright(int value, String imageAlias);

  /**
   * Generates a new greyscaled Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel greyscale(String imageAlias);

  /**
   * Generates a new sepia toned Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel sepia(String imageAlias);

  /**
   * Generates a new intensified Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel intensity(String imageAlias);

  /**
   * Generates a new max Valued pixel Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel maxValue(String imageAlias);

  /**
   * Generates a new vertically flipped Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel verticalFlipImage(String imageAlias);

  /**
   * Generates a new horizontally flipped Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel horizontalFlipImage(String imageAlias);

  /**
   * Method to Combine red component of this image and green and blue components
   * of other two images to create a new image.
   *
   * @param green      Green Channel Image.
   * @param blue       Blue Channel Image.
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  ImageModel merge3components(ImageModel green, ImageModel blue, String imageAlias);

  /**
   * Method to get a particular pixel of an image.
   *
   * @param i       ith pixel value of a particular image channel.
   * @param j       jth pixel value of a particular image channel.
   * @param channel red, green, or blue channel.
   * @return particular pixel color value.
   */
  int getPixelValue(int i, int j, int channel);

  /**
   * Method to get the width of an image.
   *
   * @return image width.
   */
  int getImageWidth();

  /**
   * Method to get the height of an image.
   *
   * @return image height.
   */
  int getImageHeight();
}
