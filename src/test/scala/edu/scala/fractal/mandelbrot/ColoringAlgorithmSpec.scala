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

import edu.scala.fractal.mandelbrot.ColoringAlgorithm._
import java.awt.Color
import org.specs2.mutable._

class ColoringAlgorithmSpec extends Specification {
  private[this] val MAX_ITERATIONS = 100

  "escapeTimeRgb" should {
    "return black colour for max iterations" in {
      escapeTimeRgb(MAX_ITERATIONS, MAX_ITERATIONS) must be equalTo(Color.BLACK.getRGB())
    }
    "not return black colour for number of iterations smaller than max iterations" in {
      escapeTimeRgb(MAX_ITERATIONS - 1, MAX_ITERATIONS) must not be equalTo(Color.BLACK.getRGB())
      escapeTimeRgb(0, MAX_ITERATIONS) must not be equalTo(Color.BLACK.getRGB())
	  escapeTimeRgb(1, MAX_ITERATIONS) must not be equalTo(Color.BLACK.getRGB())
    }
  }
  
  "continuousRgb" should {
    "return black colour for max iterations" in {
      continuousRgb((0.0, 0.0, MAX_ITERATIONS), MAX_ITERATIONS) must be equalTo(Color.BLACK.getRGB())
    }
    "not return black colour for number of iterations smaller than max iterations" in {
      continuousRgb((1.0, 1.0, MAX_ITERATIONS - 1), MAX_ITERATIONS) must not be equalTo(Color.BLACK.getRGB())
      continuousRgb((1.0, 1.0, 0), MAX_ITERATIONS) must not be equalTo(Color.BLACK.getRGB())
	  continuousRgb((1.0, 1.0, 1), MAX_ITERATIONS) must not be equalTo(Color.BLACK.getRGB())
    }
  }

}