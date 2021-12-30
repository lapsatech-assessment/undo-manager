package undo.impl

import spock.lang.Specification
import undo.Change
import undo.Document

class UndoManagerImplTest extends Specification {

  def 'test general operations'() {

    given:
    def doc = Stub(Document)
    def buffSize = 10
    def manager = new UndoManagerImpl(doc, buffSize)

    def c = Mock(Change)

    when:
    manager.registerChange(c)

    then:
    manager.canUndo()
    !manager.canRedo()

    when:
    manager.undo()

    then:
    1 * c.revert(doc)
    !manager.canUndo()
    manager.canRedo()

    when:
    manager.redo()

    then:
    1 * c.apply(doc)
    manager.canUndo()
    !manager.canRedo()
  }

  def 'test undo is rolled'() {
    given:
    def doc = Stub(Document)
    def buffSize = 2
    def manager = new UndoManagerImpl(doc, buffSize)
    def c1 = Mock(Change)
    def c2 = Mock(Change)
    def c3 = Mock(Change)

    manager.registerChange(c1)
    manager.registerChange(c2)
    manager.registerChange(c3)

    when:
    manager.undo()
    manager.undo()

    then:
    0 * c1.revert(doc)
    1 * c2.revert(doc)
    1 * c3.revert(doc)

    when:
    manager.undo()

    then:
    thrown(IllegalStateException)

    and:
    0 * c1.revert(doc)
    0 * c2.revert(doc)
    0 * c3.revert(doc)
  }

  def 'test failed apply have not affected the manager\'s state'() {
    given:
    def doc = Stub(Document)
    def buffSize = 2
    def manager = new UndoManagerImpl(doc, buffSize)
    def c = Mock(Change)
    //     {
    //      apply(doc) >>> {throw new IllegalStateException('Can\'t apply')}
    //      revert(doc) >>> {throw new IllegalStateException('Can\'t revert')}
    //    }

    when:
    manager.registerChange(c)

    then:
    manager.canUndo() // undo is available
    !manager.canRedo() // and redo is not

    when:
    manager.undo()

    then:
    1 * c.revert(doc) >> {throw new IllegalStateException('Can\'t revert')}

    and:
    def e = thrown(IllegalStateException)
    e.message == 'Can\'t revert'

    and:
    manager.canUndo() // undo still available
    !manager.canRedo() // and redo still not
  }
}
