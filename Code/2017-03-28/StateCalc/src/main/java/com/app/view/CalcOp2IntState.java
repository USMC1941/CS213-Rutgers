package com.app.view;

import javafx.scene.control.Button;

/**
 * This class represents the integer second operand state. It is a subclass of CalcState,
 * implementing the State design pattern for the Calculator. This class also implements the
 * Singleton design pattern.
 *
 * <p>In this state all buttons are enabled.
 *
 * @author Sesh Venugopal
 */
class CalcOp2IntState extends CalcState {

    /** Singleton instance */
    private static CalcOp2IntState instance = null;

    /** Prevents instantiation, to implement singleton pattern. */
    private CalcOp2IntState() {}

    /**
     * Returns singleton instance.
     *
     * @return Singleton instance
     */
    public static CalcOp2IntState getInstance() {
        if (instance == null) {
            instance = new CalcOp2IntState();
        }
        return instance;
    }

    /**
     * Called when the state is entered. Updates display with single digit. The operator and '='
     * buttons are re-enabled.
     */
    void enter() {
        // set display to digit in event
        int digit = calcController.digitsController.getDigit((Button) lastEvent.getSource());
        calcController.display.setText(digit + "");

        // enable equals button
        calcController.resController.enableEquals();

        // enable operator buttons
        calcController.opsController.enableOperators();
    }

    /**
     * Processes an event that is fired when in digit state. Event source is a digit, decimal,
     * operator, clear, or '='. If source is clear, transitions to clear state. If source is
     * decimal, transitions to op1 float state. If source is digit, appends digit to display and
     * stays in this state. If source is '=', transitions to compute state after storing second
     * operand If source is operator, compute result, set op1 as result, and go to operator state
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

        // if decimal button pressed, then go to floating point second operator state
        if (b == calcController.digitsController.decimalButton) {
            calcController.calcOp2FloatState.enter();
            return calcController.calcOp2FloatState;
        }

        // if equals button is pressed, transition to equals state after updating first operand
        // with result of applying operator on first and second operands
        if (b == calcController.resController.equalsButton) {
            op2 = Double.parseDouble(calcController.display.getText().trim());
            op1 = calculate();
            calcController.calcEqualsState.enter();
            return calcController.calcEqualsState;
        }

        // if digit pressed, append to text and display, stay in same state
        int digit = calcController.digitsController.getDigit(b);
        if (digit != -1) {
            String currText = calcController.display.getText();
            calcController.display.setText(currText + digit);
            return calcController.calcOp2IntState;
        }

        // operator button pressed, calculate op1 operator op2, update op1 and go to operator state
        op2 = Double.parseDouble(calcController.display.getText().trim());
        op1 = calculate();
        // set display to op1
        calcController.display.setText(op1 + "");
        calcController.calcOperatorState.enter();
        return calcController.calcOperatorState;
    }
}
