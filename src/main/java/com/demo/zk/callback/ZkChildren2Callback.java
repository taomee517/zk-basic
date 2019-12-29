package com.demo.zk.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * zk节点异步创建回调类
 *
 * @Author luotao
 * @E-mail taomee517@qq.com
 * @Date 2019\12\29 0029 11:01
 */
@Slf4j
public class ZkChildren2Callback implements AsyncCallback.Children2Callback {

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        log.info(rc + "::" + path + "::" +ctx +"::" + children + "::" + stat);
        if(rc != KeeperException.Code.OK.intValue()) {
            log.info("执行失败，失败原因：{}", KeeperException.Code.get(rc).name());
        }
    }
}
