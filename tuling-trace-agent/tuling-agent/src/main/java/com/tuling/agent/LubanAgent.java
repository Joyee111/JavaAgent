package com.tuling.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;

/**
 * @author Tommy
 * Created by Tommy on 2019/3/20
 **/
public class LubanAgent {

    //jvmti c++
    public static void premain(String args, Instrumentation instrumentation) throws Exception {


    }


    public static TraceInfo begin(Object[] args) {
        return new TraceInfo(System.currentTimeMillis(), args);
    }

    public static void end(Object param) {
        TraceInfo traceInfo = (TraceInfo) param;
        System.out.println("执行时间" + (System.currentTimeMillis() - traceInfo.getBegin()));
        System.out.println("执行参数" + Arrays.toString(traceInfo.getArgs()));
    }

    public static class TraceInfo {
        long begin;
        Object[] args;

        public TraceInfo(long begin, Object[] args) {
            this.begin = begin;
            this.args = args;
        }

        public TraceInfo() {
        }

        public long getBegin() {
            return begin;
        }

        public void setBegin(long begin) {
            this.begin = begin;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }
    }


}
