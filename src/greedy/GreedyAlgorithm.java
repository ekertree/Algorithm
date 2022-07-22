package greedy;

import java.util.*;

/**
 * ClassName: GreedyAlgorithm
 * Description:贪心算法
 * date: 2022/7/22 9:17
 * 假设存在下面需要付费的广播台，以及广播台信号可以覆盖的地区。
 * 如何选择最少的广播台，让所有的地区 都可以接收到信号
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建广播台放入set中
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        //将各个广播台 放入broadcasts
        HashSet<String> radio1 = new HashSet<>();
        radio1.add("北京");
        radio1.add("上海");
        radio1.add("天津");

        HashSet<String> radio2 = new HashSet<>();
        radio2.add("广州");
        radio2.add("北京");
        radio2.add("深圳");

        HashSet<String> radio3 = new HashSet<>();
        radio3.add("成都");
        radio3.add("上海");
        radio3.add("杭州");

        HashSet<String> radio4 = new HashSet<>();
        radio4.add("上海");
        radio4.add("天津");

        HashSet<String> radio5 = new HashSet<>();
        radio5.add("杭州");
        radio5.add("大连");

        broadcasts.put("K1", radio1);
        broadcasts.put("K2", radio2);
        broadcasts.put("K3", radio3);
        broadcasts.put("K4", radio4);
        broadcasts.put("K5", radio5);

        //将每个广播台的区域加入所有区域，set自动去重
        HashSet<String> areas = new HashSet<>();
        radio1.forEach(city -> areas.add(city));
        radio2.forEach(city -> areas.add(city));
        radio3.forEach(city -> areas.add(city));
        radio4.forEach(city -> areas.add(city));
        radio5.forEach(city -> areas.add(city));

        //创建ArrayList 存放选择的广播台集合
        List<String> selects = new ArrayList<>();

        //定义临时集合 存放广播台覆盖的地区和全部地区的交集
        HashSet<String> tempSet = new HashSet<>();

        String maxKey = null;
        //存放maxKey所指向的广播台的区域
        HashSet<String> maxSet = new HashSet<>();

        while (areas.size() > 0) {
            for (String key : broadcasts.keySet()) {
                //当前这个广播台能覆盖的区域
                HashSet<String> area = broadcasts.get(key);
                //放入临时变量
                tempSet.addAll(area);
                //当前这个广播台和全部区域取交集后放入tempSet
                tempSet.retainAll(areas);
                //如果maxKey 不为空 则取出这个广播台和全部区域取交际后存入maxSet
                if (maxKey != null) {
                    HashSet<String> maxArea = broadcasts.get(maxKey);
                    maxSet.addAll(maxArea);
                    maxSet.retainAll(areas);
                }
                //如果当前这个广播台和全部区域的交际个数大于零
                // 且 它的个数大于之前maxKey所指向的广播台与全部区域的交集的个数
                if (tempSet.size() > 0 && (maxKey == null || tempSet.size() > maxSet.size())) {
                    //让maxKey指向当前这个广播台
                    maxKey = key;
                }
                //清空tempSet
                tempSet.clear();
            }
            //maxKey不为空 将其加入选择的集合selects
            selects.add(maxKey);
            //将maxKey所指向的广播台所覆盖的区域从全部区域中去掉
            areas.removeAll(broadcasts.get(maxKey));
            //清除已经选择的广播台
            broadcasts.remove(maxKey);
            //置空maxKey
            maxKey = null;
        }
        System.out.println("选择的结果为："+selects);
    }
}
