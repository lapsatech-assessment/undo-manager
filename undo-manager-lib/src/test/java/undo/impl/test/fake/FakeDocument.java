package undo.impl.test.fake;

import undo.Document;

public class FakeDocument implements Document {

  private final StringBuilder sb = new StringBuilder();
  private int cpos = 0;

  @Override
  public String toString() {
    return sb.substring(0, cpos) + "." + sb.substring(cpos);
  }

  public String getRaw() {
    return sb.toString();
  }

  /**
   * Deletes a string from the document.
   *
   * @param pos The position to start deletion.
   * @param s   The string to delete.
   * @throws IllegalStateException If the document doesn't have <code>s</code> as
   *                               <code>pos</code>.
   */
  @Override
  public void delete(final int pos, final String s) {
    setDot(pos);
    if (!sb.substring(cpos, cpos + s.length()).equals(s))
      throw new IllegalStateException("No such string on the position");
    sb.delete(cpos, cpos + s.length());
  }

  /**
   * Inserts a string into the document.
   *
   * @param pos The position to insert the string at.
   * @param s   The string to insert.
   * @throws IllegalStateException If <code>pos</code> is an illegal position
   *                               (that is, if document is shorter than that).
   */
  @Override
  public void insert(final int pos, final String s) {
    setDot(pos);
    sb.insert(cpos, s);
    cpos += s.length();
  }

  /**
   * Sets the dot (cursor) position of the document.
   *
   * @param pos The dot position to set.
   * @throws IllegalStateException If <code>pos</code> is an illegal position
   *                               (that is, if document is shorter than that).
   */
  @Override
  public void setDot(final int pos) {
    if (pos < 0 || pos > sb.length()) // The spec doesn't ask to throw
      // IllegalStateException on lower than
      // zero pos, but I still decided to do
      // it
      throw new IllegalStateException("Illegal position number");
    cpos = pos;
  }

  public static void main(final String[] args) {
    final FakeDocument d = new FakeDocument();
    System.out.println(d);

    d.insert(0, "ABC");
    System.out.println(d);
    d.insert(1, "def");
    System.out.println(d);
    d.delete(0, "Adef");
    System.out.println(d);
    d.setDot(0);
    System.out.println(d);
    d.setDot(1);
    System.out.println(d);
    d.setDot(2);
    System.out.println(d);
    d.insert(2, "QQQQ");
    System.out.println(d);
  }
}