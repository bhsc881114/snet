package code.github.snet.core;

//package code.github.snet;
//
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.nio.channels.ClosedChannelException;
//import java.nio.channels.SocketChannel;
//import java.util.Queue;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import code.github.snet.codec.FrameDecoder;
//import code.github.snet.codec.LengthFieldBasedFrameDecoder;
//import code.github.snet.common.BsFutureDone;
//import code.github.snet.common.BsNetListener;
//import code.github.snet.common.DefaultFutureResult;
//import code.github.snet.msg.AbstractPackage;
//import code.github.snet.msg.RequstPackage;
//import code.github.snet.msg.ResponsePackage;
//import code.github.snet.msg.WriteObj;
//import code.github.snet.reactor.ReactorCore;
//import code.github.snet.util.BsFactory;
//import code.github.snet.util.BsThreadPool;
//
//
///**
// * 
// * @author bishan.ct
// *
// */
//public class SClient extends AbstractNet{
//
//	ReactorCore reactor;
//	SocketChannel socketChannel;
//	boolean isServer;
//	BsFutureDone<SClient> connectFuture;
//	
//	/**
//	 * 写锁和写队列
//	 */
//	protected final Object writeLock = new Object();
//	protected final Queue<WriteObj> writeBufferQueue = new ConcurrentLinkedQueue<WriteObj>();
//	WriteObj currentWriteMsg;	//有可能写部分，没全写出去
//	
//	protected final Runnable writeTask = new WriteTask();
//	protected final AtomicBoolean writeTaskInTaskQueue = new AtomicBoolean();
//	protected volatile boolean inWriteNowLoop;
//
//	/**
//	 * 用于接收流式消息的缓冲
//	 */
//	FrameDecoder byteStreamDecoder=new LengthFieldBasedFrameDecoder(
//			BsThreadPool.MAX_MESSAGE);
//
//	public SClient(ReactorCore reactor,BsNetListener readCallBack) throws IOException{
//		this(reactor,SocketChannel.open(),readCallBack);
//	}
//	
//	public SClient(ReactorCore reactor,SocketChannel socketChannel,
//			BsNetListener readCallBack) throws IOException{
//		if(readCallBack==null){
//			throw new NullPointerException("readCallBack can not be null");
//		}
//		
//		this.reactor=reactor;
//		this.socketChannel=socketChannel;
//		this.socketChannel.socket().setTcpNoDelay(true);
//		this.msgCallBack=readCallBack;
//		this.socketChannel.configureBlocking(false);
//		connectFuture=new DefaultFutureResult<SClient>(this);
//	}
//	
//	public boolean isClose(){
//		return (state==ST_CLOSED);
//	}
//	
//	public BsFutureDone<Boolean> write(AbstractPackage obj) throws IOException{
//		if(isClose()){
//			throw new ClosedChannelException();
//		}
//		BsFutureDone<Boolean> writeFuture=new DefaultFutureResult<Boolean>();
//		
//		byte[] bs=BsFactory.kryoSerial.get().encode(obj);
//		//将长度和数据写到一块，作为整个数据输出
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		DataOutputStream  bos=new DataOutputStream (baos);
//		bos.writeInt(bs.length);
//		bos.write(bs);
//		WriteObj wobj=new WriteObj();
//		wobj.setObjBytes(baos.toByteArray());
//		
//		wobj.setWriteFuture(writeFuture);
//		
//		reactor.writeInUser(this,wobj);
//		
//		return writeFuture;
//	}
//	
//
//	public static void main(String[] args) throws IOException{
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		DataOutputStream  bos=new DataOutputStream (baos);
//		bos.writeInt(110);
//		
//		System.out.println(baos.toByteArray()[0]);
//	}
//	public void onRead(final Object obj){
//		BsThreadPool.DEAL_POOL.execute(new Runnable(){
//			@Override
//			public void run() {
//				byte[] datas=(byte[])obj;
//				Object decodeObj=null;
//				if(isServer){
//					decodeObj=BsFactory.kryoSerial.get().decode(datas,RequstPackage.class);
//				}else{
//					decodeObj=BsFactory.kryoSerial.get().decode(datas,ResponsePackage.class);
//				}
//				
//				msgCallBack.onMsg(SClient.this,decodeObj);
//			}});
//	}
//	public void onException(Exception exe){
//		msgCallBack.onException(exe);
//	}
//	@Override
//	protected void innerClose() {
//		try {
//			socketChannel.close();
//			byteStreamDecoder.cleanup(this);
//			writeBufferQueue.clear();
//		} catch (IOException e) {
//			onException(e);
//		}
//	}
//	
//	private final class WriteTask implements Runnable {
//
//        WriteTask() {
//        }
//
//        public void run() {
//            writeTaskInTaskQueue.set(false);
//            reactor.writeInReactor(SClient.this);
//        }
//    }
// }
