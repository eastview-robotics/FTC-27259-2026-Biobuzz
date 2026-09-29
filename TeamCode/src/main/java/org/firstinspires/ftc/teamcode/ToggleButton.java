package org.firstinspires.ftc.teamcode;

public class ToggleButton  {
    boolean toggleStateA;
    boolean toggleStateB;
    boolean toggleStateX;
    boolean toggleStateY;
    boolean previousButtonStateA;
    boolean previousButtonStateB;
    boolean previousButtonStateX;
    boolean previousButtonStateY;

    public ToggleButton(boolean initToggleState) {
        this.toggleStateA = initToggleState;
    }
    public boolean toggleA(boolean currentButtonState) {
        // this expression is only true for one single loop cycle the moment you press the button.
        // even if you keep holding the button down, it will not trigger again until you release it and press it a second time.
        if (currentButtonState && !previousButtonStateA) {
            // Flip the toggle state
            this.toggleStateA = !toggleStateA;
        }

        // save the current state for the next loop iteration
        this.previousButtonStateA = currentButtonState;
        return toggleStateA;
    }
    public boolean toggleB(boolean currentButtonState) {
        // this expression is only true for one single loop cycle the moment you press the button.
        // even if you keep holding the button down, it will not trigger again until you release it and press it a second time.
        if (currentButtonState && !previousButtonStateB) {
            // Flip the toggle state
            this.toggleStateB = !toggleStateB;
        }

        // save the current state for the next loop iteration
        this.previousButtonStateB = currentButtonState;
        return toggleStateB;
    }
    public boolean toggleX(boolean currentButtonState) {
        // this expression is only true for one single loop cycle the moment you press the button.
        // even if you keep holding the button down, it will not trigger again until you release it and press it a second time.
        if (currentButtonState && !previousButtonStateX) {
            // Flip the toggle state
            this.toggleStateX = !toggleStateX;
        }

        // save the current state for the next loop iteration
        this.previousButtonStateX = currentButtonState;
        return toggleStateX;
    }
    public boolean toggleY(boolean currentButtonState) {
        // this expression is only true for one single loop cycle the moment you press the button.
        // even if you keep holding the button down, it will not trigger again until you release it and press it a second time.
        if (currentButtonState && !previousButtonStateY) {
            // Flip the toggle state
            this.toggleStateY = !toggleStateY;
        }

        // save the current state for the next loop iteration
        this.previousButtonStateY = currentButtonState;
        return toggleStateY;
    }

}
