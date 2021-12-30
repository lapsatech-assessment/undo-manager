package undo.impl.test;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OverallTest extends ABaseTest {

  @Test
  public void processTest() {

    insertionChange(0, "A");

    assertThat(doc.getRaw()).isEqualTo("A");

    assertThat(manager.canUndo()).isTrue();
    assertThat(manager.canRedo()).isFalse();

    insertionChange(0, "B");
    assertThat(doc.getRaw()).isEqualTo("BA");

    assertThat(manager.canUndo()).isTrue();
    assertThat(manager.canRedo()).isFalse();

    manager.undo();
    assertThat(doc.getRaw()).isEqualTo("A");

    assertThat(manager.canUndo()).isTrue();
    assertThat(manager.canRedo()).isTrue();

    insertionChange(1, "Another string");
    assertThat(doc.getRaw()).isEqualTo("AAnother string");

    assertThat(manager.canUndo()).isTrue();
    assertThat(manager.canRedo()).isFalse();

    manager.undo();
    assertThat(doc.getRaw()).isEqualTo("A");

    manager.redo();
    assertThat(doc.getRaw()).isEqualTo("AAnother string");

    insertionChange(0, "C");
    assertThat(doc.getRaw()).isEqualTo("CAAnother string");

    deletionChange(2, "Another string");
    assertThat(doc.getRaw()).isEqualTo("CA");

    manager.undo();
    assertThat(doc.getRaw()).isEqualTo("CAAnother string");

    manager.undo();
    assertThat(doc.getRaw()).isEqualTo("AAnother string");

    manager.redo();
    assertThat(doc.getRaw()).isEqualTo("CAAnother string");

    manager.undo();
    assertThat(doc.getRaw()).isEqualTo("AAnother string");

    manager.undo();
    assertThat(doc.getRaw()).isEqualTo("A");

    manager.undo();
    assertThat(doc.getRaw()).isEmpty();
    ;
  }
}
