package code.github.snet.core.msg;

import code.github.snet.core.common.BsFutureDone;

public class WriteObj {

	private int writeOutLength;
	private byte[] objBytes;
	private BsFutureDone<Boolean> writeFuture;

	public BsFutureDone<Boolean> getWriteFuture() {
		return writeFuture;
	}

	public void setWriteFuture(BsFutureDone<Boolean> writeFuture) {
		this.writeFuture = writeFuture;
	}

	public void getMsgBuffer() {

	}

	public byte[] getObjBytes() {
		return objBytes;
	}

	public void setObjBytes(byte[] objBytes) {
		this.objBytes = objBytes;
	}

	public int getWritedOutLength() {
		return writeOutLength;
	}

	public void setWriteOutLength(int writeOutLength) {
		this.writeOutLength = writeOutLength;
	}

}
