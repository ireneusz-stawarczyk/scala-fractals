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
import org.specs2.mutable._

class MandelbrotAlgorithmSpec extends Specification {
  private[this] val MAX_ITERATIONS = 100
  private[this] val WIDTH = 800
  private[this] val HEIGHT = 600
  private[this] val MIN_X = 0
  private[this] val MAX_X = WIDTH - 1
  private[this] val MIN_Y = 0
  private[this] val MAX_Y = HEIGHT - 1

  private[this] val mandelbrotAlgorithm = MandelbrotAlgorithm(MAX_ITERATIONS, WIDTH, HEIGHT)

  "rgb" should {
    "return black colour for max iterations" in {
      mandelbrotAlgorithm.rgb(MAX_ITERATIONS) must be equalTo(Color.BLACK.getRGB())
    }
    "not return black colour for number of iterations smaller than max iterations" in {
      mandelbrotAlgorithm.rgb(MAX_ITERATIONS - 1) must not be equalTo(Color.BLACK.getRGB())
      mandelbrotAlgorithm.rgb(0) must not be equalTo(Color.BLACK.getRGB())
    }
  }

  "escapeIt" should {
    "return max iterations value for a point inside mandelbrot set" in {
      mandelbrotAlgorithm.escapeIt(0, 0) must be equalTo(MAX_ITERATIONS)
    }
    "return 1 iteration value for the point (1 + i) that is outside of the mandelbrot set" in {
      mandelbrotAlgorithm.escapeIt(1, 1) must be equalTo(1)
    }
  }

  "re" should {
    "return -2 value for the first x pixel (0) with -0.5 displacement in the mandelbrot set with 1.0 zoom" in {
      mandelbrotAlgorithm.re(MIN_X, 1.0, -0.5) must be equalTo(-2)
    }
	"return 1 value for the maximum width with -0.5 displacement in the mandelbrot set with 1.0 zoom" in {
      mandelbrotAlgorithm.re(WIDTH, 1.0, -0.5) must be equalTo(1)
    }
	"return value smaller than 1 for the last possible x pixel with -0.5 displacement in the mandelbrot set with 1.0 zoom" in {
      mandelbrotAlgorithm.re(MAX_X, 1.0, -0.5) must be lessThan(1)
    }
  }

  "im" should {
    "return -1 value for the first y pixel (0) with 0.0 displacement in the mandelbrot set with 1.0 zoom" in {
      mandelbrotAlgorithm.im(MIN_Y, 1.0, 0.0) must be equalTo(-1)
    }
	"return 1 value for the maximum height with 0.0 displacement in the mandelbrot set with 1.0 zoom" in {
      mandelbrotAlgorithm.im(HEIGHT, 1.0, 0.0) must be equalTo(1)
    }
	"return value smaller than 1 for the last possible y pixel with 0.0 displacement in the mandelbrot set with 1.0 zoom" in {
      mandelbrotAlgorithm.im(MAX_Y, 1.0, 0.0) must be lessThan(1)
    }	
  }

}