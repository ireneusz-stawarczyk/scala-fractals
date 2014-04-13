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
import java.awt.image.BufferedImage
import java.awt.{Dimension, Graphics2D}
import scala.swing.event.{Key, KeyPressed}
import scala.swing.{SimpleSwingApplication, MainFrame, Panel}

/** The view part of the application based on Java Swing.
  *
  * Arrows move the screen, PageDown zoomes in, PageUp zoomes out, N chooses
  * the next algorithm.
  */
object FractalsWindow extends SimpleSwingApplication {

  private[this] val WIDTH = 800
  private[this] val HEIGHT = 600

  def top = new MainFrame {

    title = "scala-fractals 1.1"
    resizable = false

    object fractalsPanel extends Panel {

      preferredSize = new Dimension(WIDTH, HEIGHT)

      class DrawerImpl(val image : BufferedImage) extends Drawer {
        override def drawPixel(x : Int, y : Int, rgb : Int) : Unit = image.setRGB(x, y, rgb)
      }

      private[this] val image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB)
      private[this] val drawer = new DrawerImpl(image)
      private[this] val buffers = FractalsBuffers(drawer)
      private[this] val fractals = FractalsImpl(WIDTH, HEIGHT)

      override def paintComponent(g : Graphics2D) = {
        fractals.updateBuffer(buffer)
        g.drawImage(image, 0, 0, null)
      }

      def buffer : FractalBuffer = buffers.buffer

      focusable = true
      listenTo(keys)

      reactions += {
        case KeyPressed(_, Key.Up, _, _) =>
          buffer.moveUp
          repaint
        case KeyPressed(_, Key.Down, _, _) =>
          buffer.moveDown
          repaint
        case KeyPressed(_, Key.Left, _, _) =>
          buffer.moveLeft
          repaint
        case KeyPressed(_, Key.Right, _, _) =>
          buffer.moveRight
          repaint
        case KeyPressed(_, Key.PageDown, _, _) =>
          buffer.zoomIn
          repaint
        case KeyPressed(_, Key.PageUp, _, _) =>
          buffer.zoomOut
          repaint
        case KeyPressed(_, Key.N, _, _) =>
          buffers.next
          repaint
      }
    }

    contents = fractalsPanel
    centerOnScreen
  }
}