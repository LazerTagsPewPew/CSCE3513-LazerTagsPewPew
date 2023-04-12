package teamseven.lasertag;
// Java program to illustrate Client Side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UDPClient
{
	public void intialize() throws IOException
	{
		Scanner sc = new Scanner(System.in);

		// Step 1:Create the socket object for
		// carrying the data.
		DatagramSocket ds = new DatagramSocket();

		InetAddress ip = InetAddress.getLocalHost();
		byte buf[] = null;

		System.out.println("this program will generate some test traffic for 2 players on the red");
		System.out.println("team as well as 2 players on the green team");
		System.out.println("");

		System.out.println("Enter id of red player 1 ==> ");
		int red1 = Integer.parseInt(sc.nextLine());
		System.out.println("Enter id of red player 2 ==> ");
		int red2 = Integer.parseInt(sc.nextLine());
		System.out.println("Enter id of green player 1 ==> ");
		int green1 = Integer.parseInt(sc.nextLine());
		System.out.println("Enter id of green player 2 ==> ");
		int green2 = Integer.parseInt(sc.nextLine());

		System.out.println("");
		System.out.println("How many events do you want ==> ");
		int counter = Integer.parseInt(sc.nextLine());
		int redplayer = 0;
		int greenplayer = 0;
		String message = "";
		// loop while user not enters "bye"
		int i = 1;
		Random random = new Random();
		while (i <= counter)
		{
			if (random.nextInt(1,3) == 1)
			{
				redplayer = red1;
			}
			else
			{
				redplayer = red2;
			}

			if (random.nextInt(1,3) == 1)
			{
				greenplayer = green1;
			}
			else
			{
				greenplayer = green2;
			}

			if(random.nextInt(1,3) == 1)
			{
				message = String.valueOf(redplayer) + ":" + String.valueOf(greenplayer);
			}
			else
			{
				message = String.valueOf(greenplayer) + ":" + String.valueOf(redplayer);
			}

			// convert the String input into the byte array.
			buf = message.getBytes();

			// Step 2 : Create the datagramPacket for sending
			// the data.
			DatagramPacket DpSend =
				new DatagramPacket(buf, buf.length, ip, 7501);

			// Step 3 : invoke the send call to actually send
			// the data.
			ds.send(DpSend);
			i = i + 1;
		}
	}
}
