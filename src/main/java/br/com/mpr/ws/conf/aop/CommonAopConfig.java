package br.com.mpr.ws.conf.aop;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonAopConfig {

    @Pointcut("execution(* br.com.mpr.ws.rest.*.*(..))")
    public void restLayerExecution(){}

}
