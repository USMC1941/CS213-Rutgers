package f2c.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class F2CController {

   @FXML
   Button    f2c;
   @FXML
   Button    c2f;
   @FXML
   TextField f;
   @FXML
   TextField c;

   public void convert(ActionEvent e) {
      Button b = (Button) e.getSource();
      if (b == f2c) {
         float fval = Float.valueOf(f.getText());
         float cval = (fval - 32) * 5 / 9;
         c.setText(String.format("%5.1f", cval));
      }
      else {
         float cval = Float.valueOf(c.getText());
         float fval = cval * 9 / 5 + 32;
         f.setText(String.format("%5.1f", fval));
      }
   }
}
