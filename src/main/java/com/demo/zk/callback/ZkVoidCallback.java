package com.demo.zk.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;

/**
 * zk节点异步创建回调类
 *
 * @Author luotao
 * @E-mail taomee517@qq.com
 * @Date 2019\12\29 0029 11:01
 */
@Slf4j
public class ZkVoidCallback implements AsyncCallback.VoidCallback {

    @Override
    public void processResult(int rc, String path, Object ctx) {
        log.info("rc:"+rc+",path:"+path+",ctx:"+ctx);
        if(rc != KeeperException.Code.OK.intValue()) {
            log.info("执行失败，失败原因：{}", KeeperException.Code.get(rc).name());
        }
    }
}
