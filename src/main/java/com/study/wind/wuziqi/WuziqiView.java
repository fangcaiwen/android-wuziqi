package com.study.wind.wuziqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WuziqiView extends View {

    Paint paint = new Paint();

    // 棋每格的长度
    private float maxLineHeight;

    // 棋盘线条数
    private int BOOK_NUM = 19;

    // 棋盘的宽
    private int mViewWidth;

    //定义黑白棋
    private Bitmap whitePiece,blackPiece;
    private float ratioPieceOfLineHeight = 3 * 1.0f / 4;

    // 记录黑白棋位置的列表
    private ArrayList<Point> whitePieceArray = new ArrayList<>();
    private ArrayList<Point> blackPieceArray = new ArrayList<>();

    // 是否是白字
    private boolean mIsWhite = true;

    // 游戏是否结束
    private boolean isGameover = false;

    private TextView showTextView;


    public WuziqiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0x770000ff);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        maxLineHeight = mViewWidth * 1.0f / BOOK_NUM;

        int pieceWidth = (int) (maxLineHeight * ratioPieceOfLineHeight);
        whitePiece = Bitmap.createScaledBitmap(whitePiece, pieceWidth, pieceWidth, false);
        blackPiece = Bitmap.createScaledBitmap(blackPiece, pieceWidth, pieceWidth, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);
        checkGameOver();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        if (widthModel == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightModel == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(isGameover){
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point point = getValidPoint(x,y);
            if(whitePieceArray.contains(point)||blackPieceArray.contains(point)){
                return false;
            }
            if(mIsWhite){
                whitePieceArray.add(point);
            }else{
                blackPieceArray.add(point);
            }
            invalidate();
            setShowTextViewString();
        }
        return true;
    }

    // 设置TextView
    public void setTextView(TextView mTextView){
        showTextView = mTextView;
    }

    // 设置改谁下
    private void setShowTextViewString(){
        mIsWhite = ! mIsWhite;
        if(mIsWhite){
            showTextView.setText("白子下");
        }else{
            showTextView.setText("黑子下");
        }
    }

    private Point getValidPoint(int x, int y) {
        int validX = (int) (x / maxLineHeight);
        int validY = (int) (y / maxLineHeight);

        return new Point(validX, validY);
    }

    // 初始化
    private void init(){
        // 线条颜色
        paint.setColor(0x88000000);
        // 抗锯齿
        paint.setAntiAlias(true);
        // 线条宽度
        paint.setStrokeWidth(3);

        whitePiece = BitmapFactory.decodeResource(getResources(),R.mipmap.stone_w2);
        blackPiece = BitmapFactory.decodeResource(getResources(),R.mipmap.stone_b1);
    }

    // 画棋盘
    private void drawBoard(Canvas canvas) {

        for(int i=0;i<BOOK_NUM;i++){
            // 画竖线
            canvas.drawLine((float) ((0.5+i)*maxLineHeight), (float) (0.5*maxLineHeight),(float) ((0.5+i)*maxLineHeight), (float) (mViewWidth-0.5*maxLineHeight),paint);
            // 画横线
            canvas.drawLine((float) (0.5*maxLineHeight),(float) ((0.5+i)*maxLineHeight),(float) (mViewWidth-0.5*maxLineHeight),(float) ((0.5+i)*maxLineHeight),paint);
        }

        paint.setColor(0xFF000000);

        // 中心线画圈
        canvas.drawCircle((float) ((0.5+BOOK_NUM/2)*maxLineHeight), (float) ((0.5+BOOK_NUM/2)*maxLineHeight),10,paint);
    }

    // 画棋子
    private void drawPieces(Canvas canvas){
        for(int i=0;i<whitePieceArray.size();i++){
            Point point = whitePieceArray.get(i);
            float left = (point.x + (1 - ratioPieceOfLineHeight) / 2)*maxLineHeight;
            float top = (point.y + (1 - ratioPieceOfLineHeight) / 2)*maxLineHeight;
            canvas.drawBitmap(whitePiece,left,top,null);
        }

        for(int i=0;i<blackPieceArray.size();i++){
            Point point = blackPieceArray.get(i);
            float left = (point.x + (1 - ratioPieceOfLineHeight) / 2)*maxLineHeight;
            float top = (point.y + (1 - ratioPieceOfLineHeight) / 2)*maxLineHeight;
            canvas.drawBitmap(blackPiece,left,top,null);
        }
    }

    // 重新开始
    public void reStart(){
        whitePieceArray.clear();
        blackPieceArray.clear();
        invalidate();
    }

    // 毁棋一步
    public void backStep(){
        if(blackPieceArray.size()>0||whitePieceArray.size()>0){
            if(mIsWhite ){
                blackPieceArray.remove(blackPieceArray.size()-1);
            }else {
                whitePieceArray.remove(whitePieceArray.size()-1);
            }
            setShowTextViewString();
            invalidate();
        }else{
            Toast.makeText(getContext(),"不能再悔棋啦",Toast.LENGTH_SHORT).show();
        }
    }

    // 判断游戏是否结束
    public void checkGameOver(){
        CheckWiner checkWiner = new CheckWiner();
        boolean backWin = checkWiner.isWiner(blackPieceArray);
        boolean whiteWin = checkWiner.isWiner(whitePieceArray);
        if(backWin){
            Toast.makeText(getContext(),"黑棋胜利",Toast.LENGTH_SHORT).show();
            showTextView.setText("黑棋胜利");
            isGameover = true;
        }else if(whiteWin){
            Toast.makeText(getContext(),"白棋胜利",Toast.LENGTH_SHORT).show();
            showTextView.setText("白棋胜利");
            isGameover = true;
        }else{
            isGameover = false;
        }
    }
}
