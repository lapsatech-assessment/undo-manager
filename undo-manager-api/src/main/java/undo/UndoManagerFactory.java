package undo;

import static java.lang.String.format;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * A factory for {@link UndoManager}s.
 *
 */
public interface UndoManagerFactory {

  /**
   * Creates an undo manager for a {@link Document}.
   * 
   * @param doc        The document to create the {@link UndoManager} for.
   * @param bufferSize The number of {@link Change}es stored.
   * @return The {@link UndoManager} created.
   */
  UndoManager createUndoManager(Document doc, int bufferSize);

  /**
   * Create and return an instance of default {@link UndoManagerFactory}
   * implementation. An {@linkplain UndoManagerFactory} implementation providers
   * should register themselves via SPI mechanism.
   * 
   * @throws IllegalStateException If there is no any factory provider registered
   * @return Default implementation of {@link UndoManagerFactory}
   */
  public static UndoManagerFactory getInstanceSPI() {
    final Iterator<UndoManagerFactory> i = ServiceLoader.load(UndoManagerFactory.class)
        .iterator();
    if (i.hasNext()) {
      return i.next();
    }
    throw new IllegalStateException(
        format("There is no any %1$s providers registered", UndoManagerFactory.class.getName()));
  }
}
