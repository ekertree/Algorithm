package kmp;

/**
 * ClassName: ViolenceMatch
 * Description:暴力匹配
 * date: 2022/7/21 10:21
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class ViolenceMatch {
    public static void main(String[] args) {
        String str1 = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你~";
        int i = violenceMatch(str1, str2);
        System.out.println("起始下标："+ i);
    }

    public static int violenceMatch(String str1,String str2) {
        char[] charArray1 = str1.toCharArray();
        char[] charArray2 = str2.toCharArray();
        int len1 = charArray1.length;
        int len2 = charArray2.length;

        //指向charArray1
        int i = 0;
        //指向charArray2
        int j = 0;

        while (i < len1 && j < len2) {
            //匹配成功
            if (charArray1[i] == charArray2[j]) {
                i++;
                j++;
            } else {
                //匹配失败
                //返回刚才匹配起始下标的下一个
                i = i - (j - 1);
                //将子串的指针置零
                j = 0;
            }
        }
        //如果j等于子串长度 则匹配成功
        if (j == len2) {
            //返回i的起始下标
            return i - j;
        } else {
            return -1;
        }
    }
}
