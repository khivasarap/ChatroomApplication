package assignment6.message;

/**
 * The interface Message.
 */
public interface Message {

  /**
   * The constant DELIMIT.
   */
  public static String DELIMIT=" ";

  /**
   * To bytes byte [ ].
   *
   * @return the byte [ ]
   */
  public byte[] toBytes();

  /**
   * Concate string arr string.
   *
   * @param tokens the tokens
   * @param start  the start
   * @return the string
   */
  public static String concateStringArr(String[] tokens,int start){
    String res="";
    for(int i=start;i<tokens.length;i++){
      if(i!=tokens.length-1)res+=tokens[i]+" ";
      else res+=tokens[i];
    }
    return res;
  }

}
