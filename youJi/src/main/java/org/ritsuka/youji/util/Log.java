package org.ritsuka.youji.util;

import org.slf4j.Logger;

/**
 * Date: 9/30/11
 * Time: 7:44 PM
 */
public class Log {
    private final Logger logger;

    public Log(final Logger logger) {
        this.logger = logger;
    }

    Logger get()
    {
        return logger;
    }

    public void trace(final String message, final Object... args) {
        logger.trace(message, args);
    }

    public void debug(final String message, final Object... args) {
        logger.debug(message, args);
    }

    public void info(final String message, final Object... args) {
        logger.info(message, args);
    }

    public void warn(final String message, final Object... args) {
        logger.warn(message, args);
    }

    public void error(final String message, final Object... args) {
        logger.error(message, args);
    }
}
