package animation;

import javax.swing.*;
import java.awt.*;

public class Animator extends JPanel implements Runnable {

   public static int XOFF = 10;
   public static int YOFF = 10;
   int     delay;
   int     radius        = 20;
   int     unitWidth     = 2 * radius;
   int     displayWidth  = 8 * unitWidth;
   int     displayHeight = 4 * radius;
   boolean rising        = false;
   int     ballX         = displayWidth - unitWidth;
   int     ballY         = 3 * radius;
   private Thread animatorThread = null;

   public Animator(int delay) {
      this.delay = delay;
   }

   public void start() {
      animatorThread = new Thread(this);
      animatorThread.start();
   }

   public void stop() {
      animatorThread.interrupt();
   }

   public void slower() {
      delay += 10;
   }

   public void faster() {
      if (delay > 50) {
         delay -= 10;
      }
   }

   public void run() {
      long startTime = System.currentTimeMillis();

      // animation loop
      while (!Thread.interrupted()) {
         repaint();

         try {
            startTime += delay;
            Thread.sleep(Math.max(0, startTime - System.currentTimeMillis()));
         }
         catch (InterruptedException e) {
            break;
         }
      }
   }

   public Dimension getPreferredSize() {
      return new Dimension(displayWidth, displayHeight);
   }

   public Dimension getMinimumSize() {
      return new Dimension(displayWidth, displayHeight);
   }

   public void paintComponent(Graphics g) {
      g.setColor(Color.blue);
      g.fillRect(XOFF, YOFF, displayWidth, displayHeight);

      g.setColor(Color.yellow);

      ballX += unitWidth;
      if (ballX == displayWidth) {
         g.fillArc(XOFF + displayWidth - radius, YOFF + displayHeight - 3 * radius, 2 * radius, 2 * radius, 90, 180);
         g.fillArc(XOFF - radius, YOFF + displayHeight - 3 * radius, 2 * radius, 2 * radius, 90, -180);
         ballX = 0;
         ballY = 0;
         rising = true;
         return;
      }

      if (rising) {
         ballY += radius;
         if (ballY == (displayHeight - radius)) {
            rising = false;
         }
      }
      else {
         ballY -= radius;
         if (ballY == radius) {
            rising = true;
         }
      }
      g.fillOval(XOFF + ballX - radius, YOFF + displayHeight - ballY - radius, 2 * radius, 2 * radius);
   }
}
