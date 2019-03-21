package com.cbt.agent;/**
 * Created by Administrator on 2018/6/28.
 */

import com.cbt.agent.test.LubanServiceImpl;

/**
 * @author Tommy
 *         Created by Tommy on 2018/6/28
 **/
public class LubanAgentTest {
    public static void main(String[] args) {
        System.out.println("main");
        new LubanServiceImpl().hello("", "");
    }
}
