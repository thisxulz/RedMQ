package com.github.thisxulz.redmq.client.log;

/**
 * @title 
 * @author xulz
 * @date 2019年1月18日下午4:50:37
 */
public interface ClientLog {
	
	String getName();

    void debug(String var1);

    void debug(String var1, Object var2);

    void debug(String var1, Object var2, Object var3);

    void debug(String var1, Object... var2);

    void debug(String var1, Throwable var2);

    void info(String var1);

    void info(String var1, Object var2);

    void info(String var1, Object var2, Object var3);

    void info(String var1, Object... var2);

    void info(String var1, Throwable var2);

    void warn(String var1);

    void warn(String var1, Object var2);

    void warn(String var1, Object... var2);

    void warn(String var1, Object var2, Object var3);

    void warn(String var1, Throwable var2);

    void error(String var1);

    void error(String var1, Object var2);

    void error(String var1, Object var2, Object var3);

    void error(String var1, Object... var2);

    void error(String var1, Throwable var2);
}
