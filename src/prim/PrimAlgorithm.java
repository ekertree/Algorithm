package prim;

import java.util.Arrays;

/**
 * ClassName: PrimAlgorithm
 * Description:Prim 算法
 * date: 2022/7/24 11:14
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int verxs = data.length;
        //10000代表不连通
        int[][] weight = new
                int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000},};
        Graph graph = new Graph(verxs);
        MinTree minTree = new MinTree();
        minTree.createGraph(graph, verxs, data, weight);
        minTree.showGraph(graph);
        minTree.prim(graph, 3);
    }
}

class Graph {
    //图结点的个数
    int verxs;
    //存放结点数据
    char[] data;
    //存放边 邻接矩阵
    int[][] weight;

    public Graph(int verxs) {
        this.verxs = verxs;
        data = new char[this.verxs];
        weight = new int[this.verxs][this.verxs];
    }
}

class MinTree {
    /**
     * @param graph  图对象
     * @param verxs  顶点个数
     * @param data   顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(Graph graph, int verxs, char[] data, int[][] weight) {
        for (int i = 0; i < verxs; i++) {
            graph.data[i] = data[i];
            for (int j = 0; j < verxs; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    //显示图的邻接矩阵
    public void showGraph(Graph graph) {
        for (int i = 0; i < graph.verxs; i++) {
            System.out.println(Arrays.toString(graph.weight[i]));
        }
    }

    /**
     * prim算法
     * @param graph 图
     * @param index 生成最小生成树的开始顶点
     */
    public void prim(Graph graph,int index){
        //标记顶点是否被访问过
        boolean[] visited = new boolean[graph.verxs];
        //将当前结点标记为已访问
        visited[index] = true;
        //v1,v2记录两个结点的下标
        int v1 = -1;
        int v2 = -1;
        //初始化路径最小值为一个超大数值
        int minWeight = 10000;
        //图有graph.verxs个顶点，则最小生成树有graph.verxs - 1边，则循环graph.verxs - 1次
        for (int i = 0; i < graph.verxs - 1; i++) {
            //j 代表被访问过的结点 具体是不是访问过 if判断
            for (int j = 0; j < graph.verxs; j++) {
                if(!visited[j]){
                    continue;
                }
                //k 代表没有访问过的结点 具体是不是访问过 if判断
                for (int k = 0; k < graph.verxs; k++) {
                    //j被访问过了 k没有被访问 且 这两点的权值小于之前最小的权值
                    if ( !visited[k] && graph.weight[j][k] < minWeight) {
                        //替换最小权值的值
                        minWeight = graph.weight[j][k];
                        v1 = j;
                        v2 = k;
                    }
                }
            }
            System.out.println("边<"+graph.data[v1]+","+graph.data[v2]+">权值:"+minWeight);
            //将当前找到的结点标记为已经访问
            visited[v2] = true;
            //重新设置最小值
            minWeight = 10000;
        }
    }
}
