package undo.impl.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import undo.UndoManagerFactory;
import undo.impl.UndoManagerFactoryImpl;

public class UndoManagerFactoryTest {

    @Test
    public void getInstanceCDITest() {
	final UndoManagerFactory factory = UndoManagerFactory.getInstanceSPI();
	assertThat(factory, allOf(not(nullValue()), instanceOf(UndoManagerFactoryImpl.class)));
    }
}
