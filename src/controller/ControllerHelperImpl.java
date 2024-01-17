package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.ImageModelStore;

/**
 * Helper Class for Controller Logic.
 */
class ControllerHelperImpl implements ControllerHelper {

  private ImageModelStore modelStore;

  /**
   * Constructor that create a map to track the loaded images.
   */
  public ControllerHelperImpl(ImageModelStore modelStore) {
    this.modelStore = modelStore;
  }


  /**
   * Method that helps the image to get loaded in the application.
   *
   * @param words operation words in an array.
   */
  @Override
  public void loadHelper(String[] words) {
    modelStore.loadHelper(words);
  }


  /**
   * Method that helps controller to brighten the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void brightenHelper(String[] words) {
    int incrementValue = Integer.parseInt(words[1]);
    String imageName = words[2];
    String newImageName = words[3];
    modelStore.loadImage(modelStore.getImageForAlias(imageName).bright(incrementValue
                    , newImageName)
            , newImageName);
  }

  /**
   * Method that helps controller to vertically flip the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void verticalFlipHelper(String[] words) {
    String imageName = words[1];
    String newImageName = words[2];
    modelStore.loadImage(modelStore.getImageForAlias(imageName).verticalFlipImage(newImageName)
            , newImageName);
  }

  /**
   * Method that helps controller to horizontally flip the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void horizontalFlipHelper(String[] words) {
    String imageName = words[1];
    String newImageName = words[2];
    modelStore.loadImage(modelStore.getImageForAlias(imageName).horizontalFlipImage(newImageName)
            , newImageName);
  }

  /**
   * Method that helps controller to save the image.
   *
   * @param words operation words in an array.
   * @throws IOException If the save path is not present.
   */
  @Override
  public void saveHelper(String[] words) throws IOException {
    modelStore.saveHelper(words);
  }


