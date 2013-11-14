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
import util.continuations._

abstract class ContinuationPassingParser(val input: ParserInput[Char]) extends Parser {
  type InputType = Char

  //def InputLine = rule { "123" ~ "45" }
  def InputLine = rule { str("12345") }
}

object ContinuationPassingParser {
  //  def matchString(inputLine: String): Unit = {
  //    if (inputLine != "") {
  //      println("--------------------\n")
  //      val abcParser = new ContinuationPassingParser(inputLine)
  //      abcParser.run(_.InputLine) match {
  //        case Right(x)  ⇒ println(s"Expression is valid: $x")
  //        case Left(err) ⇒ println(s"Expression is not valid. Error: ${ErrorUtils.formatError(inputLine, err)}")
  //      }
  //    }
  //  }

  def main(args: Array[String]): Unit = {
    import Parser._
    val input1 = "12"
    val abcParser = new ContinuationPassingParser(input1) {
      private val data = List("12", "3", "3334", "5").iterator

      def nextChunk(): Option[String] =
        if (data.hasNext) Some(data.next())
        else None
    }

    abcParser.startParsing(_.InputLine) match {
      case Value(v) ⇒ println(s"Expression is valid: $v")
      case err @ Error(_, _) ⇒
        println(s"Expression is not valid. Error: ${ErrorUtils.formatError(input1, err)}")
    }

    //    abcParser.startParsing(_.InputLine) match {
    //      case Continuation(cont1) ⇒
    //        val input2 = "34"
    //        cont1(input2) match {
    //          case Continuation(cont2) ⇒
    //            val input3 = "5"
    //            cont2(input3) match {
    //              case Value(v) ⇒
    //                println(s"Expression is valid: $v")
    //            }
    //        }
    //      case err @ Error(_, _) ⇒
    //        println(s"Expression is not valid. Error: ${ErrorUtils.formatError(input1, err)}")
    //    }
  }
}