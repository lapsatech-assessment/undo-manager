package undo.impl.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static undo.impl.test.ThrowingStatement.*;

import org.junit.Test;

public class UndoRedoTest extends ABaseTest {

    @Test
    public void undoTest1() {
	expectException(manager::undo, IllegalStateException.class);

	assertFalse("canUndo() should return false, due to none changes was committed", manager.canUndo());

	insertionChange(0, "ABC");
	manager.undo();
	assertFalse("canUndo() should return false, due to all UNDOs was performed", manager.canUndo());
    }

    @Test
    public void undoBufferSizeTest1() {
	// let there will be a number of changes larger
	// than the buffer size by 10

	for (int i = 0; i < BUFFER_SIZE + 10; i++)
	    insertionChange(0, "ABC");

	int undoPerformedCount = 0;
	while (manager.canUndo()) {
	    manager.undo();
	    undoPerformedCount++;
	}

	assertThat("Performed UNDOs count is not equals to max buffer size", undoPerformedCount, equalTo(BUFFER_SIZE));
    }

    @Test
    public void redoTest1() {
	expectException(manager::redo, IllegalStateException.class);

	assertFalse("canRedo() should return false, due to there are no any UNDOs applied at the moment",
		manager.canRedo());

	insertionChange(0, "ABC");
	manager.undo();

	assertTrue("canRedo() should return true, due to as least one UNDO was just done", manager.canRedo());

	manager.redo();

	assertFalse(
		"canRedo() should return false, due to all REDOs have been applied and no any REDOs available at the moment",
		manager.canRedo());
    }

    @Test
    public void redoTest2() {
	insertionChange(0, "ABC");
	manager.undo();
	insertionChange(0, "DEF");
	assertFalse("canRedo() should return false, due to new change has commited", manager.canRedo());
    }

}
