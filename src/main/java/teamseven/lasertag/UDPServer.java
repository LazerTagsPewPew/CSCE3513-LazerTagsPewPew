package teamseven.lasertag;
// Java program to illustrate Server side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class UDPServer implements Runnable
{
	public ArrayList<Integer> playerShooter = new ArrayList<>();
	public ArrayList<Integer> playerHit = new ArrayList<>();

	DatagramSocket ds;

	public void run()
	{
		// Step 1 : Create a socket to listen at port 1234
		try
		{
			ds = new DatagramSocket(7501);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		byte[] receive = new byte[65535];

		DatagramPacket DpReceive = null;
		while (true)
		{

			// Step 2 : create a DatgramPacket to receive the data.
			DpReceive = new DatagramPacket(receive, receive.length);

			// Step 3 : revieve the data in byte buffer.
			try
			{
				ds.receive(DpReceive);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}

			System.out.println("Client:-" + data(receive));
			String message = data(receive).toString();
			String[] idsOfPlayers = message.split("\\:");
			int idOfPlayerTransmitting = Integer.parseInt(idsOfPlayers[0]);
			int idOfPlayerHit = Integer.parseInt(idsOfPlayers[1]);
			playerShooter.add(idOfPlayerTransmitting);
			playerHit.add(idOfPlayerHit);
			// Exit the server if the client sends "bye"
			if (data(receive).toString().equals("bye"))
			{
				System.out.println("Client sent bye.....EXITING");
				break;
			}

			// Clear the buffer after every message.
			receive = new byte[65535];
		}
	}

	// A utility method to convert the byte array
	// data into a string representation.
	public static StringBuilder data(byte[] a)
	{
		if (a == null)
			return null;
		StringBuilder ret = new StringBuilder();
		int i = 0;
		while (a[i] != 0)
		{
			ret.append((char) a[i]);
			i++;
		}
		return ret;
	}
	
}