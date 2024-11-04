package ru.t1.java.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.service.impl.DataErrorLogServiceImpl;

@Slf4j
@Aspect
@Component
public class LogDataSourceErrorAspect {
    @Autowired
    private DataErrorLogServiceImpl dataSourceErrorLogService;

    @Pointcut("within(ru.t1.java.demo.*)")
    public void loggingMethods() {
    }

    @AfterThrowing(pointcut = "@annotation(LogDataError)", throwing = "ex")
    public void logExceptionAnnotation(JoinPoint joinPoint, Exception ex) {

        dataSourceErrorLogService.writeErrorMessage(joinPoint, ex);
    }
}
