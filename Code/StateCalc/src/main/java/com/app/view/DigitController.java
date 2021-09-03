package com.app.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;

public class DigitController {

    protected CalcController calcController;

    @FXML protected List<Button> digitButtons;

    @FXML protected Button decimalButton;

    @FXML
    private void digitPressed(ActionEvent event) {
        // pass event over to controller
        calcController.processEvent(event);
    }

    public void enableDigits() {
        for (Button b : digitButtons) {
            b.setDisable(false);
        }
    }

    public void disableDigits() {
        for (Button b : digitButtons) {
            b.setDisable(true);
        }
    }

    public void enableDecimal() {
        decimalButton.setDisable(false);
    }

    public void disableDecimal() {
        decimalButton.setDisable(true);
    }

    public int getDigit(Button button) {
        int digit = 0;
        for (Button b : digitButtons) {
            if (b == button) {
                return digit;
            }
            digit++;
        }
        return -1;
    }

    public void setCalcController(CalcController calcController) {
        this.calcController = calcController;
    }
}
