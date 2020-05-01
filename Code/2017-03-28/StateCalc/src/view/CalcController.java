package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * This class acts as listener for all events fired from the calculator buttons.
 * It keeps track of the current state instance of the calculator model.
 * When an event is handled by the controller, it delegates event processing
 * to the current state instance, which processes the event and returns the next
 * state.
 * When the calculator starts up, it is set to the clear state.
 *
 * @author Sesh Venugopal
 */
public class CalcController {

   /**
    * The clear state.
    */
   protected CalcClearState calcClearState;

   /**
    * The first operand integer state.
    */
   protected CalcOp1IntState calcOp1IntState;

   /**
    * The first operand floating point state.
    */
   protected CalcOp1FloatState calcOp1FloatState;

   /**
    * The second operand integer state.
    */
   protected CalcOp2IntState calcOp2IntState;

   /**
    * The second operand floating point state.
    */
   protected CalcOp2FloatState calcOp2FloatState;

   /**
    * The operator state.
    */
   protected CalcOperatorState calcOperatorState;

   /**
    * The equals state.
    */
   protected CalcEqualsState calcEqualsState;

   @FXML
   protected TextField display;

   @FXML
   protected DigitController digitsController;

   @FXML
   protected OperatorController opsController;

   @FXML
   protected ResultController resController;

   /**
    * Current state instance
    */
   protected CalcState currentState;

   /**
    * Starts up the state machine in clear state.
    */
   public void start() {
      // set up the view controllers
      digitsController.setCalcController(this);
      opsController.setCalcController(this);
      resController.setCalcController(this);

      // set itself as controller for all state machine
      CalcState.calcController = this;

      // make the states
      calcClearState = CalcClearState.getInstance();
      calcOp1IntState = CalcOp1IntState.getInstance();
      calcOp1FloatState = CalcOp1FloatState.getInstance();
      calcOp2IntState = CalcOp2IntState.getInstance();
      calcOp2FloatState = CalcOp2FloatState.getInstance();
      calcOperatorState = CalcOperatorState.getInstance();
      calcEqualsState = CalcEqualsState.getInstance();

      // get the machine up and running
      currentState = calcClearState;
      currentState.enter();
   }

   /**
    * Process event fired from any of the buttons
    *
    * @param e The action event that is to be passed on for handling.
    */
   public void processEvent(ActionEvent e) {
      CalcState.lastEvent = e;
      currentState = currentState.processEvent();
   }

}
