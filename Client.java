import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	private Socket socket = null;
	private BufferedReader userInput = null;
	private DataOutputStream sendServer = null;

	private DataInputStream serverInput = null;

	private static String addr = "10.173.204.158";
	private static int port = 5894;


	public Client(String addrress, int port) {
		// establish a connection
		try {
			socket = new Socket(addrress, port);
			System.out.println("Connected with server on: " + addr);

			userInput = new BufferedReader(new InputStreamReader(System.in));


			sendServer = new DataOutputStream(socket.getOutputStream());

			serverInput = new DataInputStream(socket.getInputStream());

			String line = "";

			while (!line.equals("0/0=")) {

				line = userInput.readLine();
				sendServer.writeUTF(line);

				if (line.equals("0/0=")) {
					System.out.println("User input ends; end the client program\n");
				} else {

					System.out.println("Answer from server " + serverInput.readUTF());
				}

			}

			// close the connection
			userInput.close();
			sendServer.close();

			serverInput.close();

			socket.close();

		} catch (IOException i) {
			System.out.println(i);
		}
	}

	@SuppressWarnings("unused")
	public static void main(String args[]) {

		Client client = new Client(addr, port);
	}
}