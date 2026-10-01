package org.firstinspires.ftc.teamcode;

public class ToggleButton {
    boolean toggleState;

    boolean previousButtonState;


    public ToggleButton(boolean initToggleState) {
        this.toggleState = initToggleState;
    }
    public boolean toggle(boolean currentButtonState) {
        // this expression is only true for one single loop cycle the moment you press the button.
        // even if you keep holding the button down, it will not trigger again until you release it and press it a second time.
        if (currentButtonState && !previousButtonState) {
            // Flip the toggle state
            this.toggleState = !toggleState;
        }

        // save the current state for the next loop iteration
        this.previousButtonState = currentButtonState;
        return toggleState;
    }

    public boolean currentStatus() {
        return toggleState;
    }
}
