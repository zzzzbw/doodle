package com.github.zzzzbw.mvc.server;

/**
 * 服务器 interface
 *
 * @author zzzzbw
 * @since 2018/5/25 11:32
 */
public interface Server {
    /**
     * 启动服务器
     *
     * @throws Exception Exception
     */
    void startServer() throws Exception;

    /**
     * 停止服务器
     *
     * @throws Exception Exception
     */
    void stopServer() throws Exception;
}
