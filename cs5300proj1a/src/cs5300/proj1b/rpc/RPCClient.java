package cs5300.proj1b.rpc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * @author adityagaitonde
 *
 */
public class RPCClient {

	private static final int MAX_PACKET_SIZE = 512;
	
	private static final int SOCKET_TIME_OUT_MS = 5 * 1000;

	//TODO - Add retry in case of IO Exception
	public String callServer(
			String hostname, 
			int port, 
			String data, 
			String callId) throws IOException{
		
		DatagramSocket rpcSocket = new DatagramSocket(); 
		rpcSocket.setSoTimeout(SOCKET_TIME_OUT_MS);
		try {
		
			InetAddress IP = InetAddress.getByName(hostname);
			byte[] outBuf = new byte[MAX_PACKET_SIZE];
			outBuf = data.getBytes();

			DatagramPacket sendPkt = new DatagramPacket(outBuf, outBuf.length, IP, 5300);
			rpcSocket.send(sendPkt);

			byte [] inBuf = new byte[MAX_PACKET_SIZE];
			DatagramPacket recvPkt = new DatagramPacket(inBuf, inBuf.length);
			
			do{	
				recvPkt.setLength(inBuf.length);
				rpcSocket.receive(recvPkt);
			}while(verifyResponse(new String(inBuf),  callId));
			
			return new String(inBuf);
			
		} catch (IOException e) {
			
			return null;
			
		}finally{
			rpcSocket.close();
		}

	}

	boolean verifyResponse(String response, String callId){
		return false;
	}

}