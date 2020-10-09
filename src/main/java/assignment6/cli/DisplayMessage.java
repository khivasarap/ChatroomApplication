package assignment6.cli;

import java.util.Objects;

/**
 * The type Display message.
 */
public class DisplayMessage {

  /**
   * The Type.
   */
  String type;
  /**
   * The Sender.
   */
  String sender;
  /**
   * The Content.
   */
  String content;

  /**
   * Instantiates a new Display message.
   *
   * @param type    the type
   * @param sender  the sender
   * @param content the content
   */
  public DisplayMessage(String type, String sender, String content) {
    this.type = type;
    this.sender = sender;
    this.content = content;
  }

  @Override
  public String toString() {
    return " ("+type+") "+sender+": "+content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DisplayMessage)) {
      return false;
    }
    DisplayMessage that = (DisplayMessage) o;
    return Objects.equals(type, that.type) &&
        Objects.equals(sender, that.sender) &&
        Objects.equals(content, that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, sender, content);
  }
}
