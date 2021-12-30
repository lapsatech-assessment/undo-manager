package undo.impl;

import java.util.Collection;
import java.util.LinkedList;

public class EvictingLinkedList<E> extends LinkedList<E> {

  private final int capacity;

  public EvictingLinkedList(final int capacity) {
    this.capacity = capacity;
  }

  @Override
  public boolean add(final E e) {
    final boolean res = super.add(e);
    truncateFirst();
    return res;
  }

  @Override
  public boolean addAll(final int index, final Collection<? extends E> c) {
    final boolean res = super.addAll(index, c);
    truncateLast();
    return res;
  }

  @Override
  public void addLast(final E e) {
    super.addLast(e);
    truncateFirst();
  }

  @Override
  public void addFirst(final E e) {
    super.addFirst(e);
    truncateLast();
  }

  private void truncateFirst() {
    while (size() > capacity)
      removeFirst();
  }

  private void truncateLast() {
    while (size() > capacity)
      removeLast();
  }
}