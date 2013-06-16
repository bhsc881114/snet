//package code.github.snet.util;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import code.github.snet.SClient;
//import code.github.snet.SServer;
//import code.github.snet.codec.SerialInterface;
//import code.github.snet.common.BsFutureDone;
//import code.github.snet.common.BsNetListener;
//import code.github.snet.reactor.ReactorClient;
//import code.github.snet.reactor.ReactorCore;
//import code.github.snet.reactor.ReactorServer;
//
///**
// * 一个Factory代表一组资源，对于客户端来说，最好只用一个Factory 对于Server来说，不同的监听端口使用不同的Factory
// * 
// * @author bishan.ct
// * 
// */
//public class BsFactory {
//
//	private static ReactorCore[] reactors =null;
//	public static boolean IS_INIT = false;
//	public static boolean IS_CLIENT_INIT = false;
//	public boolean IS_SERVER_INIT = false;
//	/**序列化/反序列化**/
//	static ThreadLocal<SerialInterface> kryoSerial=new ThreadLocal<SerialInterface>(){
//		protected SerialInterface initialValue(){
//			return new KryoSerial();			
//		}
//	};
//	
//	private static final AtomicInteger workerIndex = new AtomicInteger();
//	
//	public static ReactorClient reactorClientConnect;
//	public ReactorServer reactorServerListener;
//	
//	public BsFactory(){
//		try {
//			init();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public BsFactory(boolean isInitClint,boolean isInitServer) throws IOException {
//		if(isInitClint){
//			initClinet();
//		}
//		if(isInitServer){
//			initServer();
//		}
//	}
//
//	public BsFutureDone<SClient> clientConnect(
//			String host, int port,BsNetListener readCallBack) throws Exception {
//		if(reactorClientConnect==null){
//			throw new NullPointerException("client connect reactor is null," +
//			"call initClinet() first");
//		}
//		InetSocketAddress remoteAdr=new InetSocketAddress(host,port);
//		
//		SClient bc = new SClient(nextReactor(),readCallBack);
//		//connect
//		reactorClientConnect.connect(bc,remoteAdr);
//		
//		return bc.connectFuture;
//	}
//
//	public BsFutureDone<SServer> serverListen(int port,
//			BsNetListener msgCallBack) throws IOException {
//		if(reactorServerListener==null){
//			throw new NullPointerException("server lisenter reactor is null," +
//					"call initServer() first");
//		}
//		SServer bns=new SServer(msgCallBack);
//		
//		reactorServerListener.listen(bns,port);
//		return bns.serverFuture;
//	}
//	
//	/**
//	 * 只初始化一次,如果一个极其即有Server也有Client，reactor其实可以共用
//	 * @throws IOException
//	 */
//	public static synchronized void init() throws IOException {
//		if (IS_INIT) {
//			return;
//		}
//		reactors = new ReactorCore[BsThreadPool.IO_THREADS];
//		for (int i = 0; i < reactors.length; i++) {
//			reactors[i] = new ReactorCore();
//		}
//		IS_INIT=true;
//	}
//	
//	/**
//	 * 初始化REACTOR资源 主要是有个REACTOR专门用于发起连接
//	 * @throws IOException 
//	 */
//	public static synchronized void initClinet() throws IOException {
//		if (IS_CLIENT_INIT) {
//			return;
//		}
//		reactorClientConnect=new ReactorClient();
//		IS_CLIENT_INIT=true;
//	}
//
//	/**
//	 * 初始化Server资源 主要是有个REACTOR专门用于接受连接
//	 * @throws IOException 
//	 */
//	public synchronized void initServer() throws IOException {
//		if(IS_SERVER_INIT){
//			return;
//		}
//		reactorServerListener=new ReactorServer();
//		IS_SERVER_INIT=true;
//	}
//
//	public static ReactorCore nextReactor(){
//		return reactors[workerIndex.getAndIncrement()&BsThreadPool.AND_IO_THREADS];
//	}
//	
// }
