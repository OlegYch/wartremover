package org.brianmckenna.wartremover
package test

import org.scalatest.FunSuite

import org.brianmckenna.wartremover.warts.OptionPartial

class OptionPartialTest extends FunSuite {
  test("can't use Option#get on Some") {
    val result = WartTestTraverser(OptionPartial) {
      println(Some(1).get)
    }
    assert(result.errors == List("Option#get is disabled - use Option#fold instead"))
    assert(result.warnings == List.empty)
  }
  test("can't use Option#get on None") {
    val result = WartTestTraverser(OptionPartial) {
      println(None.get)
    }
    assert(result.errors == List("Option#get is disabled - use Option#fold instead"))
    assert(result.warnings == List.empty)
  }
  test("doesn't detect other `get` methods") {
    val result = WartTestTraverser(OptionPartial) {
      case class A(get: Int)
      println(A(1).get)
    }
    assert(result.errors == List.empty)
    assert(result.warnings == List.empty)
  }
}
