package com.tuling.agent;

/**
 * @author Tommy
 * Created by Tommy on 2019/3/20
 **/
public class LubanServer {
    public Integer sayHello(String name, String message) {
        LubanAgent.TraceInfo t = LubanAgent.begin(new Object[]{name, message});
        try {
            return sayHello$agent(name, message);
        } finally {
            LubanAgent.end(t);
        }
    }

    public Integer sayHello$agent(String name, String message) {
        LubanAgent.TraceInfo t = LubanAgent.begin(new Object[]{name, message});
        try {
            System.out.println("hello V2.0");
            return 0;
        } finally {
            LubanAgent.end(t);
        }
    }

    public String append(String name, String message) {
        return name + message;
    }

    public Object getInt() {
        return 1;
    }
}
