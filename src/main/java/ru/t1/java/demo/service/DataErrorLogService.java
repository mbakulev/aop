package ru.t1.java.demo.service;

import org.aspectj.lang.JoinPoint;
import ru.t1.java.demo.model.DataSourceErrorLog;

public interface DataErrorLogService {
    void writeErrorMessage(JoinPoint joinPoint, Exception exception);
    void writeErrorMessage(DataSourceErrorLog dataSourceErrorLog);
}
