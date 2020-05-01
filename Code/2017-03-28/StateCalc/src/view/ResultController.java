package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ResultController {

   @FXML
   protected Button equalsButton;
   @FXML
   protected Button clearButton;
   private CalcController calcController;

   @FXML
   protected void resultPressed(ActionEvent event) {
      // pass event over to controller
      calcController.processEvent(event);
   }

   public void enableEquals() {
      equalsButton.setDisable(false);
   }

   public void disableEquals() {
      equalsButton.setDisable(true);
   }

   public void setCalcController(CalcController calcController) {
      this.calcController = calcController;
   }
}
