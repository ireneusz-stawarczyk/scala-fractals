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
package edu.scala.fractal.mandelbrot

import edu.scala.fractal.buffer.{Drawer, DefaultFractalBuffer}

object JuliaBuffer {
  def apply(drawer : Drawer) : JuliaBuffer = new  JuliaBuffer(drawer)
}

/** Stores the julia fractal state. Client has to provide the implementation
  * for drawing the pixels, for example, to a temporary buffer or directly to the screen.
  */
final class JuliaBuffer(drawer : Drawer) extends DefaultFractalBuffer(drawer) {

  override def algorithm() : String = JuliaAlgorithm.algorithm
}