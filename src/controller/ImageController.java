package controller;


import view.ImageView;

/**
 * Image controller interface.
 */
public interface ImageController {

  /**
   * Method to return Helper Class of Controller Class.
   *
   * @return used Helper Class
   */
  ControllerHelper getHelper();

  /**
   * Execute the commands sent to controller.
   *
   * @param line operation string that is needed to be executed.
   */
  void execute(String line);

  /**
   * Method to Return the View Instance of the particular controller.
   *
   * @return Image View Of Controller Instance.
   */
  ImageView getView();
}
