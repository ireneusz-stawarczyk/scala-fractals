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

import edu.scala.fractal.FractalBuffer

/** Stores the mandelbrot fractal state. Client has to provide implementation
  * for drawing the pixels, for example, to a temporary buffer or directly to the screen.
  */
abstract class MandelbrotBuffer extends FractalBuffer {
  
  private[this] var _zoom = 1.0
  private[this] var _moveX = -0.5
  private[this] var _moveY = 0.0
  private[this] var _move = 0.006

  final override def zoom = _zoom
  final override def moveX = _moveX
  final override def moveY = _moveY

  final override def zoomIn() : Unit = _zoom *= 2
  final override def zoomOut() : Unit = _zoom /= 2
  final override def moveUp() : Unit = _moveY -= (_move / _zoom)
  final override def moveDown() : Unit = _moveY += (_move / _zoom)
  final override def moveLeft() : Unit = _moveX -= (_move / _zoom)
  final override def moveRight() : Unit = _moveX += (_move / _zoom)
}