
package com.zy.test;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by zy on 2017/3/4.
 */
public class ZookeeperOperator extends AbstractZookeeper {

    private static Logger log = LoggerFactory.getLogger(ZookeeperOperator.class);

    /**
     * 创建节点
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void create(String path, byte[] data)throws KeeperException, InterruptedException{
        /*        此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect.
                * EPHEMERAL 表示The znode will be deleted upon the client's disconnect. */
        this.zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 获取节点
     * @param path
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void getChild(String path)throws KeeperException, InterruptedException{
        try{
            List<String> list = this.zookeeper.getChildren(path, false);
            if(list.isEmpty()){
                System.out.println(path+"中没有节点");
            } else {
                System.out.println(path+"中有节点");
                for(String child:list){
                    System.out.println("节点为"+child);
                }
            }
        } catch (KeeperException e) {
            throw e;
        }

    }

    public byte[] getData(String path) throws KeeperException, InterruptedException{
        return  this.zookeeper.getData(path, false, null);
    }

    public static void main(String[] args){
        try {
            ZookeeperOperator zkoperator = new ZookeeperOperator();
            zkoperator.connect("192.168.1.5:2181");

            byte[] data = new byte[]{'a', 'b', 'c', 'd'};

            String zktest = "zookeeper test";
//            boolean isExists = zkoperator.zookeeper.exists("/my_data", null);
//            zkoperator.create("/my_data/child1", zktest.getBytes());
            System.out.println("获取位置信息："+ new String(zkoperator.getData("/my_data/child1")));
             System.out.println("节点信息列表：");
            zkoperator.getChild("/my_data");

            zkoperator.close();
        } catch (KeeperException e1){
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
