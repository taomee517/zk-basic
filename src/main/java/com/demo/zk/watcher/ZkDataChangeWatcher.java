package com.demo.zk.watcher;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 节点数据变更监听器
 *
 * @Author luotao
 * @E-mail taomee517@qq.com
 * @Date 2019\12\29 0029 12:14
 */
@Slf4j
@Data
public class ZkDataChangeWatcher implements Watcher {
    private CountDownLatch latch;
    private ZooKeeper zk;
    private Stat stat;

    public ZkDataChangeWatcher(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void process(WatchedEvent event) {
        if(Event.KeeperState.SyncConnected.equals(event.getState())){

            if (Event.EventType.None.equals(event.getType()) && Objects.isNull(event.getPath())) {
                log.info("连接事件：{}",event);
                latch.countDown();
            }else if(Event.EventType.NodeChildrenChanged.equals(event.getType())){
                log.info("子节点变更事件，path:{} children nodes changed",event.getPath());
            }else if(Event.EventType.NodeDataChanged.equals(event.getType())){
                String data = null;
                try {
                    data = new String(zk.getData(event.getPath(), true, stat));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("节点数据变更事件，path:" +  event.getPath() + ",data:" + data + ",watch czxid::"+stat.getCzxid()+",mzxid::" + stat.getMzxid() + ",version::" +  stat.getVersion());
            }
        }
    }
}
