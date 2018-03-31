package net.relay2.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;

public class FileCompletionHandler implements CompletionHandler<Integer, ByteBuffer>{
	
	private long pos = 0;
	private FileChannel outFileChannel;
	private AsynchronousFileChannel fileChannel;
	public FileCompletionHandler(AsynchronousFileChannel fromChannel, FileChannel chanel){
		fileChannel = fromChannel;
		outFileChannel = chanel;
	}
	
	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		//System.out.println("the result is "+result);
        if(result!=-1){
        	attachment.flip();
	        while(attachment.hasRemaining()){
				try {
					outFileChannel.write(attachment);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	        attachment.clear();
	        
	        pos = pos + result;
			fileChannel.read(attachment, pos, attachment, this);
        }else{
        	long end  = System.currentTimeMillis();
        	System.out.println("process end  at "+ end);
        }
		
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		
	}

}
