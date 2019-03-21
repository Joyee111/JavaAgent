package com.tuling.agent;

import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

/**
 * @author Tommy
 * Created by Tommy on 2019/3/20
 **/
public class LubanAgent1 {

    //jvmti c++
    public static void premain(String args, Instrumentation instrumentation) {
        LubanServer lubanServer = new LubanServer();
        lubanServer.sayHello("", "");// 打印2.0
        // 威力非常大
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) throws IllegalClassFormatException {
                if (!"com/tuling/agent/LubanServer".equals(className)) {
                    return null;
                }
                InputStream input = LubanAgent1.class.getResourceAsStream("/LubanServer.class");
                try {
                    return IOUtils.readFully(input, -1, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, true);

        try {
            // 清空已装载的当前类
            instrumentation.retransformClasses(LubanServer.class);
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }

        lubanServer.sayHello("", "");

     /*   InputStream input = LubanAgent.class.getResourceAsStream("/LubanServer.class");
        try {
            byte[] bytes = IOUtils.readFully(input, -1, false);
            // 重新装载定义
            instrumentation.redefineClasses(new ClassDefinition(LubanServer.class, bytes));
            //??? 热装载的原理
            lubanServer.sayHello("", "");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/


    }
}
