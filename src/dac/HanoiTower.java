package dac;

/**
 * ClassName: HanoiTower
 * Description:分治算法解决汉诺塔
 * date: 2022/7/19 12:02
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class HanoiTower {

    static int cnt = 0;

    public static void main(String[] args) {
        hanoiTower(10, 'A', 'B', 'C');
        System.out.println("共"+cnt+"步");
    }

    /**
     *
     * @param num 盘的个数
     * @param a 最初始盘放的位置
     * @param b 辅助位置
     * @param c 最终要放的位置
     */
    public static void hanoiTower(int num,char a,char b,char c) {
        //如果只有一个盘
        if (num == 1 ) {
            System.out.println("第1个盘从"+ a +"->" + c);
            cnt++;
        }else {
            //如果大于等于两个盘
            //我们总是可以看成是两个盘
            //最下面的一个盘，上面的所有盘看成一个盘

            //先把上面的所有盘从A移动到B,C充当辅助
            hanoiTower(num - 1, a, c, b);
            //把最下面的盘从A移动到C
            cnt++;
            System.out.println("第"+ num + "个盘从" + a + "->" + c);
            //把B的所有盘移动到C，A充当辅助
            hanoiTower(num - 1, b, a, c);
        }
    }
}
