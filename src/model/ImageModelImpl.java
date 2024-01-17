package model;

/**
 * Model class that represents a particular image instance.
 */
public class ImageModelImpl implements ImageModel {
  private String imageAliasName;
  private int[][][] imageMatrix;
  private int imageWidth;
  private int imageHeight;

  /**
   * Private Constructor which sets image matrix to given image matrix.
   *
   * @param imageMatrix1 sets a given 3d matrix of an image to its 3d matrix of image.
   * @param imageAlias   Image name to be set.
   */
  public ImageModelImpl(int[][][] imageMatrix1, String imageAlias) {
    this.imageWidth = imageMatrix1.length;
    this.imageHeight = imageMatrix1[0].length;
    this.imageMatrix = new int[this.imageWidth][this.imageHeight][3];

    for (int x = 0; x < this.imageWidth; x++) {
      for (int y = 0; y < this.imageHeight; y++) {
        this.imageMatrix[x][y][0] = imageMatrix1[x][y][0];
        this.imageMatrix[x][y][1] = imageMatrix1[x][y][1];
        this.imageMatrix[x][y][2] = imageMatrix1[x][y][2];
      }
    }
    this.imageAliasName = imageAlias;
  }

  /**
   * Method to get the name of the image.
   *
   * @return Returns the name of the image.
   */
  public String getImageAliasName() {
    return this.imageAliasName;
  }


  /**
   * Common method to perform operations for RGB components.
   *
   * @param channel channel to perform operation on.
   * @return matrix after performing RBG operations.
   */
  private int[][][] individualComponent(int channel) {
    int[][][] mat = new int[this.imageWidth][this.imageHeight][3];
    for (int x = 0; x < this.imageWidth; x++) {
      for (int y = 0; y < this.imageHeight; y++) {
        for (int c = 0; c < 3; c++) {
          if (c == channel) {
            mat[x][y][c] = this.imageMatrix[x][y][c];
          } else {
            mat[x][y][c] = 0;
          }
        }
      }
    }
    return mat;
  }

  /**
   * Generates the red component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel generateRed(String imageAlias) {
    return new ImageModelImpl(individualComponent(0), imageAlias);
  }

  /**
   * Generates the green component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel generateGreen(String imageAlias) {
    return new ImageModelImpl(individualComponent(1), imageAlias);
  }

  /**
   * Generates the blue component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel generateBlue(String imageAlias) {
    return new ImageModelImpl(individualComponent(2), imageAlias);
  }

  /**
   * Private method to clamp the pixel values.
   *
   * @param matrix clamp the matrix pixel values between 0 and 255.
   */
  private void clamp(int[][][] matrix) {
    for (int x = 0; x < this.imageWidth; x++) {
      for (int y = 0; y < this.imageHeight; y++) {
        matrix[x][y][0] = Math.min(matrix[x][y][0], 255);
        matrix[x][y][1] = Math.min(matrix[x][y][1], 255);
        matrix[x][y][2] = Math.min(matrix[x][y][2], 255);

        matrix[x][y][0] = Math.max(matrix[x][y][0], 0);
        matrix[x][y][1] = Math.max(matrix[x][y][1], 0);
        matrix[x][y][2] = Math.max(matrix[x][y][2], 0);
      }
    }
  }

