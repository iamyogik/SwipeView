package com.example.yogesh.swipeview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Yogesh on 30/06/2017.
 */


public class SwipeView extends RelativeLayout implements View.OnTouchListener {
    OnSwipeEventListener mySwipeEventListener;

    Context c;

    private float initialX;


    private float mSwipeDistance=4;
    private Boolean mCanSelect,mActiveState;

//Getters and Setters------------------------------

    public float getSwipeDistance() {
        return mSwipeDistance;
    }

    public void setSwipeDistance(float mswipeDistance) {
        this.mSwipeDistance = mswipeDistance;
    }


    public Boolean getCanSelect() {
        return mCanSelect;
    }

    public void setCanSelect(Boolean mCanSelect) {
        this.mCanSelect = mCanSelect;
        invalidate();
        requestLayout();
    }

    public Boolean getActiveState() {
        return mActiveState;
    }

    public void setActiveState(Boolean mActiveState) {
        this.mActiveState = mActiveState;
    }

    //Constructors----------------------------------
    public SwipeView(Context context) {
        super(context);
        init(context, null, 0, 0);
        c = context;
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
        c = context;
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
        c = context;
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
        c = context;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray mArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeView);

        try {
            mCanSelect = mArray.getBoolean(R.styleable.SwipeView_canSelect, false);
            mActiveState = mArray.getBoolean(R.styleable.SwipeView_activeState, false);
            mSwipeDistance = mArray.getFloat(R.styleable.SwipeView_swipeDistance, 0);

        } catch (Exception e) {

        } finally {
            mArray.recycle();
        }

        setOnTouchListener(this);
    }


////////canSwipe will allow or dissallow the user to swipe and is specifically for me
////////


    @Override
    public boolean onTouch(View view, final MotionEvent motionEvent) {

        if (mActiveState) {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:


                    if (SwipeView.this.mySwipeEventListener != null) {
                        SwipeView.this.mySwipeEventListener.onClick(view, motionEvent);
                    }
                    initialX = motionEvent.getX();
                    break;

                case MotionEvent.ACTION_MOVE:

                  /* if (SwipeView.this.mySwipeEventListener != null) {
                       SwipeView.this.mySwipeEventListener.onSwipe(view, motionEvent);
                    }*/


                    if (motionEvent.getX() - initialX > 0) {
                        setX(getX() + motionEvent.getX() - initialX);
                        invalidate();
                        requestLayout();
                    }

                    break;

                case MotionEvent.ACTION_UP:
                    funcForOnUp(view, motionEvent);

                    break;
                case MotionEvent.ACTION_CANCEL:

                    funcForOnUp(view, motionEvent);
                    break;
            }

        }

        return true;
    }

    private void funcForOnUp(final View view, final MotionEvent motionEvent) {
        if (mCanSelect) {
            int Width = Resources.getSystem().getDisplayMetrics().widthPixels;
            if (getX() > (Width / mSwipeDistance)) {
                if (isSelected()) {
                    setSelected(false);
                    animate().translationX(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            if (SwipeView.this.mySwipeEventListener != null) {
                                SwipeView.this.mySwipeEventListener.onSwipe(view, motionEvent);
                            }
                        }
                    }).start();
                } else {
                    setSelected(true);
                    animate().translationX(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            if (SwipeView.this.mySwipeEventListener != null) {
                                SwipeView.this.mySwipeEventListener.onSelected(view, motionEvent);
                            }
                        }
                    }).start();

                }
            }
        } else {
            int Width = Resources.getSystem().getDisplayMetrics().widthPixels;
            if (getX() > (Width / mSwipeDistance)) {


                animate().translationX(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        if (SwipeView.this.mySwipeEventListener != null) {
                            SwipeView.this.mySwipeEventListener.onSwipe(view, motionEvent);
                        }
                    }
                }).start();
            } else {
                animate().translationX(0).start();
            }


        }
        invalidate();
        requestLayout();
    }


    public void setSwipeEventListener(OnSwipeEventListener eventListener) {
        //setting custom listener from activity
        mySwipeEventListener = eventListener;
    }







}

