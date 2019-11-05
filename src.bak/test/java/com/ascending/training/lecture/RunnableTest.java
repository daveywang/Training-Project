/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.lecture;

/*
public class RunnableTest {
    public static void main(String[] args) {
        Runnable r1 = new PrintMsg();
        r1.run();
    }
}
*/

/*
public class RunnableTest {
    public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            @Override
            public void run(){
                System.out.println("Hello, I'm running by a thread from anonymous inner class!");
            }
        };
        r1.run();
    }
}
*/

public class RunnableTest {
    public static void main(String[] args) {
        Runnable r1 = () -> System.out.println("Hello, I'm running by a thread from a lambda!");
        r1.run();
    }
}

