package com.logoocc.main;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZkConnect {
	private ZooKeeper zk;
	private CountDownLatch connSignal = new CountDownLatch(0);

	// host should be 127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002
	public ZooKeeper connect(String host) throws Exception {
		zk = new ZooKeeper(host, 3000, new Watcher() {
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {
					connSignal.countDown();
				}
			}
		});
		connSignal.await();
		return zk;
	}

	public void close() throws InterruptedException {
		zk.close();
	}

	public void createNode(String path, byte[] data) throws Exception {
		zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	public void updateNode(String path, byte[] data) throws Exception {
		zk.setData(path, data, zk.exists(path, true).getVersion());
	}

	public void deleteNode(String path) throws Exception {
		zk.delete(path, zk.exists(path, true).getVersion());
	}

	public static void main(String args[]) throws Exception {
		ZkConnect connector = new ZkConnect();
		// ZooKeeper zk = connector.connect("54.169.132.0,52.74.51.0");
		ZooKeeper zk = connector.connect("127.0.0.1:2181");
		// String newNode = "/deepakDate"+new Date();
		// connector.createNode(newNode, new Date().toString().getBytes());
		//
		List<String> zNodes = zk.getChildren("/", true);
		//

//		for (String zNode : zNodes) {
//			System.out.println("ChildrenNode " + zNode);
//			connector.deleteNode("/" + zNode);
//		}

//		for (int i = 0; i < zNodes.size(); i++) {
			connector.deleteNode("/" + zNodes.get(1));
//		}
		// byte[] data = zk.getData(newNode, true, zk.exists(newNode, true));
		// System.out.println("GetData before setting");
		// for ( byte dataPoint : data)
		// {
		// System.out.print ((char)dataPoint);
		// }
		//
		// System.out.println("GetData after setting");
		// connector.updateNode(newNode, "Modified data".getBytes());
		// data = zk.getData(newNode, true, zk.exists(newNode, true));
		// for ( byte dataPoint : data)
		// {
		// System.out.print ((char)dataPoint);
		// }
		// connector.deleteNode("deepakDateMon Mar 14 14:25:56 CST 2016");
		// connector.deleteNode(newNode);
	}

}
