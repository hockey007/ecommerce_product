package com.ecommerce.product.logger;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ProductLogger {

    private static final String CORRELATION_ID = "CORRELATION_ID = ";
    private static final String CLASS_NAME = ", CLASS_NAME = ";
    private static final String METHOD = ", METHOD = ";
    private static final String MESSAGE = ", MESSAGE = ";

    public static UUID getCorrelationId() {
        // Fetch from ThreadLocal
        return UUID.randomUUID();
    }

    public static void logInfo(String className, String method, String message) {
        log.info(CORRELATION_ID + getCorrelationId() + CLASS_NAME + className + METHOD + method + MESSAGE + message);
    }

    public static void logDebug(String className, String method, String message) {
        log.debug(CORRELATION_ID + getCorrelationId() + CLASS_NAME + className + METHOD + method + MESSAGE + message);
    }

    public static void logWarn(String className, String method, String message) {
        log.warn(CORRELATION_ID + getCorrelationId() + CLASS_NAME + className + METHOD + method + MESSAGE + message);
    }

    public static void logError(String className, String method, String message) {
        log.info(CORRELATION_ID + getCorrelationId() + CLASS_NAME + className + METHOD + method + MESSAGE + message);
    }

}
