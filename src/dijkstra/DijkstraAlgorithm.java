package dijkstra;

import java.util.Arrays;

/**
 * ClassName: DijkstraAlgorithm
 * Description:迪杰斯特拉算法 求两点最短路径
 * date: 2022/7/26 11:17
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class DijkstraAlgorithm {
    public static final int NOT_CONNECT = 65535;

    public static void main(String[] args) {
        char[] vertexes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = new int[vertexes.length][vertexes.length];
        matrix[0] = new int[]{NOT_CONNECT, 5, 7, NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 2};
        matrix[1] = new int[]{5, NOT_CONNECT, NOT_CONNECT, 9, NOT_CONNECT, NOT_CONNECT, 3};
        matrix[2] = new int[]{7, NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 8, NOT_CONNECT, NOT_CONNECT};
        matrix[3] = new int[]{NOT_CONNECT, 9, NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 4, NOT_CONNECT};
        matrix[4] = new int[]{NOT_CONNECT, NOT_CONNECT, 8, NOT_CONNECT, NOT_CONNECT, 5, 4};
        matrix[5] = new int[]{NOT_CONNECT, NOT_CONNECT, NOT_CONNECT, 4, 5, NOT_CONNECT, 6};
        matrix[6] = new int[]{2, 3, NOT_CONNECT, NOT_CONNECT, 4, 6, NOT_CONNECT};
        Gragh gragh = new Gragh(vertexes, matrix);
        gragh.showGraph();
        gragh.dijkstra(0);
        gragh.showDijkstra();
    }
}

class Gragh {
    //顶点数组
    private char[] vertexes;
    //邻接矩阵
    private int[][] matrix;
    //顶点信息
    private VertexesInfo vertexesInfo;

    public Gragh(char[] vertexes, int[][] matrix) {
        this.vertexes = vertexes;
        this.matrix = matrix;
    }

    public void showGraph() {
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

    /**
     *
     * @param index 出发顶点对应的下标
     *  1.先更新出发点和相连顶点的距离和前驱
     *  2.每次选择距离出发点最近的顶点作为更新相连结点的距离和前驱的下一个顶点
     *  3.这样子，就相当于遍历了以每一个顶点为前驱顶点到下一个顶点的距离，而每次只要距离小于上次的结果，距离就会被更新
     *    则最终保留的就是最短的距离
     *  4.可以说这就是广度优先+贪心了
     */
    public void dijkstra(int index) {
        //初始化顶点信息
        this.vertexesInfo = new VertexesInfo(index, this.vertexes.length);
        //更新出发点和出发点相连结点的距离和前驱，因为不相连的距离不会被更新
        this.updateDistanceAndPre(index);
        //因为上面出发点已经更新过了，所以下标从1开始
        for (int i = 1; i < this.vertexes.length; i++) {
            //更新已经访问的下标为index的顶点所相连的所有顶点中与出发顶点距离最短的顶点的访问状态为已访问
            //并返回它的下标
            index = vertexesInfo.updateMinVisitedAndReturnWillVisitVertex();
            //更新下标为index的顶点所相连的所有顶点中与出发顶点距离最短的顶点 和 它相连的顶点相连结点的距离和前驱
            updateDistanceAndPre(index);
        }
    }

    //更新下标为index的顶点到周围顶点的距离、更新周围顶点的前驱顶点
    public void updateDistanceAndPre(int index) {
        int len = 0;
        for (int i = 0; i < this.matrix[index].length; i++) {
            //出发点到当前顶点的距离加上当前顶点到顶点i的距离
            len = this.vertexesInfo.getDistance(index) + matrix[index][i];
            //如果顶点i没有被访问过且len小于出发点到顶点i的距离
            if (!vertexesInfo.isVisited(i) && len <  vertexesInfo.getDistance(i)) {
                //更新顶点i的前驱顶点为index
                vertexesInfo.updatePre(i, index);
                //更新出发点到顶点i的距离为len
                vertexesInfo.updateDistance(i,len);
            }
        }
    }

    //展示结果
    public void showDijkstra(){
        this.vertexesInfo.showResult();
    }
}

class VertexesInfo{
    //记录各个顶点是否访问过
    public boolean[] isVisited;
    //记录每个顶点的前驱顶点
    public int[] preVertexes;
    //记录出发点到各个顶点的最短距离
    public int[] distance;

    /**
     *
     * @param index 出发顶点对应的下标
     * @param length 顶点的个数
     */
    public VertexesInfo(int index,int length) {
        this.isVisited = new boolean[length];
        this.preVertexes = new int[length];
        this.distance = new int[length];
        //初始化距离为int最大值
        Arrays.fill(distance, DijkstraAlgorithm.NOT_CONNECT);
        //将起点距离置为0
        distance[index] = 0;
        //设置出发顶点已访问
        this.isVisited[index] = true;
    }

    //判断index下标对应的顶点是否被访问过
    public boolean isVisited(int index) {
        return this.isVisited[index];
    }

    //更新出发顶点到index下标对应的顶点的距离
    public void updateDistance(int index,int len){
        this.distance[index] = len;
    }

    //更新下标为index的顶点的前驱结点为pre
    public void updatePre(int index,int pre){
        this.preVertexes[index] = pre;
    }

    //返回出发顶点到下标为index的顶点的距离
    public int getDistance(int index) {
        return this.distance[index];
    }

    //选择并返回没有访问过且到出发顶点距离最短的顶点
    //用于updateDistanceAndPre 更新完距离和前驱之后 返回index用于下一轮的updateDistanceAndPre
    //也就是先更新距离和前驱然后找到下一个没访问过且距离出发点最近的顶点作为下一个要更新距离和前驱的顶点
    public int updateMinVisitedAndReturnWillVisitVertex(){
        //最开始表示无法连接
        int min = DijkstraAlgorithm.NOT_CONNECT;
        int index = 0;
        //遍历每个顶点，找到距离出发点最近的顶点
        for (int i = 0; i < isVisited.length; i++) {
            //如果该顶点没有访问过且出发顶点到该顶点的距离小于min
            if (!isVisited[i] && this.distance[i] < min) {
                min = this.distance[i];
                index = i;
            }
        }
        //记录到出发点距离最小的顶点已访问
        isVisited[index] = true;
        //返回它的下标
        return index;
    }

    //显示结果
    public void showResult() {
        System.out.println("访问情况：");
        for (boolean b : this.isVisited) {
            System.out.print(b+" ");
        }
        System.out.println();
        System.out.println("前驱顶点：");
        for (int preVertex : this.preVertexes) {
            System.out.print(preVertex + " ");
        }
        System.out.println();
        System.out.println("距离：");
        for (int i : this.distance) {
            System.out.print(i+" ");
        }
    }
}
