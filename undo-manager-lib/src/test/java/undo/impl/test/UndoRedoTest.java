package undo.impl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class UndoRedoTest extends ABaseTest {

  @Test
  public void undoTest1() {

    assertThatThrownBy(manager::undo)
        .isInstanceOf(IllegalStateException.class);

    assertThat(manager.canUndo())
        .as("canUndo() should return false, due to none changes was committed")
        .isFalse();

    insertionChange(0, "ABC");
    manager.undo();

    assertThat(manager.canUndo())
        .as("canUndo() should return false, due to all UNDOs was performed")
        .isFalse();

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

    assertThat(undoPerformedCount)
        .as("Performed UNDOs count is not equals to max buffer size")
        .isEqualTo(BUFFER_SIZE);
  }

  @Test
  public void redoTest1() {
    assertThatThrownBy(manager::redo)
        .isInstanceOf(IllegalStateException.class);

    assertThat(manager.canRedo())
        .as("canRedo() should return false, due to there are no any UNDOs applied at the moment")
        .isFalse();

    insertionChange(0, "ABC");
    manager.undo();

    assertThat(manager.canRedo())
        .as("canRedo() should return true, due to as least one UNDO was just done")
        .isTrue();

    manager.redo();

    assertThat(manager.canRedo())
        .as("canRedo() should return false, due to all REDOs have been applied and no any REDOs available at the moment")
        .isFalse();
  }

  @Test
  public void redoTest2() {
    insertionChange(0, "ABC");
    manager.undo();
    insertionChange(0, "DEF");
    assertThat(manager.canRedo())
        .as("canRedo() should return false, due to new change has commited")
        .isFalse();
  }

}
