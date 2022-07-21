package kmp;

import java.util.Arrays;

/**
 * ClassName: KMPAlgorithm
 * Description:KMP算法
 * date: 2022/7/21 11:28
 *复习链接：https://blog.csdn.net/v_july_v/article/details/7041827
 * @author Ekertree
 * @since JDK 1.8
 */
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        System.out.println(KMPSearch(str1, str2,kmpNext(str2)));
    }

    /**
     *
     * @param str1 源字符串
     * @param str2 子串
     * @param next 部分匹配表
     * @return 返回下标
     */
    //移动位数 = 已匹配的字符数 - 对应的部分匹配值
    public static int KMPSearch(String str1,String str2,int[] next) {
        /*
            i:源字符串的下标
            j:匹配的字符个数
         */
        for (int i = 0,j = 0; i < str1.length(); i++) {
            /*
                前面有匹配上的字符，当前源字符串i下标的字符和目标字符串j下标的字符不匹配
                则让j下标等于去掉失配字符后的那个字符串的前后缀最大长度，也就是相等前缀的下一个位置
                比较这个下一个位置和i下标的字符是否相等
                举例：
                    源字符串:   BBC ABCDAB ABCDABCDABDE
                    目标字符串:     ABCDABD
                   可以看到源字符串中(ABCDAB'')和目标字符串ABCDABD的前六个匹配 第七个空格和D不匹配
                   去掉D后的相等前后缀最大长度为2 ：AB这两个字符
                   则令j = 2 也就是让C 跟空格比较
                   这时候你发现源字符串空格前的AB与目标字符串头的AB对齐了
                   也就是说这样子，相当于源字符串不动，目标字符串向右移动了j - next[j] = 6 - 2 = 4
                   原来是把相同的前缀对齐后，比较下一个
             */
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j-1];
            }
            //字符相同 则匹配字符数加一
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            //如果匹配的字符数等于目标字符数的长度，则返回下标
            if (j == str2.length()) {
                //i是下标 j是长度
                //所以i+1变成长度再减去j
                return i + 1 - j;
            }
        }
        //找不到，返回-1
        return  -1;
    }

    //获取子串部分匹配值表(前后缀相同最大长度)
    //用于跳过中间无需匹配的字符
    /*
        对于字符串‘bread’
        前缀：b,br,bre,brea
        后缀：read,ead,ad,d
        “部分匹配值”就是”前缀”和”后缀”的最长的共有元素的长度。
        以”ABCDABD”为例，
        －”A”的前缀和后缀都为空集，共有元素的长度为 0；
        －”AB”的前缀为[A]，后缀为[B]，共有元素的长度为 0；
        －”ABC”的前缀为[A, AB]，后缀为[BC, C]，共有元素的长度 0；
        －”ABCD”的前缀为[A, AB, ABC]，后缀为[BCD, CD, D]，共有元素的长度为 0；
        －”ABCDA”的前缀为[A, AB, ABC, ABCD]，后缀为[BCDA, CDA, DA, A]，共有元素为”A”，长度为 1；
        －”ABCDAB”的前缀为[A, AB, ABC, ABCD, ABCDA]，后缀为[BCDAB, CDAB, DAB, AB, B]，共有元素为”AB”， 长度为 2；
        －”ABCDABD”的前缀为[A, AB, ABC, ABCD, ABCDA, ABCDAB]，后缀为[BCDABD, CDABD, DABD, ABD, BD, D]，共有元素的长度为 0。
     */
    public static int[] kmpNext(String dest) {
        //创建数组保存部分匹配值
        int[] next = new int[dest.length()];
        //next[i]的含义：dest字符串中 0-i的下标的子串中前缀和后缀相同d的最大长度值
        //字符串长度为1，前缀和后缀都为空，则部分匹配值为0
        next[0] = 0;
        //i是next数组下标，也就是指定的子串的长度-1
        //j用于遍历子串
        //for循环 每次对i-0+1长度的子串判断它的前后缀相同字符最大长度 并存入next数组
        for (int i = 1,j = 0; i < dest.length(); i++) {

            /*
                j > 0 说明前面有匹配的字符了，但是当前子串的最后一个字符不匹配
                则需要在更短的子串中寻找有相同匹配的前缀的子串
                next[]数组中存放的是不同长度子串的前后缀相同的最大长度
                这个长度刚好就是dest字符串有相同前缀下一个位置的下标

                比如：p4p5p6p7 p10p11p12p13  p4p5p6 = p10p11p12  p7!=p13
                若p0p1p2p3 p0p1p2 = p4p5p6 = p10p11p12
                则取比较p3是否和p13相等 若相等则基于当前前后缀相同的最大长度，最大长度再加一
                然后存入当前子串的next数组中
                如果不等，则再往前找有相同前缀的下一位进行比较
                直到找不到有相同前缀 也就是找到next[0] 则 当前子串的最大长度为0
             */
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }
            //对于dest字符串或子串，长度为i，若有p0p1..pk = pi-kpi-k+1...pi
            //则该串的前后缀相同的最大长度为k+1（公式，记就完了）
            /*
                举例 i = 3 k = 0 j=0(记录相同前后缀的长度)
                如果p0 = p2 =>  j++ => j = 1（这里就算出了i=2的时候的最大长度）
                又如果p1 = p3 (也就是p0p1 = p2p3) => j++ => j = 2
                所以我们只要逐个字符比较便可以得出最大长度
                所以说最大长度是可以递归求出的，知道前面短的字符串子串的前后缀最大长度就能知道长度更长的子串的最大长度
             */
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            /*
                存入next数组这有三种清空
                1.字符相等 存入
                2.字符不相等，在有相同前缀的更短子串的前缀的下一位找到相同的字符 存入
                3.字符不相等，在更短的子串中找不到相同字符，这是j=0 存入
             */
            next[i] = j;
        }
        return next;
    }
}






