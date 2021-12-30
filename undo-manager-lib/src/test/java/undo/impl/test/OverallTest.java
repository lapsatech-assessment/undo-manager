package undo.impl.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class OverallTest extends ABaseTest {

    @Test
    public void processTest() {

	insertionChange(0, "A");
	assertThat(doc.getRaw(), equalTo("A"));

	assertTrue(manager.canUndo());
	assertFalse(manager.canRedo());

	insertionChange(0, "B");
	assertThat(doc.getRaw(), equalTo("BA"));

	assertTrue(manager.canUndo());
	assertFalse(manager.canRedo());

	manager.undo();
	assertThat(doc.getRaw(), equalTo("A"));

	assertTrue(manager.canUndo());
	assertTrue(manager.canRedo());

	insertionChange(1, "Another string");
	assertThat(doc.getRaw(), equalTo("AAnother string"));

	assertTrue(manager.canUndo());
	assertFalse(manager.canRedo());

	manager.undo();
	assertThat(doc.getRaw(), equalTo("A"));

	manager.redo();
	assertThat(doc.getRaw(), equalTo("AAnother string"));

	insertionChange(0, "C");
	assertThat(doc.getRaw(), equalTo("CAAnother string"));

	deletionChange(2, "Another string");
	assertThat(doc.getRaw(), equalTo("CA"));

	manager.undo();
	assertThat(doc.getRaw(), equalTo("CAAnother string"));

	manager.undo();
	assertThat(doc.getRaw(), equalTo("AAnother string"));

	manager.redo();
	assertThat(doc.getRaw(), equalTo("CAAnother string"));

	manager.undo();
	assertThat(doc.getRaw(), equalTo("AAnother string"));

	manager.undo();
	assertThat(doc.getRaw(), equalTo("A"));

	manager.undo();
	assertThat(doc.getRaw(), is(""));
    }
}
