package com.app.view;

import javafx.scene.control.Button;

/**
 * This class represents the clear state. In this state, the display shows 0, and all buttons except
 * = are enabled. Operand 1 is set to 0. It is a subclass of CalcState, implementing the State
 * design pattern for Calculator.
 *
 * @author Sesh Venugopal
 */
class CalcClearState extends CalcState {

    /** Singleton instance */
    private static CalcClearState instance = null;

    /** Prevents instantiation, to implement singleton pattern. */
    private CalcClearState() {}

    /**
     * Returns singleton instance.
     *
     * @return Singleton instance
     */
    public static CalcClearState getInstance() {
        if (instance == null) {
            instance = new CalcClearState();
        }
        return instance;
    }

    /**
     * Called when the state is entered. Sets display to "0". Enables decimal and operator buttons.
     * Disables the '=' button.
     */
    void enter() {

        // operand1 is 0
        op1 = 0;

        // set display to zero
        calcController.display.setText("0");

        // enable decimal button
        calcController.digitsController.enableDecimal();

        // enable operator buttons
        calcController.opsController.enableOperators();

        // disable '=' button
        calcController.resController.disableEquals();
    }

    /**
     * Processes an event that is fired when in clear state. Event source is any button except '='
     * If source is clear, then remain in this state If source is decimal, then go to op1 float
     * state If source is digit, then go to op1 int state If source is operator, then go to operator
     * state
     *
     * @return New state
     */
    CalcState processEvent() {

        Button b = (Button) lastEvent.getSource();

        // if clear pressed, stay in this state
        if (b == calcController.resController.clearButton) {
            return instance;
        }

        // if decimal pressed, go to op1 float state
        if (b == calcController.digitsController.decimalButton) {
            calcController.calcOp1FloatState.enter();
            return calcController.calcOp1FloatState;
        }

        // if digit pressed, go to op1 int state
        int digit = calcController.digitsController.getDigit(b);
        if (digit != -1) {
            calcController.calcOp1IntState.enter();
            return calcController.calcOp1IntState;
        }

        // operator pressed, go to operator state
        calcController.calcOperatorState.enter();
        return calcController.calcOperatorState;
    }
}
