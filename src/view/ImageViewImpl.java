package view;

import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import model.ImageModelNew;

/**
 * Class that represents view of program.it is used for the output part of the program.
 */
public class ImageViewImpl implements ImageView {
  private String messageToView = "";

  /**
   * Method to display image.
   *
   * @param image image to displayed.
   */
  @Override
  public void displayImage(ImageModelNew image) {
    BufferedImage newImage = new BufferedImage(image.getImageWidth()
            , image.getImageHeight(), BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < image.getImageWidth(); x++) {
      for (int y = 0; y < image.getImageHeight(); y++) {
        int rgb = (image.getPixelValue(x, y, 0) << 16)
                | (image.getPixelValue(x, y, 1) << 8)
                | image.getPixelValue(x, y, 2);
        newImage.setRGB(x, y, rgb);
      }
    }
    JFrame frame = new JFrame("Image Display");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JLabel label = new JLabel(new ImageIcon(newImage));
    frame.getContentPane().add(label);
    frame.pack();
    frame.setVisible(true);
  }


  /**
   * Output message of required exception.
   *
   * @param message message that is needed to be displayed.
   */
  @Override
  public void displayMessage(String message) {
    this.messageToView = message;
    System.out.println(message);
  }

  /**
   * Gives String of Message to be displayed.
   *
   * @return String of Message to be displayed
   */
  @Override
  public String getMessageToView() {
    return this.messageToView;
  }
}
