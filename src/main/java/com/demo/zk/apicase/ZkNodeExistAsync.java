package com.demo.zk.apicase;

import com.demo.zk.callback.ZkStatCallBack;
import com.demo.zk.callback.ZkStringCallback;
import com.demo.zk.callback.ZkVoidCallback;
import com.demo.zk.watcher.ZkNodeDeleteWatcher;
import com.demo.zk.watcher.ZkNodeExistAsyncWatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZkNodeExistAsync {
    private static final String ZK_ADDR = "127.0.0.1:2181";
    private static final String PREFIX_EXIST= "/existTest";

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZkNodeExistAsyncWatcher watcher = new ZkNodeExistAsyncWatcher(countDownLatch);
        ZooKeeper zk = new ZooKeeper(ZK_ADDR,5000, watcher);
        watcher.setZk(zk);
        countDownLatch.await();
        log.info("ZK创建连接成功！");
        zk.exists(PREFIX_EXIST,watcher,new ZkStatCallBack(),"zk connected");
        zk.create(PREFIX_EXIST, "exist-test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.setData(PREFIX_EXIST, "data-change".getBytes(), -1);
        zk.delete(PREFIX_EXIST, -1);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
