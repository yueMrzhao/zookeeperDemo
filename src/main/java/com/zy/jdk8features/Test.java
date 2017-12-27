package com.zy.jdk8features;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    private static Long start ;
    public static void main(String[] args) {

        List<Students> list = new ArrayList<>();

        for (int i = 0; i < 1000000; i++) {
            list.add(new Students("name"+i, i,(i&1)!=0?1:0));
        }

        /*foreachTest(list);*/
        /*filterTest(list);*/
        /*groupbyTest(list);*/
        /*agvTest(list);*/
        /*newWrapper(list);*/
        sortTest(list);


    }

    public static void foreachTest(List<Students> list){
        /*效率对比，循环打印 3343ms,stream :3404ms*/
        start = System.currentTimeMillis();
        for (Students students : list) {
            System.out.println("name="+students.getName()+"\tage="+students.getAge()+"\tsex="+(students.getSex()==1?"男":"女"));
        }

        System.out.println("所花时间："+(System.currentTimeMillis() - start) +"ms");

        start = System.currentTimeMillis();
        list.stream().forEach(System.out::println);
        System.out.println("所花时间："+(System.currentTimeMillis() - start) +"ms");

    }

    public static void filterTest(List<Students> list){
        /*效率对比，normal 3441ms,stream :2597ms*/
        start = System.currentTimeMillis();
        for (Students students : list) {
            if (students.getAge() > 10000){
                System.out.println("name=" + students.getName() + "\tage=" + students.getAge() + "\tsex=" + (students.getSex() == 1 ? "男" : "女"));
            }
        }

        long time1 = (System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        list.stream().filter(item -> item.getAge() > 10000).forEach(System.out::println);
        System.out.println("所花时间："+ time1 +"ms");
        System.out.println("所花时间："+(System.currentTimeMillis() - start) +"ms");
    }

    public static void groupbyTest(List<Students> list){
        /*效率对比，normal 57ms,stream :748ms*/
        start = System.currentTimeMillis();
        Map<Integer,List<Students>> map1 = new HashMap<>();
        for (Students students : list) {
            if (students.getSex() == 1){
                List<Students> list1 = map1.get(students.getSex());
                if(list1 == null){
                    list1 = new ArrayList<>();
                    map1.put(students.getSex(),list1);
                }
                list1.add(students);
            } else {
                List<Students> list1 = map1.get(students.getSex());
                if(list1 == null){
                    list1 = new ArrayList<>();
                    map1.put(students.getSex(),list1);
                }
                list1.add(students);
            }
        }

        System.out.println("normal 按sex分组后的map："+ map1.keySet());

        long time1 = (System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        Map<Integer,List<Students>> map=  list.stream().collect(Collectors.groupingBy(item->item.getSex()));
        System.out.println("stream 按sex分组后的map："+ map.keySet());
        System.out.println("所花时间："+ time1 +"ms");
        System.out.println("所花时间："+(System.currentTimeMillis() - start) +"ms");


    }

    public static void agvTest(List<Students> list){
         /*效率对比，normal 14ms,stream :74ms*/
        start = System.currentTimeMillis();
        int total =0, avg = 0;
        for (Students students : list) {
            total += students.getAge();
        }

        avg = total/list.size();
        System.out.println("nornaml avg is :" + avg);
        long time1 = (System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        int sum = list.stream().mapToInt(t -> t.getAge()).reduce((a,b)-> a+b).getAsInt();
        avg = sum/list.size();
        System.out.println("stream avg is :"+ avg);
        System.out.println("所花时间："+ time1 +"ms");
        System.out.println("所花时间："+(System.currentTimeMillis() - start) +"ms");
    }

    public static void newWrapper(List<Students> list){
        /*效率对比，循环打印 3153ms,stream :62ms*/
        start = System.currentTimeMillis();
        for (Students students : list) {
            System.out.println(students.getName()+"\t=\t"+students.getAge());
        }

        System.out.println("所花时间："+(System.currentTimeMillis() - start) +"ms");

        start = System.currentTimeMillis();
        list.stream().map(t -> t.getName() + "\t=\t" + t.getAge());
        System.out.println("所花时间："+(System.currentTimeMillis() - start) +"ms");
    }

    public static void sortTest(List<Students> list){
        /*效率对比，循环打印 24ms,stream :3ms*/
        start = System.currentTimeMillis();

        Comparator<Students> ageComparator = new Comparator<Students>() {

            @Override
            public int compare(Students o1, Students o2) {
                if(o1.getAge()>o2.getAge())return 1;
                if(o1.getAge()<o2.getAge())return -1;
                return 0;
            }
        };


        Collections.sort(list, (o1,o2) -> {  if(o1.getAge()>o2.getAge())return 1;
            if(o1.getAge()<o2.getAge())return -1;
            return 0;});


        System.out.println("所花时间："+(System.currentTimeMillis() - start) +"ms");

        start = System.currentTimeMillis();
        list.stream().sorted(ageComparator);
        System.out.println("所花时间："+(System.currentTimeMillis() - start) +"ms");
    }




}
