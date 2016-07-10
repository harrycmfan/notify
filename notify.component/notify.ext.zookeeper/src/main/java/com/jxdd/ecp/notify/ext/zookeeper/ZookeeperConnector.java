package com.jxdd.ecp.notify.ext.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ZookeeperConnector {
	
	
	private Logger logger = LoggerFactory.getLogger(ZookeeperConnector.class);
	
	private CuratorFramework client = null;
	
	public CuratorFramework getClient() {
		return client;
	}

	public ZookeeperConnector(String hosts) {
		client = CuratorFrameworkFactory.newClient(hosts,
				new ExponentialBackoffRetry(1000, 3));
		client.getConnectionStateListenable().addListener(
				new ConnectionStateListener() {
					@Override
					public void stateChanged(CuratorFramework arg0,
							ConnectionState arg1) {
						logger.info("client state:" + arg1.name());
					}
		});
		client.start();
	}
}
