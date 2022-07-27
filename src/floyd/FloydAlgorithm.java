package floyd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: FloydAlgorithm
 * Description:弗洛伊德算法 求各点到其他点的最短路径
 * date: 2022/7/27 10:06
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class FloydAlgorithm {
    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;
        matrix[0] = new int[]{0, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, 0, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, 0, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, 0, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, 0, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, 0, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, 0};
        Graph graph = new Graph(matrix, vertex);
        graph.floyd();
        graph.show(0,4);
    }
}

class Graph {
    //存放顶点
    char[] vertexes;
    //保存从各个顶点出发到其他顶点的距离
    private int[][] distance;
    //保存到达目标顶点的中间顶点
    private int[][] pre;

    /**
     * @param matrix   大小
     * @param vertexes 顶点数组
     */
    public Graph(int[][] matrix, char[] vertexes) {
        this.vertexes = vertexes;
        this.distance = matrix;
        //用于记录路径
        // pre[i][j]表示从i到j的最后一个前驱顶点,也就是i到j中间最后经过那个顶点
        //只要不断递归往前找，就能找到整个最短路径
        //如果pre[i][j] = i 则说明已经连通
        this.pre = new int[this.vertexes.length][this.vertexes.length];
        //初始化pre数组
        for (int i = 0; i < vertexes.length; i++) {
            //初始的时候，从i到任意顶点的前驱都为i
            Arrays.fill(pre[i], i);
        }
    }

    public void show(int i,int j) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        List<Integer> list = new ArrayList<>();
        this.find(i,j,list);
        System.out.print(vertex[i]+"到"+vertex[j]+"的最短路径为：");
        for (int k = list.size() - 1; k >= 0; k--) {
            System.out.print(vertex[list.get(k)] + "->");
        }
        System.out.print(vertex[j]);
        System.out.println();
        System.out.println("最短路径长度为:"+this.distance[i][j]);
    }

    public void find(int i, int j, List<Integer> list) {
        if (pre[i][j] != i) {
            find(pre[i][j],j,list);
            list.add(i);
        }else{
            list.add(i);
        }
    }

    public void floyd() {
        int len = 0;
        //遍历中间顶点
        // 其实就是每一遍历就是判断两点的最短距离是不是可能经过下标为j的顶点
        //如果经过的距离小于上一轮遍历的的结果 则更新距离为这个更短的距离
        //更新前驱为中间顶点到终点的前驱
        for (int j = 0; j < this.vertexes.length; j++) {
            //遍历出发顶点
            for (int i = 0; i < this.vertexes.length; i++) {
                //遍历终点
                for (int k = 0; k < this.vertexes.length; k++) {
                    //求出从i出发，中间经过j，最终到达k的距离
                    len = this.distance[i][j] + this.distance[j][k];
                    //经过中间顶点的距离小于直连的距离 则替换最小距离
                    if (len < this.distance[i][k]) {
                        this.distance[i][k] = len;
                        //出发点(i)到终点（k）的中间顶点是中间顶点(j)到终点()的中间顶点
                        this.pre[i][k] = this.pre[j][k];
                    }
                }
            }
        }
    }
}
