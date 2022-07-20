package dynamic;

import java.util.Arrays;

/**
 * ClassName: KnapsackProblem
 * Description:分治算法解决背包问题
 * date: 2022/7/20 11:53
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        //物品重量
        int[] weight = {1,4,3,6};
        //物品价值
        int[] value = {1500,3000,2000,6000};
        //背包容量
        int capacity = 8;
        //物品的个数
        int number = value.length;
        //用于记录每一次选择新商品而不是选择上一次的方案的情况
        int[][] store = new int[number + 1][capacity + 1];

        //表，用于记录能放入背包的商品的类型和背包容量共同作用下的最大价值
        //即把所有商品种类和背包容量大小的可能的组合的最大价值列成表
        int[][] table = new int[number + 1][capacity + 1];

        //初始化第一列
        for (int i = 0; i < table.length; i++) {
            //即背包容量为0时，价值最大值就为0
            table[i][0] = 0;
        }
        //初始化第一行
        for (int i = 0; i < table[0].length; i++) {
            //即只有价值为0的商品时，价值最大值就为0
            table[0][i] = 0;
        }

        for (int i = 1; i < table.length; i++) {
            for (int j = 1; j < table[0].length; j++) {
                //当商品的重量大于背包容量
                //下标是从1开始，取重量时下标要减一
                if (weight[i-1] > j) {
                    //如果商品的重量大于背包的容量
                    table[i][j] = table[i-1][j];
                }else {
                    //如果商品重量小于等于背包容量
                    //用把新商品装入背包的价值加上剩余空间装其他商品的最大价值 和 上一次的最大价值进行比较
                   //下标是从1开始，取重量、和价值的时候下标要减一
                   // table[i][j] = Math.max(table[i-1][j],value[i-1] + table[i-1][j-weight[i-1]]);
                    if (table[i-1][j] < value[i-1] + table[i-1][j-weight[i-1]]) {
                        //记录每一次选择新商品而不是选择上一次的方案的情况
                        store[i][j] = 1;
                        table[i][j] = value[i-1] + table[i-1][j-weight[i-1]];
                    }else {
                        table[i][j] = table[i-1][j];
                    }
                }
            }
        }

        for (int i = 0; i < table.length; i++) {
            System.out.println(Arrays.toString(table[i]));
        }

        //store存储的每一个1都是选择新商品
        //从后遍历
        //行的最大下标
        int i = store.length - 1;
        //列的最大下标
        /*
            为什么只看最后一列？
            列是背包容量，最后一列的背包容量是最大的，最后一列都装不上要添加的商品
            那么添加的商品肯定是上一层的
         */
        int j = store[0].length - 1;
        while (i > 0 && j > 0) {
            //选择了加入新商品而不是选择了之前的情况
            if (store[i][j] == 1) {
                System.out.println("第"+ i +"个商品放入背包");
                //减去已经放入的商品的重量
                j -= weight[i-1];
            }
            i--;
        }
    }
}
