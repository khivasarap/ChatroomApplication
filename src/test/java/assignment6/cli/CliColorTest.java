package assignment6.cli;

import static org.junit.Assert.*;

import com.googlecode.lanterna.TextColor;
import org.junit.Test;

public class CliColorTest {


  @Test
  public void toColor() {
    assertEquals(CliColor.GREY.toColor(),new TextColor.RGB(233,243,249).toColor());
  }
}
