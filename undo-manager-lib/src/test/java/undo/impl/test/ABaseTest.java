package undo.impl.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import undo.Change;
import undo.UndoManager;
import undo.UndoManagerFactory;
import undo.impl.test.fake.FakeChangeFactory;
import undo.impl.test.fake.FakeDocument;

public abstract class ABaseTest {

  static final int BUFFER_SIZE = 13;
  static UndoManagerFactory factory;

  @BeforeAll
  public static void init() {
    factory = UndoManagerFactory.getInstanceSPI();
  }

  FakeDocument doc;
  UndoManager manager;

  @BeforeEach
  public void prepare() {
    doc = new FakeDocument();
    manager = factory.createUndoManager(doc, BUFFER_SIZE);
  }

  public Change insertionChange(final int pos, final String s) {
    final Change c = FakeChangeFactory.insertion(pos, s);
    c.apply(doc);
    manager.registerChange(c);
    return c;
  }

  public Change deletionChange(final int pos, final String s) {
    final Change c = FakeChangeFactory.deletion(pos, s);
    c.apply(doc);
    manager.registerChange(c);
    return c;
  }
}
