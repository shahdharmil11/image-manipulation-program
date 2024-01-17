package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import model.ImageModelStore;
import view.ImageView;

/**
 * Controller for Application.
 * It mainly controls the flow of Code of entire Program.
 */
public class ImageControllerImpl implements ImageController {
  private ControllerHelper helper;
  private ImageView onScreenView;
  private ImageModelStore modelStore;


  /**
   * Constructor to initialise Helper and View in Controller.
   *
   * @param onScreenView View for program
   */
  public ImageControllerImpl(ImageView onScreenView, ImageModelStore modelStore) {
    this.helper = new ControllerHelperImpl(modelStore);
    this.onScreenView = onScreenView;
  }

  /**
   * Return Helper Of this Controller.
   *
   * @return Helper Of this Controller
   */
  @Override
  public ControllerHelper getHelper() {
    return this.helper;
  }

  /**
   * Private method to fetch of correct number of operation arguments.
   *
   * @param line fetch correct number of arguments after validation.
   * @return Array list of operation arguments.
   */
  private ArrayList<String> getCorrectArguments(String line) {
    String[] words = line.split("\"");
    ArrayList<String> arrayList = new ArrayList<>();
    for (int i = 0; i < words.length; i++) {
      if (i % 2 == 0) {
        String[] k = words[i].split(" ");
        arrayList.addAll(Arrays.asList(k));
      } else {
        arrayList.add(words[i]);
      }
    }
    arrayList.removeIf(s -> s.isEmpty());
    return arrayList;
  }

  /**
   * Method to Return the View Instance of the particular controller.
   *
   * @return Image View Of Controller Instance.
   */
  @Override
  public ImageView getView() {
    return this.onScreenView;
  }

