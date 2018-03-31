package net.relay2.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileIO {
	 private static final int ZERO_COPY_CHUNK_SIZE = 512 * 1024;
	
	public static void main(String[] args) throws IOException {
		String source = "F:\\os\\ubuntu-12.04.5-server-i386.iso";
		String dest = "F:\\os\\copy.iso";
		long start = System.currentTimeMillis();
		System.out.println("start process at "+ start);
		//long btCount = synchCopyFile( source, dest);
		//long btCount=  asynchrCopyFile(source,dest);
		long btCount  = sendFileCopyFile(source, dest);
		System.out.println("total bytes transferred--"+btCount+" and time taken in MS--"+(System.currentTimeMillis() - start));
//      async need to add following code
//		System.out.println("start to do other things");
//		try {
//			Thread.sleep(40000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	//send file  execute 31111 seconds
	private static long sendFileCopyFile(String source, String dest) throws IOException {
		FileInputStream fio = new FileInputStream(source);
		FileOutputStream fout = new FileOutputStream(dest);
		FileChannel sChanel = fio.getChannel();
		FileChannel dChanel = fout.getChannel();
		long total = 0 ;
		long pos = 0 ;
		long copied = 0;
		do {
			copied = sChanel.transferTo(pos, ZERO_COPY_CHUNK_SIZE, dChanel);
			pos = pos + copied;
			sChanel.position(pos);
			total = total+ copied; 
		}while(copied>0 || pos < sChanel.size() );
		sChanel.close();
		dChanel.close();
		fio.close(); 
		fout.close();
		return total;
	}

	//execute  1517071780386- 1517071757095 =23291 ms 
	private static long asynchrCopyFile(String source, String dest) throws IOException {
		Path path = Paths.get(source);
	    final AsynchronousFileChannel fileChannel =
		    AsynchronousFileChannel.open(path, StandardOpenOption.READ);
	    FileOutputStream fout = new FileOutputStream(dest);
		final FileChannel dChanel = fout.getChannel();
	    byte [] btarray = new byte[8192];
	    ByteBuffer buf = ByteBuffer.wrap(btarray);
	    final long position = 0;
	    final long start = System.currentTimeMillis();
	    FileCompletionHandler hander = new FileCompletionHandler(fileChannel, dChanel);
	    fileChannel.read(buf, position, buf,hander);
//		fileChannel.read(buf, position, buf, new CompletionHandler<Integer, ByteBuffer>() {
//		    @Override
//		    public void completed(Integer result, ByteBuffer attachment){
//		    	System.out.println("the result is "+result);
//		        if(result!=-1){
//		        	attachment.flip();
//			        while(attachment.hasRemaining()){
//						try {
//							dChanel.write(attachment);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//			        attachment.clear();
//			        
//			        long pos = fileChannel.size()+ result;
//					fileChannel.read(attachment, pos, attachment, this);
//					
//		        }else{
//		        	System.out.println("and time taken in MS--"+(System.currentTimeMillis() - start));
//		        }
//		        
//		    }
//
//		    @Override
//		    public void failed(Throwable exc, ByteBuffer attachment) {
//		    	exc.printStackTrace();
//		    }
////		});
		//fout.close();
		return 0;
	}

	//execute  55553 ms
	private static long synchCopyFile(String source, String dest) throws IOException {
		FileInputStream fio = new FileInputStream(source);
		FileOutputStream fout = new FileOutputStream(dest);
		FileChannel sChanel = fio.getChannel();
		FileChannel dChanel = fout.getChannel();
		byte [] btarray = new byte[8192];
		ByteBuffer buf = ByteBuffer.wrap(btarray);
		long total = 0;
		while(sChanel.read(buf)!=-1){
			buf.flip();
			while(buf.hasRemaining()){
				total+=dChanel.write(buf);
			}
			buf.clear();
		}
		sChanel.close();
		dChanel.close();
		fio.close(); 
		fout.close();
		return total;
		
	}
}
