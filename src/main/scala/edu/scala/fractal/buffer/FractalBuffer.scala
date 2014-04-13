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

/** Stores the state of a fractal algorithm.
  *
  * Implementations have to provide a way to draw the pixels.
  */
trait FractalBuffer {
  
  def zoom : Double
  def moveX : Double
  def moveY : Double

  def zoomIn : Unit
  def zoomOut : Unit

  def moveUp : Unit
  def moveDown : Unit
  def moveLeft : Unit
  def moveRight : Unit

  def drawPixel(x : Int, y : Int, rgb : Int) : Unit
  def algorithm : String
}