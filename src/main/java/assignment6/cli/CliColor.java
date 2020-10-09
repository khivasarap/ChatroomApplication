package assignment6.cli;

import com.googlecode.lanterna.TextColor;
import java.awt.Color;

/**
 * The enum Cli color.
 */
public enum CliColor implements TextColor {
  /**
   * Sky blue cli color.
   */
  SKY_BLUE(178,235,242),
  /**
   * Orange red cli color.
   */
  ORANGE_RED(255,87,33),
  /**
   * Pink cli color.
   */
  PINK(229,133,253),
  /**
   * Grey cli color.
   */
  GREY(233,243,249);
  private final Color color;

  CliColor(int red, int green, int blue) {
    this.color = new Color(red, green, blue);
  }

  @Override
  public byte[] getForegroundSGRSequence() {
    return new byte[0];
  }

  @Override
  public byte[] getBackgroundSGRSequence() {
    return new byte[0];
  }

  @Override
  public Color toColor() {
    return this.color;
  }

  @Override
  public String toString() {
    return "CliColor{" +
        "color=" + color +
        '}';
  }
}
