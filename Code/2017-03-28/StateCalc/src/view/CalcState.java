package view;

import javafx.event.ActionEvent;

/**
 * This is the base class for all types of state classes. It cannot be instantiated.
 * This class is part of the State design pattern implementation for the
 * Calculator.
 *
 * @author Sesh Venugopal
 */
public abstract class CalcState {

   /**
    * The controller that drives the state machine
    */
   static CalcController calcController;

   /**
    * The operands.
    */
   static double op1, op2;

   /**
    * The operator.
    */
   static char operator;

   /**
    * The last action event that was fired.
    */
   static ActionEvent lastEvent;

   /**
    * Utility method that applies current operator on the current operands
    * and returns the result.
    *
    * @return Result of applying current operator on current operands.
    */
   static double calculate() {
      switch (operator) {
         case '+':
            return op1 + op2;
         case '-':
            return op1 - op2;
         case '*':
            return op1 * op2;
         case '/':
            return op1 / op2;
         default:
            return 0;
      }
   }

   /**
    * This method is overridden by each subclass with a state-specific implementation.
    * It is called when a state is entered.
    */
   abstract void enter();

   /**
    * This method is called when an event is fired, on the current state instance.
    * Each specific instance will override this method as needed.
    */
   abstract CalcState processEvent();
}
