package model;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * New Implementation of the Image Model with extra methids.
 */
public class ImageModelNewImpl implements ImageModelNew {

  private final ImageModel delegate;
  private int[][][] imageMatrix;
  private String imageAliasName;
  private int imageWidth;
  private int imageHeight;

  /**
   * Constructor to set the private fields.
   *
   * @param matrix         Image matrix to store into the private image representation.
   * @param imageAliasName Name to the image.
   */
  public ImageModelNewImpl(int[][][] matrix, String imageAliasName) {
    this.imageWidth = matrix.length;
    this.imageHeight = matrix[0].length;
    this.imageMatrix = new int[this.imageWidth][this.imageHeight][3];
    this.delegate = new ImageModelImpl(matrix, imageAliasName);

    for (int x = 0; x < this.imageWidth; x++) {
      for (int y = 0; y < this.imageHeight; y++) {
        this.imageMatrix[x][y][0] = matrix[x][y][0];
        this.imageMatrix[x][y][1] = matrix[x][y][1];
        this.imageMatrix[x][y][2] = matrix[x][y][2];
      }
    }
    this.imageAliasName = imageAliasName;
  }


  /**
   * Public method to compress the image.
   *
   * @param imageAlias       image name of the new-generated image.
   * @param compressionRatio compression percentage to compress the image upto that extent.
   * @return A new Image Model representation of the compressed image.
   */
  @Override
  public ImageModelNew compress(String imageAlias, Double compressionRatio)
          throws IllegalArgumentException {
    if (compressionRatio < 0 || compressionRatio > 1) {
      throw new IllegalArgumentException("Invalid Compression Percentage: " +
              "Must be between 0 and 100.");
    }
    int[][][] imageMatrixReturn = new int[getImageWidth()][getImageHeight()][3];
    double[][] redChannel = new double[getImageWidth()][getImageHeight()];
    double[][] greenChannel = new double[getImageWidth()][getImageHeight()];
    double[][] blueChannel = new double[getImageWidth()][getImageHeight()];
    for (int x = 0; x < getImageWidth(); x++) {
      for (int y = 0; y < getImageHeight(); y++) {
        redChannel[x][y] = getPixelValue(x, y, 0);
      }
    }
    for (int x = 0; x < getImageWidth(); x++) {
      for (int y = 0; y < getImageHeight(); y++) {
        greenChannel[x][y] = getPixelValue(x, y, 1);
      }
    }
    for (int x = 0; x < getImageWidth(); x++) {
      for (int y = 0; y < getImageHeight(); y++) {
        blueChannel[x][y] = getPixelValue(x, y, 2);
      }
    }
    double[][][] returned = performCompression(redChannel, greenChannel,
            blueChannel, compressionRatio);
    for (int x = 0; x < imageWidth; x++) {
      for (int y = 0; y < imageHeight; y++) {
        imageMatrixReturn[x][y][0] = (int) (Math.round(returned[x][y][0]));
        imageMatrixReturn[x][y][1] = (int) (Math.round(returned[x][y][1]));
        imageMatrixReturn[x][y][2] = (int) (Math.round(returned[x][y][2]));
      }
    }
    this.clamp(imageMatrixReturn);
    ImageModelNewImpl img = new ImageModelNewImpl(imageMatrixReturn, imageAlias);
    return img;
  }

  /**
   * Public method to adjust the color levels of an image.
   *
   * @param imageAlias image name of the new-generated image.
   * @param b          Black value of the image.
   * @param m          Middle value of the image.
   * @param w          White image of the image.
   * @param per        percentage of image.
   * @return A new Image Model representation of the compressed image.
   */
  @Override
  public ImageModelNew levelAdjust(String imageAlias, int b, int m, int w, double per)
          throws IllegalArgumentException {
    if (b < 0 || w > 255 || m > w || b > m) {
      throw new IllegalArgumentException("Invalid Values: Enter B,M,W Values in Range.");
    }
    double[] coefficients = calculateCorrectionCoeff(b, m, w);
    int[][][] imageMatrixReturn = new int[getImageWidth()][getImageHeight()][3];
    for (int x = 0; x < getImageWidth(); x++) {
      for (int y = 0; y < getImageHeight(); y++) {
        imageMatrixReturn[x][y][0] = applyLevelAdjustEquation(coefficients,
                getPixelValue(x, y, 0));
        imageMatrixReturn[x][y][1] = applyLevelAdjustEquation(coefficients,
                getPixelValue(x, y, 1));
        imageMatrixReturn[x][y][2] = applyLevelAdjustEquation(coefficients,
                getPixelValue(x, y, 2));
      }
    }
    clamp(imageMatrixReturn);
    ImageModelNew img = new ImageModelNewImpl(imageMatrixReturn, imageAlias);
    return this.split(per, img, imageAlias);

  }

