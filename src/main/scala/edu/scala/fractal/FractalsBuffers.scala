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
package edu.scala.fractal

import edu.scala.fractal.buffer.{FractalBuffer, Drawer}
import edu.scala.fractal.mandelbrot.{JuliaBuffer, MandelbrotBuffer}

object FractalsBuffers {
  def apply(drawer : Drawer) : FractalsBuffers =
    new  FractalsBuffers(drawer)
}

/** Holds all available fractal algorithms states. Allows switching between them
  * and handles movement, zoom in and zoom out.
  */
class FractalsBuffers(drawer : Drawer) {
  
  private[this] val mandelbrotBuffer = MandelbrotBuffer(drawer)
  private[this] val juliaBuffer = JuliaBuffer(drawer)

  private[this] val buffers = Array(mandelbrotBuffer, juliaBuffer)
  private[this] var bufferNr = 0

  def buffer : FractalBuffer = buffers(bufferNr)

  def zoomIn() : Unit = buffer.zoomIn
  def zoomOut() : Unit = buffer.zoomOut
  def moveUp() : Unit = buffer.moveUp
  def moveDown() : Unit = buffer.moveDown
  def moveLeft() : Unit = buffer.moveLeft
  def moveRight() : Unit = buffer.moveRight
  def next() : Unit =  bufferNr = (bufferNr + 1) % buffers.size
}