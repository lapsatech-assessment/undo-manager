package undo.impl

import spock.lang.Specification
import undo.Document
import undo.UndoManagerFactory

class UndoManagerFactoryImplTest extends Specification {

  def 'test that spi factory creates correct undo manager factory instance'() {
    when:
    def managerFactory = UndoManagerFactory.getInstanceSPI()

    then:
    managerFactory instanceof UndoManagerFactoryImpl
  }

  def 'test that factory creates correct undo manager instance'() {
    given:
    def managerFactory = new UndoManagerFactoryImpl()
    def doc = Stub(Document)
    def bufferSize = 10

    when:
    def manager = managerFactory.createUndoManager(doc, bufferSize)

    then:
    manager instanceof UndoManagerImpl
    ((UndoManagerImpl)manager).doc == doc
    ((UndoManagerImpl)manager).bufferSize == bufferSize
  }
}
