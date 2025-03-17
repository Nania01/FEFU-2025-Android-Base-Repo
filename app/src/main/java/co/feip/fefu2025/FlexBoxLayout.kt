package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val horizontalSpace = 16
    private val verticalSpace = 16

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var totalHeight = 0
        var lineWidth = 0
        var lineHeight = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE) continue

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin + horizontalSpace
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin

            if (lineWidth + childWidth > widthSize) {
                totalHeight += lineHeight + verticalSpace
                lineWidth = childWidth
                lineHeight = childHeight
            } else {
                lineWidth += childWidth
                lineHeight = maxOf(lineHeight, childHeight)
            }

            if (i == childCount - 1) {
                totalHeight += lineHeight
            }
        }

        val finalWidth = if (widthMode == MeasureSpec.EXACTLY) widthSize else lineWidth
        val finalHeight = if (heightMode == MeasureSpec.EXACTLY) heightSize else totalHeight
        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val parentWidth = r - l
        var currentLeft = 0
        var currentTop = 0
        var lineHeight = 0
        val childrenInLine = mutableListOf<View>()

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE) continue

            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin + horizontalSpace
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin

            if (currentLeft + childWidth > parentWidth) {
                layoutLine(childrenInLine, currentLeft, currentTop, lineHeight)
                currentTop += lineHeight + verticalSpace
                currentLeft = 0
                lineHeight = 0
                childrenInLine.clear()
            }

            childrenInLine.add(child)
            currentLeft += childWidth
            lineHeight = maxOf(lineHeight, childHeight)
        }

        if (childrenInLine.isNotEmpty()) {
            layoutLine(childrenInLine, currentLeft, currentTop, lineHeight)
        }
    }

    private fun layoutLine(views: List<View>, usedWidth: Int, top: Int, lineHeight: Int) {
        var left = 0
        for (child in views) {
            if (child.visibility == View.GONE) continue
            val lp = child.layoutParams as MarginLayoutParams

            val childW = child.measuredWidth
            val childH = child.measuredHeight

            val cl = left + lp.leftMargin
            val ct = top + lp.topMargin
            val cr = cl + childW
            val cb = ct + childH

            child.layout(cl, ct, cr, cb)
            left += childW + lp.leftMargin + lp.rightMargin + horizontalSpace
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }
}