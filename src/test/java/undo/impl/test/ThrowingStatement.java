package undo.impl.test;

import static org.junit.Assert.*;

import java.util.Objects;

@FunctionalInterface
public interface ThrowingStatement<X extends Exception> {

    void process() throws X;

    public static <X extends Exception> void expectException(final ThrowingStatement<X> statement,
	    final Class<X> exceptionClazz) {
	expectException("Exception expectation assertion error", statement, exceptionClazz);
    }

    public static <X extends Exception> void expectException(final String message,
	    final ThrowingStatement<X> statement,
	    final Class<X> exceptionClazz) {
	Objects.requireNonNull(statement);
	Objects.requireNonNull(exceptionClazz);
	try {
	    statement.process();
	    fail(String.format("%1$s Expected %2$s but no exception has throw",
		    message,
		    exceptionClazz.getName()));
	} catch (final Exception e) {
	    if (!exceptionClazz.isAssignableFrom(e.getClass()))
		fail(String.format("%1$s Expected %2$s but was %3$s",
			message,
			exceptionClazz.getName(),
			e.getClass().getName()));
	}
    }
}