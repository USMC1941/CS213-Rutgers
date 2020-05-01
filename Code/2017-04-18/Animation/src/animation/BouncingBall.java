package animation;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BouncingBall extends JFrame implements ActionListener {

   public static final int delay = 100;

   private final JButton  slower;
   private final JButton  faster;
   private final JButton  pause;
   private final JButton  resume;
   private final Animator animPanel;

   public BouncingBall(String title) {
      super(title);
      animPanel = new Animator(delay);
      JPanel controlPanel = new JPanel();

      slower = new JButton("Slower");
      pause = new JButton("Pause");
      resume = new JButton("Resume");
      faster = new JButton("Faster");

      pause.addActionListener(this);
      resume.addActionListener(this);
      slower.addActionListener(this);
      faster.addActionListener(this);

      controlPanel.add(slower);
      controlPanel.add(pause);
      controlPanel.add(resume);
      controlPanel.add(faster);

      add(animPanel, BorderLayout.CENTER);
      add(controlPanel, BorderLayout.SOUTH);

      animPanel.start();
   }

   public static void main(String[] args) {
      JFrame frame = new BouncingBall("Bouncing Ball");
      frame.pack();
      frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public void actionPerformed(ActionEvent e) {
      JButton button = (JButton) e.getSource();
      if (button == slower) {
         animPanel.slower();
      }
      else if (button == faster) {
         animPanel.faster();
      }
      else if (button == resume) {
         animPanel.start();
      }
      else {
         animPanel.stop();
      }
   }
}


