package code.github.snet.core;

//package code.github.snet;
//
//import java.io.IOException;
//import java.nio.channels.ServerSocketChannel;
//
//import code.github.snet.common.BsFutureDone;
//import code.github.snet.common.BsNetListener;
//import code.github.snet.common.DefaultFutureResult;
//
//
///**
// *
// * 
// * @author bishan.ct
// *
// */
//public class SServer extends AbstractNet{
//
//	ServerSocketChannel serverSocketChannel;
//	BsFutureDone<SServer> serverFuture;
//	
//	private SServer(){
//		
//	}
//	
//	public static bindServer(){
//		
//	}
//	
//	public SServer(BsNetListener msgCallBack) throws IOException{
//		//TODO CONFIG
//		serverSocketChannel = ServerSocketChannel.open();
//
//		serverSocketChannel.configureBlocking(false);
//		serverFuture=new DefaultFutureResult<SServer>(this);
//		this.msgCallBack=msgCallBack;
//	}
//
//	@Override
//	protected void innerClose() {
//		// TODO Auto-generated method stub
//		try {
//			serverSocketChannel.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
// }
