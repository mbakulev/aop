package ru.t1.java.demo.service;

import org.aspectj.lang.JoinPoint;

public interface DataErrorLogService {
    void writeErrorMessage(JoinPoint joinPoint, Exception exception);
}
