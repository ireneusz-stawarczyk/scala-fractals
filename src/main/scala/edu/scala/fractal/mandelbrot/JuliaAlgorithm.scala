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

import edu.scala.fractal.Fractal
import edu.scala.fractal.mandelbrot.ColoringAlgorithm.{continuousRgb => rgb}
import java.awt.Color

object JuliaAlgorithm {
  def algorithm = "Julia"

  def apply(width : Int, height : Int) : JuliaAlgorithm =
    new  JuliaAlgorithm(width, height)

  def apply(maxIterations : Int, width : Int, height : Int) : JuliaAlgorithm =
    new  JuliaAlgorithm(maxIterations, width, height)
}

/** Implements the Julia fractal algorithm.
  *
  * This transforms a screen point to the Julia plane of complex numbers and then checks
  * if it's inside or outside of the Julia set using the traditional algorithm.
  *
  * Coloring is delegated to the separate object responsible just for the coloring algorithms.  
  */
class JuliaAlgorithm(val maxIterations : Int, val width : Int, val height : Int, val cRe : Double, val cIm : Double) extends Fractal {

  def this(maxIterations : Int, width : Int, height : Int) = this(maxIterations, width, height, -0.7017, -0.3842)
  
  def this(width : Int, height : Int) = this(5000, width, height)

  def algorithm = JuliaAlgorithm.algorithm

  def calculateRgb(x : Int, y : Int, zoom : Double, moveX : Double, moveY : Double) : Int  = {
    val escapeValues = escapeIt(re(x, zoom, moveX), im(y, zoom, moveY))
    rgb(escapeValues, maxIterations)
  }

  /** Recursively checks if point is inside the Julia set.
    *
    * Returns the last point and the number of iterations that are required 
	* for the point to escape outside of the mandelbrot set.
    */
  def escapeIt(z0Re : Double, z0Im : Double) : (Double, Double, Int) = {
    /** Checks if complex number is outside 2 radius circle. 
      * If so it escapes to infinity inevitably. 
      */
    def outside2RadiusCircle(re : Double, im : Double) = {
      (re * re + im * im) > 4
    }

    def escape(oldRe : Double, oldIm : Double, i : Int) : (Double, Double, Int)  = {
      if (i == maxIterations) return (oldRe, oldIm, i)

      val newRe = oldRe * oldRe - oldIm * oldIm + cRe
      val newIm = 2 * oldRe * oldIm + cIm

      if (outside2RadiusCircle(newRe, newIm)) return (newRe, newIm, i)
      else escape(newRe, newIm, i + 1)
    }

    escape(z0Re, z0Im, 1)
  }

  /** Returns the real part of the given screen point.
    */
  def re(x : Int, zoom : Double, moveX : Double) : Double = {
    1.5 * (x - (width / 2)) / (0.5 * zoom * width) + moveX
  }

   /** Returns the imaginary part of the given screen point.
     */
  def im(y : Int, zoom : Double, moveY : Double) : Double = {
    (y - height / 2) / (0.5 * zoom * height) + moveY
  }
}