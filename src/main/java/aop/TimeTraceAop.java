package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeTraceAop {

    //@Around("execution(* com.example.project1..*(..))")
    public Object execute(ProceedingJoinPoint joinpoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinpoint.toString());
        try{
            return joinpoint.proceed();
        }finally{
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinpoint.toString() + " " + timeMs + "ms");
        }
    }
}
