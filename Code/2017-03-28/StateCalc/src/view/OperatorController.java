package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;

public class OperatorController {

   protected CalcController calcController;

   @FXML
   protected List<Button> operatorButtons;

   @FXML
   protected void operatorPressed(ActionEvent event) {
      // pass event over to controller
      calcController.processEvent(event);
   }

   public void enableOperators() {
      for (Button b : operatorButtons) {
         b.setDisable(false);
      }
   }

   public void disableOperators() {
      for (Button b : operatorButtons) {
         b.setDisable(true);
      }
   }

   public void setCalcController(CalcController calcController) {
      this.calcController = calcController;
   }
}
