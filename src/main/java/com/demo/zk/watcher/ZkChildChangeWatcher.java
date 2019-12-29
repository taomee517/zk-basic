package com.demo.zk.watcher;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 子节点发生变化监听器
 *
 * @Author luotao
 * @E-mail taomee517@qq.com
 * @Date 2019\12\29 0029 12:14
 */
@Slf4j
@Data
public class ZkChildChangeWatcher implements Watcher {
    private CountDownLatch latch;

    public ZkChildChangeWatcher(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void process(WatchedEvent event) {
        if(Event.KeeperState.SyncConnected.equals(event.getState())){
            log.info("收到事件：{}",event);
            if (Event.EventType.None.equals(event.getType()) && Objects.isNull(event.getPath())) {
                latch.countDown();
            }else if(Event.EventType.NodeChildrenChanged.equals(event.getType())){
                    log.info("path:{} children nodes changed",event.getPath());
            }
        }
    }
}
