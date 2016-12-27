package com.jamin.android.demo.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.jamin.android.demo.R;
import com.jamin.framework.util.LogUtil;
import com.jamin.framework.widget.IconFontTextView;

public class LikeButton extends View {


    /**
     * Reference size is bellow, this component will be resize by real canvas size.
     * TODO only support square for now!!
     */
    private static final float REF_CANVAS_SIZE = 76f;//dp
    private static final float HEART_SIZE = 24;//dp
    private static final float CIRCLE_RADIUS = 20;//dp

    private static final int HEART_COLOR = Color.parseColor("#FF6868");

    private enum Status {
        LIKED,
        UNLIKE,
        ANIMATING
    }

    private Status mStatus = Status.UNLIKE;
    private Status mNextStatus = null;
    private final Typeface mFont;

    public LikeButton(Context context) {
        this(context, null, 0);
    }

    public LikeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFont = IconFontTextView.getFont(context);
    }

    /**
     * Check button is animating.
     * If yes, button can't change state to like/unlike.
     */
    public boolean isAnimating() {
        return mStatus == Status.ANIMATING;
    }

    @UiThread
    public boolean setLike() {
        return setLike(false);
    }

    @UiThread
    public boolean setLike(boolean animation) {

        LogUtil.d("setLike: anim:" + animation + ", status:" + mStatus);
        if (mStatus != Status.UNLIKE) {
            return false;
        }
        if (animation) {
            mNextStatus = Status.LIKED;
            mStatus = Status.ANIMATING;
            startLikeAnimation(LIKE_ANIMATION_INFO);
        } else {
            mStatus = Status.LIKED;
        }
        invalidate();
        return true;
    }

    /**
     * Reset the like state if bind this view
     * Cancel all animation and release all listener
     */
    @UiThread
    public void reset() {
        LogUtil.d("reset");
        stopLikeAnimation(LIKE_ANIMATION_INFO);
        stopLikeAnimation(DISLIKE_ANIMATION_INFO);
        mStatus = Status.UNLIKE;
        mNextStatus = null;
    }

    @UiThread
    public boolean setUnLike() {
        return setUnLike(false);
    }

    @UiThread
    public boolean setUnLike(boolean animation) {
        LogUtil.d("setUnLike: anim:" + animation + ", status:" + mStatus);
        if (mStatus != Status.LIKED) {
            return false;
        }

        if (animation) {
            mNextStatus = Status.UNLIKE;
            mStatus = Status.ANIMATING;
            startLikeAnimation(DISLIKE_ANIMATION_INFO);
        } else {
            mStatus = Status.UNLIKE;
        }
        invalidate();
        return true;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null || mFont == null || canvas.getWidth() <= 0 || canvas.getHeight() <= 0) {
            LogUtil.d("onDraw: not draw" + (canvas == null) + ", " + (mFont == null));
            return;
        }

        mRefSize = canvas.getWidth();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(mFont);

        //paint shadow anyway
        paintShadow(canvas, paint);

        if (mStatus == Status.LIKED) {
            //circle
            paint.setColor(HEART_COLOR);
            paintCircle(canvas, paint, (int) CIRCLE_RADIUS);

            //Big heart
            paint.setColor(Color.WHITE);
            paintBigHeart(canvas, paint, HEART_SIZE, R.string.iconfont_like_solid);

        } else if (mStatus == Status.UNLIKE) {
            paintUnlikeHeart(canvas, paint, HEART_SIZE);
        } else if (mStatus == Status.ANIMATING) {
            if (mNextStatus == Status.LIKED) {
                for (LikeAnimationInfo info : LIKE_ANIMATION_INFO) {
                    info.paintCanvas(canvas, paint);
                }
            } else if (mNextStatus == Status.UNLIKE) {
                for (LikeAnimationInfo info : DISLIKE_ANIMATION_INFO) {
                    info.paintCanvas(canvas, paint);
                }
            }
        }
        paint.reset();
    }

    private void paintShadow(Canvas canvas, Paint paint) {
        //circle && circle shadow
        //TODO software will re-call onDraw continuously
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint.setShadowLayer(getRelativeSize(2), 0, getRelativeSize(2), Color.parseColor("#20000000"));
        paint.setColor(Color.WHITE);
        paintCircle(canvas, paint, (int) CIRCLE_RADIUS);
        paint.clearShadowLayer();
    }

    private void paintUnlikeHeart(Canvas canvas, Paint paint, float heartSize) {
        paintUnlikeHeart(canvas, paint, heartSize, 1);
    }

    private void paintUnlikeHeart(Canvas canvas, Paint paint, float heartSize, float alpha) {
        int colorStroke = Color.parseColor("#80262626");
        //Big heart
        paint.setColor(colorStroke);

        int alphaVal = (int) (alpha * Color.alpha(colorStroke));
        LogUtil.d("paintUnlikeHeart: alpha " + alphaVal);
        paint.setAlpha(alphaVal);
        paintBigHeart(canvas, paint, heartSize, R.string.iconfont_like_stroke);
    }

    private void paintBigHeart(Canvas canvas, Paint paint, float heartSize, int resFont) {
        int refHeartSize = getRelativeSize(heartSize);
        paint.setTextSize(refHeartSize);
        Point center = getCircleCenter();
        float heartPosX = center.x - heartSize / 2;
        float heartPosY = center.y + heartSize / 2;//let heart in circle's center
        Point relativePos = getRelativePoint(heartPosX, heartPosY);

        canvas.drawText(getResources().getString(resFont), relativePos.x, relativePos.y, paint);
    }

    private void paintCircle(Canvas canvas, Paint paint, int radius) {
        paintCircle(canvas, paint, radius, 1f);
    }

    private void paintCircle(Canvas canvas, Paint paint, int radius, float alpha) {
        //circle
        Point center = getCircleCenter();
        Point cPos = getRelativePoint(center.x, center.y);
        paint.setAlpha((int) (alpha * 0xFF));
        canvas.drawCircle(cPos.x, cPos.y, getRelativeSize(radius), paint);
    }

    private float mRefSize = REF_CANVAS_SIZE;

    private Point getRelativePoint(float x, float y) {
        return new Point((int) (mRefSize * x / REF_CANVAS_SIZE), (int) (mRefSize * y / REF_CANVAS_SIZE));
    }

    private int getRelativeSize(float size) {
        return (int) (mRefSize * size / REF_CANVAS_SIZE);
    }

    private Point getCircleCenter() {
        //circle
        int centerX = (int) (REF_CANVAS_SIZE / 2);
        int centerY = (int) (REF_CANVAS_SIZE - 8 - CIRCLE_RADIUS);
        return new Point(centerX, centerY);
    }

    /**
     * The animation set for dislike.
     */
    private LikeAnimationInfo[] DISLIKE_ANIMATION_INFO = new LikeAnimationInfo[]{
            new DislikeAnimInfo((int) HEART_SIZE, (int) CIRCLE_RADIUS, 0, 300, 1f),
    };

    /**
     * The animation set for like, the first item will be draw at the bottom of z-order
     */
    private LikeAnimationInfo[] LIKE_ANIMATION_INFO = new LikeAnimationInfo[]{
            //like stroke (unlike)
            new LikeStrokeInfo((int) HEART_SIZE, 0, 300, 1f),
            //Red circle
            new LikeRedCircleInfo((int) CIRCLE_RADIUS, 200, 300, 1f),

            //Path 1
            new LikePathInfo(8, 500, 500, 0.8f,
                    new Point(28, 40), new Point(24, 36),
                    new Point(16, 40), new Point(10, 28)),

            //Path 2
            new LikePathInfo(10, 600, 600, 0.6f,
                    new Point(40, 36), new Point(24, 24),
                    new Point(40, 16), new Point(26, 6)),

            //Path 3
            new LikePathInfo(8, 800, 600, 0.8f,
                    new Point(40, 36), new Point(38, 28),
                    new Point(48, 24), new Point(42, 12)),

            //Path 4
            new LikePathInfo(10, 500, 500, 0.6f,
                    new Point(50, 36), new Point(60, 30),
                    new Point(50, 22), new Point(60, 10)),

            //Path 5
            new LikePathInfo(8, 700, 600, 0.4f,
                    new Point(54, 46), new Point(62, 42),
                    new Point(64, 38), new Point(64, 30)),

            //white heart
            new LikeWhiteHeartInfo((int) HEART_SIZE, 200, 1000, 1f),


    };

    /**
     * Check all animation is ready or not.
     */
    private boolean checkAllAnimationReady(@NonNull LikeAnimationInfo... params) {
        for (final LikeAnimationInfo param : params) {
            if (param.isAnimating()) {
                LogUtil.d("checkAllAnimationReady: not finished " + param.hashCode());
                return false;
            }
        }
        LogUtil.d("checkAllAnimationReady: ready");
        return true;
    }

    private void stopLikeAnimation(final @NonNull LikeAnimationInfo... params) {
        for (final LikeAnimationInfo param : params) {
            param.setAnimationListener(null, null);
            param.releaseAnimation(false);
        }
    }

    private void startLikeAnimation(final @NonNull LikeAnimationInfo... params) {
        for (final LikeAnimationInfo param : params) {
            param.setAnimationListener(this, new AnimationListener() {
                @Override
                public void onAnimationEnd() {
                    //Check all animation is ready or not.
                    //If yes, sync current state to next.
                    if (checkAllAnimationReady(params) && mStatus == Status.ANIMATING) {
                        mStatus = mNextStatus;
                        mNextStatus = null;
                    }
                }
            });
            param.startAnimation();
        }
    }

    private interface AnimationListener {
        void onAnimationEnd();
    }

    private abstract class LikeAnimationInfo {
        private boolean mAnimating = false;
        float progress = 0f; /* 0 - 1f */
        final int size;
        private final int delayStartMs;
        protected final int durationMs;
        final float alpha;
        private Animator mAnimator = null;
        private AnimationListener mAnimListener = null;
        protected View mHostView = null;

        public LikeAnimationInfo(int size, int delayStartMs, int durationMs, float alpha) {
            this.size = size;
            this.delayStartMs = delayStartMs;
            this.durationMs = durationMs;
            this.alpha = alpha;
        }

        public void setAnimationListener(View hostView, AnimationListener listener) {
            mAnimListener = listener;
            mHostView = hostView;
        }

        public boolean isAnimating() {
            return mAnimating;
        }

        protected boolean isNeedDraw() {
            return isAnimating();
        }

        protected abstract Animator generateAnimator();

        public void paintCanvas(Canvas canvas, Paint paint) {
            if (isNeedDraw()) {
                doPaintCanvas(canvas, paint);
            }
        }

        public abstract void doPaintCanvas(Canvas canvas, Paint paint);

        public void startAnimation() {
            if (!mAnimating && mAnimator == null) {
                progress = 0f;
                mAnimator = generateAnimator();
                mAnimator.setStartDelay(delayStartMs);
                mAnimator.setDuration(durationMs);
                mAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        LogUtil.d("onAnimationStart: " + this.hashCode());
                        mAnimating = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        LogUtil.d("onAnimationEnd: " + this.hashCode());
                        releaseAnimation(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        LogUtil.d("onAnimationCancel: " + this.hashCode());
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        LogUtil.d("onAnimationRepeat: " + this.hashCode());
                    }
                });
                mAnimator.start();
            }
        }

        public void releaseAnimation(boolean notifyListener) {
            if (mAnimator != null) {
                mAnimator.removeAllListeners();
                if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                        mAnimator.isStarted()) || mAnimator.isRunning()) {
                    mAnimator.cancel();
                }
            }
            mAnimating = false;
            if (notifyListener && mAnimListener != null) {
                mAnimListener.onAnimationEnd();
            }
            progress = 1f;
            mAnimListener = null;
            mAnimator = null;
        }
    }

    private abstract class LikeProgressInfo extends LikeAnimationInfo {
        public LikeProgressInfo(int size, int delayStartMs, int durationMs, float alpha) {
            super(size, delayStartMs, durationMs, alpha);
        }

        @Override
        protected Animator generateAnimator() {
            final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
//                    currentPlayTime = animator.getCurrentPlayTime();
                    if (mHostView != null && animation.getAnimatedValue() instanceof Float) {
                        progress = (float) animation.getAnimatedValue();
                        LogUtil.d("onAnimationUpdate: " + progress);
                        mHostView.invalidate();
                    }
                }
            });
            return animator;
        }
    }

    private class DislikeAnimInfo extends LikeProgressInfo {
        private final int heartSize;

        public DislikeAnimInfo(int heartSize, int circleSize, int delayStartMs, int durationMs, float alpha) {
            super(circleSize, delayStartMs, durationMs, alpha);
            this.heartSize = heartSize;
        }

        @Override
        public void doPaintCanvas(Canvas canvas, Paint paint) {
//            int alphaVal = (int) ((1-progress)*0xFF);
            //circle
//            paint.setColor(HEART_COLOR);
//            //like status
//            paint.setAlpha(alphaVal);
//            paintCircle(canvas, paint, size, size);
//            //draw white heart
//            paint.setColor(Color.WHITE);
//            paint.setAlpha(alphaVal);
//            paintBigHeart(canvas, paint, heartSize, R.string.iconfont_like_solid);

//            //dislike status
            paintUnlikeHeart(canvas, paint, heartSize, progress);
        }
    }

    private class LikeStrokeInfo extends LikeProgressInfo {

        public LikeStrokeInfo(int size, int delayStartMs, int durationMs, float alpha) {
            super(size, delayStartMs, durationMs, alpha);
        }

        @Override
        public void doPaintCanvas(Canvas canvas, Paint paint) {
            float blendingAlpha = alpha;
            float shrinkSize = size;
            if (progress < 0.6667f) {
                shrinkSize *= (1 - 0.6f * progress); // 1 --> 0.6 @ 0- 200ms/0-67%
            } else {
                shrinkSize *= 0.6f; // 0.6f after 66%
            }

            if (progress > 0.3333f) {
                blendingAlpha = (1 - progress) / (1 - 0.3333f); // 1---> 0 @ 100 - 300ms/33%-100%
            }
            paint.setAlpha((int) (blendingAlpha * 0xFF));
            paintUnlikeHeart(canvas, paint, shrinkSize);
        }
    }

    private class LikeRedCircleInfo extends LikeProgressInfo {

        public LikeRedCircleInfo(int size, int delayStartMs, int durationMs, float alpha) {
            super(size, delayStartMs, durationMs, alpha);
        }

        @Override
        public void doPaintCanvas(Canvas canvas, Paint paint) {
            //circle
            paint.setColor(HEART_COLOR);
            paintCircle(canvas, paint, (int) (size * progress));
        }

        protected boolean isNeedDraw() {
            return super.isNeedDraw() || progress >= 1f;
        }
    }

    private class LikeWhiteHeartInfo extends LikeProgressInfo {

        public LikeWhiteHeartInfo(int size, int delayStartMs, int durationMs, float alpha) {
            super(size, delayStartMs, durationMs, alpha);
        }

        @Override
        public void doPaintCanvas(Canvas canvas, Paint paint) {
            float ratio;
            if (progress < 0.3) {
                ratio = 1.2f * progress / 0.3f; //0-1.2 @ 0-300ms
            } else if (progress < 0.600) {
                ratio = 1.2f - 0.2f * (progress - 0.3f) / 0.3f; //1.2 - 1 @ 300-600ms
            } else if (progress < 0.800) {
                ratio = 1f + 0.1f * (progress - 0.6f) / 0.2f; //1-1.1 @ 600-800ms
            } else if (progress < 1) {
                ratio = 1.1f - 0.1f * (progress - 0.8f) / 0.2f; //1.1-1 @ 800-1000ms
            } else {
                ratio = 1f;
            }
            //draw white heart
            paint.setColor(Color.WHITE);
            paintBigHeart(canvas, paint, ratio * size, R.string.iconfont_like_solid);
        }

        protected boolean isNeedDraw() {
            return super.isNeedDraw() || progress >= 1f;
        }
    }

    private class LikePathInfo extends LikeAnimationInfo {
        final float[] currentPt;
        final Path path;

        public LikePathInfo(int size, int delayStartMs, int durationMs, float alpha, Point start, Point cubicPt1, Point cubicPt2, Point end) {
            super(size, delayStartMs, durationMs, alpha);
            this.path = new Path();
            path.moveTo(start.x, start.y);
//            path.lineTo(end.x, end.y);
            path.cubicTo(cubicPt1.x, cubicPt1.y, cubicPt2.x, cubicPt2.y, end.x, end.y);
            currentPt = new float[]{start.x, start.y};
        }

        @Override
        protected Animator generateAnimator() {
            //Compatible way to implement path animation
            final PathMeasure pathMeasure = new PathMeasure(path, false);
            final float pathLen = pathMeasure.getLength();
            ValueAnimator animator = ValueAnimator.ofFloat(0, pathLen);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedValue() instanceof Float) {
                        float val = (float) animation.getAnimatedValue();
                        progress = val / pathLen;
                        //update current position
                        pathMeasure.getPosTan(val, currentPt, null);

                        LogUtil.d("onAnimationUpdate: " + progress);
                        invalidate();
                    }
                }
            });
            return animator;
        }

        @Override
        public void doPaintCanvas(Canvas canvas, Paint paint) {
            paint.setColor(HEART_COLOR);
            paint.setTextSize(getRelativeSize(size));
            float blendingThreshold = 0.7f;
            float alphaRatio = alpha;
            if (progress > blendingThreshold) {
                alphaRatio *= (1 - progress) / (1 - blendingThreshold);
            }
            paint.setAlpha((int) (alphaRatio * 0xFF));
            //current position means center pt, we use icon font so need offset
            Point pos = getRelativePoint(currentPt[0] - size / 2, currentPt[1] + size / 2);
            canvas.drawText(getResources().getString(R.string.iconfont_like_solid),
                    pos.x, pos.y, paint);
        }
    }
}