  /**
   * Method to clamp the values between 0 and 255.
   *
   * @param matrix Input matrix that is needed to be clamped.
   */
  protected void clamp(int[][][] matrix) {
    for (int x = 0; x < getImageWidth(); x++) {
      for (int y = 0; y < getImageHeight(); y++) {
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
   * Method to pad the matrix with zeros to make it square.
   *
   * @param matrix matrix that is needed to be padded.
   * @param rows   rows of the input matrix.
   * @param cols   columns of the inout matrix.
   * @return Padded resultant matrix.
   */
  private double[][] addMatrixPadding(double[][] matrix, int rows, int cols) {
    int newSize = (int) Math.pow(2, Math.ceil(Math.log(Math.max(rows, cols)) / Math.log(2)));
    double[][] paddedMatrix = new double[newSize][newSize];
    for (int i = 0; i < rows; i++) {
      System.arraycopy(matrix[i], 0, paddedMatrix[i], 0, cols);
    }
    return paddedMatrix;
  }

  /**
   * Method to un-pad the already padded matrix.
   *
   * @param paddedMatrix matrix that is needed to be unpad.
   * @param rows         final rows in the output matrix.
   * @param cols         final columns in the output matrix.
   * @return Un-padded resultant matrix.
   */
  private double[][] removeMatrixPadding(double[][] paddedMatrix, int rows, int cols) {
    double[][] originalMatrix = new double[rows][cols];
    for (int i = 0; i < rows; i++) {
      System.arraycopy(paddedMatrix[i], 0, originalMatrix[i], 0, cols);
    }
    return originalMatrix;
  }

  /**
   * Method the calculate the values of the transform array.
   *
   * @param subarray Array that is needed to be transformed.
   * @param length   length that is needed to be transformed.
   * @return transformed array.
   */
  private double[] avgDiff(double[] subarray, int length) {
    int resultLength = length / 2;
    double[] avg = new double[resultLength];
    double[] diff = new double[resultLength];

    for (int i = 0, j = 0; i < length; i += 2, j++) {
      double a = subarray[i];
      double b = subarray[i + 1];

      double av = (a + b) / Math.sqrt(2);
      double di = (a - b) / Math.sqrt(2);

      avg[j] = applyThresholdScaling(av);
      diff[j] = applyThresholdScaling(di);
    }

    double[] result = new double[length];
    for (int q = 0; q < resultLength; q++) {
      result[q] = avg[q];
    }
    for (int q = resultLength; q < length; q++) {
      result[q] = diff[q - resultLength];
    }
    return result;
  }

  /**
   * Method th calculate the values of the inverse transform of array.
   *
   * @param subArray Array that is needed to be inverse transformed.
   * @param length   length that is needed to be inverse transformed.
   * @return inverse transformed array.
   */
  private double[] avgDiffInv(double[] subArray, int length) {
    int middle = length / 2;
    double[] avg = new double[middle];
    double[] diff = new double[middle];

    for (int i = 0, j = middle; i < middle && j < length; i++, j++) {
      double a = subArray[i];
      double b = subArray[j];

      double av = (a + b) / Math.sqrt(2);
      double de = (a - b) / Math.sqrt(2);

      avg[i] = applyThresholdScaling(av);
      diff[i] = applyThresholdScaling(de);
    }

    double[] interleavedResult = new double[length];
    for (int i = 0, k = 0; i < middle; i++, k += 2) {
      interleavedResult[k] = avg[i];
      interleavedResult[k + 1] = diff[i];
    }
    return interleavedResult;
  }

  /**
   * Method to make values zero if they approach near zero values.
   *
   * @param num threshold to make value zero.
   * @return new values of that particular value,
   */
  private double applyThresholdScaling(double num) {
    double thresholdValue = 0.1;
    if (Math.abs(num) <= thresholdValue) {
      return 0;
    } else {
      return num;
    }
  }

  /**
   * Method to perform 2d Haar transformation.
   *
   * @param image 2D matrix channel to perform haar transformation on.
   * @param size  size of matrix to perform haar transformation on.
   * @return Haar transformed channel matrix.
   */
  private double[][] haarTransformTwoDimension(double[][] image, int size) {
    int c = size;
    while (c > 1) {
      for (int i = 0; i < c; i++) {
        double[] subArrayRow = Arrays.copyOfRange(image[i], 0, c);
        double[] result2 = avgDiff(subArrayRow, c);
        System.arraycopy(result2, 0, image[i], 0, c);
      }

      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = image[i][j];
        }
        double[] subArrayColumn = avgDiff(column, c);
        for (int i = 0; i < c; i++) {
          image[i][j] = subArrayColumn[i];
        }
      }

      c = c / 2;
    }

    return image;
  }

  /**
   * Method to perform 2d Inverse Haar transformation.
   *
   * @param image 2D matrix channel to perform inverse haar transformation on.
   * @param s     size of matrix to perform inverse haar transformation on.
   * @return Inverse Haar transformed channel matrix.
   */
  private double[][] invHaarTransformTwoDimension(double[][] image, int s) {
    int c = 2;
    while (c <= s) {
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = image[i][j];
        }
        double[] subArrayColumn = avgDiffInv(column, c);
        for (int i = 0; i < c; i++) {
          image[i][j] = subArrayColumn[i];
        }
      }
      for (int i = 0; i < c; i++) {
        double[] subArrayRow = Arrays.copyOfRange(image[i], 0, c);
        double[] result2 = avgDiffInv(subArrayRow, c);
        System.arraycopy(result2, 0, image[i], 0, c);
      }
      c = c * 2;
    }
    for (int x = 0; x < image.length; x++) {
      for (int y = 0; y < image[0].length; y++) {
        image[x][y] = Math.round(image[x][y]);
      }
    }
    return image;
  }

  /**
   * Method that converts given array to unique value array.
   *
   * @param array array to be converted to unique value array.
   * @return Array with unique values.
   */
  private double[] getUniqueValues(double[] array) {
    Set<Double> set = new HashSet<>();
    for (double value : array) {
      set.add(value);
    }
    double[] uniqueArray = new double[set.size()];
    int index = 0;
    for (double value : set) {
      uniqueArray[index++] = value;
    }
    return uniqueArray;
  }

  /**
   * Method to apply threshold on the transformed image.
   *
   * @param red              red channel of the image.
   * @param green            green channel of the image.
   * @param blue             blue channel of the image.
   * @param compressionRatio Compression ratio value for the image to be compressed.
   */
  private void applyCombinedCompressionRatio(double[][] red, double[][] green,
                                             double[][] blue, double compressionRatio) {
    int rows = red.length;
    int cols = red[0].length;
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < cols; y++) {
        if (Math.abs(Math.round(red[x][y]) - red[x][y]) < 0.1) {
          red[x][y] = Math.round(red[x][y]);
        }
        if (Math.abs(Math.round(green[x][y]) - green[x][y]) < 0.1) {
          green[x][y] = Math.round(green[x][y]);
        }
        if (Math.abs(Math.round(blue[x][y]) - blue[x][y]) < 0.1) {
          blue[x][y] = Math.round(blue[x][y]);
        }
      }
    }
    double[] flattenedImage = new double[rows * cols * 3];
    int index = 0;
    for (double[] row : red) {
      for (double value : row) {
        flattenedImage[index++] = Math.abs(value);
      }
    }
    for (double[] row : green) {
      for (double value : row) {
        flattenedImage[index++] = Math.abs(value);
      }
    }
    for (double[] row : blue) {
      for (double value : row) {
        flattenedImage[index++] = Math.abs(value);
      }
    }
    double[] nonZeroValues = getUniqueValues(flattenedImage);
    Arrays.sort(nonZeroValues);
    int thresholdIndex = (int) (nonZeroValues.length * compressionRatio);
    double thresholdValue;
    if (compressionRatio == 1) {
      thresholdValue = nonZeroValues[thresholdIndex - 1];
    } else {
      thresholdValue = nonZeroValues[thresholdIndex];
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (Math.abs(red[i][j]) <= thresholdValue) {
          red[i][j] = 0;
        }
        if (Math.abs(green[i][j]) <= thresholdValue) {
          green[i][j] = 0;
        }
        if (Math.abs(blue[i][j]) <= thresholdValue) {
          blue[i][j] = 0;
        }
      }
    }
  }

  /**
   * Method to perform compression steps on channels and return a final image matrix.
   *
   * @param red              red channel of the image.
   * @param green            green channel of the image.
   * @param blue             blue channel of the image.
   * @param compressionRatio Compression ratio value for the image to be compressed.
   * @return returns new compressed image matrix.
   */
  private double[][][] performCompression(double[][] red, double[][] green, double[][] blue,
                                          double compressionRatio) {
    double[][] paddedMatrixRed = addMatrixPadding(red, red.length, red[0].length);
    double[][] paddedMatrixGreen = addMatrixPadding(green, green.length, green[0].length);
    double[][] paddedMatrixBlue = addMatrixPadding(blue, blue.length, blue[0].length);

    double[][] transformedImageRed = haarTransformTwoDimension(paddedMatrixRed,
            paddedMatrixRed.length);
    double[][] transformedImageGreen = haarTransformTwoDimension(paddedMatrixGreen,
            paddedMatrixGreen.length);
    double[][] transformedImageBlue = haarTransformTwoDimension(paddedMatrixBlue,
            paddedMatrixBlue.length);

    applyCombinedCompressionRatio(transformedImageRed, transformedImageGreen,
            transformedImageBlue, compressionRatio);

    double[][] invertedImageRed = invHaarTransformTwoDimension(transformedImageRed,
            transformedImageRed.length);
    double[][] invertedImageGreen = invHaarTransformTwoDimension(transformedImageGreen,
            transformedImageGreen.length);
    double[][] invertedImageBlue = invHaarTransformTwoDimension(transformedImageBlue,
            transformedImageBlue.length);

    double[][] invertedWithoutPaddingRed = removeMatrixPadding(invertedImageRed,
            red.length, red[0].length);
    double[][] invertedWithoutPaddingGreen = removeMatrixPadding(invertedImageGreen,
            green.length, green[0].length);
    double[][] invertedWithoutPaddingBlue = removeMatrixPadding(invertedImageBlue,
            blue.length, blue[0].length);

    double[][][] result = new double[red.length][red[0].length][3];
    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        result[x][y][0] = (int) (Math.round(invertedWithoutPaddingRed[x][y]));
        result[x][y][1] = (int) (Math.round(invertedWithoutPaddingGreen[x][y]));
        result[x][y][2] = (int) (Math.round(invertedWithoutPaddingBlue[x][y]));
      }
    }
    return result;
  }


  /**
   * Method to calculate the equation coefficients based on b,m,w values.
   *
   * @param b black value of the pixel color.
   * @param m mid-value of the pixel color.
   * @param w white value of the pixel color
   * @return 3-length array with coefficients value.
   */
  private double[] calculateCorrectionCoeff(int b, int m, int w) {
    double[] coefficientResults = new double[3];
    double aValue = (m - w) * ((b * b) - (b * (m + w)) + (w * m));
    double aAValue = (128 * w) - (255 * m) - (b * (128 - 255));
    double bAValue = ((b * b) * (128 - 255)) + (255 * (m * m)) - (128 * (w * w));
    double cAValue = ((b * b) * ((255 * m) - (128 * w))) - (b * ((255 * (m * m))
            - (128 * (w * w))));
    coefficientResults[0] = aAValue / aValue;
    coefficientResults[1] = bAValue / aValue;
    coefficientResults[2] = cAValue / aValue;
    return coefficientResults;
  }

  /**
   * Method to update the pixel values of the image as per the levels-adjust equation.
   *
   * @param coefficients calculated coefficients of the equations.
   * @param pixelValue   value of pixel to be changed.
   * @return updated pixel value as per the new equation.
   */
  private int applyLevelAdjustEquation(double[] coefficients, int pixelValue) {
    return (int) ((coefficients[0] * (pixelValue * pixelValue)) +
            (coefficients[1] * pixelValue) + (coefficients[2]));
  }

  /**
   * Method to get the name of the image.
   *
   * @return Name of the image.
   */
  public String getImageAliasName() {
    return this.imageAliasName;
  }

  /**
   * Method to convert he old Image to new representation.
   *
   * @param model Old representation of the image instance.
   * @return New representation of the same image.
   */
  private ImageModelNew imageToImageNew(ImageModel model) {
    int width = delegate.getImageWidth();
    int height = delegate.getImageHeight();
    int[][][] mat = new int[width][height][3];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        mat[i][j][0] = model.getPixelValue(i, j, 0);
        mat[i][j][1] = model.getPixelValue(i, j, 1);
        mat[i][j][2] = model.getPixelValue(i, j, 2);
      }
    }
    ImageModelNew img = new ImageModelNewImpl(mat, delegate.getImageAliasName());
    return img;
  }

  /**
   * Implements color Correction on given Image.
   *
   * @param imageAlias Alias name for new image.
   * @param per        percentage of image.
   * @return ImageModelNew of Color corrected image.
   */
  @Override
  public ImageModelNew colorCorrect(String imageAlias, double per) {
    int[][] lineHistogram = findFrequencyMatrix();
    for (int i = 0; i < lineHistogram.length; i++) {
      for (int j = 0; j <= 10; j++) {
        lineHistogram[i][j] = 0;
      }

      for (int j = 245; j < lineHistogram[0].length; j++) {
        lineHistogram[i][j] = 0;
      }
    }
    int redPeak = getMaxvalueIndex(lineHistogram[0]);
    int greenPeak = getMaxvalueIndex(lineHistogram[1]);
    int bluePeak = getMaxvalueIndex(lineHistogram[2]);
    int avgPeak = (redPeak + greenPeak + bluePeak) / 3;

    int redShift = avgPeak - redPeak;
    int greenShift = avgPeak - greenPeak;
    int blueShift = avgPeak - bluePeak;

    int[][][] colorMatrix = new int[imageWidth][imageHeight][3];

    for (int i = 0; i < colorMatrix.length; i++) {
      for (int j = 0; j < colorMatrix[0].length; j++) {

        colorMatrix[i][j][0] = imageMatrix[i][j][0] + redShift;
        colorMatrix[i][j][1] = imageMatrix[i][j][1] + greenShift;
        colorMatrix[i][j][2] = imageMatrix[i][j][2] + blueShift;

      }
    }
    this.clamp(colorMatrix);
    ImageModelNew img = new ImageModelNewImpl(colorMatrix, imageAlias);
    return this.split(per, img, imageAlias);

  }

  /**
   * Method to find the max value index from an array.
   *
   * @param array the array from which max value is needed.
   * @return the max value index of th given array.
   */
  private int getMaxvalueIndex(int[] array) {
    int max = array[0];
    int maxInd = 0;
    for (int i = 0; i < array.length; i++) {
      if (array[i] > max) {
        max = array[i];
        maxInd = i;
      }
    }
    return maxInd;
  }

  /**
   * Method to count and get frequency of ech unique pixel in an image.
   *
   * @return calculated frequency matrix of all the 3 channels of image.
   */
  private int[][] findFrequencyMatrix() {
    int[][] lineHistogram = new int[3][256];

    for (int x = 0; x < imageWidth; x++) {
      for (int y = 0; y < imageHeight; y++) {
        // Update histograms
        lineHistogram[0][imageMatrix[x][y][0]]++;
        lineHistogram[1][imageMatrix[x][y][1]]++;
        lineHistogram[2][imageMatrix[x][y][2]]++;
      }
    }
    return lineHistogram;
  }

  /**
   * Method to generate Histogram of Given Image.
   *
   * @param imageAlias Alias name of Histogram.
   * @return ImageModelNew with model representation of Histogram.
   */
  @Override
  public ImageModelNew generateHistogram(String imageAlias) {
    int[][] lineHistogram = findFrequencyMatrix();
    int[][][] imageMatrix1 = this.makeHistogram(lineHistogram);
    return new ImageModelNewImpl(imageMatrix1, imageAlias);
  }

  /**
   * Method to create a histogram from the pixel frequencies.
   *
   * @param lineHistogram frequencies of the pixels to create histogram.
   * @return calculated histogram matrix.
   */
  private int[][][] makeHistogram(int[][] lineHistogram) {
    int imageWidth = 256;
    int imageHeight = 256;
    int gridlines = 20;
    BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();

    g.setColor(Color.WHITE);
    g.fillRect(0, 0, imageWidth, imageHeight);
    g.setColor(Color.LIGHT_GRAY);

    int xGap = (imageWidth) / gridlines;
    int yGap = (imageHeight) / gridlines; // Number of horizontal grid lines
    for (int i = 0; i < gridlines + 1; i++) {
      int x = i * xGap;
      g.drawLine(x, 0, x, imageHeight); // Vertical grid lines

      int y = imageHeight - i * yGap;
      g.drawLine(0, y, imageWidth, y); // Horizontal grid lines
    }

    int maxCount = getMaxValue(lineHistogram);
    double scale = 256.0 / maxCount; //3093

    drawRGBHistogram(g, imageWidth, imageHeight, Color.RED, lineHistogram[0], scale);
    drawRGBHistogram(g, imageWidth, imageHeight, Color.GREEN, lineHistogram[1], scale);
    drawRGBHistogram(g, imageWidth, imageHeight, Color.BLUE, lineHistogram[2], scale);

    int[][][] matrix = this.imageToMatrix(image);
    g.dispose();

    return matrix;
  }

  /**
   * Method to convert a buffered image representation to matrix representation.
   *
   * @param image buffered image object that is needed to be converted into matrix form.
   * @return converted image matrix.
   */
  private int[][][] imageToMatrix(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    int[][][] mat = new int[width][height][3];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int rgb = image.getRGB(i, j);
        mat[i][j][0] = (rgb >> 16) & 0xFF;
        mat[i][j][1] = (rgb >> 8) & 0xFF;
        mat[i][j][2] = rgb & 0xFF;
      }
    }
    return mat;
  }

  /**
   * Method to Find scales and calls the method to draw line on histogram.
   *
   * @param g      graphics object.
   * @param width  width of image.
   * @param height height of image.
   * @param color  color of line to draw histogram.
   * @param hist   frequency matrix on which histogram is drawn.
   * @param scale  scale of the histogram.
   */
  private static void drawRGBHistogram(Graphics2D g, int width, int height,
                                       Color color, int[] hist, double scale) {
    g.setColor(color);
    for (int i = 0; i < width - 1; i++) {
      int x1 = i;
      int y1 = height - 1 - (int) (hist[i] * scale);

      int x2 = i + 1;
      int y2 = height - 1 - (int) (hist[i + 1] * scale);
      g.drawLine(x1, y1, x2, y2);
    }
  }

  /**
   * Method to find the maximum element from an array.
   *
   * @param array 2D matrix to find the maximum element from.
   * @return the maximum element form the array.
   */
  private static int getMaxValue(int[][] array) {
    int max = array[0][0];
    for (int[] value : array) {
      int v = getMax1DArray(value);
      if (v > max) {
        max = v;
      }
    }
    //System.out.println(max);
    return max;
  }

  /**
   * Returns the maximum value of the 1D array.
   *
   * @param array 1D array to find the max element from.
   * @return the maximum element of an 1D array.
   */
  private static int getMax1DArray(int[] array) {
    int max = array[0];
    for (int v : array) {
      if (v > max) {
        max = v;
      }
    }
    return max;
  }


  /**
   * Generates the red component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelNew generateRed(String imageAlias) {
    ImageModelNew img = imageToImageNew(delegate.generateRed(imageAlias));
    return img;
  }

  /**
   * Generates the green component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */

  public ImageModelNew generateGreen(String imageAlias) {
    ImageModelNew img = imageToImageNew(delegate.generateGreen(imageAlias));
    return img;
  }

  /**
   * Generates the blue component image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */

  public ImageModelNew generateBlue(String imageAlias) {
    ImageModelNew img = imageToImageNew(delegate.generateBlue(imageAlias));
    return img;
  }

  /**
   * Method to blur an image.
   *
   * @param imageAlias alias name for the new image.
   * @param per        percentage of image to blur.
   * @return New Instance of blurred image.
   */
  @Override
  public ImageModelNew blur(String imageAlias, double per) throws IllegalArgumentException {
    ImageModelNew img = imageToImageNew(delegate.blur(imageAlias));
    return this.split(per, img, imageAlias);
  }

  /**
   * Method to perform correct split operation on a given image.
   *
   * @param per        percentage of image to blur.
   * @param img        model on which operation is to be performed.
   * @param imageAlias name of the image.
   * @return New instance of modified model.
   */
  private ImageModelNew split(double per, ImageModelNew img, String imageAlias) {
    if (per < 0 || per > 100) {
      throw new IllegalArgumentException("Percentage should be between 0 and 100.");
    }
    int w = (int) (per * imageWidth) / 100;
    int[][][] mat = new int[imageWidth][imageHeight][3];
    for (int i = 0; i < imageWidth; i++) {
      for (int j = 0; j < imageHeight; j++) {
        for (int k = 0; k < 3; k++) {
          if (i < w) {
            mat[i][j][k] = img.getPixelValue(i, j, k);
          } else {
            mat[i][j][k] = this.imageMatrix[i][j][k];
          }
        }
      }
    }
    return new ImageModelNewImpl(mat, imageAlias);
  }

  /**
   * Generates a new sharper Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @param per        percentage of image.
   * @return New instance of modified model.
   */
  @Override
  public ImageModelNew sharpen(String imageAlias, double per) throws IllegalArgumentException {
    ImageModelNew img = imageToImageNew(delegate.sharpen(imageAlias));
    return this.split(per, img, imageAlias);
  }

  /**
   * Generates a new brighter or darker Image of this image based on the value.
   *
   * @param value      bright by value.
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelNew bright(int value, String imageAlias) {
    ImageModelNew img = imageToImageNew(delegate.bright(value, imageAlias));
    return img;
  }

  /**
   * Generates a new greyscaled Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @param per        percentage of image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelNew greyscale(String imageAlias, double per) throws IllegalArgumentException {
    ImageModelNew img = imageToImageNew(delegate.greyscale(imageAlias));
    return this.split(per, img, imageAlias);
  }

  /**
   * Generates a new sepia toned Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @param per        percentage of image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelNew sepia(String imageAlias, double per) throws IllegalArgumentException {
    ImageModelNew img = imageToImageNew(delegate.sepia(imageAlias));
    return this.split(per, img, imageAlias);
  }

  /**
   * Generates a new intensified Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelNew intensity(String imageAlias) {
    ImageModelNew img = imageToImageNew(delegate.intensity(imageAlias));
    return img;
  }

  /**
   * Generates a new max Valued pixel Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelNew maxValue(String imageAlias) {
    ImageModelNew img = imageToImageNew(delegate.maxValue(imageAlias));
    return img;
  }

  /**
   * Generates a new vertically flipped Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelNew verticalFlipImage(String imageAlias) {
    ImageModelNew img = imageToImageNew(delegate.verticalFlipImage(imageAlias));
    return img;
  }

  /**
   * Generates a new horizontally flipped Image of this image.
   *
   * @param imageAlias alias name for the new image.
   * @return Image Model Instance of the new generated Image.
   */
  @Override
  public ImageModelNew horizontalFlipImage(String imageAlias) {
    ImageModelNew img = imageToImageNew(delegate.horizontalFlipImage(imageAlias));
    return img;
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
  public ImageModelNew merge3components(ImageModelNew green, ImageModelNew blue,
                                        String imageAlias) {
    ImageModel g = this.imageNewToImage(green);
    ImageModel b = this.imageNewToImage(blue);
    ImageModelNew img = imageToImageNew(delegate.merge3components(g, b, imageAlias));
    return img;
  }

  /**
   * Method to convert the New Image to Old representation.
   *
   * @param model Old representation of the image instance.
   * @return New representation of the same image.
   */
  private ImageModel imageNewToImage(ImageModelNew model) {
    int width = model.getImageWidth();
    int height = model.getImageHeight();
    int[][][] mat = new int[width][height][3];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        mat[i][j][0] = model.getPixelValue(i, j, 0);
        mat[i][j][1] = model.getPixelValue(i, j, 1);
        mat[i][j][2] = model.getPixelValue(i, j, 2);
      }
    }
    ImageModel img = new ImageModelImpl(mat, delegate.getImageAliasName());
    return img;
  }


  /**
   * Method to get a particular pixel of an image.
   *
   * @param i       ith pixel value of a particular image channel.
   * @param j       jth pixel value of a particular image channel.
   * @param channel red, green, or blue channel.
   * @return particular pixel color value.
   */

  public int getPixelValue(int i, int j, int channel) {
    return this.imageMatrix[i][j][channel];
  }

  /**
   * Method to get the width of an image.
   *
   * @return image width.
   */
  public int getImageWidth() {
    return this.imageWidth;
  }

  /**
   * Method to get the height of an image.
   *
   * @return image height.
   */
  public int getImageHeight() {
    return this.imageHeight;
  }

}

