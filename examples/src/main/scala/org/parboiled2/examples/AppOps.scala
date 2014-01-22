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

package object extensions {

  implicit class AppOps(val app: App) extends AnyVal {
    /**
     * <p>Extension method hides <a href='https://issues.scala-lang.org/browse/SI-8167'>BUG</a>
     * at `scala.Predef.readLine(text: String, args: Any*)`</p>
     *
     * <p>`scala.Console.out` that `readLine` depends on to write welcome string does
     * not flush whole string on Windows platform. So, the workaround forces to flush
     * `scala.Console.out` manually.</p>
     *
     * <p>Feel free to remove this method once
     * <a href='https://issues.scala-lang.org/browse/SI-8167'>BUG</a> is fixed</p>
     *
     * @param text Text of prompt
     * @return String from `scala.Console.in`
     */
    def readLine(text: String) = {
      print(text)
      Console.out.flush()
      Console.readLine()
    }
  }
}