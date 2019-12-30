package com.demo.zk.watcher;

import com.demo.zk.callback.ZkStatCallBack;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

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
public class ZkNodeExistAsyncWatcher implements Watcher {
    private CountDownLatch latch;
    private ZooKeeper zk;

    public ZkNodeExistAsyncWatcher(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void process(WatchedEvent event) {

        if(Event.KeeperState.SyncConnected.equals(event.getState())){
            try {
                String path = event.getPath();
                if (Objects.nonNull(path)) {
                    zk.exists(path,true, new ZkStatCallBack(),event.getType().name());
                }
                if (Event.EventType.None.equals(event.getType()) && Objects.isNull(path)) {
                    log.info("连接事件：{}",event);
                    latch.countDown();
                }else if(Event.EventType.NodeCreated.equals(event.getType())){
                    log.info("节点创建事件，path:{}", path);
                }else if(Event.EventType.NodeDeleted.equals(event.getType())){
                    log.info("节点删除事件，path:{}", path);
                }else if(Event.EventType.NodeChildrenChanged.equals(event.getType())){
                    log.info("子节点变更事件，path:{} children nodes changed",path);
                }else if(Event.EventType.NodeDataChanged.equals(event.getType())){
                    log.info("节点数据变更事件，path:{}",  path);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
