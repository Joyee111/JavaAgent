package com.tuling.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AccessFlag;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

/**
 * @author Tommy
 * Created by Tommy on 2019/3/20
 **/
public class LubanAgent4 {

    //jvmti c++
    public static void premain(String args, Instrumentation instrumentation) throws Exception {
        //
        // 重新定义 不能够添加方法
        instrumentation.redefineClasses(new ClassDefinition(LubanServer.class, buildMonitorClass()));
    }

    private static byte[] buildMonitorClass() throws Exception {
        ClassPool pool = new ClassPool();
        pool.appendSystemPath();
        CtClass ctClss = pool.get("com.tuling.agent.LubanServer");
        CtMethod ctMethod = ctClss.getDeclaredMethod("sayHello");
        for (CtMethod declaredMethod : ctClss.getDeclaredMethods()) {
            if (AccessFlag.isPublic(declaredMethod.getModifiers())) {

            }
        }

        ctMethod.insertBefore(" System.out.println(this==$0);"); // false
        ctMethod.insertBefore(" System.out.println($1);");
        ctMethod.insertBefore(" System.out.println($2);");
        ctMethod.insertBefore(" System.out.println(java.util.Arrays.toString($args));");
        ctMethod.insertBefore(" System.out.println(append($$));");
        ctMethod.insertBefore(" System.out.println(java.util.Arrays.toString($sig));");
        ctMethod.insertBefore(" System.out.println($type);");
        ctMethod.insertBefore(" System.out.println($class);");
        ctMethod.insertAfter("  Integer a1= ($w)3;");
        ctMethod.insertAfter(" $_= ($r)getInt();");
        ctMethod.insertAfter("return ($w)3;");
        return ctClss.toBytecode();
    }


}
