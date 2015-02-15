package com.karmeloz.combinenumbers.rest.interceptor;

import com.google.common.collect.ImmutableSet;
import org.apache.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggingInterceptor {

    ImmutableSet<Class<?>> httpMethods = ImmutableSet.of(GET.class, POST.class, PUT.class, DELETE.class, OPTIONS.class);
    private Map<Class<?>, Logger> loggerCache = new ConcurrentHashMap<>();

    @AroundInvoke
    protected Object businessMethodInterceptor(InvocationContext ic) throws Exception {
        Method method = ic.getMethod();
        Class clazz = method.getDeclaringClass();
        String httpMethod = extractHttpMethod(method.getDeclaredAnnotations());

        Logger logger = loggerCache.computeIfAbsent(clazz, Logger::getLogger);
        StringBuilder log = new StringBuilder(httpMethod).append(": ").append(path(clazz, method));

        Object result;
        try {
            result = ic.proceed();
        } catch (Exception e) {
            logger.error(log, e);
            throw e;
        }
        log.append("Result: ").append(result);
        logger.info(log);

        return result;
    }

    private String path(Class clazz, Method method) {
        String mainPath = extractPathOrDefault(clazz.getAnnotations(), "/");
        String methodPath = extractPathOrDefault(method.getAnnotations(), "");
        return "/rest" + mainPath + methodPath + System.getProperty("line.separator");
    }

    private String extractPathOrDefault(Annotation[] annotations, String defaultValue) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Path.class)) {
                return ((Path) annotation).value();
            }
        }
        return defaultValue;
    }

    private String extractHttpMethod(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (httpMethods.contains(annotation.annotationType())) {
                return annotation.annotationType().getSimpleName();
            }
        }
        return "UNKNOWN";
    }
}
