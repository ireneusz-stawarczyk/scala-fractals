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
import akka.routing.RoundRobinRouter
import FractalProtocol._

/** Messages passed between actors. */
object FractalProtocol {

  sealed trait FractalMessage
  case class CalculateFractal(zoom : Double, moveX : Double, moveY : Double, algorithm : String) extends FractalMessage
  case class FractalResult(image : Array[Array[Int]]) extends FractalMessage
  case class Work(x : Int, y : Int, zoom : Double, moveX : Double, moveY : Double, algorithm : String) extends FractalMessage
  case class WorkResult(x : Int, y : Int, rgb : Int) extends FractalMessage
}

object Master {
  def props(width : Int, height : Int) : Props = Props(new Master(width, height))
}

/** Main actor controlling the workers that do the calculations for fractals. */
class Master(val width : Int, val height : Int) extends Actor {

  private[this] val nrOfMessages = height * width
  private[this] val nrOfWorkers = Runtime.getRuntime().availableProcessors() * 2
  private[this] val workerRouter = context.actorOf(Worker.props(width, height).withRouter(RoundRobinRouter(nrOfWorkers)), name = "workerRouter")
  private[this] val fractal = Array.ofDim[Int](height, width)

  private[this] var nrOfResults : Int = _
  private[this] var fractalResultListener : ActorRef = _

  def receive = {
    case CalculateFractal(zoom, moveX, moveY, algorithm) =>
      fractalResultListener = sender
      calculateFractal(zoom, moveX, moveY, algorithm)
    case WorkResult(x, y, rgb) =>
      nrOfResults += 1
      fractal(y)(x) = rgb

      if (nrOfResults == nrOfMessages) {
        fractalResultListener ! FractalResult(fractal)
      }
  }

  def calculateFractal(zoom : Double, moveX : Double, moveY : Double, algorithm : String) = {
    nrOfResults = 0

    for { 
      y <- 0 until height
      x <- 0 until width 
    } {
      fractal(y)(x) = 0
      workerRouter ! Work(x, y, zoom, moveX, moveY, algorithm)
    }
  }
}