  /**
   * Execute the commands sent to controller.
   *
   * @param line operation string that is needed to be executed.
   */
  @Override
  public void execute(String line) {
    ArrayList<String> correctedWords = getCorrectArguments(line);
    String[] words = correctedWords.toArray(new String[0]);
    int operationLength = words.length;

    if (operationLength > 0) {
      String firstWord = words[0].toLowerCase();
      switch (firstWord) {
        case "load":
          if (operationLength != 3) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            this.helper.loadHelper(words);
          }
          break;
        case "brighten":
          if (operationLength != 4) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.brightenHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage("Image Not Loaded");
            }
          }
          break;
        case "vertical-flip":
          if (operationLength != 3) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.verticalFlipHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage("Image Not Loaded");
            }
          }
          break;
        case "horizontal-flip":
          if (operationLength != 3) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.horizontalFlipHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage("Image Not Loaded");
            }
          }
          break;
        case "save":
          if (operationLength != 3) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              try {
                helper.saveHelper(words);
                onScreenView.displayMessage("Image Saved");
              } catch (Exception ex) {
                onScreenView.displayMessage("Image Not Saved");
              }
            } catch (Exception e) {
              onScreenView.displayMessage("Invalid Operation -> "
                      + String.join(" ", words));
            }
          }
          break;
        case "rgb-split":
          if (operationLength != 5) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.rgbSplitHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage("Image Not Loaded");
            }
          }
          break;
        case "rgb-combine":
          if (operationLength != 5) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.rgbCombineHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage("Image Not Loaded");
            }
          }
          break;
        case "blur":
          if (operationLength != 3 && operationLength != 5) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            if (operationLength == 3) {
              try {
                helper.blurHelper(words);
              } catch (IllegalArgumentException e) {
                onScreenView.displayMessage(e.getMessage());
              } catch (Exception ex) {
                onScreenView.displayMessage("Image Not Loaded");
              }
            } else {
              if (Objects.equals(words[3], "split")) {
                try {
                  helper.blurHelper(words);
                } catch (IllegalArgumentException e) {
                  onScreenView.displayMessage(e.getMessage());
                }
              } else {
                onScreenView.displayMessage("Invalid Operation -> " +
                        String.join(" ", words));
              }
            }
          }
          break;
        case "sharpen":
          if (operationLength != 3 && operationLength != 5) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            if (operationLength == 3) {
              try {

                helper.sharpenHelper(words);

              } catch (Exception ex) {
                onScreenView.displayMessage("Image Not Loaded");
              }
            } else {
              if (Objects.equals(words[3], "split")) {
                try {
                  helper.sharpenHelper(words);
                } catch (IllegalArgumentException e) {
                  onScreenView.displayMessage(e.getMessage());
                }
              } else {
                onScreenView.displayMessage("Invalid Operation -> " +
                        String.join(" ", words));
              }
            }
          }
          break;
        case "luma-component":
          if (operationLength != 3 && operationLength != 5) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            if (operationLength == 3) {
              try {
                helper.greyscaleHelper(words);
              } catch (Exception ex) {
                onScreenView.displayMessage("Image Not Loaded");
              }
            } else {
              if (Objects.equals(words[3], "split")) {
                helper.greyscaleHelper(words);
              } else {
                onScreenView.displayMessage("Invalid Operation -> " +
                        String.join(" ", words));
              }
            }
          }
          break;
        case "sepia":
          if (operationLength != 3 && operationLength != 5) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            if (operationLength == 3) {
              try {

                helper.sepiaHelper(words);

              } catch (Exception ex) {
                onScreenView.displayMessage("Image Not Loaded");
              }
            } else {
              if (Objects.equals(words[3], "split")) {
                try {
                  helper.sepiaHelper(words);
                } catch (IllegalArgumentException e) {
                  onScreenView.displayMessage(e.getMessage());
                }
              } else {
                onScreenView.displayMessage("Invalid Operation -> " +
                        String.join(" ", words));
              }
            }
          }
          break;
        case "red-component":
          if (operationLength != 3) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.redComponentHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage("Image Not Loaded");
            }
          }
          break;
        case "green-component":
          if (operationLength != 3) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.greenComponentHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage("Image Not Loaded");
            }
          }
          break;
        case "blue-component":
          if (operationLength != 3) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.blueComponentHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage("Image Not Loaded");
            }
          }
          break;
        case "intensity-component":
          if (operationLength != 3) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.intensityHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage("Image Not Loaded");
            }
          }
          break;
        case "value-component":
          if (operationLength != 3) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.valueHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage("Image Not Loaded");
            }
          }
          break;
        case "run":
          if (operationLength != 2) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            ArrayList<String> lines = null;
            try {
              lines = this.helper.runHelper(words);
            } catch (FileNotFoundException e) {
              onScreenView.displayMessage("File Not Found: Kindly check the Filepath.");
            }
            if (lines != null) {
              for (String elementLine : lines) {
                this.execute(elementLine);
              }
            }
          }
          break;
        case "compress":
          if (operationLength != 4) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {

              helper.compressHelper(words);

            } catch (Exception ex) {
              onScreenView.displayMessage(ex.getMessage());
            }
          }
          break;
        case "levels-adjust":
          if (operationLength != 6 && operationLength != 8) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            if (operationLength == 6) {
              try {
                helper.levelsAdjustHelper(words);

              } catch (Exception ex) {
                onScreenView.displayMessage(ex.getMessage());
              }
            } else {
              if (Objects.equals(words[6], "split")) {
                try {
                  helper.levelsAdjustHelper(words);
                } catch (Exception ex) {
                  onScreenView.displayMessage(ex.getMessage());
                }
              } else {
                onScreenView.displayMessage("Invalid Operation -> " +
                        String.join(" ", words));
              }
            }
          }
          break;
        case "histogram":
          if (operationLength != 3) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            try {
              helper.histogramHelper(words);
            } catch (Exception ex) {
              onScreenView.displayMessage(ex.getMessage());
            }
          }
          break;
        case "color-correct":
          if (operationLength != 3 && operationLength != 5) {
            onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
          } else {
            if (operationLength == 3) {
              try {
                helper.colorCorrectHelper(words);
              } catch (Exception ex) {
                onScreenView.displayMessage(ex.getMessage());
              }
            } else {
              if (Objects.equals(words[3], "split")) {
                try {
                  helper.colorCorrectHelper(words);
                } catch (Exception ex) {
                  onScreenView.displayMessage(ex.getMessage());
                }
              } else {
                onScreenView.displayMessage("Invalid Operation -> " +
                        String.join(" ", words));
              }
            }
          }
          break;
        default:
          onScreenView.displayMessage("Invalid Operation -> " + String.join(" ", words));
      }
    }
  }

}
