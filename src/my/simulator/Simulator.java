package my.simulator;

import networking.UDPServer;

public class Simulator {

	public Simulator() {
		
	}
	public static void main(String[] args) {
		Simulator simulator = new Simulator();
		
		// Start a new thread to receive data from operators.
		UDPServer udpServer = new UDPServer(simulator);
		new Thread(udpServer).start();
	}
	
	public String handleMessage(String clientMsg) {
		System.out.println("FROM CLIENT: " + clientMsg);
		
		return clientMsg.toUpperCase();
	}
	
}
