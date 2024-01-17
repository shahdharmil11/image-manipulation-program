package view;

import controller.Features;

/**
 * View Interface For the GUI View.
 */
public interface ImageGUIView {

  /**
   * Public method to display the GUI Window.
   */
  void displayGUI();

  /**
   * Method to add any type of features to GUI as long as they are instance of features interface.
   *
   * @param features instance for features interface that need to use in UI.
   */
  void addFeatures(Features features);

}
