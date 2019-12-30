package com.demo.zk.apicase;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZkAuthTest implements Watcher {
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);
    private static final String ADDRESS = "127.0.0.1:2181";
    private static final String PREFIX_AUTH = "/auth";
    private static ZooKeeper zk1 ;

    /**
     * 权限模式:
     * 　1) IP
     * 　　ip模式是指权限针对这个ip而设置的,比如"ip:192.168.0.6",即允许这个ip访问数据节点
     *   2) digest
     *     digest模式是最常用的一种模式,形如"username:password"的方式。
     * 　3) world
     *     该模式对所有用户开放
     *   4) super
     *     超级管理员模式。需要在zkServer.sh中配置，形如"super:password" ，需要重启服务器
     *
     * 说明：
     *
     * 可以看到,第一次我们使用无权限的zk2去删除,显然会报错,
     * 第二次我们使用带权限的zk3去操作,子节点被删除成功,
     * 但是当我们使用zk4去执行删除操作的时候并没有指定任何权限,依然能够删除其父节点,
     * 说明zk在进行删除操作的时候,其权限的作用范围是其子节点。
     * 也就是说,当我们对一个节点添加了权限之后我们依然可以随意删除该节点但是对于这个节点的子节点,就必须拥有相应的权限才能删除。
     * 而且zk原生api不支持递归删除,即在存在子节点的情况下,不允许删除其父节点。
     */
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        try {
            Watcher watcher = new ZkAuthTest();
            zk1 = new ZooKeeper(ADDRESS, 5000, watcher);
            countDownLatch.await();
            zk1.addAuthInfo("digest", "true".getBytes());
            zk1.create(PREFIX_AUTH, "auth".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            zk1.create(PREFIX_AUTH+"/c2", "auth child".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
            ZooKeeper zk2 = new ZooKeeper(ADDRESS, 5000, watcher);
            try {
                zk2.delete(PREFIX_AUTH+"/c2", -1);
            } catch (Exception e) {
                log.info("delete exception: " + e);
            }
            ZooKeeper zk3 = new ZooKeeper(ADDRESS, 5000, watcher);
            zk3.addAuthInfo("digest", "true".getBytes());
            zk3.delete(PREFIX_AUTH+"/c2", -1);
            log.info("delete {} success",PREFIX_AUTH+"/c2");
            ZooKeeper zk4 = new ZooKeeper(ADDRESS, 5000, watcher);
            zk4.delete(PREFIX_AUTH, -1);
            log.info("delete {} success", PREFIX_AUTH);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            log.info("exception ::" +e);
        }

    }

    @Override
    public void process(WatchedEvent event) {
        if(Event.KeeperState.SyncConnected == event.getState()){
            if(Event.EventType.None == event.getType() && null == event.getPath()){
                countDownLatch.countDown();
            }
        }
    }
}
