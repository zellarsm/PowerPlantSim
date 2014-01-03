package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import my.simulator.Simulator;


public class UDPServer implements Runnable {
	Simulator simulator;
	public UDPServer(Simulator simulator) {
		this.simulator = simulator;
	}
	@SuppressWarnings("resource")
	public void run() {
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(9876);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		
		while(true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Retrieve message from packet.
			String clientMsg = new String(receivePacket.getData()).replaceAll("[^a-zA-Z0-9]", "");
			
			// Have the simulator handle the message.
			String responseMsg = simulator.handleMessage(clientMsg);
			
			// Send the simulator's response to client.
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			sendData = responseMsg.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
