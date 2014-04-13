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
import akka.util.Timeout
import akka.pattern.ask
import edu.scala.fractal.Fractals
import edu.scala.fractal.actor.FractalProtocol.{CalculateFractal, FractalResult}
import edu.scala.fractal.buffer.FractalBuffer
import scala.concurrent.Await
import scala.concurrent.duration._

object ActorsFractals {
  def apply(width : Int, height : Int) : Fractals =
    new ActorsFractals(width, height)
}

/** Starting point for all fractals.
  *
  * This implementation is based on concurrent actors: it creates
  * main 'master' actor that handles the processing.
  */
class ActorsFractals(val width : Int, val height : Int) extends Fractals {

  implicit private[this] val timeout = Timeout(100.seconds)

  private[this] val system = ActorSystem("FractalsSystem")
  private[this] val master = system.actorOf(Master.props(width, height), name = "master")

  override def updateBuffer(buffer : FractalBuffer) = {
    require(null != buffer, "buffer is empty")

    val future = master ? CalculateFractal(buffer.zoom, buffer.moveX, buffer.moveY, buffer.algorithm)
    val result = Await.result(future, timeout.duration).asInstanceOf[FractalResult]

    for {
      y <- 0 until height
      x <- 0 until width
    } buffer.drawPixel(x, y, result.image(y)(x))
  }
}