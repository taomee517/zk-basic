package com.demo.zk.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;

/**
 * zk节点异步创建回调类
 *
 * @Author luotao
 * @E-mail taomee517@qq.com
 * @Date 2019\12\29 0029 11:01
 */
@Slf4j
public class ZkCallback implements AsyncCallback.StringCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        log.info("rc:"+rc+",path:"+path+",ctx:"+ctx+",name:"+name);
    }
}
