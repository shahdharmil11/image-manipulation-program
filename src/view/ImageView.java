package view;

import model.ImageModelNew;

/**
 * Image View part to display the outputs.
 */
public interface ImageView {


  /**
   * Method to display image on screen.
   *
   * @param image image to displayed.
   */
  public void displayImage(ImageModelNew image);


  /**
   * Output message of required exception.
   *
   * @param message message that is needed to be displayed.
   */
  public void displayMessage(String message);

  /**
   * Getter Method to View.
   */
  public String getMessageToView();
}
