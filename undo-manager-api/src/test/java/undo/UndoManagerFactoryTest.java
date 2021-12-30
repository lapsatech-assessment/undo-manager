package undo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UndoManagerFactoryTest implements UndoManagerFactory {

  @Override
  public UndoManager createUndoManager(Document doc, int bufferSize) {
    return null;
  }

  @Test
  public void testSpiMechanism() {
    Assertions.assertThat(UndoManagerFactory.getInstanceSPI())
        .isNotNull()
        .isInstanceOf(UndoManagerFactoryTest.class);
  }

}
