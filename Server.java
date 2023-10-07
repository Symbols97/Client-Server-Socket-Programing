import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream clientInput = null;
	private DataOutputStream sendClient = null;
	
	private static int port = 5894;
	
	
	public Server(int port) {

		try {
			server = new ServerSocket(port);

			socket = server.accept();
			String clientAddr = socket.getInetAddress().toString();
			System.out.println("Connected by client on: " + clientAddr);


			clientInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			sendClient = new DataOutputStream(socket.getOutputStream());
			
			String line = "";


			while (!line.equals("0/0=")) {
      
				line = clientInput.readUTF();
        
				if (line.equals("0/0=")) {
					System.out.println("Recieved question \"" + line + "\"; end the server program\n");
					
				} else {
					
					
					String answer = calculateExpression(line);
					
					sendClient.writeUTF(answer);
					
					System.out.println("Recieved question \"" + line + "\"; send back answer " + answer + "\n");
				}

			}
			

			// close connection
			socket.close();
			clientInput.close();
      server.close();
      sendClient.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static String calculateExpression(String input) {

		String regex = "([0-9]+\\.?[0-9]*)([+\\-*/])([0-9]+\\.?[0-9]*)=";

		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(input);

		if (matcher.matches()) {

			String firstNumberStr = matcher.group(1);
			String operator = matcher.group(2);
			String secondNumberStr = matcher.group(3);


			double firstNumber = Double.parseDouble(firstNumberStr);
			double secondNumber = Double.parseDouble(secondNumberStr);


			double result = 0.0;
			switch (operator) {
			case "+":
				result = firstNumber + secondNumber;
				break;
			case "-":
				result = firstNumber - secondNumber;
				break;
			case "*":
				result = firstNumber * secondNumber;
				break;
			case "/":
				result = firstNumber / secondNumber;

			}

			return String.valueOf(result);
		} else {
			return "Input error. Re-type the math question again.";
		}
	}
 
 	@SuppressWarnings("unused")
	public static void main(String args[]) {

		Server server = new Server(port);

	}

}