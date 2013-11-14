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

/*

package org.parboiled2.examples

import org.parboiled2._
import scala.annotation.tailrec
import shapeless._
import util.continuations._

class SandboxParser(val input: ParserInput) extends Parser {
  //def foo = rule { capture("123") ~> (_.toInt) }
  //def InputLine = rule { foo ~ "45" }
  def InputLine = rule { str("12345") }
}

object SandboxParser {
  def matchString(inputLine: String): Unit = {
    if (inputLine != "") {
      println("--------------------\n")
      val abcParser = new SandboxParser(inputLine)
      abcParser.run(_.InputLine) match {
        case Right(x)  ⇒ println(s"Expression is valid: $x")
        case Left(err) ⇒ println(s"Expression is not valid. Error: ${ErrorUtils.formatError(inputLine, err)}")
      }
    }
  }

  /* // Works

   */

  class Test {
    import Parser._

    def parse(input: String): Result[HNil] = {
      println(s"parse: $input")
      input match {
        case "12" ⇒ Continuation(null)
        case "34" ⇒ Continuation(null)
        case "5"  ⇒ Value(HNil)
      }
    }
  }

  def io[A, C](): String @cpsParam[Unit, Unit] = shift { k: (String ⇒ Unit) ⇒
    for (s ← List("12", "34", "5"))
      k(s)
  }

  def main(args: Array[String]): Unit = {
    import Parser._

    //    val t = new Test()
    //    val r = reset {
    //      println(t.parse(io()))
    //    }

    //    println(r)

    /*
    val abcParser = new SandboxParser("12")
    val r = {
      val Continuation(r1) = abcParser.startParsing(_.InputLine)
      val Continuation(r2) = r1("34")
      val r3 = abcParser.parseChunk("5")
      r3
    }
    println(r)
    */
  }
}

 */ 