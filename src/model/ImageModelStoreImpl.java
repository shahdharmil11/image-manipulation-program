package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Class that stores all the instance sof the processed images that are being currently worked on.
 */
public class ImageModelStoreImpl implements ImageModelStore {
  private Map<String, ImageModelNew> loadedImageMap;

  /**
   * Constructor that create a map to track the loaded images.
   */
  public ImageModelStoreImpl() {
    this.loadedImageMap = new HashMap<>();
  }

  /**
   * Constructor that create a map to track the loaded images with initial map.
   */
  public ImageModelStoreImpl(String alias, ImageModelNew img) {
    this.loadedImageMap = new HashMap<>();
    this.loadedImageMap.put(alias, img);
  }

  /**
   * Return Image for given alias.
   *
   * @param aliasName aliasname of image.
   * @return Model with given aliasname
   */
  @Override
  public ImageModelNew getImageForAlias(String aliasName) {
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
    this.loadedImageMap.put(aliasName, image);
  }

  /**
   * Method that helps the image to get loaded in the application.
   *
   * @param words operation words in an array.
   */
  @Override
  public void loadHelper(String[] words) {
    String imagePath = words[1];
    String nameOfImage = words[2];
    if (checkImageType(imagePath) != null) {
      ImageModelNew image = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(imagePath)),
              nameOfImage);
      loadImage(image, nameOfImage);
    } else {
      System.out.println("File Not Exists!");
    }
  }

  /**
   * Constructor helper function to fetch the image from path and set it into matrix.
   *
   * @param inputPath Input path of the image.
   */
  private int[][][] checkImageType(String inputPath) {
    File imageFile;
    BufferedImage inputImage;
    int[][][] imageMatrix = new int[3][][];

    try {
      String fileTypeString = inputPath.substring(inputPath.length() - 3);
      if (fileTypeString.equals("ppm")) {
        Scanner sc;
        try {
          sc = new Scanner(new FileInputStream(inputPath));
        } catch (FileNotFoundException e) {
          System.out.println("File " + inputPath + " not found!");
          return null;
        }
        StringBuilder builder = new StringBuilder();
        while (sc.hasNextLine()) {
          String s = sc.nextLine();
          if (s.charAt(0) != '#') {
            builder.append(s + System.lineSeparator());
          }
        }
        sc = new Scanner(builder.toString());
        String token;
        token = sc.next();
        if (!token.equals("P3")) {
          System.out.println("Invalid PPM file: plain RAW file should begin with P3");
        }
        int width = sc.nextInt();
        int height = sc.nextInt();
        int maxValue = sc.nextInt();
        imageMatrix = new int[width][height][3];

        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            int r = sc.nextInt();
            int g = sc.nextInt();
            int b = sc.nextInt();
            imageMatrix[j][i][0] = r;
            imageMatrix[j][i][1] = g;
            imageMatrix[j][i][2] = b;
          }
        }
      } else {
        imageFile = new File(inputPath);
        if (!imageFile.exists()) {
          return null;
        }
        inputImage = ImageIO.read(imageFile);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        imageMatrix = new int[width][height][3];
        for (int i = 0; i < width; i++) {
          for (int j = 0; j < height; j++) {
            int rgb = inputImage.getRGB(i, j);
            imageMatrix[i][j][0] = (rgb >> 16) & 0xFF;
            imageMatrix[i][j][1] = (rgb >> 8) & 0xFF;
            imageMatrix[i][j][2] = rgb & 0xFF;
          }
        }
      }
    } catch (IOException e) {
      System.out.println("Exception: " + e);
    }
    return imageMatrix;
  }


  /**
   * Method that helps controller to save the image.
   *
   * @param words operation words in an array.
   * @throws IOException If the save path is not present.
   */
  @Override
  public void saveHelper(String[] words) throws IOException {
    String destinationPath = words[1];
    String imageName = words[2];
    ImageModelNew img = getImageForAlias(imageName);
    int width = img.getImageWidth();
    int height = img.getImageHeight();
    int[][][] matrix = new int[width][height][3];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        for (int c = 0; c < 3; c++) {
          matrix[i][j][c] = img.getPixelValue(i, j, c);
        }
      }
    }
    saveImage(matrix, destinationPath);
  }

  /**
   * Saves image at a given path.
   *
   * @param imageMatrix matrix to generate image to save.
   * @param fileName    image name that is to be saved.
   * @throws IOException id the save path doesn't exist.
   */
  private void saveImage(int[][][] imageMatrix, String fileName) throws IOException {
    int width = imageMatrix.length;
    int height = imageMatrix[0].length;
    int lastDotIndex = fileName.lastIndexOf('.');
    String format = fileName.substring(lastDotIndex + 1).toLowerCase();

    int pathInd = fileName.lastIndexOf('/');

    Boolean pathExist = true;
    if (pathInd != -1) {
      String path = fileName.substring(0, pathInd).toLowerCase();
      pathExist = new File(path).exists();
    }

    if (pathExist) {

      if (format.equals("ppm")) {
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        // Write the PPM header
        printWriter.println("P3");
        printWriter.println(width + " " + height);
        printWriter.println("255"); // Maximum color value

        // Write the image data
        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            int red = imageMatrix[x][y][0];
            int green = imageMatrix[x][y][1];
            int blue = imageMatrix[x][y][2];
            printWriter.println(red);
            printWriter.println(green);
            printWriter.println(blue);
          }
        }
        printWriter.close();
        fileWriter.close();
      } else {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
          for (int y = 0; y < height; y++) {
            int rgb = (imageMatrix[x][y][0] << 16) |
                    (imageMatrix[x][y][1] << 8) |
                    (imageMatrix[x][y][2]);
            newImage.setRGB(x, y, rgb);
          }
        }

        File outputImageFile = new File(fileName);
        ImageIO.write(newImage, format, outputImageFile);
      }
    } else {
      throw new FileNotFoundException();
    }
  }

  /**
   * Method to get the buffered image instance of processed form the map.
   *
   * @param aliasName Name of the image to get.
   * @return instance of buffered image.
   */
  public BufferedImage getBufferedImage(String aliasName) {
    ImageModelNew image = loadedImageMap.get(aliasName);
    int width = image.getImageWidth();
    int height = image.getImageHeight();
    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int rgb = (image.getPixelValue(x, y, 0) << 16) |
                (image.getPixelValue(x, y, 1) << 8) |
                (image.getPixelValue(x, y, 2));
        newImage.setRGB(x, y, rgb);
      }
    }
    return newImage;
  }
}
