/*
 * Copyright (C) 2009-2013 Mathias Doenitz, Alexander Myltsev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.parboiled2

import org.specs2.specification.{ NoToHtmlLinkFragments, Scope }
import org.specs2.mutable.Specification
import org.specs2.control.NoNumberOfTimes
import org.parboiled2.support.Unpack
import shapeless._
import org.parboiled2.support.RunResult

abstract class TestParserSpec1  {
  type TestParser0 = TestParser[HNil, Unit]
  type TestParser1[T] = TestParser[T :: HNil, T]
  type TestParserN[L <: HList] = TestParser[L, L]

  abstract class TestParser[L <: HList, Out](implicit unpack: Unpack.Aux[L, Out]) extends Parser with Scope {
    var input: ParserInput = _
    def targetRule: RuleN[L]

    def parse(input: String): Either[ParseError, Out] = {
      this.input = input
      import Parser.DeliveryScheme.Either
      targetRule.run()
    }
  }
}

class ActionSpec extends TestParserSpec1 {

    new TestParser1[Int] {

      /////////// PROBLEM START /////////////

      def compute[T](implicit rr: RunResult[T]): rr.Out = ???

      // compiles but shouldn't compile
      // compute[String ⇒ Unit]: Rule0

      // apparently this is what the compiler (erroneously?) selects
      // compute[String ⇒ Unit](RunResult.fromAux(RunResult.Aux.forAny)): Rule0

      compute[String ⇒ Unit]: Rule[String :: HNil, HNil] // doesn't compile but should compile

      // compiles!!! This is what the compiler should select automatically but for some reason doesn't
      compute[String ⇒ Unit](RunResult.fromAux(RunResult.Aux.forF1)): Rule[String :: HNil, HNil]

      // doesn't compile, why doesn't the compiler select `RunResult.Aux.forF1` when it is legal (as the previous line shows)?
      // compute[String ⇒ Unit](RunResult.fromAux): Rule[String :: HNil, HNil]

      /////////// PROBLEM END /////////////

      // Doesn't compile due to regression in Scala 2.11.0?
      // def targetRule = rule { push(1 :: "X" :: HNil) ~ run((x: String) ⇒ require(x == "X")) ~ EOI }
      // def targetRule = rule { push(1 :: "X" :: HNil) ~ drop[String] ~ EOI }
      // "" must beMatchedWith(1)
    }
}