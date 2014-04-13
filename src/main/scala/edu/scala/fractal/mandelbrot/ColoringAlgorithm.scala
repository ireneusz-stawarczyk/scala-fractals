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
import scala.math._

object ColoringAlgorithm {

/** Traditional continuous implementation of coloring. Gives better looking fractals.
  */
  def continuousRgb(escapeValues : (Double, Double, Int), maxIterations : Int) : Int = {
    escapeValues match { 
      case (re, im, nrOfIterations) =>
        if (maxIterations == nrOfIterations) Color.BLACK.getRGB()
        else  {
          val distance = sqrt(re * re + im * im)
          val color = (nrOfIterations + 1 - log(log(distance))/log(2)).toFloat / maxIterations
          Color.HSBtoRGB(0.60f + 10 * color, 0.7f, 0.7f)
        }
    } 
  }

/** Traditional approach: based on number of iterations it takes to escape from the fractal set
  * we find out the rgb colour of the screen point. Here additionally we use HSV model (HSB in Java)
  * to achieve continuous colours.
  *
  * For points that achieve maximum number of iterations we assume that they are part of the set
  * and we draw them with black colour.
  */
  def escapeTimeRgb(nrOfIterations : Int, maxIterations : Int) : Int = {
    if (maxIterations == nrOfIterations) Color.BLACK.getRGB()
    else  Color.HSBtoRGB((nrOfIterations.toFloat / maxIterations), 0.7f, 0.7f)
  }
}