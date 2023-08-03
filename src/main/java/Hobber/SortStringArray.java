package Hobber;

import java.util.Arrays;

/**
 * @author lzn
 * @date 2023/03/20 16:28
 * @description 一个字符串数组, 由字母+数字组成
 * [hello51,  what202,  hello10, hello100]
 * 先按安母排序,  如果字母一样, 就按后边的数字排序.
 * 所以结果应该是: [hello10, hello5‍‍‍‍‌‍‌‍‌‍‍‍‍‍‌‌‍‌‍‍1, hello100, what202]
 */
public class SortStringArray {

    public static String[] sortString(String[] strings) {
        Arrays.sort(strings, (o1, o2) -> {
            int i = 0;
            while (i < o1.length()) {
                if (Character.isDigit(o1.charAt(i))) {
                    break;
                }
                i++;
            }
            String o1String = o1.substring(0, i);
            int o1Digit = Integer.parseInt(o1.substring(i));
            int j = 0;
            while (j < o2.length()) {
                if (Character.isDigit(o2.charAt(j))) {
                    break;
                }
                j++;
            }
            String o2String = o2.substring(0, j);
            int o2Digit = Integer.parseInt(o2.substring(j));
            if (o1String.equals(o2String)) {
                return o1Digit - o2Digit;
            }
            return o1.compareTo(o2);
        });

        return strings;
    }

    public static void main(String[] args) {
        //[hello10, hello51, hello100, what202]
        System.out.println(Arrays.toString(sortString(new String[]{"hello51", "what202", "hello10", "hello100"})));
        //[hello0010, hello0100, hello510, what2025]
        System.out.println(Arrays.toString(sortString(new String[]{"hello510", "what2025", "hello0010", "hello0100"})));
    }
}
