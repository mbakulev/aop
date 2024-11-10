package ru.t1.java.demo.service.impl;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;
import ru.t1.java.demo.service.DataErrorLogService;

import java.util.Arrays;

@Service
public class DataErrorLogServiceImpl implements DataErrorLogService {
    @Autowired
    DataSourceErrorLogRepository repository;

    @Override
    public void writeErrorMessage(JoinPoint joinPoint, Exception exception) {
        DataSourceErrorLog dataSourceErrorLog = DataSourceErrorLog.builder()
                .methodName(joinPoint.getSignature().getName())
                .stackTrace(Arrays.toString(exception.getStackTrace()))
                .message(exception.getMessage())
                .build();

        repository.save(dataSourceErrorLog);
    }

    @Override
    public void writeErrorMessage(DataSourceErrorLog dataSourceErrorLog) {
        repository.save(dataSourceErrorLog);
    }
}
