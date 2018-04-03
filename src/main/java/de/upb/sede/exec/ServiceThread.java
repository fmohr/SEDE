//package de.upb.sede.exec;
//
//import java.util.List;
//import java.lang.Runnable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.ListIterator;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.ReentrantLock;
//
//import interfaces.AbstractNode;
//import interfaces.StartNode;
//import interfaces.EndNode;
//import interfaces.Node;
//import interfaces.MeasurementNode;
//import service.Service;
//import service.ServiceData;
//import service.ServiceInstance;
//
//public class ServiceThread implements Runnable {
//	// Lock to set a Node Done or to set getsProcessed
//	private static ReentrantLock mAllThreadLock = new ReentrantLock(true);
//	private static List<?> mInputObjectListStart;
//	// Lock and Condition to signal the Distributor the end of the composition
//	private static Condition mExitCondition;
//	private static ReentrantLock mLock;
//
//	private char mResource;
//	private AbstractNode mCurrentNode;
//	private ResourceAllocator mRA;
//	private List<Object> mInputObjectList;
//	private ServiceManager mServiceManager;
//	private ExecutorService mthreadPool;
//	// The Input image forwarding is necessary for measurement purposes.
//	// Usually you set this to false
//	private boolean mInputImageForwarding;
//
//	/**
//	 * @param node
//	 *            node which will be executed by this Service_Thread
//	 * @param serviceID
//	 *            serviceID of the node
//	 * @param ra
//	 *            RessourceAllocator
//	 * @param serviceManager
//	 *            List with all available plugins
//	 * @param ms
//	 *            global MeasurementSystem
//	 */
//	public ServiceThread(AbstractNode node, ResourceAllocator ra, ServiceManager serviceManager) {
//		mCurrentNode = node;
//		mRA = ra;
//		mServiceManager = serviceManager;
//		mInputObjectList = new LinkedList<Object>();
//		mInputImageForwarding = false;
//	}
//
//	/**
//	 * Activates the input image forwarding. If activated all nodes get the source
//	 * images as input.
//	 */
//	public void setinputImageForwarding() {
//		mInputImageForwarding = true;
//	}
//
//	/**
//	 * Set the input image list which will be used for image forwarding.
//	 * 
//	 * @param inputObjectList
//	 *            Image list from the start node
//	 */
//	public void setInputObjectListStart(List<?> inputObjectList) {
//		mInputObjectListStart = inputObjectList;
//	}
//
//	/**
//	 * Set the Condition for the EndNode and the lock for the condition.
//	 * 
//	 * @param lock
//	 *            Lock for the Condition
//	 * @param exitCondition
//	 *            Condition to signal the end to the Distributor
//	 */
//	public void setExitCondition(ReentrantLock lock, Condition exitCondition) {
//		mLock = lock;
//		mExitCondition = exitCondition;
//	}
//
//	/**
//	 * Starts the execution of a node
//	 * 
//	 * @param threadPool
//	 *            threadPool which is used to run the thread
//	 */
//	public void start(ExecutorService threadPool) {
//		mthreadPool = threadPool;
//		if (mExitCondition != null) {
//			mthreadPool.execute(this);
//		} else {
//			System.err.println("The ExitConditon needs to be set before a new thread gets started!");
//		}
//	}
//
//	/**
//	 * run method of the thread
//	 */
//	public void run() {
//		long threadId = Thread.currentThread().getId();
//		System.out.println("Thread " + threadId + " starts processing Node " + "\"" + mCurrentNode.getLabelName() + "\""
//				+ " ID: " + mCurrentNode.getID());
//		boolean allInNodesDone = true;
//
//		if (mCurrentNode instanceof Node || mCurrentNode instanceof MeasurementNode
//				|| mCurrentNode instanceof EndNode) {
//			ListIterator<AbstractNode> it = mCurrentNode.getInNodeList().listIterator();
//			AbstractNode prevNode;
//
//			// check if all InNodes are done
//			while (it.hasNext()) {
//				prevNode = it.next();
//				if (prevNode.isDone() == false) {
//					allInNodesDone = false;
//				}
//			}
//		}
//
//		if (allInNodesDone) {
//			// fill InputImageList
//			if (!(mCurrentNode instanceof StartNode)) {
//				ListIterator<Integer> it2 = mCurrentNode.getSource().listIterator();
//				int k = 0;
//				int index = 0;
//				while (it2.hasNext()) {
//					index = it2.next();
//					if (index >= mCurrentNode.getInNodeList().get(k).getOutObjectList().size()) {
//						System.err.println("ERROR: Missing input images! at Node " + mCurrentNode.getLabelName());
//						System.exit(-1);
//					} else {
//						// System.out.println("CurrentNode: " + mCurrentNode.getLabelName());
//						// System.out.println("PictureSource: " + index);
//						// System.out.println("InNodeListIndex: " + k);
//						// System.out.println("prevNode: " +
//						// mCurrentNode.getInNodeList().get(k).getLabelName());
//						mInputObjectList.add(mCurrentNode.getInNodeList().get(k).getOutObjectList().get(index));
//						k++;
//					}
//				}
//			}
//
//			// identify currentNode
//			if (mCurrentNode instanceof EndNode) {
//				mAllThreadLock.lock();
//				System.out.println("Reached EndNode");
//				mCurrentNode.setOutObjectList(mInputObjectList);
//				mCurrentNode.setDone();
//				mLock.lock();
//				try {
//					mExitCondition.signal();
//				} catch (Exception e) {
//					System.err.println(e.getMessage());
//				} finally {
//					mLock.unlock();
//				}
//				mAllThreadLock.unlock();
//
//			} else if (mCurrentNode instanceof StartNode) {
//				System.out.println("StartThread allocating new Threads");
//				allocateNewThreads();
//
//			} else if ((mCurrentNode instanceof MeasurementNode) && (!mCurrentNode.isDone())) {
//				MeasurementNode node = (MeasurementNode) mCurrentNode;
//				System.out.println(node.toString());
//				mCurrentNode.setOutObjectList(mInputObjectList);
//				if (node.isStart()) {
//					System.out.println("Thread " + threadId + " starts Measurement " + node.getName());
//				} else {
//					System.out.println("Thread " + threadId + " stops Measurement " + node.getName());
//				}
//				mCurrentNode.setDone();
//				allocateNewThreads();
//
//			} else if ((mCurrentNode instanceof Node) && (!mCurrentNode.isDone())) {
//				mResource = mCurrentNode.getService().getResource();
//				switch (mResource) {
//				case 'c':
//					// CPU OpenMP
//					mRA.getCPU();
//					runService();
//					break;
//				case 'g':
//					// GPU
//					mRA.getGPU();
//					runService();
//					break;
//				case 'f':
//					// FPGA
//					mRA.getFPGA();
//					// uncomment to force FPGA to idle before every execution on the fpga.
//					// mMS.fpgaForceIdle();
//					runService();
//					break;
//				case 's':
//					// CPU Single-Threaded
//					mRA.getCPU();
//					runService();
//					break;
//				}
//			}
//		} else {
//			// not all in nodes are done
//			mAllThreadLock.lock();
//			mCurrentNode.setGetsProcessed(false);
//			mAllThreadLock.unlock();
//			System.out.println("Thread " + threadId + " can't process Node " + "\"" + mCurrentNode.getLabelName() + "\""
//					+ " ID: " + mCurrentNode.getID() + ", because not all in nodes are processed.");
//		}
//	}
//
//	/**
//	 * run the service on the selected resource
//	 */
//	public void runService() {
//		ServiceData serviceData = mCurrentNode.getService();
//		List<Service> serviceList = mServiceManager.getServices();
//		Service service = null;
//		for (Service s : serviceList) {
//			if (s.getMetaInfos().getIDs().contains(serviceData.getParsedInvocation().getClassName())) {
//				service = s;
//				break;
//			}
//		}
//		if (service == null) {
//			if (serviceData != null && serviceData.getParsedInvocation() != null) {
//				System.err.println("Service: \"" + serviceData.getParsedInvocation().getClassName() + "\" unknown!");
//			} else {
//				System.err.println("The ServiceData or CallParse is null!");
//			}
//			return;
//		}
//		// if input image forwarding is activated the input images are replaced with the
//		// images from the StartNode.
//		if (mInputImageForwarding) {
//			mInputObjectList = (List<Object>) mInputObjectListStart;
//		}
//		// FIXME Give the parameters actual names!
//		if (serviceData.getParsedInvocation().isConstructCall()) {
//			Map<String, Object> params = new HashMap<>();
//			for (int i = 0; i < serviceData.getParams().size(); i++) {
//				params.put("" + i, serviceData.getParams().get(i));
//			}
//			System.out.println("Constructor call of service: " + serviceData.getParsedInvocation().getClassName());
//			System.out.println("Parameter number of constructor call: " + params.size());
//			for (Map.Entry<String, Object> e : params.entrySet())
//				System.out.println(e.getKey() + " --> " + e.getValue());
//			// FIXME edges need to have the option of no dataflow.
//			List<Object> tmpList = new ArrayList<>();
//			tmpList.add(service.newInstance(serviceData.getParsedInvocation().getInstanceName(), params));
//			mCurrentNode.setOutObjectList(tmpList);
//		} else { // start service
//			Map<String, Object> params = new HashMap<>();
//			if (serviceData.getParsedInvocation().isInstanceCall()) {
//				System.out.println("INSTANCE CALL");
//				ServiceInstance s = service.getInstance(
//						ServiceManager.getNameSpace() + "|" + serviceData.getParsedInvocation().getInstanceName());
//				Map<String, Object> params2 = new HashMap<>();
//				for (int i = 0; i < serviceData.getParams().size(); i++) {
//					params2.put("" + i, serviceData.getParams().get(i));
//				}
//				mCurrentNode.setOutObjectList(s.invokeOp(serviceData.getParsedInvocation().getOperation(), params2));
//			}
//			// The default process method is called
//			else {
//				System.out.println("process call : " + mCurrentNode.getAlgo() + " || " + mCurrentNode.getLabelName());
//				params.put("resource", mResource);
//				params.put("params", serviceData.getParams());
//				params.put("images", mInputObjectList);
//				String key = service.newInstance("tmp", params);
//				ServiceInstance instance = service.getInstance(key);
//				mCurrentNode.setOutObjectList(instance.invokeOp("process", params));
//				service.killInstance(key);
//			}
//		}
//		System.out.print("))) NodeID #" + mCurrentNode.getID() + " | Service: '"
//				+ serviceData.getParsedInvocation().getClassName() + "' | Parameters: '");
//		for (int j = 0; j < serviceData.getParams().size(); ++j) {
//			if (j == 0)
//				System.out.print(serviceData.getParams().get(j).toString());
//			else
//				System.out.print(", " + serviceData.getParams().get(j));
//		}
//		System.out.println("' | Resource: '" + serviceData.getResource() + "'");
//
//		switch (mResource) {
//		case 'c':
//			mRA.unblockCPU();
//			break;
//		case 'g':
//			mRA.unblockGPU();
//			break;
//		case 'f':
//			mRA.unblockFPGA();
//			break;
//		case 's':
//			mRA.unblockCPU();
//			break;
//		}
//		mCurrentNode.setDone();
//		allocateNewThreads();
//	}
//
//	/**
//	 * If the end of a node is not reached this method is called to start the
//	 * execution of the next nodes.
//	 */
//	public void allocateNewThreads() {
//
//		int count = 0;
//		ListIterator<AbstractNode> it = mCurrentNode.getOutNodeList().listIterator();
//		AbstractNode nextNode;
//
//		while (it.hasNext()) {
//			nextNode = it.next();
//			mAllThreadLock.lock();
//			if ((!nextNode.isDone()) && (!nextNode.getsProcessed())) {
//				ServiceThread nextThread = new ServiceThread(nextNode, mRA, mServiceManager);
//				if (mInputImageForwarding) {
//					nextThread.setinputImageForwarding();
//				}
//				nextNode.setGetsProcessed(true);
//				nextThread.start(mthreadPool);
//				count++;
//			}
//			mAllThreadLock.unlock();
//		}
//
//		System.out.println("Thread " + Thread.currentThread().getId() + " calls " + count + " next Node/s");
//	}
//}
