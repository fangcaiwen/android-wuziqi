package com.study.wind.wuziqi;

import android.graphics.Point;

import java.util.List;

public class CheckWiner {
    private final int TYPE_HORIZONTAL = 0;
    private final int TYPE_VERTICAL = 1;
    private final int TYPE_LEFT_DIAGONAL = 2;
    private final int TYPE_RIGHT_DIAGONAL = 3;

    // 五子连珠
    private int MAX_COUNT_IN_LINE = 5;

    private Point point1,point2;

    // 检测是否是胜利
    public boolean isWiner(List<Point> mArray){
        for(Point point:mArray){
            int x = point.x;
            int y = point.y;
             if(check(x,y,mArray,TYPE_HORIZONTAL)||check(x,y,mArray,TYPE_VERTICAL)||check(x,y,mArray,TYPE_LEFT_DIAGONAL)||check(x,y,mArray,TYPE_RIGHT_DIAGONAL)){
                return true;
             }
        }
        return false;
    }

    private boolean check(int x,int y,List points,int type){
        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            switch (type) {
                case TYPE_HORIZONTAL:
                    point1 = new Point(x - i, y);
                    break;
                case TYPE_VERTICAL:
                    point1 = new Point(x, y - i);
                    break;
                case TYPE_LEFT_DIAGONAL:
                    point1 = new Point(x - i, y + i);
                    break;
                case TYPE_RIGHT_DIAGONAL:
                    point1 = new Point(x + i, y + i);
                    break;
            }
            if (points.contains(point1)) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            switch (type) {
                case TYPE_HORIZONTAL:
                    point2 = new Point(x + i, y);
                    break;
                case TYPE_VERTICAL:
                    point2 = new Point(x, y + i);
                    break;
                case TYPE_LEFT_DIAGONAL:
                    point2 = new Point(x + i, y - i);
                    break;
                case TYPE_RIGHT_DIAGONAL:
                    point2 = new Point(x - i, y - i);
                    break;
            }
            if (points.contains(point2)) {
                count++;
            } else {
                break;
            }
        }

        if(count==MAX_COUNT_IN_LINE){
            return true;
        }
        return false;
    }
}
