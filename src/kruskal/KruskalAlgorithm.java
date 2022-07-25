package kruskal;


import java.util.Arrays;

/**
 * ClassName: KruskalAlgorithm
 * Description:
 * date: 2022/7/25 11:03
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class KruskalAlgorithm {
    //边的个数
    private int edgeNum;
    //顶点数组
    private char[] vertexes;
    //图的邻接矩阵
    private int[][] matrix;
    //使用Integer最大值来表示两个顶点不能连通
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        char[] vertexes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int matrix[][] = {
                /*A*/ {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 7, 6, INF, 2, 0, 9},
                /*G*/ {14, INF, INF, INF, 8, 9, 0}};
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(vertexes, matrix);
        kruskalAlgorithm.print();
        EdgeData[] edges = kruskalAlgorithm.getEdges();
        kruskalAlgorithm.sortEdge(edges);
        System.out.println("排序后边的集合：");
        for (int i = 0; i < edges.length; i++) {
            System.out.println(edges[i]);
        }
        kruskalAlgorithm.kruskal();
    }

    public KruskalAlgorithm(char[] vertexes, int[][] matrix) {
        this.vertexes = vertexes;
        this.matrix = matrix;
        for (int i = 0; i < vertexes.length; i++) {
            //统计上三角
            for (int j = i+1; j < vertexes.length; j++) {
                if (this.matrix[i][j] != INF) {
                    this.edgeNum++;
                }
            }
        }
    }

    //输出邻接矩阵
    public void print() {
        System.out.println("邻接矩阵为:");
        for (int i = 0; i < vertexes.length; i++) {
            for (int j = 0; j < vertexes.length; j++) {
                System.out.printf("%10d\t", matrix[i][j]);
            }
            System.out.println();
        }
    }

    //对边进行排序，不用接口了，复习冒泡了...
    public void sortEdge(EdgeData[] edgeDatas) {
        EdgeData temp;
        boolean end = true;
        for (int i = 0; i < edgeDatas.length-1; i++) {
            for (int j = 0; j < edgeDatas.length - 1 - i; j++) {
                if (edgeDatas[j].weight > edgeDatas[j+1].weight) {
                    temp = edgeDatas[j];
                    edgeDatas[j] = edgeDatas[j+1];
                    edgeDatas[j+1] = temp;
                    end = false;
                }
            }
            if (end) {
                break;
            }else{
                end = true;
            }
        }
    }

    //获取顶点的下标
    public int getPosition(char ch) {
        for (int i = 0; i < vertexes.length; i++) {
            if (vertexes[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    //获取矩阵中的边 放到EdgeData数组中，方便之后的遍历查看每条边的权值
    public EdgeData[] getEdges() {
        int index = 0;
        EdgeData[] edgeDatas = new EdgeData[this.edgeNum];
        for (int i = 0; i < vertexes.length; i++) {
            //矩阵有一半的数据相同重复，所以只取三角，这里遍历上三角
            for (int j = i + 1; j < vertexes.length; j++) {
                if (matrix[i][j] != this.INF){
                    //放入边的两个端点和边的权值
                    edgeDatas[index++] = new EdgeData(vertexes[i],vertexes[j], matrix[i][j] );
                }
            }
        }
        return edgeDatas;
    }

    //获取下标为i的顶点所在的连线（在最小生成树中）中所能到达的最远的顶点（从小下标往大下标找）
    //ends[i] != 0 它会一直往前找，一直到到前面为0，这时候得到的就是最远的顶点
    //ends[i] == 0 直接返回当前的下标，也就是它自己，它自己就是找到的最远的顶点
    public int getEnd(int[] ends,int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }

    public void kruskal() {
        //最后结果数组的索引
        int index = 0;
        //用于保存已在最小生成树的顶点的终点
        int[] ends = new int[this.edgeNum];
        //结果数组
        EdgeData[] result = new EdgeData[this.edgeNum];
        //将矩阵的边封装进EdgeData
        EdgeData[] edges = getEdges();
        //按照权值从小到大排序
        sortEdge(edges);
        //遍历edgs，将边加入最小生成树中时，判断准备加入的边是否会构成回路
        //构成回路则不加入，反之加入
        for (int i = 0; i < this.edgeNum; i++) {
            for (int j = 0; j < this.edgeNum; j++) {
                //获取第i条边的顶点(起点)
                int p1 = this.getPosition(edges[i].start);
                //获取第i条边的另一个顶点
                int p2 = this.getPosition(edges[i].end);
                //获取p1能到达的最远的顶点（从小到大排序，则是下标最大的顶点）
                int end1 = this.getEnd(ends, p1);//4
                int end2 = this.getEnd(ends, p2);//5
                /*
                    从数学上看，[p1,end1]，[p2,end2]
                    现在要让[p1,p2]成为一个集合，如果end1 = end2 则有[p1,p2,end(1\2)]为一个集合 也就是闭环了
                    未加入最小生成树之前,最远的顶点认为是自己，用0表示
                    最开始ends数组：[0,0,0,0,0,0,0,0,0,0,0,0]
                    加入第一条边<E,F>
                    [0,0,0,5,0,0,0,0,0,0,0,0]
                 */
                if (end1 != end2) {
                    ends[end1] = end2;
                    //不需要ends[end2] = end2
                    //因为getEnd()方法中 如果ends[end2] = 0 直接返回它本身的下标end2
                    result[index++] = edges[i];
                }
            }
        }
        //统计并打印最小生成树 result数组
        System.out.println("最小生成树为：");
        for (int i = 0; i < index; i++) {
            System.out.println(result[i]);
        }
    }

}

class EdgeData{
    //边的一个点
    char start;
    //边的另一个点
    char end;
    //权值
    int weight;

    public EdgeData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "edgeData{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }
}
