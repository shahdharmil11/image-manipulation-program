package controller;

import org.junit.Test;

import java.io.IOException;

import model.MockImage;
import model.MockImageStore;

import static org.junit.Assert.assertEquals;

/**
 * Test Class to test ImageControllerGUIImpl class.
 */
public class ImageControllerGUIImplTest {

  private MockImage mock = new MockImage();
  private MockImageStore modelStore = new MockImageStore("curr", mock);


  @Test
  public void testLoad() throws IOException {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.loadImage("Assignment6/res/images/image-small.png");
    assertEquals("", mock.getLogger());
  }

  @Test
  public void testSave() throws IOException {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.saveImage("Assignment6/res/images/image-small-save1.png");
    assertEquals("", mock.getLogger());
  }

  @Test
  public void testHistogram() throws IOException {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.generateHistogram();
    assertEquals("histogram called.", mock.getLogger());
  }

  @Test
  public void testSepia() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeSepiaOperation(100, false);
    assertEquals("sepia called.", mock.getLogger());
  }

  @Test
  public void testRed() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeRedComponent();
    assertEquals("red-component called.", mock.getLogger());
  }

  @Test
  public void testGreen() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeGreenComponent();
    assertEquals("green-component called.", mock.getLogger());
  }

  @Test
  public void testBlue() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeBlueComponent();
    assertEquals("blue-component called.", mock.getLogger());
  }

  @Test
  public void testHorizontal() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeHorizontalFlip();
    assertEquals("horizontal-flip called.", mock.getLogger());
  }

  @Test
  public void testVertical() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeVerticalFlip();
    assertEquals("vertical-flip called.", mock.getLogger());
  }

  @Test
  public void testBlur() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeBlurOperation(20, true);
    assertEquals("blur called.", mock.getLogger());
  }

  @Test
  public void testSharpen() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeSharpenOperation(20, true);
    assertEquals("sharpen called.", mock.getLogger());
  }

  @Test
  public void testGrayscale() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeGreyscaleOperation(20, true);
    assertEquals("luma called.", mock.getLogger());
  }

  @Test
  public void testCompress() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeCompressionOperation("20");
    assertEquals("compress called.", mock.getLogger());
  }

  @Test
  public void testBright() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeBrightenOperation("20");
    assertEquals("brighten called.", mock.getLogger());
  }

  @Test
  public void testDark() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeDarkenOperation("20");
    assertEquals("brighten called.", mock.getLogger());
  }

  @Test
  public void testColorCorrect() throws IOException {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeColorCorrectOperation(20, false);
    assertEquals("color-correct called.", mock.getLogger());
  }

  @Test
  public void testLevelAdjust() {
    Features controller = new ImageControllerGUIImpl(modelStore);
    controller.executeLevelsAdjustOperation("20", "100", "255", 20, false);
    assertEquals("levels-adjust called.", mock.getLogger());
  }


}