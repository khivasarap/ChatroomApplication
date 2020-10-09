package assignment6.cli;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CliTest {
  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream oriout = System.out;
  @Before
  public void setUp() throws Exception {
    System.setOut(new PrintStream(outputStream));
  }

  @After
  public void tearDown() throws Exception {
    System.setOut(oriout);
  }

  @Test
  public void parseUserInput() {
    assertEquals("19 3 tao", Cli.parseUserInput("tao", "login"));
  }

  @Test
  public void parseResponse() {
    String rawInput="25 3 tao 5 pooja 5 hello";
    assertEquals(" (@user) tao->pooja: hello",Cli.parseResponse(rawInput));
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void displayCommandLineOptions() {
    Cli.displayCommandLineOptions();
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void printWelcome() {
    Cli.printWelcome("test");
  }

  @Test
  public void concateStringArr() {
    String[]strArr=new String[]{"a","b","c"};
    assertEquals(Cli.concateStringArr(strArr,1),"b c");
  }
}
