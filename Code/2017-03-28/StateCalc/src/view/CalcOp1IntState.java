package view;

import javafx.scene.control.Button;

/**
 * This class represents the integer first operand state. 
 * It is a subclass of CalcState, implementing the State design pattern for
 * the Calculator. This class also implements the Singleton design pattern.
 * <p>
 * As in the clear state, all buttons except '=' are enabled. 
 *
 * @author Sesh Venugopal
 */
class CalcOp1IntState extends CalcState {

   /**
    * Singleton instance
    */
   private static CalcOp1IntState instance = null;

   /**
    * Prevents instantiation, to implement singleton pattern.
    */
   private CalcOp1IntState() {

   }

   /**
    * Returns singleton instance.
    *
    * @return Singleton instance
    */
   public static CalcOp1IntState getInstance() {
      if (instance == null) {
         instance = new CalcOp1IntState();
      }
      return instance;
   }

   /**
    * Called when the state is entered. Updates display with single digit.
    * Digit buttons are always enabled. Decimal, 'C', and operator buttons must
    * already be enabled at the time this state is entered, and they remain enabled.
    * The '=' must already be disabled, and remains disabled.
    */
   void enter() {
      // set display to digit in event
      Button button = (Button) lastEvent.getSource();
      String digit  = button.getText().trim();
      calcController.display.setText(digit + "");
   }

   /**
    * Processes an event that is fired when in digit state.
    * Event source is a digit, decimal, an operator, or clear.
    * If source is clear, transitions to clear state.
    * If source is decimal, transitions to op1 float state.
    * If source is digit, appends digit to display and stays in this state.
    * If source is an operator, gets and stores operand1, and transitions to operator state.
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

      // if decimal button pressed, then go to floating point state
      if (b == calcController.digitsController.decimalButton) {
         calcController.calcOp1FloatState.enter();
         return calcController.calcOp1FloatState;
      }

      // if digit pressed, append to text and display, stay in same state
      int digit = calcController.digitsController.getDigit(b);
      if (digit != -1) {
         String currText = calcController.display.getText();
         calcController.display.setText(currText + digit);
         return calcController.calcOp1IntState;
      }

      // operator button pressed, get operand 1 and then go to operator state
      op1 = Double.parseDouble(calcController.display.getText().trim());
      calcController.calcOperatorState.enter();
      return calcController.calcOperatorState;
   }
}
