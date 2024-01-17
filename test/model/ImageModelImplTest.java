package model;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import javax.imageio.ImageIO;

import static org.junit.Assert.assertEquals;


/**
 * Test Class for Image Model Class.
 */

public class ImageModelImplTest {
  private int[][][] matrix = {
          {{0, 0, 0}, {10, 90, 20}, {20, 180, 40}},
          {{200, 10, 150}, {210, 100, 170}, {220, 190, 190}},
          {{255, 20, 255}, {255, 110, 255}, {255, 200, 255}}
  };


  /**
   * Test Width and Height of image.
   */

  @Test
  public void testImage() {
    ImageModelNew image = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.png")), "original");
    assertEquals(3, image.getImageWidth());
    assertEquals(3, image.getImageHeight());
  }


  /**
   * Tests if PNG image is loaded successfully.
   */

  @Test
  public void testLoadPNG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.png")), "org");
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrix[y][x][0], img.getPixelValue(y, x, 0));
        assertEquals(matrix[y][x][1], img.getPixelValue(y, x, 1));
        assertEquals(matrix[y][x][2], img.getPixelValue(y, x, 2));
      }
    }
  }

  /**
   * Tests if PPM image is loaded successfully.
   */

  @Test
  public void testLoadPPM() {
    ImageModelNew image = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.ppm")), "org");

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrix[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrix[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrix[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  /**
   * Tests if JPG image is loaded successfully.
   */

  @Test
  public void testLoadJPG() {
    ImageModelNew image = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.jpg")), "org");
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrix[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrix[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrix[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  /**
   * Test Brighten Image command.
   */

  @Test
  public void testBrighten() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.ppm")), "org");
    ImageModelNew image = img.bright(10, "org-bright");
    int[][][] matrix = this.matrix;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        matrix[y][x][0] = matrix[y][x][0] + 10;
        matrix[y][x][1] = matrix[y][x][1] + 10;
        matrix[y][x][2] = matrix[y][x][2] + 10;
      }
    }

    this.clamp(matrix);
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrix[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrix[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrix[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  private void clamp(int[][][] matrix) {
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
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
   * Test Brighten Image command(Negative Value).
   */

  @Test
  public void testDarken() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.ppm")), "org");
    ImageModelNew image = img.bright(-10, "org-dark");
    int[][][] matrix = this.matrix;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        matrix[y][x][0] = matrix[y][x][0] - 10;
        matrix[y][x][1] = matrix[y][x][1] - 10;
        matrix[y][x][2] = matrix[y][x][2] - 10;
      }
    }
    this.clamp(matrix);
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrix[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrix[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrix[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }


  /**
   * Test Horizontal Flip.
   */

  @Test
  public void testHorizontal() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.ppm")), "org");
    ImageModelNew image = img.horizontalFlipImage("org-hor");

    int[][][] matrix = {
            {{255, 20, 255}, {255, 110, 255}, {255, 200, 255}},
            {{200, 10, 150}, {210, 100, 170}, {220, 190, 190}},
            {{0, 0, 0}, {10, 90, 20}, {20, 180, 40}}
    };

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrix[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrix[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrix[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }


  /**
   * Test Vertical Flip.
   */
  @Test
  public void testVertical() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.ppm")), "org");
    ImageModelNew image = img.verticalFlipImage("org-ver");

    int[][][] matrix = {
            {{20, 180, 40}, {10, 90, 20}, {0, 0, 0}},
            {{220, 190, 190}, {210, 100, 170}, {200, 10, 150}},
            {{255, 200, 255}, {255, 110, 255}, {255, 20, 255}}
    };

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrix[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrix[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrix[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  /**
   * Tests if image is RGB Combine successfully.
   */
  @Test
  public void testRGBCombine() {

    ImageModelNew imgR = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-red.png")), "org-red");
    ImageModelNew imgG = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-green.png")), "org-g");
    ImageModelNew imgB = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-blue.png")), "org-b");

    ImageModelNew image = imgR.merge3components(imgG, imgB, "org-merge");

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrix[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrix[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrix[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }


  /**
   * Tests if image is blurred successfully.
   */

  @Test
  public void testBlur() {

    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.png")), "org");
    ImageModelNew image = img.blur("org-blur", 100);
    int[][][] matrixBlur = {
            {{39, 18, 31}, {57, 70, 52}, {46, 86, 46}},
            {{124, 30, 107}, {171, 100, 153}, {132, 120, 122}},
            {{133, 26, 125}, {180, 80, 170}, {136, 93, 130}}
    };

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrixBlur[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixBlur[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixBlur[y][x][2], image.getPixelValue(y, x, 2));
      }
    }

  }

  /**
   * Tests if image is sharpened successfully.
   */

  @Test
  public void testSharpen() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.png")), "org");
    ImageModelNew image = img.sharpen("org-sharp", 100);

    int[][][] matrixSharp = {
            {{0, 0, 0}, {76, 168, 61}, {9, 232, 20}},
            {{255, 18, 255}, {255, 255, 255}, {255, 255, 255}},
            {{255, 0, 255}, {255, 206, 255}, {255, 255, 255}}
    };

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrixSharp[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixSharp[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixSharp[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }


  /**
   * Tests if image is viewed as Luma successfully.
   */

  @Test
  public void testLuma() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.png")), "org");
    ImageModelNew image = img.greyscale("org-luma", 100);
    int[][][] matrixLuma = {
            {{0, 0, 0}, {67, 67, 67}, {135, 135, 135}},
            {{60, 60, 60}, {128, 128, 128}, {196, 196, 196}},
            {{86, 86, 86}, {151, 151, 151}, {215, 215, 215}}
    };

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrixLuma[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixLuma[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixLuma[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  /**
   * Tests if image is converted to Sepia successfully.
   */

  @Test
  public void testSepia() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.png")), "org");
    ImageModelNew image = img.sepia("org-sepia", 100);
    int[][][] matrixSepia = {
            {{0, 0, 0}, {76, 68, 53}, {153, 137, 106}},
            {{114, 101, 79}, {191, 170, 132}, {255, 239, 186}},
            {{163, 145, 113}, {233, 207, 161}, {255, 255, 209}}
    };

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrixSepia[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixSepia[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixSepia[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  /**
   * Tests if image is converted to Red, Blue, Green Component successfully.
   */

  @Test
  public void testRedGreenBlue() throws IOException {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.png")), "org");

    ImageModelNew imageR = img.generateRed("original-red");
    ImageModelNew imageG = img.generateGreen("original-green");
    ImageModelNew imageB = img.generateBlue("original-blue");

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrix[y][x][0], imageR.getPixelValue(y, x, 0));
        assertEquals(0, imageR.getPixelValue(y, x, 1));
        assertEquals(0, imageR.getPixelValue(y, x, 2));

        assertEquals(0, imageG.getPixelValue(y, x, 0));
        assertEquals(matrix[y][x][1], imageG.getPixelValue(y, x, 1));
        assertEquals(0, imageG.getPixelValue(y, x, 2));

        assertEquals(0, imageB.getPixelValue(y, x, 0));
        assertEquals(0, imageB.getPixelValue(y, x, 1));
        assertEquals(matrix[y][x][2], imageB.getPixelValue(y, x, 2));
      }
    }
  }

  /**
   * Tests if image is converted to Intensity successfully.
   */

  @Test
  public void testIntensity() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.png")), "org");

    ImageModelNew image = img.intensity("original-intensity");
    int[][][] matrixIntensity = {
            {{0, 0, 0}, {40, 40, 40}, {80, 80, 80}},
            {{120, 120, 120}, {160, 160, 160}, {200, 200, 200}},
            {{176, 176, 176}, {206, 206, 206}, {236, 236, 236}}
    };

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrixIntensity[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixIntensity[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixIntensity[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  /**
   * Tests if image is converted to Value successfully.
   */

  @Test
  public void testValue() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/image-small.png")), "org");

    ImageModelNew image = img.maxValue("original-value");
    int[][][] matrixValue = {
            {{0, 0, 0}, {90, 90, 90}, {180, 180, 180}},
            {{200, 200, 200}, {210, 210, 210}, {220, 220, 220}},
            {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}}
    };

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

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

  //NEW TESTS FOR NEW METHODS FROM HERE

  @Test
  public void testCompressionOriginalPNG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.png")), "org");

    double compRatio = 0;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{0, 0, 0}, {10, 90, 20}, {20, 180, 40}, {60, 60, 60}, {70, 70, 70}},
            {{200, 10, 150}, {210, 100, 170}, {220, 190, 190}, {120, 120, 120}, {200, 255, 200}},
            {{255, 20, 255}, {255, 110, 255}, {255, 200, 255}, {30, 40, 50}, {130, 0, 10}},
            {{155, 20, 255}, {155, 110, 55}, {255, 200, 255}, {10, 70, 0}, {0, 0, 90}},
            {{155, 20, 155}, {255, 110, 155}, {255, 200, 255}, {10, 200, 0}, {0, 240, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testCompressionOriginalPPM() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.ppm")), "org");

    double compRatio = 0;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{0, 0, 0}, {10, 90, 20}, {20, 180, 40}, {60, 60, 60}, {70, 70, 70}},
            {{200, 10, 150}, {210, 100, 170}, {220, 190, 190}, {120, 120, 120}, {200, 255, 200}},
            {{255, 20, 255}, {255, 110, 255}, {255, 200, 255}, {30, 40, 50}, {130, 0, 10}},
            {{155, 20, 255}, {155, 110, 55}, {255, 200, 255}, {10, 70, 0}, {0, 0, 90}},
            {{155, 20, 155}, {255, 110, 155}, {255, 200, 255}, {10, 200, 0}, {0, 240, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testCompressionOriginalJPG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.jpg")), "org");

    double compRatio = 0;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{48, 0, 41}, {73, 32, 72}, {112, 129, 123}, {29, 79, 42}, {37, 91, 41}},
            {{123, 29, 113}, {187, 123, 183}, {187, 185, 198}, {95, 127, 106}, {213, 253, 216}},
            {{202, 57, 188}, {247, 136, 239}, {242, 198, 249}, {43, 39, 53}, {30, 40, 29}},
            {{182, 23, 152}, {200, 80, 178}, {242, 200, 238}, {46, 58, 48}, {0, 17, 0}},
            {{164, 27, 107}, {204, 115, 161}, {234, 240, 214}, {71, 149, 65}, {76, 203, 70}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testCompression90PNG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.png")), "org");

    double compRatio = 0.9;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {12, 41, 12}},
            {{90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {12, 41, 12}},
            {{90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {12, 41, 12}},
            {{90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {12, 41, 12}},
            {{90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {12, 41, 12}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testCompression90PPM() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.ppm")), "org");

    double compRatio = 0.9;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {12, 41, 12}},
            {{90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {12, 41, 12}},
            {{90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {12, 41, 12}},
            {{90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {12, 41, 12}},
            {{90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {90, 41, 84}, {12, 41, 12}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testCompression90JPG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.jpg")), "org");

    double compRatio = 0.9;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{120, 41, 116}, {120, 41, 116}, {120, 41, 116}, {120, 41, 116}, {39, 41, 41}},
            {{120, 41, 116}, {120, 41, 116}, {120, 41, 116}, {120, 41, 116}, {39, 41, 41}},
            {{120, 41, 116}, {120, 41, 116}, {218, 41, 116}, {21, 41, 116}, {39, 41, 41}},
            {{120, 41, 116}, {120, 41, 116}, {218, 41, 116}, {21, 41, 116}, {39, 41, 41}},
            {{64, 41, 57}, {64, 41, 57}, {64, 138, 57}, {64, 138, 57}, {0, 41, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testCompression50PNG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.png")), "org");

    double compRatio = 0.5;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{5, 43, 19}, {5, 43, 19}, {40, 118, 94}, {40, 118, 94}, {92, 116, 91}},
            {{205, 43, 169}, {205, 43, 169}, {170, 118, 94}, {170, 118, 94}, {92, 116, 91}},
            {{171, 43, 172}, {171, 43, 172}, {255, 191, 255}, {54, 46, 57}, {25, 34, 23}},
            {{171, 43, 172}, {171, 43, 172}, {255, 191, 255}, {54, 46, 57}, {25, 34, 23}},
            {{187, 80, 148}, {187, 80, 148}, {255, 180, 255}, {28, 180, 7}, {0, 1, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }


  @Test
  public void testCompression50PPM() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.ppm")), "org");

    double compRatio = 0.5;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{5, 43, 19}, {5, 43, 19}, {40, 118, 94}, {40, 118, 94}, {92, 116, 91}},
            {{205, 43, 169}, {205, 43, 169}, {170, 118, 94}, {170, 118, 94}, {92, 116, 91}},
            {{171, 43, 172}, {171, 43, 172}, {255, 191, 255}, {54, 46, 57}, {25, 34, 23}},
            {{171, 43, 172}, {171, 43, 172}, {255, 191, 255}, {54, 46, 57}, {25, 34, 23}},
            {{187, 80, 148}, {187, 80, 148}, {255, 180, 255}, {28, 180, 7}, {0, 1, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testCompression50JPG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.jpg")), "org");

    double compRatio = 0.5;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{59, 60, 64}, {59, 60, 64}, {115, 127, 118}, {28, 127, 32}, {10, 91, 13}},
            {{154, 60, 155}, {154, 60, 155}, {186, 127, 188}, {98, 127, 101}, {185, 253, 187}},
            {{175, 26, 168}, {175, 94, 168}, {255, 202, 255}, {77, 52, 72}, {35, 14, 36}},
            {{175, 26, 168}, {175, 94, 168}, {255, 202, 255}, {77, 52, 72}, {35, 14, 36}},
            {{176, 102, 135}, {176, 102, 135}, {242, 164, 213}, {79, 164, 64}, {5, 165, 4}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testLevelAdjustPNG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.png")), "org");

    int b = 20;
    int m = 100;
    int w = 255;
    ImageModelNew image = img.levelAdjust("original-value", b, m, w, 100);
    int[][][] matrixValue = {
            {{0, 0, 0}, {0, 114, 0}, {0, 213, 35}, {69, 69, 69}, {84, 84, 84}},
            {{228, 0, 186}, {234, 128, 205}, {240, 221, 221}, {153, 153, 153}, {228, 255, 228}},
            {{255, 0, 255}, {255, 141, 255}, {255, 228, 255}, {18, 35, 52}, {165, 0, 0}},
            {{191, 0, 255}, {191, 141, 61}, {255, 228, 255}, {0, 84, 0}, {0, 0, 114}},
            {{191, 0, 191}, {255, 141, 191}, {255, 228, 255}, {0, 228, 0}, {0, 249, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testLevelAdjustPPM() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.ppm")), "org");

    int b = 20;
    int m = 100;
    int w = 255;
    ImageModelNew image = img.levelAdjust("original-value", b, m, w, 100);
    int[][][] matrixValue = {
            {{0, 0, 0}, {0, 114, 0}, {0, 213, 35}, {69, 69, 69}, {84, 84, 84}},
            {{228, 0, 186}, {234, 128, 205}, {240, 221, 221}, {153, 153, 153}, {228, 255, 228}},
            {{255, 0, 255}, {255, 141, 255}, {255, 228, 255}, {18, 35, 52}, {165, 0, 0}},
            {{191, 0, 255}, {191, 141, 61}, {255, 228, 255}, {0, 84, 0}, {0, 0, 114}},
            {{191, 0, 191}, {255, 141, 191}, {255, 228, 255}, {0, 228, 0}, {0, 249, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testLevelAdjustJPG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.jpg")), "org");

    int b = 20;
    int m = 100;
    int w = 255;
    ImageModelNew image = img.levelAdjust("original-value", b, m, w, 100);
    int[][][] matrixValue = {
            {{49, 0, 37}, {89, 21, 88}, {143, 163, 156}, {16, 98, 39}, {30, 115, 37}},
            {{156, 16, 144}, {218, 156, 215}, {218, 217, 226}, {121, 161, 135}, {236, 254, 238}},
            {{229, 64, 219}, {252, 171, 249}, {250, 226, 253}, {41, 34, 57}, {18, 35, 16}},
            {{215, 5, 188}, {228, 99, 211}, {250, 228, 248}, {46, 66, 49}, {0, 0, 0}},
            {{199, 12, 137}, {230, 147, 197}, {247, 249, 236}, {86, 185, 77}, {94, 230, 84}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testHistogram() throws IOException {
    ImageModelNew grid = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/grid.png")), "grid");

    int[][][] mat = new int[][][]{{{0, 0, 0}, {0, 0, 0}}};

    ImageModelNew img = new ImageModelNewImpl(mat, "mat");
    ImageModelNew hist = img.generateHistogram("mat-hist");
    int[][][] freq = new int[256][256][3];

    for (int i = 0; i < freq.length; i++) {
      for (int j = 0; j < freq[0].length; j++) {
        for (int c = 0; c < 3; c++) {
          freq[i][j][c] = grid.getPixelValue(i, j, c);
        }
      }
    }

    for (int i = 0; i < freq.length; i++) {
      for (int j = 0; j < freq[0].length; j++) {
        for (int c = 0; c < 3; c++) {
          //freq[i][j][c] = grid.getPixelValue(i,j,c);
          if (j == freq.length - 1 || (i == 0 && j < freq.length / 2 - 1)
                  || (i == 1 && j >= freq.length / 2 - 1)) {
            if (c != 2) {
              freq[i][j][c] = 0;
            } else {
              freq[i][j][c] = 255;
            }
            if (i == 0 && j >= freq.length / 2) {
              freq[i][j][c] = 192;
            }
          }
        }
      }
    }


    for (int i = 0; i < hist.getImageWidth(); i++) {
      for (int j = 0; j < hist.getImageHeight(); j++) {
        for (int c = 0; c < 3; c++) {
          assertEquals(freq[i][j][c], hist.getPixelValue(i, j, c));
        }
      }
    }
  }


  @Test
  public void testCompression100PNG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.png")), "org");

    double compRatio = 1;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testCompression100PPM() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.ppm")), "org");

    double compRatio = 1;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testCompression100JPG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.jpg")), "org");

    double compRatio = 1;
    ImageModelNew image = img.compress("original-value", compRatio);
    int[][][] matrixValue = {
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testLevelAdjustSplitPNG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.png")), "org");

    int b = 20;
    int m = 100;
    int w = 255;
    ImageModelNew image = img.levelAdjust("original-value", b, m, w, 80);
    int[][][] matrixValue = {
            {{0, 0, 0}, {0, 114, 0}, {0, 213, 35}, {69, 69, 69}, {84, 84, 84}},
            {{228, 0, 186}, {234, 128, 205}, {240, 221, 221}, {153, 153, 153}, {228, 255, 228}},
            {{255, 0, 255}, {255, 141, 255}, {255, 228, 255}, {18, 35, 52}, {165, 0, 0}},
            {{191, 0, 255}, {191, 141, 61}, {255, 228, 255}, {0, 84, 0}, {0, 0, 114}},
            {{155, 20, 155}, {255, 110, 155}, {255, 200, 255}, {10, 200, 0}, {0, 240, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testLevelAdjustSplitPPM() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.ppm")), "org");

    int b = 20;
    int m = 100;
    int w = 255;
    ImageModelNew image = img.levelAdjust("original-value", b, m, w, 80);
    int[][][] matrixValue = {
            {{0, 0, 0}, {0, 114, 0}, {0, 213, 35}, {69, 69, 69}, {84, 84, 84}},
            {{228, 0, 186}, {234, 128, 205}, {240, 221, 221}, {153, 153, 153}, {228, 255, 228}},
            {{255, 0, 255}, {255, 141, 255}, {255, 228, 255}, {18, 35, 52}, {165, 0, 0}},
            {{191, 0, 255}, {191, 141, 61}, {255, 228, 255}, {0, 84, 0}, {0, 0, 114}},
            {{155, 20, 155}, {255, 110, 155}, {255, 200, 255}, {10, 200, 0}, {0, 240, 0}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testLevelAdjustSplitJPG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.jpg")), "org");

    int b = 20;
    int m = 100;
    int w = 255;
    ImageModelNew image = img.levelAdjust("original-value", b, m, w, 80);
    int[][][] matrixValue = {
            {{49, 0, 37}, {89, 21, 88}, {143, 163, 156}, {16, 98, 39}, {30, 115, 37}},
            {{156, 16, 144}, {218, 156, 215}, {218, 217, 226}, {121, 161, 135}, {236, 254, 238}},
            {{229, 64, 219}, {252, 171, 249}, {250, 226, 253}, {41, 34, 57}, {18, 35, 16}},
            {{215, 5, 188}, {228, 99, 211}, {250, 228, 248}, {46, 66, 49}, {0, 0, 0}},
            {{164, 27, 107}, {204, 115, 161}, {234, 240, 214}, {71, 149, 65}, {76, 203, 70}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }


  @Test
  public void testColorCorrectPNG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.png")), "org");

    ImageModelNew image = img.colorCorrect("original-value", 100);
    int[][][] matrixValue = {
            {{15, 0, 15}, {25, 60, 35}, {35, 150, 55}, {75, 30, 75}, {85, 40, 85}},
            {{215, 0, 165}, {225, 70, 185}, {235, 160, 205}, {135, 90, 135}, {215, 225, 215}},
            {{255, 0, 255}, {255, 80, 255}, {255, 170, 255}, {45, 10, 65}, {145, 0, 25}},
            {{170, 0, 255}, {170, 80, 70}, {255, 170, 255}, {25, 40, 15}, {15, 0, 105}},
            {{170, 0, 170}, {255, 80, 170}, {255, 170, 255}, {25, 170, 15}, {15, 210, 15}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testColorCorrectPPM() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.ppm")), "org");

    ImageModelNew image = img.colorCorrect("original-value", 100);
    int[][][] matrixValue = {
            {{15, 0, 15}, {25, 60, 35}, {35, 150, 55}, {75, 30, 75}, {85, 40, 85}},
            {{215, 0, 165}, {225, 70, 185}, {235, 160, 205}, {135, 90, 135}, {215, 225, 215}},
            {{255, 0, 255}, {255, 80, 255}, {255, 170, 255}, {45, 10, 65}, {145, 0, 25}},
            {{170, 0, 255}, {170, 80, 70}, {255, 170, 255}, {25, 40, 15}, {15, 0, 105}},
            {{170, 0, 170}, {255, 80, 170}, {255, 170, 255}, {25, 170, 15}, {15, 210, 15}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test
  public void testColorCorrectJPG() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.jpg")), "org");

    ImageModelNew image = img.colorCorrect("original-value", 100);
    int[][][] matrixValue = {
            {{0, 64, 81}, {0, 96, 112}, {6, 193, 163}, {0, 143, 82}, {0, 155, 81}},
            {{17, 93, 153}, {81, 187, 223}, {81, 249, 238}, {0, 191, 146}, {107, 255, 255}},
            {{96, 121, 228}, {141, 200, 255}, {136, 255, 255}, {0, 103, 93}, {0, 104, 69}},
            {{76, 87, 192}, {94, 144, 218}, {136, 255, 255}, {0, 122, 88}, {0, 81, 40}},
            {{58, 91, 147}, {98, 179, 201}, {128, 255, 254}, {0, 213, 105}, {0, 255, 110}}
    };
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(matrixValue[y][x][0], image.getPixelValue(y, x, 0));
        assertEquals(matrixValue[y][x][1], image.getPixelValue(y, x, 1));
        assertEquals(matrixValue[y][x][2], image.getPixelValue(y, x, 2));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalid() {
    ImageModelNew img = new ImageModelNewImpl(Objects.requireNonNull(checkImageType(
            "res/images/test-image-new.jpg")), "org");

    int b = 120;
    int m = 100;
    int w = 255;
    img.levelAdjust("original-value", b, m, w, 80);
    img.levelAdjust("original-value", 10, m, w, 120);
    img.levelAdjust("original-value", -10, m, w, 120);

    img.compress("original-value", -10.0);
    img.compress("original-value", 200.0);


  }


}
