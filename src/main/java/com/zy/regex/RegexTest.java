package com.zy.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    public static void main(String[] args) {

        String orginal  = "ni hao ya ni yee hao ya!";
        String orginal2 = "?ni hao ya wT100？af!";
        String pattern1 = "\b([a-z]+) \1\b/gi";
        String pattern2 = "[a-z | \b]*";
        String pattern3 = "(\\D*)(\\d+)(.?)";

        /**
         *   * 表示 0个以上相当于{0，}  + 表示 1个以上想当于{1，} ？表示 0到一个 相当于{0,1} ? 号是非贪婪的，也表示前面
         *   的是可选的，其他两个是；
         *   定位符：^表示以哪个位开始 例如 ^abc 就是匹配以abc开始的字符串， $则相反，abc$ 匹配以abc为结尾的字符串，\b匹
         *   配到字符串与空格的位置  \B 则跟 \b相反；比如 er\b匹配到 never 不匹配 nvera ,er\B 则匹配到了nvera
         *   占位符 0-9 表示数字，a-z表示 字母 \d 表示一个或者多个数字 \D 表示一个或者多个字母 \d(\.\d)? 表示可以匹配整数或者 有理数或者无理数
         *   \s 匹配任何空白字符，相当于[\r\n\t\v\f]等，\S则匹配任何非空白字符 相当于 [^\r\n\t\f\v](r是回车符，n换行符，t制表符，f换页符，v垂直符)
         *   \w 相当于[0-9A-Za-z]
         *
         */
        Pattern pattern = Pattern.compile(pattern3);
        Matcher matcher = pattern.matcher(orginal2);
        while (matcher.find()) {
            System.out.println("group 0 is:"+matcher.group(0)); //group(0) 匹配整个字符串，1匹配第一个括号里的字符串
            System.out.println("group 1 is:"+matcher.group(1));
            System.out.println("group 2 is:"+matcher.group(2));
            System.out.println("group 3 is:"+matcher.group(3));
        }

        /*替换的用法*/
        String input = "dog say who am I ,anyone tell me I'm a dog!";
        String pattern4 = "do\\B";
        String replace  = "cat";

        Pattern patte   = Pattern.compile(pattern4);
        Matcher matche  = patte.matcher(input);
        input = matche.replaceAll(replace);

        System.out.println("input = " + input);




    }
}
