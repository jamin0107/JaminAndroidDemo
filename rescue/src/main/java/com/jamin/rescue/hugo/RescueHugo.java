package com.jamin.rescue.hugo;

import android.util.Log;

import com.jamin.rescue.db.RescueSP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangjieming on 2017/8/14.
 */
@Aspect
public class RescueHugo {


    @Pointcut("within(@com.jamin.rescue.hugo.RescueTimeLog *)")
    public void withinAnnotatedClass() {
    }


    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(!synthetic *.new(..)) && withinAnnotatedClass()")
    public void constructorInsideAnnotatedType() {
    }

    @Pointcut("execution(@com.jamin.rescue.hugo.RescueTimeLog * *(..)) && methodInsideAnnotatedType()")
    public void method() {
    }

    @Pointcut("execution(@com.jamin.rescue.hugo.RescueTimeLog * onCreate(..)) && methodInsideAnnotatedType()")
    public void activity_create() {
    }

    @Pointcut("execution( * onStart(..)) && methodInsideAnnotatedType()")
    public void activity_start() {
    }

    @Pointcut("execution( * onResume(..)) && methodInsideAnnotatedType()")
    public void activity_resume() {
    }

    @Pointcut("execution( * onPause(..)) && methodInsideAnnotatedType()")
    public void activity_pause() {
    }

    @Pointcut("execution( * onStop(..)) && methodInsideAnnotatedType()")
    public void activity_stop() {
    }

    @Pointcut("execution(@com.jamin.rescue.hugo.RescueTimeLog *.new(..)) && constructorInsideAnnotatedType()")
    public void constructor() {
    }


    @Around("method() || constructor() ||activity_create() ||activity_resume() || activity_start() ")
    public Object logAndExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        enterMethod(joinPoint);

        long startNanos = System.nanoTime();
        Object result = joinPoint.proceed();
        long stopNanos = System.nanoTime();
        long lengthMillis = TimeUnit.NANOSECONDS.toMillis(stopNanos - startNanos);

        exitMethod(joinPoint, result, lengthMillis);

        return result;
    }

    private static void enterMethod(JoinPoint joinPoint) {
        if (!RescueSP.hugoEnable())
            return;

//        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
//        Class<?> cls = codeSignature.getDeclaringType();
//        String methodName = codeSignature.getName();
//        String[] parameterNames = codeSignature.getParameterNames();
//        Object[] parameterValues = joinPoint.getArgs();
//
//        StringBuilder builder = new StringBuilder("\u21E2 ");
//        builder.append(methodName).append('(');
//        for (int i = 0; i < parameterValues.length; i++) {
//            if (i > 0) {
//                builder.append(", ");
//            }
//            builder.append(parameterNames[i]).append('=');
//            builder.append(Strings.toString(parameterValues[i]));
//        }
//        builder.append(')');
//
//        if (Looper.myLooper() != Looper.getMainLooper()) {
//            builder.append(" [Thread:\"").append(Thread.currentThread().getName()).append("\"]");
//        }
//
//        Log.v(asTag(cls) + "Jamin", builder.toString());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            final String section = builder.toString().substring(2);
//            Trace.beginSection(section);
//        }
    }

    private static void exitMethod(JoinPoint joinPoint, Object result, long lengthMillis) {
        if (!RescueSP.hugoEnable()) return;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            Trace.endSection();
//        }

        Signature signature = joinPoint.getSignature();
        Class<?> cls = signature.getDeclaringType();
        String methodName = signature.getName();
//        boolean hasReturnType = signature instanceof MethodSignature
//                && ((MethodSignature) signature).getReturnType() != void.class;

//        StringBuilder builder = new StringBuilder("\u21E0 ")
//                .append(methodName)
//                .append(" [")
//                .append(lengthMillis)
//                .append("ms]");

//        if (hasReturnType) {
//            builder.append(" = ");
//            builder.append(Strings.toString(result));
//        }

//        Log.v(asTag(cls), builder.toString());
        String page = asTag(cls);
        String method = methodName;
        long cost = lengthMillis;
        Log.d("RescueHugo", asTag(cls) + " --> page = " + page + ",method = " + method + ",cost = " + cost);

    }


    private static String asTag(Class<?> cls) {
        if (cls.isAnonymousClass()) {
            return asTag(cls.getEnclosingClass());
        }
        return cls.getSimpleName();
    }
}
