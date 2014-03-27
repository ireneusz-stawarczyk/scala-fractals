/* Copyright 2014 Ireneusz Stawarczyk
 *
 * This file is part of scala-fractals.
 *
 * scala-fractals is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * scala-fractals is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with scala-fractals. If not, see <http://www.gnu.org/licenses/>.
 */
package edu.scala.fractal.actor

import akka.actor._
import edu.scala.fractal.mandelbrot.MandelbrotAlgorithm
import FractalProtocol.{Work, WorkResult}

object Worker {
  def props(width : Int, height : Int) : Props = Props(new Worker(width, height))
}

/** Every worker calculates single screen point. */
class Worker(val width : Int, val height : Int) extends Actor {
  private[this] val alg = MandelbrotAlgorithm(width, height)

  def receive = {
    case work : Work =>
      sender ! WorkResult(work.x, work.y, alg.calculateRgb(work.x, work.y, work.zoom, work.moveX, work.moveY))
  }
}
