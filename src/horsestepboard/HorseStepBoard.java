package horsestepboard;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: HorseStepBoard
 * Description:马踏棋盘/骑士周游
 * date: 2022/7/31 19:29
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class HorseStepBoard {
    //棋盘列数
    private static int X;
    //棋盘行数
    private static  int Y;
    //标记棋盘位置是否被访问
    private static boolean isVisited[];
    //标记是否所有位置都被访问
    private static boolean isFinished;

    public static void main(String[] args) {
        X = 8;
        Y = 8;
        int row = 0;
        int column = 0;
        int[][] chessBoard = new int[X][Y];
        isVisited = new boolean[X * Y];
        long start = System.currentTimeMillis();
        travelRound(chessBoard,row,column,1);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                System.out.print(chessBoard[i][j]+ " ");
            }
            System.out.println();
        }
    }


    /**
     *
     * @param chessboard 棋盘
     * 从0开始算坐标
     * @param row 马当前位置所在行
     * @param column 马当前位置所在列
     * @param step 走了几步
     */
    public static void travelRound(int[][] chessboard,int row,int column,int step) {
        //标记棋盘当前位置是第几步
        chessboard[row][column] = step;
        //标记当前位置已经访问
        isVisited[row * X + column] = true;
        //获取当前位置可以走的下一个位置
        //column代表了行数
        //row代表了列数
        ArrayList<Point> nextPoints = next(new Point(column, row));
        //非递减排序
        sort(nextPoints);
        //遍历下一个位置
        while (!nextPoints.isEmpty()) {
            //取出下一个可以访问的位置
            Point canVisit = nextPoints.remove(0);
            //判断该点是否访问过
            if (!isVisited[canVisit.y * X + canVisit.x]){
                //没访问过
                //canVisit.y就是当前位置的行
                //canVisit.x就是当前位置的列
                travelRound(chessboard,canVisit.y,canVisit.x,step+1);
            }
        }
        //判断是否走完棋盘
            /*
                在最后一轮，判断的是step的次数
                在往前回溯时，step次数是不够的，但是是isFinished的
             */
        if (step < X * Y && !isFinished) {
            //将棋盘当前位置的step数置为0
            chessboard[row][column] = 0;
            isVisited[row * X + column] = false;
        }else{
            isFinished = true;
        }
    }

    /**
     * 马走日字 最多八个位置
     * 根据当前的位置，计算马还能走哪些位置，放入集合
     * @param curPoint
     * @return
     */
    public static ArrayList<Point> next(Point curPoint) {
        ArrayList<Point> points = new ArrayList<>();
        Point p = new Point();
        /*
            0点在左上角

            x 6 x 7 x
            5 x x x 0
            x x 马 x x
            4 x x x 1
            x 3 x 2 x
         */
        //能不能走5
        if ((p.x = curPoint.x - 2) >= 0 && (p.y = curPoint.y - 1) >= 0) {
            points.add(new Point(p));
        }
        //能不能走6
        if ((p.x = curPoint.x - 1) >= 0 && (p.y = curPoint.y - 2) >= 0) {
            points.add(new Point(p));
        }
        //能不能走7
        if ((p.x = curPoint.x + 1) < X && (p.y = curPoint.y - 2) >= 0) {
            points.add(new Point(p));
        }
        //能不能走0
        if ((p.x = curPoint.x + 2) < X && (p.y = curPoint.y - 1) >= 0) {
            points.add(new Point(p));
        }
        //能不能走1
        if ((p.x = curPoint.x + 2) < X && (p.y = curPoint.y + 1) < Y) {
            points.add(new Point(p));
        }
        //能不能走2
        if ((p.x = curPoint.x + 1) < X && (p.y = curPoint.y + 2) < Y) {
            points.add(new Point(p));
        }
        //能不能走3
        if ((p.x = curPoint.x - 1) >= 0 && (p.y = curPoint.y + 2) < Y) {
            points.add(new Point(p));
        }
        //能不能走4
        if ((p.x = curPoint.x - 2) >= 0 && (p.y = curPoint.y + 1) < Y) {
            points.add(new Point(p));
        }
        return points;
    }

    //根据当前位置对下一步的所有可选位置进行非递减排序 就是递减中允许有相等的
    public static void sort(ArrayList<Point> points) {
        points.sort((p1,p2)->{
            int size1 = next(p1).size();
            int size2 = next(p2).size();
            return size1 - size2;
        });
    }
}
