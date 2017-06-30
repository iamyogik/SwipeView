package com.example.yogesh.swipeview;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Yogesh on 30/06/2017.
 */
public interface OnSwipeEventListener {

    public void onClick(View view, MotionEvent motionEvent);

    public void onSwipe(View view, MotionEvent motionEvent);

    void onSelected(View view, MotionEvent motionEvent);
}