  /**
   * Generates a new blurred Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel blur(String imageAlias) {
    double[][] blurMatrix = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };
    return blurSharpen(imageAlias, blurMatrix);
  }

  /**
   * Generates a new sharper Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel sharpen(String imageAlias) {
    double[][] sharpen_matrix = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
    return blurSharpen(imageAlias, sharpen_matrix);
  }

  /**
   * Private method used by blur and sharpen.
   *
   * @param imageAlias image name.
   * @param blurMatrix kernel.
   * @return instance of image model of new generated image.
   */
  private ImageModel blurSharpen(String imageAlias, double[][] blurMatrix) {
    int width = this.imageWidth;
    int height = this.imageHeight;
    int pad = (blurMatrix.length / 2);
    int[][][] imagePad1 = new int[width + (pad * 2)][height + (pad * 2)][3];

    for (int x = 0; x < width + (pad * 2); x++) {
      for (int y = 0; y < height + (pad * 2); y++) {
        if (x < pad || y < pad || x > (width + pad - 1) || y > (height + pad - 1)) {
          imagePad1[x][y][0] = 0;
          imagePad1[x][y][1] = 0;
          imagePad1[x][y][2] = 0;
        } else {
          imagePad1[x][y][0] = imageMatrix[x - pad][y - pad][0];
          imagePad1[x][y][1] = imageMatrix[x - pad][y - pad][1];
          imagePad1[x][y][2] = imageMatrix[x - pad][y - pad][2];
        }
      }
    }

    double[][][] imageMatrixBlur = new double[width][height][3];
    int[][][] imageMatrixBlur1 = new int[width][height][3];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        imageMatrixBlur[x][y][0] = 0;
        imageMatrixBlur[x][y][1] = 0;
        imageMatrixBlur[x][y][2] = 0;
        for (int i = 0; i < blurMatrix.length; i++) {
          for (int j = 0; j < blurMatrix.length; j++) {
            imageMatrixBlur[x][y][0] = imageMatrixBlur[x][y][0] +
                    (blurMatrix[i][j] * imagePad1[x + i][y + j][0]);
            imageMatrixBlur[x][y][1] = imageMatrixBlur[x][y][1] +
                    (blurMatrix[i][j] * imagePad1[x + i][y + j][1]);
            imageMatrixBlur[x][y][2] = imageMatrixBlur[x][y][2] +
                    (blurMatrix[i][j] * imagePad1[x + i][y + j][2]);
          }
        }
        imageMatrixBlur1[x][y][0] = (int) imageMatrixBlur[x][y][0];
        imageMatrixBlur1[x][y][1] = (int) imageMatrixBlur[x][y][1];
        imageMatrixBlur1[x][y][2] = (int) imageMatrixBlur[x][y][2];

      }
    }
    this.clamp(imageMatrixBlur1);
    return new ImageModelImpl(imageMatrixBlur1, imageAlias);
  }


  /**
   * Generates a new brighter or darker Image of this image based on the value.
   *
   * @param value      bright by value.
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel bright(int value, String imageAlias) {
    int[][][] imageMatrix1 = new int[imageWidth][imageHeight][3];
    for (int x = 0; x < imageWidth; x++) {
      for (int y = 0; y < imageHeight; y++) {
        imageMatrix1[x][y][0] = imageMatrix[x][y][0] + value;
        imageMatrix1[x][y][1] = imageMatrix[x][y][1] + value;
        imageMatrix1[x][y][2] = imageMatrix[x][y][2] + value;
      }
    }
    this.clamp(imageMatrix1);
    return new ImageModelImpl(imageMatrix1, imageAlias);
  }


  /**
   * Generates a new grey-scaled Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel greyscale(String imageAlias) {
    double[][] greyscaleLuma = {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };
    return colorTransformation(imageAlias, greyscaleLuma);
  }

  /**
   * Generates a new sepia toned Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel sepia(String imageAlias) {
    double[][] sepiaLuma = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    return colorTransformation(imageAlias, sepiaLuma);
  }

  /**
   * Private method used by greyscale and sepia image transformation functions.
   *
   * @param imageAlias image name to be set for new image.
   * @param luma       luma filter for the image transformation operation.
   * @return Instance of image model
   */
  private ImageModel colorTransformation(String imageAlias, double[][] luma) {
    int[][][] imageMatrixReturn = new int[this.imageWidth][this.imageHeight][3];
    if (luma.length == 3) {
      for (int x = 0; x < this.imageWidth; x++) {
        for (int y = 0; y < this.imageHeight; y++) {
          imageMatrixReturn[x][y][0] = (int) ((luma[0][0] * this.imageMatrix[x][y][0])
                  + (luma[0][1] * this.imageMatrix[x][y][1])
                  + (luma[0][2] * this.imageMatrix[x][y][2]));
          imageMatrixReturn[x][y][1] = (int) ((luma[1][0] * this.imageMatrix[x][y][0])
                  + (luma[1][1] * this.imageMatrix[x][y][1])
                  + (luma[1][2] * this.imageMatrix[x][y][2]));
          imageMatrixReturn[x][y][2] = (int) ((luma[2][0] * this.imageMatrix[x][y][0])
                  + (luma[2][1] * this.imageMatrix[x][y][1])
                  + (luma[2][2] * this.imageMatrix[x][y][2]));
        }
      }
    }
    this.clamp(imageMatrixReturn);
    return new ImageModelImpl(imageMatrixReturn, imageAlias);
  }

  /**
   * Method to get a particular pixel of an image.
   *
   * @param i       ith pixel value of a particular image channel.
   * @param j       jth pixel value of a particular image channel.
   * @param channel red, green, or blue channel.
   * @return particular pixel color value.
   */
  @Override
  public int getPixelValue(int i, int j, int channel) {
    return this.imageMatrix[i][j][channel];
  }

  /**
   * Method to Combine red component of this image and green and blue components
   * of other two images to create a new image.
   *
   * @param green      Green Channel Image.
   * @param blue       Blue Channel Image.
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel merge3components(ImageModel green, ImageModel blue, String imageAlias) {
    int[][][] imageMatrixReturn = new int[this.imageWidth][this.imageHeight][3];
    if ((this.getImageWidth() == green.getImageWidth()
            && green.getImageWidth() == blue.getImageWidth())
            && (this.getImageHeight() == green.getImageHeight()
            && green.getImageHeight() == blue.getImageHeight())) {
      for (int x = 0; x < this.imageWidth; x++) {
        for (int y = 0; y < this.imageHeight; y++) {
          imageMatrixReturn[x][y][0] = this.getPixelValue(x, y, 0);
          imageMatrixReturn[x][y][1] = green.getPixelValue(x, y, 1);
          imageMatrixReturn[x][y][2] = blue.getPixelValue(x, y, 2);
        }
      }
    }
    return new ImageModelImpl(imageMatrixReturn, imageAlias);
  }

  /**
   * Method to get the width of an image.
   *
   * @return image width.
   */
  @Override
  public int getImageWidth() {
    return this.imageWidth;
  }

  /**
   * Method to get the height of an image.
   *
   * @return image height.
   */
  @Override
  public int getImageHeight() {
    return this.imageHeight;
  }

  /**
   * Generates a new intensified Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel intensity(String imageAlias) {
    int[][][] imageMatrixReturn = new int[this.imageWidth][this.imageHeight][3];
    for (int x = 0; x < this.imageWidth; x++) {
      for (int y = 0; y < this.imageHeight; y++) {
        int averageValue = ((this.imageMatrix[x][y][0]
                + this.imageMatrix[x][y][1] + this.imageMatrix[x][y][2]) / 3);
        imageMatrixReturn[x][y][0] = (int) (averageValue);
        imageMatrixReturn[x][y][1] = (int) (averageValue);
        imageMatrixReturn[x][y][2] = (int) (averageValue);
      }
    }
    this.clamp(imageMatrixReturn);
    return new ImageModelImpl(imageMatrixReturn, imageAlias);
  }

  /**
   * Generates a new max Valued pixel Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel maxValue(String imageAlias) {
    int[][][] imageMatrixReturn = new int[this.imageWidth][this.imageHeight][3];
    for (int x = 0; x < this.imageWidth; x++) {
      for (int y = 0; y < this.imageHeight; y++) {
        int max = Math.max(this.imageMatrix[x][y][0], Math.max(this.imageMatrix[x][y][1]
                , this.imageMatrix[x][y][2]));
        imageMatrixReturn[x][y][0] = (int) (max);
        imageMatrixReturn[x][y][1] = (int) (max);
        imageMatrixReturn[x][y][2] = (int) (max);
      }
    }
    this.clamp(imageMatrixReturn);
    return new ImageModelImpl(imageMatrixReturn, imageAlias);
  }

  /**
   * Generates a new vertically flipped Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel verticalFlipImage(String imageAlias) {
    int[][][] imageMatrixReturn = new int[this.imageWidth][this.imageHeight][3];
    for (int i = 0; i < this.imageWidth; i++) {
      for (int j = 0; j < this.imageHeight; j++) {
        imageMatrixReturn[i][j][0] = this.imageMatrix[i][this.imageHeight - 1 - j][0];
        imageMatrixReturn[i][j][1] = this.imageMatrix[i][this.imageHeight - 1 - j][1];
        imageMatrixReturn[i][j][2] = this.imageMatrix[i][this.imageHeight - 1 - j][2];
      }
    }
    return new ImageModelImpl(imageMatrixReturn, imageAlias);
  }

  /**
   * Generates a new horizontally flipped Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModel horizontalFlipImage(String imageAlias) {
    int[][][] imageMatrixReturn = new int[this.imageWidth][this.imageHeight][3];
    for (int j = 0; j < this.imageHeight; j++) {
      for (int i = 0; i < this.imageWidth; i++) {
        imageMatrixReturn[i][j][0] = this.imageMatrix[this.imageWidth - 1 - i][j][0];
        imageMatrixReturn[i][j][1] = this.imageMatrix[this.imageWidth - 1 - i][j][1];
        imageMatrixReturn[i][j][2] = this.imageMatrix[this.imageWidth - 1 - i][j][2];
      }
    }
    return new ImageModelImpl(imageMatrixReturn, imageAlias);
  }


}
