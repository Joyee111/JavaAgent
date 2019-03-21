package com.test;

import com.tuling.agent.LubanServer;

/**
 * @author Tommy
 * Created by Tommy on 2019/3/20
 **/
public class LubanAgentTest {
    public static void main(String[] args) {
        Integer r = new LubanServer().sayHello("鲁班", "is good man");

        System.out.println(r);
    }
}
