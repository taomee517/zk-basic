package com.demo.zk.apicase;

import com.demo.zk.callback.ZkCallback;
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
public class ZkAsyncConnect {
    private static final String ZK_ADDR = "127.0.0.1:2181";
    private static final String PREFIX_ASYNC = "/zktest-async-create-";

    public static void main(String[] args) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zk = new ZooKeeper(ZK_ADDR,5000,new ZkWathcer(countDownLatch));
            log.info("zk连接状态：{}",zk.getState());
            countDownLatch.await();
            log.info("zk连接创建成功！");
            zk.create(PREFIX_ASYNC, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                    new ZkCallback(), "my test text...1");
            zk.create(PREFIX_ASYNC, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                    new ZkCallback(), "my test text...2");
            zk.create(PREFIX_ASYNC, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                    new ZkCallback(), "my test text...3");
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            log.error("创建zk连接发生异常：{}",e);
        }

    }
}
