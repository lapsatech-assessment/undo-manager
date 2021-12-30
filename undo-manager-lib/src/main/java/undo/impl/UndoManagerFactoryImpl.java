package undo.impl;

import undo.Document;
import undo.UndoManager;
import undo.UndoManagerFactory;

public class UndoManagerFactoryImpl implements UndoManagerFactory {

  @Override
  public UndoManager createUndoManager(final Document doc, final int bufferSize) {
    return new UndoManagerImpl(doc, bufferSize);
  }
}
