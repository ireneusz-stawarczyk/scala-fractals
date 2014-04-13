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
package edu.scala.fractal.buffer

/** Default implementation of fractals state handling. It's based on delegation of drawing to 
  * the separate object injected by the view client.
  */
abstract class DefaultFractalBuffer(drawer : Drawer) extends FractalBuffer {
  
  protected var _zoom = 1.0
  protected var _moveX = 0.0
  protected var _moveY = 0.0
  protected var _move = 0.006

  override def zoom = _zoom
  override def moveX = _moveX
  override def moveY = _moveY

  override def zoomIn() : Unit = _zoom *= 2
  override def zoomOut() : Unit = _zoom /= 2
  override def moveUp() : Unit = _moveY -= (_move / _zoom)
  override def moveDown() : Unit = _moveY += (_move / _zoom)
  override def moveLeft() : Unit = _moveX -= (_move / _zoom)
  override def moveRight() : Unit = _moveX += (_move / _zoom)

  override def drawPixel(x : Int, y : Int, rgb : Int) = {
    drawer.drawPixel(x, y, rgb);
  }
}