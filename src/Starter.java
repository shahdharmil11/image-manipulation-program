import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import controller.Features;
import controller.ImageController;
import controller.ImageControllerGUIImpl;
import controller.ImageControllerImpl;
import model.ImageModelStore;
import model.ImageModelStoreImpl;
import view.ImageGUIView;
import view.ImageGUIViewImpl;
import view.ImageViewImpl;

/**
 * Starter file containing main method.
 */
public class Starter {
  private InputStream in;

  /**
   * Constructor initializes user input to variable and determines the input type.
   *
   * @param in user input.
   */
  public Starter(InputStream in) {
    this.in = in;
  }

  /**
   * Main method to run the application.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    Starter startProgram = new Starter(System.in);
    if (args.length == 2 && args[0].equals("-file")) {
      String fileName = args[1];
      startProgram.goController(fileName);
    } else if (args.length == 1 && args[0].equals("-text")) {
      startProgram.goController("0");
    } else if (args.length == 0) {
      startProgram.goControllerGUI();
    } else {
      System.out.println("Incorrect way to initiate the program." +
              "Valid Arguments: \n-file path-of-script-file \n-text \nor no arguments");
    }
  }

  /**
   * Method for GUI part of program.
   */
  private void goControllerGUI() {
    ImageGUIView view = new ImageGUIViewImpl();
    ImageModelStore modelStore = new ImageModelStoreImpl();
    Features controller = new ImageControllerGUIImpl(view, modelStore);
  }

  /**
   * Method to start the controller.
   *
   * @param filePath path of file if cmd line arguments.
   */
  public void goController(String filePath) {
    ImageViewImpl view = new ImageViewImpl();
    ImageModelStore modelStore = new ImageModelStoreImpl();
    ImageController controller = new ImageControllerImpl(view, modelStore);
    if (!Objects.equals(filePath, "0")) {
      controller.execute("run " + filePath);
    } else {
      Scanner scanner = new Scanner(this.in);
      String targetLine = "stop";
      while (true) {
        if (in == System.in) {
          System.out.print("Enter Operation: ");
        }
        if (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          if (line.equals(targetLine)) {
            break;
          }
          controller.execute(line);
        } else {
          break;
        }
      }
      scanner.close();
    }

  }
}
