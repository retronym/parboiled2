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

package org.parboiled2.examples

import org.parboiled2._
import scala.annotation.tailrec
import shapeless._

class ContinuationPassingParser(val input: ParserInput[Char]) extends Parser {
  type ElemType = Char

  //def InputLine = rule { "123" ~ ch('4') }
  //def InputLine = rule { "123" | "45" }
  //def InputLine = rule { capture("12345") }
  //def InputLine = rule { capture("1") ~ capture("2345") }
  //def InputLine = rule { capture("12345") ~> ((x: String) ⇒ x.toInt + 23) }
  //def InputLine = rule { capture("1") ~ capture("2345") ~> ((x: String, y: String) ⇒ x.toInt + y.toInt) }
  def InputLine = rule { capture("1") ~ capture("2345") ~> ((x: String, y: String) ⇒ x.toInt + y.toInt) ~ push() }
}

object ContinuationPassingParser {
  def matchString(inputLine: String): Unit = {
    if (inputLine != "") {
      println("--------------------\n")
      val abcParser = new ContinuationPassingParser(inputLine)
      abcParser.run(_.InputLine) match {
        case Right(x)  ⇒ println(s"Expression is valid: $x")
        case Left(err) ⇒ println(s"Expression is not valid. Error: ${ErrorUtils.formatError(inputLine, err)}")
      }
    }
  }

  def main(args: Array[String]): Unit = {
    import Parser._

    //    val inputLine = "88345"
    //    val abcParser = new ContinuationPassingParser(inputLine)
    //    abcParser.run(_.InputLine) match {
    //      case Right(x)  ⇒ println(s"Expression is valid: $x")
    //      case Left(err) ⇒ println(s"Expression is not valid. Error: ${ErrorUtils.formatError(inputLine, err)}")
    //    }

    val input1 = "12"
    val abcParser = new ContinuationPassingParser(input1)
    abcParser.startParsing(_.InputLine) match {
      case (cont1: Continuation[_, Char]) ⇒
        val input2 = "3"
        cont1.continuation(input2) match {
          case (cont2: Continuation[_, Char]) ⇒
            val input3 = "45"
            cont2.continuation(input3) match {
              case Value(v) ⇒
                println(s"Expression is valid: $v")
              case err @ Error(_, _) ⇒
                println(s"Expression is not valid for '$input3' chunk. Error: ${ErrorUtils.formatError(abcParser.input, err)}")
            }
          case Value(v) ⇒
            println(s"Expression is valid: $v")
          case err @ Error(_, _) ⇒
            println(s"Expression is not valid for '$input2' chunk. Error: ${ErrorUtils.formatError(abcParser.input, err)}")
        }
      case err @ Error(_, _) ⇒
        println(s"Expression is not valid for '$input1' chunk. Error: ${ErrorUtils.formatError(abcParser.input, err)}")
    }
  }
}
