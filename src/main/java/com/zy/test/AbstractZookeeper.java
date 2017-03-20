package com.zy.test;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zy on 2017/3/4.
 */
public class AbstractZookeeper implements Watcher {
    private static Logger log = LoggerFactory.getLogger(AbstractZookeeper.class);

    /*缓存时间*/
    private static final int SESSION_TIME = 2000;
    protected ZooKeeper zookeeper;
    protected CountDownLatch countDownLatch = new CountDownLatch(1);

    public void connect(String host)throws IOException,InterruptedException{
        zookeeper = new ZooKeeper(host, SESSION_TIME, this);
//        countDownLatch.wait();
    }

    public void process(WatchedEvent event){
        if(event.getState()== Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
    }

    public void close() throws InterruptedException{
        zookeeper.close();
    }
}
