package com.demo.zk.apicase;

import com.demo.zk.callback.ZkChildren2Callback;
import com.demo.zk.watcher.ZkChildChangeWatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * 获取zk子节点
 *
 * @Author luotao
 * @E-mail taomee517@qq.com
 * @Date 2019\12\29 0029 10:40
 */

@Slf4j
public class ZkChildrenAsyncGet {
    private static final String ZK_ADDR = "127.0.0.1:2181";
    private static final String PREFIX_CHILDREN = "/children_get_async";

    /**
     * 如果节点下有子节点，不能直接删除，必须在删除子节点后才能删除
     * @param args
     */
    public static void main(String[] args) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ZkChildChangeWatcher watcher = new ZkChildChangeWatcher(countDownLatch);
            ZooKeeper zk = new ZooKeeper(ZK_ADDR,5000,watcher);
            log.info("zk连接状态：{}",zk.getState());
            countDownLatch.await();
            log.info("zk连接创建成功！");
            zk.create(PREFIX_CHILDREN, "parentNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zk.create(PREFIX_CHILDREN + "/child1", "childNode1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            zk.getChildren(PREFIX_CHILDREN, true, new ZkChildren2Callback(), null);
            zk.create(PREFIX_CHILDREN + "/child2", "childNode2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            zk.getChildren(PREFIX_CHILDREN, true, new ZkChildren2Callback(), null);
            zk.create(PREFIX_CHILDREN + "/child3", "childNode3".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            zk.getChildren(PREFIX_CHILDREN, true, new ZkChildren2Callback(), null);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            log.error("创建zk连接发生异常：{}",e);
        }

    }
}
