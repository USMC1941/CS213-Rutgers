package view;

import javafx.scene.control.Button;

/**
 * This class represents the equals state. In this state, the display shows op1, and all
 * buttons except = are enabled. 
 * It is a subclass of CalcState, implementing the State design pattern for 
 * Calculator.
 *
 * @author Sesh Venugopal
 */
class CalcEqualsState extends CalcState {

   /**
    * Singleton instance
    */
   private static CalcEqualsState instance = null;

   /**
    * Prevents instantiation, to implement singleton pattern.
    */
   private CalcEqualsState() {

   }

   /**
    * Returns singleton instance.
    *
    * @return Singleton instance
    */
   public static CalcEqualsState getInstance() {
      if (instance == null) {
         instance = new CalcEqualsState();
      }
      return instance;
   }

   /**
    * Called when the state is entered. Sets display to op1. Digit, operator, and
    * C buttons continued to be enabled. The decimal button is enabled, and
    * the '=' button is disabled.
    * Overrides the superclass CalcState enter method
    */
   void enter() {

      // set display to op1
      calcController.display.setText(op1 + "");

      // enable decimal button
      calcController.digitsController.enableDecimal();

      // disable '=' button
      calcController.resController.disableEquals();
   }

   /**
    * Processes an event that is fired when in equals state.
    * Event source is anything but the '=' button.
    * If source is clear, then go to clear state.
    * If source is decimal, then go to op1 float state
    * If source is digit, then go to op1 int state
    * If source is operator, then go to operator state
    *
    * @return New state
    */
   CalcState processEvent() {

      Button b = (Button) lastEvent.getSource();

      // if clear pressed, go to clear state
      if (b == calcController.resController.clearButton) {
         calcController.calcClearState.enter();
         return calcController.calcClearState;
      }

      // if decimal pressed, go to op1 float state
      if (b == calcController.digitsController.decimalButton) {
         calcController.display.setText("0");
         op1 = 0;
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
