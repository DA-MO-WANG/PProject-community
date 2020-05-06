package com.newcoder.community.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AlphaAspect {

    @Pointcut("execution(* com.newcoder.community.service.*.*(..))")
    public void pointcut() {

    }

}
