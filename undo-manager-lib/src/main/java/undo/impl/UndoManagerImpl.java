package undo.impl;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReentrantLock;

import undo.Change;
import undo.Document;
import undo.UndoManager;

public class UndoManagerImpl implements UndoManager {

  private final ReentrantLock lock = new ReentrantLock();

  final Document doc;
  final int bufferSize;

  private final Deque<Change> undos = new ConcurrentLinkedDeque<>();
  private final Deque<Change> redos = new ConcurrentLinkedDeque<>();

  public UndoManagerImpl(final Document doc, final int bufferSize) {
    this.doc = doc;
    this.bufferSize = bufferSize;
  }

  @Override
  public void registerChange(final Change change) {
    lock.lock();
    try {
      undos.addFirst(change);
      while (undos.size() > bufferSize) {
        undos.removeLast();
      }
      redos.clear(); // REDOs stack should be cleared after new change has committed
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean canUndo() {
    return !undos.isEmpty();
  }

  @Override
  public void undo() {
    lock.lock();
    try {
      if (!canUndo()) {
        throw new IllegalStateException("Undo can't be done at the moment");
      }
      final Change c = undos.getFirst();
      c.revert(doc);
      redos.addFirst(c);
      undos.removeFirst();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean canRedo() {
    return !redos.isEmpty();
  }

  @Override
  public void redo() {
    lock.lock();
    try {
      if (!canRedo()) {
        throw new IllegalStateException("Redo can't be done at the moment");
      }
      final Change c = redos.getFirst();
      c.apply(doc);
      undos.addFirst(c);
      redos.removeFirst();
    } finally {
      lock.unlock();
    }
  }
}
