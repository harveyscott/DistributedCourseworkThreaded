import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class MultiThreadedServer extends Thread {
    protected Socket s;
    static int port = 4444;

    // constructor
    MultiThreadedServer(Socket s) {
        System.out.println("New client.  ");
        this.s = s;
    } // end constructor

    private static String sortNumbers(int[] numbers){
        if (numbers == null) {
            return "";
        }
        ArrayList<Integer> numbersList = new ArrayList<Integer>();

        // Convert into arraylist
        for (int i = 0; i < numbers.length; i++) {
            numbersList.add(numbers[i]);
        }

        // Sort Numbers using the collection methods
        Collections.sort(numbersList);

        // Build String to return to users
        StringBuilder stringToRun = new StringBuilder("Here are the numbers in ascending order: ");
        for (int i = 0; i < numbersList.size(); i++) {
            stringToRun.append(" ");
            stringToRun.append(numbersList.get(i));
        }
        return stringToRun.toString();
    }


    @Override
    public void run() {
        int runCount = 0;
        try {
            // Create Input and Output streams to communicate with the client
            try (BufferedReader is = new BufferedReader(new InputStreamReader(s.getInputStream()));
                 PrintWriter os = new PrintWriter(new OutputStreamWriter(s.getOutputStream()))) {
                // Set up the server state
                System.out.println("Server Waiting");
                String inputLine, outputLine;
                os.println("Server Connected");
                os.flush();
                while ((inputLine = is.readLine()) != null) {
                    String[] integersInString = inputLine.split(" ");
                    int integers[] = new int[integersInString.length];
                    for (int i = 0; i < integersInString.length; i++) {
                        try {
                            integers[i] = Integer.parseInt(integersInString[i]);
                        } catch (NumberFormatException ne) {
                            os.println("Only enter valid numbers");
                        }
                    }
                    outputLine = sortNumbers(integers);
                    runCount++;
                    os.println(outputLine);
//                    os.println(outputLine + " Method has been run " + runCount + " times");
                    os.flush();
                } // end while
            } catch (IOException e) {
                e.printStackTrace();
            }}catch (Exception ex) {
            ex.printStackTrace();
        }}

        public static void main(String args[]) throws IOException {
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                System.out.println("Waiting");
                Socket client = server.accept();
                System.out.println("Accepted from " + client.getInetAddress());
                MultiThreadedServer clientHandler = new
                        MultiThreadedServer(client);
                clientHandler.start();
            }
        } catch (IOException ex) {
            System.err.println("Unable to connect on specified port");
        }
    }

}
