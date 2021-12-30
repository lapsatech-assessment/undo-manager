package undo.impl.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import undo.UndoManagerFactory;
import undo.impl.UndoManagerFactoryImpl;

public class UndoManagerFactoryTest {

  @Test
  public void getInstanceCDITest() {
    final UndoManagerFactory factory = UndoManagerFactory.getInstanceSPI();
    assertThat(factory).isNotNull()
        .isInstanceOf(UndoManagerFactoryImpl.class);
  }
}
