package com.app.view;

import javafx.scene.control.Button;

/**
 * This class represents the operator state. It is a subclass of CalcState, implementing the State
 * design pattern for Calculator.
 *
 * <p>In this state all operators are disabled. The digits continue to be enabled, and so does the
 * clear button. The '= button continues to be disabled. The decimal button is enabled. Display is
 * unchanged from before. Operator is stored internally.
 *
 * @author Sesh Venugopal
 */
class CalcOperatorState extends CalcState {

    /** Singleton instance */
    private static CalcOperatorState instance = null;

    /** Prevents instantiation, to implement singleton pattern. */
    private CalcOperatorState() {}

    /**
     * Returns singleton instance.
     *
     * @return Singleton instance
     */
    public static CalcOperatorState getInstance() {
        if (instance == null) {
            instance = new CalcOperatorState();
        }
        return instance;
    }

    /**
     * Called when the state is entered. Digit buttons and 'C' button must already be enabled at the
     * time this state is entered, and remain enabled. The '=' button remains disabled. The decimal
     * button is enabled. The operator buttons are disabled.
     */
    void enter() {

        // get operator from last event
        Button b = (Button) lastEvent.getSource();
        operator = b.getText().trim().charAt(0);

        // enable decimal button
        calcController.digitsController.enableDecimal();

        // disable operator buttons
        calcController.opsController.disableOperators();
    }

    /**
     * Processes an event that is fired when in this state. Event source is a digit, a decimal or
     * clear. If source is clear, transitions to clear state. If source is digit, transitions to op2
     * int state. If source is decimal, transitions to op2 float state.
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

        // if decimal button is pressed go to op2 float state after setting display to 0
        if (b == calcController.digitsController.decimalButton) {
            calcController.display.setText("0");
            calcController.calcOp2FloatState.enter();
            return calcController.calcOp2FloatState;
        }

        // digit pressed, transition to op2 int state
        calcController.calcOp2IntState.enter();
        return calcController.calcOp2IntState;
    }
}
