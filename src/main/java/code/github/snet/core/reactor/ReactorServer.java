//package code.github.snet.reactor;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.SocketChannel;
//
//import code.github.snet.AbstractNet;
//import code.github.snet.SClient;
//import code.github.snet.SServer;
//
///**
// * 只处理接收请求事件
// * @author bishan.ct
// *
// */
//public class ReactorServer extends ReactorCore {
//
//	public ReactorServer() throws IOException {
//		super();
//		selectTime=500;
//	}
//
//	@Override
//	void handleKey(SelectionKey key,AbstractNet attachObj) {
//		SServer server = (SServer)attachObj;
//		try{
//			SocketChannel channel = server.serverSocketChannel.accept();
//			channel.configureBlocking(false);
//			SClient bc = new SClient(BsFactory.nextReactor(),
//					channel,server.msgCallBack);
//			bc.isServer=true;
//			bc.reactor.register(bc);
//		}catch(IOException e){
//			server.close();
//		}
//	}
//	
//	public void register(AbstractNet netConnect,final int port){
//		final SServer serverConnect=(SServer)netConnect;
//		
//		tasks.offer(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					serverConnect.serverSocketChannel.socket().bind(new InetSocketAddress(port));
//					serverConnect.serverFuture.setDone();
//					
//					//register accept
//					serverConnect.serverSocketChannel.register(
//							nioSelector,SelectionKey.OP_ACCEPT, serverConnect);
//				}catch (IOException e) {
//					serverConnect.serverFuture.setException(e);
//				}
//			}
//		});
//	}
//	
//	public void listen(SServer server,int port){
//		register(server,port);
//	}
// }
