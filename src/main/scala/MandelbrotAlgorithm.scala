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

import java.awt.Color

object MandelbrotAlgorithm {
  def apply(width : Int, height : Int) : MandelbrotAlgorithm =
    new  MandelbrotAlgorithm(width, height)

  def apply(maxIterations : Int, width : Int, height : Int) : MandelbrotAlgorithm =
    new  MandelbrotAlgorithm(maxIterations, width, height)
}

/** Implements the Mandelbrot fractal algorithm.
  *
  * This transforms a screen point to the Mandelbrot plane of complex numbers and then checks
  * if it's inside or outside of the Mandlebrot set using the traditional algorithm.
  *
  * To find out the colour of the screen point we use extremely simple transformation in HSV
  * representation (named HSB in Java) to achieve continuous colours.
  */
class MandelbrotAlgorithm(val maxIterations : Int, val width : Int, val height : Int) {

  def this(width : Int, height : Int) = this(500, width, height)

  def calculateRgb(x : Int, y : Int, zoom : Double, moveX : Double, moveY : Double) : Int  = {
    val nrOfEscapeIterations = escapeIt(re(x, zoom, moveX), im(y, zoom, moveY))
    rgb(nrOfEscapeIterations)
  }

  /** Recursively checks if point is inside the Mandelbrot set.
    *
    * Returns number of iterations that are required for the point to
    * escape outside of the mandelbrot set.
    */
  def escapeIt(cRe : Double, cImg : Double) : Int = {
    /** Checks if complex number is outside 2 radius circle. 
      * If so it escapes to infinity inevitably. 
      */
    def outside2RadiusCircle(re : Double, im : Double) = {
      (re * re + im * im) > 4
    }

    def escape(oldRe : Double, oldIm : Double, i : Int) : Int  = {
      if (i == maxIterations) return maxIterations

      var newRe = oldRe * oldRe - oldIm * oldIm + cRe
      var newIm = 2 * oldRe * oldIm + cImg

      if (outside2RadiusCircle(newRe, newIm)) return i
      else escape(newRe, newIm, i + 1)
    }

    escape(0, 0, 0)
  }

  /** Traditional approach: based on number of iterations it takes to escape from mandelbrot set
    * we find out the rgb colour of the screen point. Here additionally we use HSV model (HSB in Java)
    * to achieve continuous colours.
    *
    * For points that achieve maximum number of iterations we assume that they are part of mandelbrot set
    * and we draw them with black colour.
    */
  def rgb(nrOfIterations : Int) : Int = {
    if (maxIterations == nrOfIterations) Color.BLACK.getRGB()
    else  Color.HSBtoRGB((nrOfIterations.toFloat / maxIterations), 0.7f, 0.7f)
  }

  /** Transforms screen point to mandelbrot plane. For real part we transform screen point
    * to <-2, 1> set. Initial value for 'moveX' should be set to give value within this range.
    * Zooming is within this set: lowers proportionally the boundaries.
    */
  def re(x : Int, zoom : Double, moveX : Double) : Double = {
    1.5 * (x - (width / 2)) / (0.5 * zoom * width) + moveX
  }

   /** Transforms screen point to mandelbrot plane. For imaginary part we transform screen point
     * to <-1, 1> set. Initial value for 'moveY' should be set to give value within this range.
     * Zooming is within this set: lowers proportionally the boundaries.
     */
  def im(y : Int, zoom : Double, moveY : Double) : Double = {
    (y - height / 2) / (0.5 * zoom * height) + moveY
  }
}