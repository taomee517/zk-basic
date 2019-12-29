package com.demo.zk;

import com.demo.zk.watcher.ZkWathcer;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * 创建zk连接
 *
 * @Author luotao
 * @E-mail taomee517@qq.com
 * @Date 2019\12\29 0029 10:40
 */

@Slf4j
public class ZkSyncConnect {
    private static final String ZK_ADDR = "127.0.0.1:2181";
    private static final String PREFIX = "/zktest-sync-create-";

    public static void main(String[] args) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zk = new ZooKeeper(ZK_ADDR,5000,new ZkWathcer(countDownLatch));
            countDownLatch.await();
            log.info("zk连接创建成功！");
            String path1 = zk.create(PREFIX, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            log.info("success create znode:{}", path1);
            String path2 = zk.create(PREFIX, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            log.info("success create znode:{}", path2);
            zk.close();
        } catch (Exception e) {
            log.error("创建zk连接发生异常：{}",e);
        }

    }
}
