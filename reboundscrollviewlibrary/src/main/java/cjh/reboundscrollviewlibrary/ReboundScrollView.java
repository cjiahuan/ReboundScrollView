package cjh.reboundscrollviewlibrary;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by chenjiahuan on 16/7/11.
 */
public class ReboundScrollView extends ScrollView {

    private static final String TAG = "ReboundScrollView";

    private static final float MOVE_DELAY = 0.3f;
    private static final int ANIM_TIME = 300;

    public interface MyScrollListener {
        void onMyScrollEvent(int action, float y);
    }

    private MyScrollListener listener;

    private View childView;
    private boolean canPullUp;
    private boolean canPullDown;
    private boolean havaMoved;

    private int changeY;

    private Rect originalRect = new Rect();

    private float startY;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");

    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 2);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        if (listener != null) {
            listener.onMyScrollEvent(-1, scrollY);
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onlayout");
        if (getChildCount() > 0)
            childView = getChildAt(0);
        if (childView == null)
            return;

        originalRect.set(childView.getLeft(), childView.getTop(),
                childView.getRight(), childView.getBottom());
    }


    public ReboundScrollView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReboundScrollView(Context context) {
        super(context);
    }


    /**
     * 在触摸事件中, 处理上拉和下拉的逻辑
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (childView == null) {
            return super.dispatchTouchEvent(ev);
        }

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();

                startY = ev.getY();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!havaMoved)
                    break;

                TranslateAnimation anim = new TranslateAnimation(0, 0,
                        childView.getTop(), originalRect.top);
                anim.setDuration(ANIM_TIME);

                childView.startAnimation(anim);
                // 将标志位设回false
                canPullDown = false;
                canPullUp = false;
                havaMoved = false;
                resetViewLayout();
                if (listener != null) {
                    listener.onMyScrollEvent(action, changeY);
                }
                break;
            case MotionEvent.ACTION_MOVE:

                if (!canPullDown && !canPullUp) {
                    startY = ev.getY();
                    canPullDown = isCanPullDown();
                    canPullUp = isCanPullUp();

                    break;
                }

                float nowY = ev.getY();
                int deltaY = (int) (nowY - startY);
                changeY = deltaY;
                boolean shouldMove = (canPullDown && deltaY > 0)
                        || (canPullUp && deltaY < 0) || (canPullUp && canPullDown);

                if (shouldMove) {
                    int offset = (int) (deltaY * MOVE_DELAY);

                    childView.layout(originalRect.left, originalRect.top + offset,
                            originalRect.right, originalRect.bottom + offset);

                    havaMoved = true;
                }

                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    public void resetViewLayout() {
        childView.layout(originalRect.left, originalRect.top,
                originalRect.right, originalRect.bottom);
    }

    public void setListener(MyScrollListener listener) {
        this.listener = listener;
    }

    /**
     * 判断是否滚动到顶部
     */
    private boolean isCanPullDown() {
        return getScrollY() == 0
                || childView.getHeight() < getHeight() + getScrollY();
    }

    /**
     * 判断是否滚动到底部
     */
    private boolean isCanPullUp() {
        return childView.getHeight() <= getHeight() + getScrollY();
    }

}
