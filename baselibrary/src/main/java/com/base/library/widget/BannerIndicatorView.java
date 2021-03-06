/*
使用  https://www.jianshu.com/p/fbf8f24fb3f0
<com.base.library.widget.BannerIndicatorView
        xmlns:app="http://schemas.android.com/apk/res-auto"

        android:id="@+id/indicator_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="10dp"
        android:background="#20f0"

        app:app_cell_count="8"
        app:app_current_position="2"
        app:app_cell_radius="8dp"
        app:app_cell_margin="10dp"
        app:app_normal_color="#f00"
        app:app_selected_color="#00f"
        app:app_indicator_style="CIRCLE"
        />
 */
package com.base.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import com.base.library.R;

/***
 * Banner的当前索引指示器自定义View
 */
public class BannerIndicatorView extends View {
    public static final int INDICATOR_STYLE_CIRCLE = 0;
    public static final int INDICATOR_STYLE_RECT = 1;

    /*** 指示器中的总数**/
    private int cellCount = 0;
    /*** 当前选中的位置**/
    private int currentPosition;
    /*** 小点之间的间距*/
    private int cellMarginSize = 10;
    /*** 指示器元素的半径(注意是半径，不是直径)**/
    private int cellRadius = 10;
    /****未选中的颜色*/
    private int normalColor;
    /*** 选中的颜色**/
    private int selectedColor;
    /*** 小点的样式，默认是圆形*/
    private int indicatorStyle = INDICATOR_STYLE_CIRCLE;

    private Paint paint;

    public BannerIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.BannerIndicatorView);
        cellCount = t.getInt(R.styleable.BannerIndicatorView_app_cell_count, 0);
        currentPosition = t.getInt(R.styleable.BannerIndicatorView_app_current_position, 0);
        indicatorStyle = t.getInt(R.styleable.BannerIndicatorView_app_indicator_style, INDICATOR_STYLE_CIRCLE);

        cellRadius = t.getDimensionPixelOffset(R.styleable.BannerIndicatorView_app_cell_radius, 10);
        cellMarginSize = t.getDimensionPixelOffset(R.styleable.BannerIndicatorView_app_cell_margin, 10);

        normalColor = t.getColor(R.styleable.BannerIndicatorView_app_normal_color, Color.WHITE);
        selectedColor = t.getColor(R.styleable.BannerIndicatorView_app_selected_color, 0xfff0f0f0);
        t.recycle();
    }

    public int getCellCount() {
        return cellCount;
    }

    /***
     * 设置当前所要显示的总数
     * @param cellCount
     */
    public void setCellCount(int cellCount) {
        this.cellCount = cellCount;
        invalidate();
    }

    /**
     * 返回当前的位置
     * @return
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /***
     * 设置当前选中的位置
     * @param currentPosition
     */
    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        invalidate();
    }

    /***
     * 和ViewPager绑定，这样可以和ViewPager进行联动了
     * @param viewPager 需要绑定的ViewPager
     * @param count 传入ViewPager中实际的Item个数
     */
    public void bindWithViewPager(ViewPager viewPager, int count){
        cellCount = count;
        if (viewPager != null){
            currentPosition = viewPager.getCurrentItem();
            viewPager.addOnPageChangeListener(onPageChangeListener);
        }
    }

    /***和绑定ViewPager，这样可以和ViewPager进行联动了*/
    public void bindWithViewPager(ViewPager viewPager){
        if (viewPager != null){
            cellCount = viewPager.getAdapter().getCount();
            currentPosition = viewPager.getCurrentItem();
            viewPager.addOnPageChangeListener(onPageChangeListener);
        }
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (cellCount > 0){
                setCurrentPosition(position % cellCount);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCells(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 重新测量当前界面的宽度
        int width = getPaddingLeft() + getPaddingRight() + cellRadius * 2 * cellCount + cellMarginSize * (cellCount - 1);
        int height = getPaddingTop() + getPaddingBottom() + cellRadius * 2;
        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**绘制当前的指示器*/
    private void drawCells(Canvas canvas) {
        for (int i = 0; i < cellCount; i++) {
            if (i == currentPosition) {
                paint.setColor(selectedColor);
            } else {
                paint.setColor(normalColor);
            }
            int left = getPaddingLeft() + i * cellRadius * 2 + cellMarginSize * i;

            if (indicatorStyle == INDICATOR_STYLE_CIRCLE) {
                canvas.drawCircle(left + cellRadius, getHeight() / 2, cellRadius, paint);
            } else if (indicatorStyle == INDICATOR_STYLE_RECT) {
                Rect rect = new Rect();
                rect.left = left;
                rect.right = left + cellRadius * 2;
                rect.top = getPaddingTop();
                rect.bottom = rect.top + cellRadius * 2;
                canvas.drawRect(rect, paint);
            }
        }
    }
}