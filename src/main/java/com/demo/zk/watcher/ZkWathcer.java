package com.demo.zk.watcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * zk监听
 *
 * @Author luotao
 * @E-mail taomee517@qq.com
 * @Date 2019\12\29 0029 10:41
 */
@Slf4j
public class ZkWathcer implements Watcher {
    private CountDownLatch latch;

    public ZkWathcer(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected.equals(watchedEvent.getState())){
            log.info("收到事件：{}",watchedEvent);
            latch.countDown();
        }
    }
}
