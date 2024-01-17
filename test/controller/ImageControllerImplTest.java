
package controller;

import org.junit.Test;

import java.io.File;

import model.ImageModelNew;
import model.ImageModelStore;
import model.ImageModelStoreImpl;
import model.MockImage;
import view.ImageView;
import view.ImageViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test Class for Controller of the Program.
 */

public class ImageControllerImplTest {

  private ImageView view = new ImageViewImpl();
  private MockImage mock = new MockImage();
  private ImageModelStore modelStore = new ImageModelStoreImpl("mock", mock);

  /**
   * Test Brighten Image command.
   */
  @Test
  public void testBrighten() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    controller.execute("brighten 10 mock original-bright");
    assertEquals("brighten called.", mock.getLogger());
  }


  /**
   * Test Darken Image command.
   */

  @Test
  public void testDark() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    controller.execute("brighten -10 mock original-bright");
    assertEquals("brighten called.", mock.getLogger());

  }


  /**
   * Test Horizontal Flip.
   */

  @Test
  public void testHorizontal() {
    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("horizontal-flip mock original-horizontal");
    assertEquals("horizontal-flip called.", mock.getLogger());

  }


  /**
   * Test Vertical Flip.
   */

  @Test
  public void testVertical() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("vertical-flip mock original-horizontal");
    assertEquals("vertical-flip called.", mock.getLogger());

  }


  /**
   * Test Vertical-Horizontal Flip.
   */

  @Test
  public void testVerticalHorizontal() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    String line = "load res/images/image-small.png original";
    controller.execute(line);
    controller.execute("vertical-flip original original-vertical");
    controller.execute("horizontal-flip original-vertical original-vertical-horizontal");
    ImageModelNew image = modelStore.getImageForAlias(
            "original-vertical-horizontal");
    int[][][] matrix = {
            {{255, 200, 255}, {255, 110, 255}, {255, 20, 255}},
            {{220, 190, 190}, {210, 100, 170}, {200, 10, 150}},
            {{20, 180, 40}, {10, 90, 20}, {0, 0, 0}}
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
   * Test Horizontal-Vertical Flip.
   */

  @Test
  public void testHorizontalVertical() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    String line = "load res/images/image-small.png original";
    controller.execute(line);
    controller.execute("horizontal-flip original original-horizontal");
    controller.execute("vertical-flip original-horizontal original-horizontal-vertical");
    ImageModelNew image = modelStore.getImageForAlias(
            "original-horizontal-vertical");
    int[][][] matrix = {
            {{255, 200, 255}, {255, 110, 255}, {255, 20, 255}},
            {{220, 190, 190}, {210, 100, 170}, {200, 10, 150}},
            {{20, 180, 40}, {10, 90, 20}, {0, 0, 0}}
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
   * Tests if image is saved successfully.
   */

  @Test
  public void testSave() {

    ImageController controller = new ImageControllerImpl(view, modelStore);
    controller.execute("load res/galaxy.png mock");
    controller.execute("save res/galaxy-save.png mock");
    File f = new File("res/galaxy-save.png");
    assertEquals(true, f.exists());
  }


  /**
   * Tests if image is RGB Split successfully.
   */

  @Test
  public void testRGBSplit() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("rgb-split mock mock-red mock-g mock-b");
    assertEquals("red-component called.green-component called.blue-component called.",
            mock.getLogger());
  }


  /**
   * Tests if image is RGB Combine successfully.
   */

  @Test
  public void testRGBCombine() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("rgb-combine mock-combine mock mock-g mock-b");
    assertEquals("rgb-combine called.", mock.getLogger());
  }


  /**
   * Tests if image is blurred successfully.
   */

  @Test
  public void testBlur() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("blur mock mock-blur");
    assertEquals("blur called.", mock.getLogger());
  }


  /**
   * Tests if image is sharpened successfully.
   */

  @Test
  public void testSharpen() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("sharpen mock mock-blur");
    assertEquals("sharpen called.", mock.getLogger());
  }


  /**
   * Tests if image is viewed as Luma successfully.
   */

  @Test
  public void testLuma() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("luma-component mock mock-blur");
    assertEquals("luma called.", mock.getLogger());
  }


  /**
   * Tests if image is converted to Sepia successfully.
   */

  @Test
  public void testSepia() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("sepia mock mock-blur");
    assertEquals("sepia called.", mock.getLogger());
  }


  /**
   * Tests if image is converted to Red Component successfully.
   */

  @Test
  public void testRed() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("red-component mock original-red");
    assertEquals("red-component called.", mock.getLogger());
  }


  /**
   * Tests if image is converted to Green Component successfully.
   */

  @Test
  public void testGreen() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("green-component mock original-red");
    assertEquals("green-component called.", mock.getLogger());
  }


  /**
   * Tests if image is converted to Blue Component successfully.
   */

  @Test
  public void testBlue() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("blue-component mock original-red");
    assertEquals("blue-component called.", mock.getLogger());
  }


  /**
   * Tests if image is converted to Intensity successfully.
   */

  @Test
  public void testIntensity() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("intensity-component mock original-red");
    assertEquals("intensity-component called.", mock.getLogger());
  }


  /**
   * Tests if image is converted to Value successfully.
   */

  @Test
  public void testValue() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("value-component mock original-red");
    assertEquals("value-component called.", mock.getLogger());
  }


  /**
   * Tests if run command works successfully.
   */

  @Test
  public void testRunScript() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    String line = "run Assignment6/input/inputfile.txt";
    controller.execute(line);

    File f = new File("res/images/image-blur-script.ppm");
    assertEquals(true, f.exists());
  }


  /**
   * Test to Run Multiple Commands in different Sequences.
   */

  @Test
  public void testAlreadyLoadedMultiple() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    String line = "load res/images/image-small.png original";
    controller.execute(line);
    controller.execute("luma-component original lumaorg");
    controller.execute("red-component lumaorg lumaredorg");
    controller.execute("sharpen original lumaredsharporg");
    assertNotNull(modelStore.getImageForAlias("lumaredsharporg"));
    assertNotNull(modelStore.getImageForAlias("lumaorg"));
    assertNotNull(modelStore.getImageForAlias("lumaredorg"));
    assertNotNull(modelStore.getImageForAlias("original"));
  }


  /**
   * Test to Run Multiple Commands in different Sequences.
   */

  @Test
  public void testAlreadyLoadedMultiple2() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    String line = "load res/images/image-small.png original";
    controller.execute(line);
    controller.execute("brighten 10 original brightorg");
    controller.execute("horizontal-flip original horiorg");
    controller.execute("intensity-component brightorg intensityorg");
    assertNotNull(modelStore.getImageForAlias("intensityorg"));
    assertNotNull(modelStore.getImageForAlias("horiorg"));
    assertNotNull(modelStore.getImageForAlias("brightorg"));
    assertNotNull(modelStore.getImageForAlias("original"));
  }


  /**
   * Test to Run Multiple Commands in different Sequences.
   */

  @Test
  public void testAlreadyLoadedMultiple3() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    String line = "load res/images/image-small.png original";
    controller.execute(line);
    controller.execute("red-component original redorg");
    controller.execute("luma-component redorg greyorg");
    assertNotNull(modelStore.getImageForAlias("redorg"));
    assertNotNull(modelStore.getImageForAlias("greyorg"));
    assertNotNull(modelStore.getImageForAlias("original"));
  }


  /**
   * Test to Run Multiple Commands in different Sequences.
   */

  @Test
  public void testAlreadyLoadedMultiple4() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    String line = "load res/images/image-small.png original";
    controller.execute(line);
    controller.execute("blur original blurorg");
    controller.execute("blur blurorg borg");
    assertNotNull(modelStore.getImageForAlias("blurorg"));
    assertNotNull(modelStore.getImageForAlias("borg"));
    assertNotNull(modelStore.getImageForAlias("original"));
  }


  /**
   * Test to Run Multiple Commands in different Sequences.
   */

  @Test
  public void testAlreadyLoadedMultiple5() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    String line = "load res/images/image-small.png original";
    controller.execute(line);
    controller.execute("sharpen original sharorg");
    controller.execute("sharpen sharorg sorg");
    assertNotNull(modelStore.getImageForAlias("sharorg"));
    assertNotNull(modelStore.getImageForAlias("sorg"));
    assertNotNull(modelStore.getImageForAlias("original"));
  }


  /**
   * Test to Run Multiple Commands in different Sequences.
   */

  @Test
  public void testLoadedMultiple() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    String line = "load res/images/image-small.png original";
    String line2 = "load res/galaxy.png original2";
    controller.execute(line);
    controller.execute(line2);
    controller.execute("sharpen original sharporg");
    controller.execute("luma-component original2 lumaorg2");
    controller.execute("blur sharporg blurorg");
    controller.execute("brighten -30 lumaorg2 darkorg2");
    assertNotNull(modelStore.getImageForAlias("darkorg2"));
    assertNotNull(modelStore.getImageForAlias("blurorg"));
    assertNotNull(modelStore.getImageForAlias("lumaorg2"));
    assertNotNull(modelStore.getImageForAlias("sharporg"));
    assertNotNull(modelStore.getImageForAlias("original"));
    assertNotNull(modelStore.getImageForAlias("original2"));
  }

  @Test
  public void testOperationArgumentLoad() {
    ImageController controller = new ImageControllerImpl(view, modelStore);
    ImageView view = controller.getView();

    String line = "load res/images/image-small.png original abcd";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "brighten 10 original new abcd";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "horizontal-flip original final final2";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "vertical-flip original final final2";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "save res/images/image-small.png";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "red-component original";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "green-component original final final2";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "blue-component original";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "red-split original red green blue original";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "red-combine original red green blue original";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "blur original red green";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "sharpen original";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "luma-component original";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "sepia original";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "intensity-component original final2 final3";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "value-component original";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "run";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());


    line = "compress 50 org";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "levels-adjust 20 100 temp";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "histogram dest_name";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

    line = "color-correct";
    controller.execute(line);
    assertEquals("Invalid Operation -> " + line, view.getMessageToView());

  }

  @Test
  public void testCompress() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("compress 90 mock original");
    assertEquals("compress called.", mock.getLogger());
  }

  @Test
  public void testLevelsAdjust() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("levels-adjust 20 100 255 mock original");
    assertEquals("levels-adjust called.", mock.getLogger());
  }

  @Test
  public void testHistogram() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("histogram mock original");
    assertEquals("histogram called.", mock.getLogger());
  }

  @Test
  public void testColorCorrect() {

    ImageController controller = new ImageControllerImpl(view, modelStore);

    controller.execute("color-correct mock original");
    assertEquals("color-correct called.", mock.getLogger());
  }


}
