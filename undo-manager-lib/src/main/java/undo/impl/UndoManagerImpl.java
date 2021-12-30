package undo.impl;

import java.util.Deque;
import java.util.LinkedList;

import undo.Change;
import undo.Document;
import undo.UndoManager;

class UndoManagerImpl implements UndoManager {

  private final Document doc;

  private final Deque<Change> undos;
  private final Deque<Change> redos;

  UndoManagerImpl(final Document doc, final int bufferSize) {
    this.doc = doc;
    this.undos = new EvictingLinkedList<>(bufferSize);
    this.redos = new LinkedList<>();
  }

  @Override
  public void registerChange(final Change change) {
    undos.push(change);
    if (!redos.isEmpty())
      redos.clear(); // REDOs stack should be cleared after new change has
    // committed

  }

  @Override
  public boolean canUndo() {
    return !undos.isEmpty();
  }

  @Override
  public void undo() {
    if (!canUndo())
      throw new IllegalStateException("Undo can't be done at the moment");
    final Change c = undos.pop();
    try {
      c.revert(doc);
      redos.push(c);
    } catch (final IllegalStateException e) {
      undos.push(c); // put the change back to the UNDOs stack if applying
      // have failed
      throw e;
    }
  }

  @Override
  public boolean canRedo() {
    return !redos.isEmpty();
  }

  @Override
  public void redo() {
    if (!canRedo())
      throw new IllegalStateException("Redo can't be done at the moment");
    final Change c = redos.pop();
    try {
      c.apply(doc);
      undos.push(c);
    } catch (final IllegalStateException e) {
      redos.push(c); // put the change back to the REDOs stack if applying
      // have failed
      throw e;
    }
  }
}
