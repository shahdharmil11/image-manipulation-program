package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test file to test the ImageModelStoreImpl Class.
 */
public class ImageModelStoreImplTest {
  private ImageModelNew mock = new MockImage();
  private MockImageStore imgStore = new MockImageStore("mock", mock);

  @Test
  public void testGetImageForAlias() {
    imgStore.getBufferedImage("mock");
    assertEquals("getBufferedImage called.", imgStore.getLogger());
  }

  @Test
  public void testLoad() {
    imgStore.loadImage(mock, "mock");
    assertEquals("loadImage called.", imgStore.getLogger());
  }

  @Test
  public void testLoadHelper() {
    imgStore.loadHelper(new String[]{"load"});
    assertEquals("loadHelper called.", imgStore.getLogger());
  }

  @Test
  public void testSaveHelper() {
    imgStore.saveHelper(new String[]{"load"});
    assertEquals("saveHelper called.", imgStore.getLogger());
  }

  @Test
  public void testGetBufferedImage() {
    imgStore.getBufferedImage("load");
    assertEquals("getBufferedImage called.", imgStore.getLogger());
  }

}