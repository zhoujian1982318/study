package net.relay2.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZkTest {

	private Watcher watcher = new Watcher() {

		public void process(WatchedEvent event) {
			System.out.println("process : " + event.getType());
		}
	};
	private ZooKeeper zk;
	private String name="element_";
	public void create() {
		String result = null;
		try {
			result = zk.create("/zk001", "zk001data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("create result:" + result);
	}
	
	public void createSeqNode(){
		String result = null;
		try {
			for(int i=0; i<3 ;i++){
				result = zk.create("/zk_test/" +name , new byte[0], Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		System.out.println("create result:" + result);
	}

	public ZkTest() throws IOException {
		String hostPort = "10.10.20.94,10.10.20.102,10.10.20.101";
		zk = new ZooKeeper(hostPort, 3000, watcher);
		
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		ZkTest test = new ZkTest();
		
		test.createSeqNode();
		while(true){
			Thread.sleep(1000);
		}
	}

}
