package undo;

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
     * @param doc
     *            The document to create the {@link UndoManager} for.
     * @param bufferSize
     *            The number of {@link Change}es stored.
     * @return The {@link UndoManager} created.
     */
    UndoManager createUndoManager(Document doc, int bufferSize);

    /**
     * Create and return an instance of default {@link UndoManagerFactory}
     * implementation. An {@linkplain UndoManagerFactory} implementation providers should register themselves via
     * SPI mechanism.
     * 
     * @throws IllegalStateException
     *             If there is no any factory provider registered
     * @return Default implementation of {@link UndoManagerFactory}
     */
    public static UndoManagerFactory getInstanceSPI() {
	final Iterator<UndoManagerFactory> providersIterator = ServiceLoader.load(UndoManagerFactory.class).iterator();
	if (providersIterator.hasNext())
	    return providersIterator.next();
	throw new IllegalStateException(
		String.format("There is no any %1$s providers registered", UndoManagerFactory.class.getName()));
    }
}
