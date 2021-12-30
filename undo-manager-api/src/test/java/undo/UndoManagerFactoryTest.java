package undo;

import static org.mockito.Mockito.mock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UndoManagerFactoryTest implements UndoManagerFactory {

  @Override
  public UndoManager createUndoManager(Document doc, int bufferSize) {
    return mock(UndoManager.class);
  }

  @Test
  public void testSpiMechanism() {
    Assertions.assertThat(UndoManagerFactory.getInstanceSPI())
        .isNotNull();
  }

}
