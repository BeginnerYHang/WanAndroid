package com.yuanhang.wanandroid.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.SparseArray
import android.view.*
import android.view.animation.Interpolator
import android.widget.OverScroller
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * created by yuanhang on 2022/3/18
 * description: 自定义ViewGroup实现可滚动的流式布局
 * (目前放在RecyclerView中还是会调用多(2)次onLayout的问题)
 */
private const val TAG = "FlowLayout"
class FlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val mRects = SparseArray<Rect>()
    //初始化滚动状态为静止状态
    private var mScrollState = SCROLL_STATE_IDLE
    private var mLastTouchY = 0
    private var mLastInterceptY = 0
    private var mContentY = 0
    /**
     * (待复习)处理多根手指滑动时指明根据哪根手指进行滑动:
     * 以新加入的手指位置、当有一根手指抬起时,以剩下的手指为准
     */
    private var mScrollPointerId = INVALID_POINTER
    //处理惯性滑动
    private var mViewFlinger = ViewFlinger()
    private var mVelocityTracker: VelocityTracker? = null
    private val mViewConfiguration = ViewConfiguration.get(context)
    private var isInit = false

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            childView.layout(mRects[i].left, mRects[i].top, mRects[i].right, mRects[i].bottom)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mRects.clear()
        isInit = false
        // wrap_content时记录自身所需宽高
        var needWidth = 0
        var hasUsedWidth = paddingLeft + paddingRight
        var hasUsedHeight = paddingTop + paddingBottom
        val selfWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val selfWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val selfHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        val selfHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            val childMeasureWidth = childView.measuredWidth
            val childMeasureHeight = childView.measuredHeight
            val childMarginLeft = (childView.layoutParams as MarginLayoutParams).leftMargin
            val childMarginRight = (childView.layoutParams as MarginLayoutParams).rightMargin
            val childMarginTop = (childView.layoutParams as MarginLayoutParams).topMargin
            val childMarginBottom = (childView.layoutParams as MarginLayoutParams).bottomMargin
            if (!isInit && childView.visibility != View.GONE) {
                isInit = true
                hasUsedHeight += childMeasureHeight + childMarginTop + childMarginBottom
            }
            //判断是否换行
            if (childMeasureWidth + childMarginLeft + childMarginRight > selfWidthSize - hasUsedWidth) {
                //child需要的空间>父view剩余空间
                //需要换行
                hasUsedWidth = paddingLeft + paddingRight
                mRects.put(
                    i,
                    Rect(
                        hasUsedWidth + childMarginLeft,
                        hasUsedHeight + childMarginTop,
                        hasUsedWidth + childMeasureWidth + childMarginLeft,
                        hasUsedHeight + childMarginTop + childMeasureHeight
                    )
                )
                hasUsedWidth += childMeasureWidth + childMarginLeft + childMarginRight
                hasUsedHeight += childMeasureHeight + childMarginTop + childMarginBottom
                needWidth = max(needWidth, hasUsedWidth)
            } else {
                // 不需要换行
                hasUsedWidth += childMarginLeft
                mRects.put(
                    i,
                    Rect(
                        hasUsedWidth,
                        hasUsedHeight - childMarginBottom - childMeasureHeight,
                        hasUsedWidth + childMeasureWidth,
                        hasUsedHeight - childMarginBottom
                    )
                )
                hasUsedWidth += childMeasureWidth + childMarginRight
                needWidth = max(needWidth, hasUsedWidth)
            }
        }
        val realWidth = if (selfWidthMode == MeasureSpec.EXACTLY) selfWidthSize else needWidth
        val realHeight =
            if (selfHeightMode == MeasureSpec.EXACTLY) selfHeightSize else hasUsedHeight
        mContentY = hasUsedHeight
        setMeasuredDimension(realWidth, realHeight)
    }

    // 使子View布局可以支持Margin属性
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mScrollState = SCROLL_STATE_IDLE
                mScrollPointerId = ev.getPointerId(0)
                mLastTouchY = (ev.y + 0.5f).toInt()
                mLastInterceptY = (ev.y + 0.5f).toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val moveSlop = abs(ev.y - mLastInterceptY)
                if (moveSlop > mViewConfiguration.scaledTouchSlop) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        var eventAddedToVelocityTracker = false
        val actionIndex = event.actionIndex
        val motionEvent = MotionEvent.obtain(event)
        when(event.action) {
            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(mScrollPointerId)
                if (index < 0) {
                    return false
                }
                var dy = (event.y + 0.5f).toInt() - mLastTouchY
                if (mScrollState != SCROLL_STATE_DRAGGING) {
                    var startScroll = false
                    if (abs(dy) > mViewConfiguration.scaledTouchSlop) {
                        // 将判定为滚动的这段位置减掉
                        if (dy > 0) {
                            dy -= mViewConfiguration.scaledTouchSlop
                        } else {
                            dy += mViewConfiguration.scaledTouchSlop
                        }
                        startScroll = true
                    }
                    if (startScroll) {
                        mScrollState = SCROLL_STATE_DRAGGING
                    }
                }
                //开始滚动后不需要再判断是否滚动,直接移动View
                if (mScrollState == SCROLL_STATE_DRAGGING) {
                    mLastTouchY = (event.y + 0.5f).toInt()
                    constrainScrollBy(0, -dy)
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                if (event.getPointerId(actionIndex) == mScrollPointerId) {
                    val newIndex = if (actionIndex == 0) 1 else 0
                    mScrollPointerId = event.getPointerId(newIndex)
                    mLastTouchY = (event.getY(newIndex) + 0.5f).toInt()
                }
            }
            MotionEvent.ACTION_UP -> {
                mVelocityTracker?.addMovement(motionEvent)
                eventAddedToVelocityTracker = true
                mVelocityTracker?.computeCurrentVelocity(1000, mViewConfiguration.scaledMaximumFlingVelocity.toFloat())
                var yVelocity = -mVelocityTracker!!.getYVelocity(mScrollPointerId)
                yVelocity = if (abs(yVelocity) < mViewConfiguration.scaledMinimumFlingVelocity) {
                    0f
                } else {
                    //限制速度在[-max,max]之间
                    max(-mViewConfiguration.scaledMaximumFlingVelocity.toFloat(), min(yVelocity, mViewConfiguration.scaledMaximumFlingVelocity.toFloat()))
                }
                if (yVelocity != 0f) {
                    mViewFlinger.fling(yVelocity.toInt())
                } else {
                    mScrollState = SCROLL_STATE_IDLE
                }
                mVelocityTracker?.clear()
            }
            MotionEvent.ACTION_CANCEL -> {
                mVelocityTracker?.clear()
            }
        }
        if (!eventAddedToVelocityTracker) {
            mVelocityTracker?.addMovement(motionEvent)
        }
        motionEvent.recycle()
        return true
    }

    /**
     * TODO: 滑动到边界位置限制滑动
     *
     * @param dx
     * @param dy
     */
    private fun constrainScrollBy(dx: Int, dy: Int) {
        val viewPort = Rect()
        getGlobalVisibleRect(viewPort)
        var constrainDy = dy
        //只需要限制上下边界
        if (mContentY - scrollY - constrainDy < height) {
            constrainDy = mContentY - scrollY - height
        }
        if (scrollY + constrainDy < 0) {
            constrainDy = -scrollY
        }
        scrollBy(dx, constrainDy)
    }

    /**
     * TODO:处理惯性滑动
     */
    inner class ViewFlinger: Runnable {

        private var mLastFlingY = 0
        private var mScroller: OverScroller = OverScroller(context, sQuinticInterpolator)
        private var mEatRunOnAnimationRequest = false
        private var mReSchedulePostAnimationCallback = false

        override fun run() {
            disableRunOnAnimationRequests()
            if (mScroller.computeScrollOffset()) {
                val dy = mScroller.currY - mLastFlingY
                constrainScrollBy(0, dy)
                mLastFlingY = mScroller.currY
                doScroll()
            }
            enableRunOnAnimationRequests()
        }

        fun fling(velocityY: Int) {
            mLastFlingY = 0
            mScrollState = SCROLL_STATE_SETTING
            mScroller.fling(0,0,0, velocityY, Int.MIN_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MAX_VALUE)
            doScroll()
        }

        fun stop() {
            removeCallbacks(this)
            mScroller.abortAnimation()
        }

        private fun disableRunOnAnimationRequests() {
            mReSchedulePostAnimationCallback = false
            mEatRunOnAnimationRequest = true
        }

        private fun enableRunOnAnimationRequests() {
            mEatRunOnAnimationRequest = false
            if (mReSchedulePostAnimationCallback) {
                doScroll()
            }
        }

        private fun doScroll() {
            if (mEatRunOnAnimationRequest) {
                mReSchedulePostAnimationCallback = true
            } else {
                removeCallbacks(this)
                postOnAnimation(this)
            }
        }

    }

    companion object {
        const val SCROLL_STATE_IDLE = 0
        const val SCROLL_STATE_DRAGGING = 1
        const val SCROLL_STATE_SETTING = 2
        const val INVALID_POINTER = -1

        val sQuinticInterpolator = Interpolator { input ->
            var value = input
            value -= 1f
            value * value * value * value * value + 1f
        }
    }
}