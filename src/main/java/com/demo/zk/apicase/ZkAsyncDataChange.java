package com.demo.zk.apicase;

import com.demo.zk.callback.ZkStatCallBack;
import com.demo.zk.watcher.ZkDataChangeWatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * 节点数据变更
 *
 * @Author luotao
 * @E-mail taomee517@qq.com
 * @Date 2019\12\29 0029 10:40
 */

@Slf4j
public class ZkAsyncDataChange {
    private static final String ZK_ADDR = "127.0.0.1:2181";
    private static final String PREFIX_DATA_CHANGE = "/data_change_async";
    private  static final Stat stat = new Stat();

    /**
     * 如果节点下有子节点，不能直接删除，必须在删除子节点后才能删除
     * @param args
     */
    public static void main(String[] args) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ZkDataChangeWatcher watcher = new ZkDataChangeWatcher(countDownLatch);
            watcher.setStat(stat);
            ZooKeeper zk = new ZooKeeper(ZK_ADDR,5000,watcher);
            watcher.setZk(zk);
            log.info("zk连接状态：{}",zk.getState());
            countDownLatch.await();
            log.info("zk连接创建成功！");
            zk.create(PREFIX_DATA_CHANGE, "origin data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            zk.setData(PREFIX_DATA_CHANGE, "data changed".getBytes(), -1,new ZkStatCallBack(),"data change ctx");
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            log.error("创建zk连接发生异常：{}",e);
        }

    }
}
