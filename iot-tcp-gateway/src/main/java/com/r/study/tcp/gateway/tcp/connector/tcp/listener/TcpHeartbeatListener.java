package com.r.study.tcp.gateway.tcp.connector.tcp.listener;

import com.r.study.tcp.gateway.tcp.connector.Session;
import com.r.study.tcp.gateway.tcp.connector.api.listener.SessionEvent;
import com.r.study.tcp.gateway.tcp.connector.api.listener.SessionListener;
import com.r.study.tcp.gateway.tcp.connector.tcp.TcpSessionManager;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 心跳监听器
 * @author YiHui.He
 */
@Slf4j
public class TcpHeartbeatListener implements Runnable, SessionListener {

    private TcpSessionManager tcpSessionManager = null;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    private final int checkPeriod = 30 * 1000;
    private volatile boolean stop = false;

    public TcpHeartbeatListener(TcpSessionManager tcpSessionManager) {
        this.tcpSessionManager = tcpSessionManager;
    }

    @Override
    public void run() {
        while (!stop) {
            if (isSessionEmpty()) {
                //如果session数为0，暂停到有session创建事件监听到
                awaitQueue();
            }
            log.info("TcpHeartbeatListener/online session count : " + tcpSessionManager.getSessionCount());
            // sleep period
            try {
                Thread.sleep(checkPeriod);
            } catch (InterruptedException e) {
                log.error("TcpHeartbeatListener run occur InterruptedException!", e);
            }
            // is stop
            if (stop) {
                break;
            }
            // 检测在线用户，多久没有发送心跳，超过规定时间的删除掉
            checkHeartBeat();
        }
    }

    public void checkHeartBeat() {
        Session[] sessions = tcpSessionManager.getSessions();
        for (Session session : sessions) {
            if (session.expire()) {
                session.close();
                log.info("heart is expire,clear sessionId:" + session.getSessionId());
            }
        }
    }

    private boolean isSessionEmpty() {
        return tcpSessionManager.getSessionCount() == 0;
    }

    private void awaitQueue() {
        boolean flag = lock.tryLock();
        if (flag) {
            try {
                notEmpty.await();
            } catch (InterruptedException e) {
                log.error("TcpHeartbeatListener awaitQueue occur InterruptedException!", e);
            } catch (Exception e) {
                log.error("await Thread Queue error!", e);
            } finally {
                lock.unlock();
            }
        }
    }

    private void signalQueue() {
        boolean flag = false;
        try {
            flag = lock.tryLock(100, TimeUnit.MILLISECONDS);
            if (flag) {
                notEmpty.signalAll();
            }
        } catch (InterruptedException e) {
            log.error("TcpHeartbeatListener signalQueue occur InterruptedException!", e);
        } catch (Exception e) {
            log.error("signal Thread Queue error!", e);
        } finally {
            if (flag) {
                lock.unlock();
            }
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void sessionCreated(SessionEvent se) {
        signalQueue();
    }

    @Override
    public void sessionDestroyed(SessionEvent se) {
    }

}