  /**
   * Method that helps controller to split 3 channels of the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void rgbSplitHelper(String[] words) {
    String imageName = words[1];
    String destinationImageNameRed = words[2];
    String destinationImageNameGreen = words[3];
    String destinationImageNameBlue = words[4];
    modelStore.loadImage(modelStore.getImageForAlias(imageName)
                    .generateRed(destinationImageNameRed)
            , destinationImageNameRed);
    modelStore.loadImage(modelStore.getImageForAlias(imageName)
                    .generateGreen(destinationImageNameGreen)
            , destinationImageNameGreen);
    modelStore.loadImage(modelStore.getImageForAlias(imageName)
                    .generateBlue(destinationImageNameBlue)
            , destinationImageNameBlue);
  }

  /**
   * Method that helps controller to combine 3 channels to single instance of the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void rgbCombineHelper(String[] words) {
    String imageName = words[1];
    String imageNameRed = words[2];
    String imageNameGreen = words[3];
    String imageNameBlue = words[4];
    modelStore.loadImage(modelStore.getImageForAlias(imageNameRed)
            .merge3components(modelStore.getImageForAlias(imageNameGreen)
                    , modelStore.getImageForAlias(imageNameBlue), imageName), imageName);
  }

  /**
   * Method that helps controller to blur the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void blurHelper(String[] words) throws IllegalArgumentException {
    String imageName = words[1];
    String destinationImageName = words[2];

    if (words.length > 3) {
      double per = Double.parseDouble(words[4]);
      modelStore.loadImage(modelStore.getImageForAlias(imageName).blur(destinationImageName, per),
              destinationImageName);
    } else {
      modelStore.loadImage(modelStore.getImageForAlias(imageName)
                      .blur(destinationImageName, 100),
              destinationImageName);
    }


  }

  /**
   * Method that helps controller to sharpen the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void sharpenHelper(String[] words) throws IllegalArgumentException {
    String imageName = words[1];
    String destinationImageName = words[2];
    if (words.length > 3) {
      double per = Double.parseDouble(words[4]);
      modelStore.loadImage(modelStore.getImageForAlias(imageName)
                      .sharpen(destinationImageName, per),
              destinationImageName);
    } else {
      modelStore.loadImage(modelStore.getImageForAlias(imageName)
                      .sharpen(destinationImageName, 100),
              destinationImageName);
    }
  }

  /**
   * Method that helps controller to greyscale the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void greyscaleHelper(String[] words) throws IllegalArgumentException {
    String imageName = words[1];
    String destinationImageName = words[2];
    if (words.length > 3) {
      double per = Double.parseDouble(words[4]);
      modelStore.loadImage(modelStore.getImageForAlias(imageName)
                      .greyscale(destinationImageName, per),
              destinationImageName);
    } else {
      modelStore.loadImage(modelStore.getImageForAlias(imageName)
                      .greyscale(destinationImageName, 100),
              destinationImageName);
    }
  }

  /**
   * Method that helps controller to sepia the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void sepiaHelper(String[] words) throws IllegalArgumentException {
    String imageName = words[1];
    String destinationImageName = words[2];
    if (words.length > 3) {
      double per = Double.parseDouble(words[4]);
      modelStore.loadImage(modelStore.getImageForAlias(imageName)
                      .sepia(destinationImageName, per),
              destinationImageName);
    } else {
      modelStore.loadImage(modelStore.getImageForAlias(imageName)
                      .sepia(destinationImageName, 100),
              destinationImageName);
    }
  }

  /**
   * Method that helps controller to extract red channel of the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void redComponentHelper(String[] words) {
    String imageName = words[1];
    String destinationImageName = words[2];
    modelStore.loadImage(modelStore.getImageForAlias(imageName)
                    .generateRed(destinationImageName)
            , destinationImageName);
  }

  /**
   * Method that helps controller to extract green channel of the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void greenComponentHelper(String[] words) {
    String imageName = words[1];
    String destinationImageName = words[2];
    modelStore.loadImage(modelStore.getImageForAlias(imageName)
                    .generateGreen(destinationImageName)
            , destinationImageName);
  }

  /**
   * Method that helps controller to extract blue channel of the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void blueComponentHelper(String[] words) {
    String imageName = words[1];
    String destinationImageName = words[2];
    modelStore.loadImage(modelStore.getImageForAlias(imageName).generateBlue(destinationImageName)
            , destinationImageName);
  }

  /**
   * Method that helps controller to intensify the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void intensityHelper(String[] words) {
    String imageName = words[1];
    String destinationImageName = words[2];
    modelStore.loadImage(modelStore.getImageForAlias(imageName).intensity(destinationImageName)
            , destinationImageName);
  }

  /**
   * Method that helps controller to set the channel to max value of the image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void valueHelper(String[] words) {
    String imageName = words[1];
    String destinationImageName = words[2];
    modelStore.loadImage(modelStore.getImageForAlias(imageName).maxValue(destinationImageName)
            , destinationImageName);
  }

  /**
   * Method to help the controller run commands form a file.
   *
   * @param words operation words in an array.
   * @return the String of every line from the file to execute further.
   * @throws FileNotFoundException if the run file path is not found.
   */
  @Override
  public ArrayList<String> runHelper(String[] words) throws FileNotFoundException {
    ArrayList<String> lines = new ArrayList<>();
    String filePath = words[1];
    File file = new File(filePath);
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      String line2 = scanner.nextLine();
      lines.add(line2);
    }
    scanner.close();
    return lines;
  }

  /**
   * Method to help the controller run compress function on an image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void compressHelper(String[] words) throws IllegalArgumentException {
    double compressionPercentage = Double.parseDouble(words[1]);
    String imageName = words[2];
    String destinationImageName = words[3];
    modelStore.loadImage(modelStore.getImageForAlias(imageName).compress(destinationImageName,
                    compressionPercentage / 100)
            , destinationImageName);

  }

  /**
   * Method to help the controller run levels adjust function on an image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void levelsAdjustHelper(String[] words) throws IllegalArgumentException {
    int bValue = Integer.parseInt(words[1]);
    int mValue = Integer.parseInt(words[2]);
    int wValue = Integer.parseInt(words[3]);
    String imageName = words[4];
    String destinationImageName = words[5];

    if (words.length > 6) {
      double per = Double.parseDouble(words[7]);
      modelStore.loadImage(modelStore.getImageForAlias(imageName).levelAdjust(destinationImageName,
              bValue, mValue, wValue, per), destinationImageName);
    } else {
      modelStore.loadImage(modelStore.getImageForAlias(imageName).levelAdjust(destinationImageName,
              bValue, mValue, wValue, 100), destinationImageName);
    }

  }

  /**
   * Method to help the controller run generate histogram function on an image.
   *
   * @param words operation words in an array.
   * @throws IOException when.
   */
  @Override
  public void histogramHelper(String[] words) throws IOException {
    String imageName = words[1];
    String destinationImageName = words[2];
    modelStore.loadImage(modelStore.getImageForAlias(imageName)
                    .generateHistogram(destinationImageName)
            , destinationImageName);
  }

  /**
   * Method to help the controller run color correction function on an image.
   *
   * @param words operation words in an array.
   */
  @Override
  public void colorCorrectHelper(String[] words) throws IllegalArgumentException {
    String imageName = words[1];
    String destinationImageName = words[2];
    if (words.length > 3) {
      double per = Double.parseDouble(words[4]);
      modelStore.loadImage(modelStore.getImageForAlias(imageName)
                      .colorCorrect(destinationImageName, per),
              destinationImageName);
    } else {
      modelStore.loadImage(modelStore.getImageForAlias(imageName)
                      .colorCorrect(destinationImageName, 100),
              destinationImageName);
    }
  }

  /*
  @Override
  public void displayHelper(String[] words) {
    String imageName = words[1];
    modelStore.getImageForAlias(imageName).display();
  }
  */

}
