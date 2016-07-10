package com.jxdd.ecp.notify.ext.register;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode.Mode;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jxdd.ecp.notify.core.NotifyMateData;
import com.jxdd.ecp.notify.core.constant.NotifyConstant;
import com.jxdd.ecp.notify.core.register.NotifyMateDataRegisterService;
import com.jxdd.ecp.notify.core.serializable.util.JsonUtils;

public class ZookeeperRegisterService implements NotifyMateDataRegisterService {

	private CuratorFramework client = null;

	private String hosts;

	private String notifyPath = "/notify";

	public String getNotifyPath() {
		return notifyPath;
	}

	public void setNotifyPath(String notifyPath) {
		this.notifyPath = notifyPath;
	}

	private Logger logger = LoggerFactory
			.getLogger(ZookeeperRegisterService.class);

	private String p2pPath = notifyPath + "/" + NotifyConstant.QUEUE_TYPE_P2P;

	private String topicPath = notifyPath + "/"
			+ NotifyConstant.QUEUE_TYPE_TOPIC;

	@Override
	public void registerNotifyMateData(List<NotifyMateData> producerList,
			List<NotifyMateData> consumerList) throws Exception {
		this.regConsumerList(consumerList);
		this.regProducerList(producerList);
	}

	private void regConsumerList(List<NotifyMateData> consumerList)throws Exception{
		for (Iterator iterator = consumerList.iterator(); iterator.hasNext();) {
			NotifyMateData notifyMateData = (NotifyMateData) iterator.next();
			if (notifyMateData.getQueueType().equals(
					NotifyConstant.QUEUE_TYPE_P2P)) {
				String path = p2pPath+"/" + notifyMateData.getQueueName();
				Stat statQueue = client.checkExists().forPath(path);
				if(statQueue == null){
					client.inTransaction().create().withMode(CreateMode.PERSISTENT)
					.forPath(path).and().commit();
				}
				String consumerPath = path + "/"+NotifyConstant.NOTIFY_TYPE_CONSUMER;
				Stat producerQueue = client.checkExists().forPath(consumerPath);
				if(producerQueue == null){
					client.inTransaction().create().withMode(CreateMode.PERSISTENT)
					.forPath(consumerPath).and().commit();
				}
				String clientpath = consumerPath+"/"+notifyMateData.getId();
				PersistentEphemeralNode	node = new PersistentEphemeralNode(client, Mode.EPHEMERAL,clientpath, JsonUtils.getInstance().javaObjectToString(notifyMateData).getBytes("UTF-8"));
				node.start();
				node.waitForInitialCreate(3, TimeUnit.SECONDS);
			}else if(notifyMateData.getQueueType().equals(
					NotifyConstant.QUEUE_TYPE_TOPIC)){
				String path = topicPath+"/" + notifyMateData.getQueueName();
				Stat statQueue = client.checkExists().forPath(path);
				if(statQueue == null){
					client.inTransaction().create().withMode(CreateMode.PERSISTENT)
					.forPath(path).and().commit();
				}
				String consumerPath = path + "/"+NotifyConstant.NOTIFY_TYPE_CONSUMER;
				Stat producerQueue = client.checkExists().forPath(consumerPath);
				if(producerQueue == null){
					client.inTransaction().create().withMode(CreateMode.PERSISTENT)
					.forPath(consumerPath).and().commit();
				}
				String clientpath = consumerPath+"/"+notifyMateData.getId();
				PersistentEphemeralNode	node = new PersistentEphemeralNode(client, Mode.EPHEMERAL,clientpath, 
						JsonUtils.getInstance().javaObjectToString(notifyMateData).getBytes("UTF-8"));
				node.start();
				node.waitForInitialCreate(3, TimeUnit.SECONDS);
			}
		}
	}
	
	private void regProducerList(List<NotifyMateData> producerList) throws Exception {
		for (Iterator<NotifyMateData> iterator = producerList.iterator(); iterator.hasNext();) {
			NotifyMateData notifyMateData = (NotifyMateData) iterator.next();
			if (notifyMateData.getQueueType().equals(
					NotifyConstant.QUEUE_TYPE_P2P)) {
				String path = p2pPath +"/"+ notifyMateData.getQueueName();
				Stat statQueue = client.checkExists().forPath(path);
				if(statQueue == null){
					client.inTransaction().create().withMode(CreateMode.PERSISTENT)
					.forPath(path).and().commit();
				}
				String producerPath = path + "/"+NotifyConstant.NOTIFY_TYPE_PRODUCER;
				Stat producerQueue = client.checkExists().forPath(producerPath);
				if(producerQueue == null){
					client.inTransaction().create().withMode(CreateMode.PERSISTENT)
					.forPath(producerPath).and().commit();
				}
				String clientpath = producerPath+"/"+notifyMateData.getId();
				PersistentEphemeralNode	node = new PersistentEphemeralNode(client, Mode.EPHEMERAL,clientpath, JsonUtils.getInstance().javaObjectToString(notifyMateData).getBytes("UTF-8"));
				node.start();
				node.waitForInitialCreate(3, TimeUnit.SECONDS);
			}else if(notifyMateData.getQueueType().equals(
					NotifyConstant.QUEUE_TYPE_TOPIC)){
				String path = topicPath +"/"+ notifyMateData.getQueueName();
				Stat statQueue = client.checkExists().forPath(path);
				if(statQueue == null){
					client.inTransaction().create().withMode(CreateMode.PERSISTENT)
					.forPath(path).and().commit();
				}
				String producerPath = path + "/"+NotifyConstant.NOTIFY_TYPE_PRODUCER;
				Stat producerQueue = client.checkExists().forPath(producerPath);
				if(producerQueue == null){
					client.inTransaction().create().withMode(CreateMode.PERSISTENT)
					.forPath(producerPath).and().commit();
				}
				String clientpath = producerPath+"/"+notifyMateData.getId();
				PersistentEphemeralNode	node = new PersistentEphemeralNode(client, Mode.EPHEMERAL,clientpath, JsonUtils.getInstance().javaObjectToString(notifyMateData).getBytes("UTF-8"));
				node.start();
				node.waitForInitialCreate(3, TimeUnit.SECONDS);
			}
		}
	}

	@Override
	public void init() throws Exception {
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
		Stat statp2p = client.checkExists().forPath(p2pPath);
		if (statp2p == null) {
			client.inTransaction().create().withMode(CreateMode.PERSISTENT)
					.forPath(p2pPath).and().commit();
		}
		Stat stattopic = client.checkExists().forPath(topicPath);
		if (stattopic == null) {
			client.inTransaction().create().withMode(CreateMode.PERSISTENT)
					.forPath(topicPath).and().commit();
		}
	}

	@Override
	public void destroy() throws Exception {
		CloseableUtils.closeQuietly(client);

	}
	
	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}
}
