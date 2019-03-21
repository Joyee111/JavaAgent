package com.tuling.agent;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author Tommy
 * Created by Tommy on 2019/3/20
 **/
public class LubanAgent2 {

    //jvmti c++
    public static void premain(String args, Instrumentation instrumentation) throws Exception {
        //
//        LubanServer lubanServer = new LubanServer();
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if (!"com/tuling/agent/LubanServer".equals(className)) {
                    return null;
                }
                try {
                    return buildMonitorClass();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, true);
        // 新添加的方法  不能使用重置  与重定议
//        instrumentation.retransformClasses(LubanServer.class);
        // 重新定义 不能够添加方法
//        instrumentation.redefineClasses(new ClassDefinition(LubanServer.class, buildMonitorClass()));
    }

    private static byte[] buildMonitorClass() throws Exception {

        //1、 拷贝一个新的方法
        //2、 修改 原方法名称
        //3、 加方 监听代码
        ClassPool pool = new ClassPool();
        pool.appendSystemPath();
        CtClass ctClss = pool.get("com.tuling.agent.LubanServer");
        CtMethod ctMethod = ctClss.getDeclaredMethod("sayHello");
        CtMethod copyMethod = CtNewMethod.copy(ctMethod, ctClss, new ClassMap());
        ctMethod.setName("sayHello$agent");
        copyMethod.setBody(" {\n" +
                "            long begin = System.nanoTime();\n" +
                "            try {\n" +
                "                return sayHello$agent($$);\n" +
                "            } finally {\n" +
                "                System.out.println(System.nanoTime() - begin);\n" +
                "            }\n" +
                "        }");
        ctClss.addMethod(copyMethod);
        return ctClss.toBytecode();
    }
}
