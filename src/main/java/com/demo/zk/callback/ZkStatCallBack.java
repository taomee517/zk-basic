package com.demo.zk.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

@Slf4j
public class ZkStatCallBack implements AsyncCallback.StatCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        log.info("rc:"+rc + ", path:" + path + ",ctx:" + ctx + ", stat:" + stat);
    }
}
