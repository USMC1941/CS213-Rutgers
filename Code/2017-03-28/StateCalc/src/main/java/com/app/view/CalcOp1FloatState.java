package com.app.view;

import javafx.scene.control.Button;

/**
 * This class represents the floating point first operand state. It is a subclass of CalcState,
 * implementing the State design pattern for Calculator. This class also implements the Singleton
 * design pattern.
 *
 * <p>In this state all buttons except '=' and '.' are enabled.
 *
 * @author Sesh Venugopal
 */
class CalcOp1FloatState extends CalcState {

    /** Singleton instance */
    private static CalcOp1FloatState instance = null;

    /** Prevents instantiation, to implement singleton pattern. */
    private CalcOp1FloatState() {}

    /**
     * Returns singleton instance.
     *
     * @return Singleton instance
     */
    public static CalcOp1FloatState getInstance() {
        if (instance == null) {
            instance = new CalcOp1FloatState();
        }
        return instance;
    }

    /**
     * Called when the state is entered. Updates display by appending '.' to existing text. Digit
     * buttons are always enabled. Operator and 'C' buttons must already be enabled at the time this
     * state is entered, and remain enabled. The '=' must already be disabled, and remains disabled.
     * The decimal button is disabled.
     */
    void enter() {
        // append '.' to display
        calcController.display.setText(calcController.display.getText() + ".");

        // disable decimal button
        calcController.digitsController.disableDecimal();
    }

    /**
     * Processes an event that is fired when in this state. Event source is a digit, an operator, or
     * clear. If source is clear, transitions to clear state. If source is digit, appends digit to
     * display and stays in this state. If source is an operator, transitions to op1 operator state.
     *
     * @return New state
     */
    CalcState processEvent() {
        Button b = (Button) lastEvent.getSource();

        // if clear button is pressed, transition to clear state
        if (b == calcController.resController.clearButton) {
            calcController.calcClearState.enter();
            return calcController.calcClearState;
        }

        // if digit pressed, append to text and display, stay in same state
        int digit = calcController.digitsController.getDigit(b);
        if (digit != -1) {
            String currText = calcController.display.getText();
            calcController.display.setText(currText + digit);
            return calcController.calcOp1FloatState;
        }

        // operator button pressed, get operand 1 and then go to operator state
        op1 = Double.parseDouble(calcController.display.getText().trim());
        calcController.calcOperatorState.enter();
        return calcController.calcOperatorState;
    }
}
