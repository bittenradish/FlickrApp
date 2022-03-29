package de.example.challenge.flickrapp.ui;

import android.content.Context;
import android.util.AttributeSet;

import de.example.challenge.flickrapp.R;


public class SelectableButton extends androidx.appcompat.widget.AppCompatButton {
    public SelectableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static final int[] SELECTED_STATE = {R.attr.selected_state};

    private boolean isOnSelectedState = false;

    public void setSelectedState(boolean isOnSelectedState){
        this.isOnSelectedState = isOnSelectedState;
        refreshDrawableState();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if(isOnSelectedState){
            mergeDrawableStates(drawableState, SELECTED_STATE);
        }
        return drawableState;
    }
}
