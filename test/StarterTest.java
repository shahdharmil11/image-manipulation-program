import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.fail;

/**
 * Test Class to test Starter Class.
 * It confirms that program starts from this class.
 */
public class StarterTest {
  /**
   * Test to ensure Starter Class where program starts works as expected.
   * It means this ensures all commands are run correctly even when input is given by just file.
   *
   * @throws IOException when File cannot be written.
   */
  @Test
  public void testStarter() throws IOException {
    InputStream in = null;

    try {
      in = new FileInputStream("Assignment6/res/inputfile.txt");
    } catch (FileNotFoundException e) {
      fail("FILE NOT FOUND!");
    }
    Starter start = new Starter(in);
    start.goController("0");
  }

}