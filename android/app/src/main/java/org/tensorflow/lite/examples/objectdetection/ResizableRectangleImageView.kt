package org.tensorflow.lite.examples.objectdetection

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import android.graphics.RectF
import android.graphics.Color
import kotlin.math.max
import kotlin.math.min

class ResizableRectangleImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var rect: RectF = RectF(100f, 100f, 300f, 300f) // 초기 사각형 위치와 크기 설정

    private val paint: Paint = Paint().apply {
        color = 0xFFFF0000.toInt() // 빨간색
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    private var currentMode = NONE
    private val cornerSize = 50
    private var lastTouchX: Float = 0f
    private var lastTouchY: Float = 0f
    init {
        // 이미지 뷰의 크기 조절을 활성화
        scaleType = ScaleType.FIT_XY
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 사각형 그리기
        canvas.drawRect(rect, paint)
        // 모서리에 원 그리기
        drawCornerCircles(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 사용자가 사각형의 모서리를 터치했는지 확인
                currentMode = when {
                    inCorner(event.x, event.y, rect.left, rect.top) -> RESIZE_TOP_LEFT
                    inCorner(event.x, event.y, rect.right, rect.bottom) -> RESIZE_BOTTOM_RIGHT
                    inRectangle(event.x, event.y, rect) -> MOVE // 새로운 모드 추가
                    else -> NONE
                }
                // 마지막 터치 좌표 저장
                lastTouchX = event.x
                lastTouchY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                // 사용자가 드래그하는 동안 사각형의 위치 업데이트

                when (currentMode) {
                    RESIZE_BOTTOM_RIGHT -> {
                        // 오른쪽 하단 모서리 움직임 처리
                        if (event.x > rect.left + cornerSize && event.x < width &&
                            event.y > rect.top + cornerSize && event.y < height) {
                            rect.right = event.x
                            rect.bottom = event.y
                            invalidate()
                        }
                    }
                    RESIZE_TOP_LEFT -> {
                        // 좌상단 모서리 움직임 처리
                        if (event.x < rect.right - cornerSize && event.x > 0 &&
                            event.y < rect.bottom - cornerSize && event.y > 0) {
                            rect.left = event.x
                            rect.top = event.y
                            invalidate()
                        }
                    }
                    MOVE -> {
                        var dx = event.x - lastTouchX
                        var dy = event.y - lastTouchY
                        // 오른쪽 또는 아래쪽으로 사각형이 화면을 넘어서지 않도록 이동 범위를 제한
                        if (rect.right + dx > width.toFloat()) {
                            dx = width.toFloat() - rect.right
                        }
                        if (rect.bottom + dy > height.toFloat()) {
                            dy = height.toFloat() - rect.bottom
                        }

                        // 왼쪽 또는 위쪽으로 사각형이 화면을 넘어서지 않도록 이동 범위를 제한
                        if (rect.left + dx < 0) {
                            dx = -rect.left
                        }
                        if (rect.top + dy < 0) {
                            dy = -rect.top
                        }

                        // 사각형 이동
                        rect.offset(dx, dy)

                        // 이동 후 사각형이 화면 범위를 벗어나지 않도록 재조정
                        if (rect.right > width.toFloat()) {
                            rect.right = width.toFloat()
                            rect.left = rect.right - rect.width() // 원래의 너비를 유지하면서 왼쪽 경계 조정
                        }
                        if (rect.bottom > height.toFloat()) {
                            rect.bottom = height.toFloat()
                            rect.top = rect.bottom - rect.height() // 원래의 높이를 유지하면서 상단 경계 조정
                        }

                        invalidate()
                    }
                }
                // 현재 터치 좌표 업데이트
                lastTouchX = event.x
                lastTouchY = event.y
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                currentMode = NONE
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun inCorner(x: Float, y: Float, cornerX: Float, cornerY: Float): Boolean {
        // x, y가 사각형의 모서리 근처에 있는지 검사
        return Math.abs(cornerX - x) < cornerSize && Math.abs(cornerY - y) < cornerSize
    }
    // 사각형 내부 터치 여부 확인 함수
    private fun inRectangle(x: Float, y: Float, rect: RectF): Boolean {
        return rect.contains(x, y)
    }

    private fun drawCornerCircles(canvas: Canvas) {
        // 원의 반지름 설정
        val radius = 20f
        paint.style = Paint.Style.FILL
        // 좌상단 원 그리기
        canvas.drawCircle(rect.left, rect.top, radius, paint)
        // 우하단 원 그리기
        canvas.drawCircle(rect.right, rect.bottom, radius, paint)
        // 페인트 스타일을 다시 STROKE로 변경
        paint.style = Paint.Style.STROKE
    }
    companion object {
        const val NONE = 0
        const val RESIZE_BOTTOM_RIGHT = 1
        const val RESIZE_TOP_LEFT = 2
        const val MOVE = 3
    }
    fun getCurrentRect(): RectF {
        return rect
    }

}
