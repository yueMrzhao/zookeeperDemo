package com.zy.jdk8features;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {


        /*stream 生成方法, 打印：：用法，*/
        Stream.of(1, 2, 3, 4, 5).

                forEach(System.out::println);
        Stream.iterate(1, item -> item + 1).

                limit(10).

                forEach(System.out::println);
        Stream.generate(() -> Math.random()).

                limit(10).

                forEach(System.out::println);
        Stream.generate(Math::random).

                limit(10).

                forEach(System.out::println);
        Stream.generate(Math::random).

                limit(10).

                collect(Collectors.toList()).

                forEach(System.out::println);

        /*转换：distinct 用法，依赖于 对象的equal 方法*/
        Stream.of(1, 1, 2, 3, 2, 4, 5, 5, 5).

                distinct().

                forEach(System.out::println);

    /*map 用法，相当于*/
        int a = Stream.of(1, 2, null, null, 3, 4, 5, 6).filter(item -> item != null).distinct().
                mapToInt(item -> item * 2).peek(System.out::println).skip(2).limit(4).sum();
        System.out.println(a);

        /*汇聚：reduce*/

        /*colect*/
        Stream.of(1, 2, null, 3, 4, null, null, 4, 5, 6, 7, 8, 9).

                collect(() -> new ArrayList<>(), (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
        System.out.println(Stream.of(1, 2, null, 3, 4, null, null, 4, 5, 6, 7, 8, 9).

                filter(item -> item != null).

                collect(Collectors.toList()));

        /*reduce*/
        System.out.println("reduce get is :" + Stream.of(1, 2, null, 3, 4, null, null, 4, 5, 6, 7, 8, 9).

                filter(item -> item != null).

                reduce((sum, item) -> sum + item).

                get());
        System.out.println("reduce T get is :" + Stream.of(1, 2, null, 3, 4, null, null, 4, 5, 6, 7, 8, 9).

                filter(item -> item != null).

                reduce(1, (sum, item) -> sum + item).

                intValue());

        System.out.println("max mansul is :" + Stream.of(1, 2, null, 3, 4, null, null, 4, 5, 6, 7, 8, 9).

                filter(item -> item != null).

                max((o2, o3) -> o2.compareTo(03)).

                get());


    }

}
