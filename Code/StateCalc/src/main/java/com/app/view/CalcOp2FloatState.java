package com.app.view;

import javafx.scene.control.Button;

/**
 * This class represents the floating point first operand state. It is a subclass of CalcState,
 * implementing the State design pattern for Calculator. This class also implements the Singleton
 * design pattern.
 *
 * <p>In this state all buttons are enabled, except '.'
 *
 * @author Sesh Venugopal
 */
class CalcOp2FloatState extends CalcState {

    /** Singleton instance */
    private static CalcOp2FloatState instance = null;

    /** Prevents instantiation, to implement singleton pattern. */
    private CalcOp2FloatState() {}

    /**
     * Returns singleton instance.
     *
     * @return Singleton instance
     */
    public static CalcOp2FloatState getInstance() {
        if (instance == null) {
            instance = new CalcOp2FloatState();
        }
        return instance;
    }

    /**
     * Called when the state is entered. Updates display by appending '.' to existing text. Operator
     * and '=' buttons are enabled. The decimal button is disabled.
     */
    void enter() {
        // append '.' to display
        calcController.display.setText(calcController.display.getText() + ".");

        // enable operator buttons
        calcController.opsController.enableOperators();

        // enable equals button
        calcController.resController.enableEquals();

        // disable decimal button
        calcController.digitsController.disableDecimal();
    }

    /**
     * Processes an event that is fired when in this state. Event source is a digit, or clear, or
     * the '=' button. If source is clear, transitions to clear state. If source is digit, appends
     * digit to display and stays in this state. If source is '=', transitions to compute state
     * after storing second operand If source is operator, compute result, set op1 as result, and go
     * to operator state
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
            return calcController.calcOp2FloatState;
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
