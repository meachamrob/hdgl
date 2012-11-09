package hdgl.db.server.bsp;

import static org.junit.Assert.*;

import java.util.List;

import hdgl.db.conf.GraphConf;
import hdgl.db.server.HConf;
import hdgl.util.StringHelper;

import org.apache.hadoop.conf.Configuration;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

public class BSPTest implements Watcher{

	@Test
	public void test() throws Exception {
//		Configuration conf = GraphConf.getDefault();
//		ZooKeeper zk = HConf.getZooKeeper(conf, this);
//		String root="/bsptest";
//		try {
//            Stat s = zk.exists(root, false);
//            if (s == null) {
//                zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE,
//                        CreateMode.PERSISTENT);
//            }
//            List<String> children = zk.getChildren(root, false);
//            if(children.size()>0){
//            	for(String child:children){
//            		zk.delete(StringHelper.makePath(root, child), 0);
//            	}
//            }
//            assert zk.getChildren(root, false).size() == 0;
//        } catch (NodeExistsException e) {
//           
//        } 
//		for(int i=0;i<10;i++){
//			new BSPRunner(null,null, root, 10, i, conf).start();
//		}
	}

	public static void main(String[] args){
		try {
			new BSPTest().test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
