import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleHttpServer {

    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("HTTP Server running on port " + port);

            while (true) {
                Socket client = serverSocket.accept(); //this line wait until the browser connect, curl connect or burp connect. and when connection happens socket is created.
                handleClient(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket client) {
        try (
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream())); //This one reads what browser sends
            OutputStream out = client.getOutputStream()
        ) {

            // Read request line
            String requestLine = in.readLine();
            if (requestLine == null) return;

            System.out.println("Request: " + requestLine);
            //split request
            StringTokenizer tokenizer = new StringTokenizer(requestLine);
            String method = tokenizer.nextToken();
            String path = tokenizer.nextToken();

            // Read remaining headers (ignore for now)
            String line;
            while (!(line = in.readLine()).isEmpty()) {}

            //create HTML page
            String body = "<html><body><h1>Java HTTP Server</h1></body></html>";

            if (method.equals("GET")) {
                sendResponse(out, "200 OK", body, true);
            } 
            else if (method.equals("HEAD")) {
                sendResponse(out, "200 OK", body, false);
            } 
            else {
                sendResponse(out, "405 Method Not Allowed",
                        "<h1>405 Method Not Allowed</h1>", true);
            }

            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(OutputStream out,
                                     String status,
                                     String body,
                                     boolean sendBody) throws IOException {

        PrintWriter writer = new PrintWriter(out);

        writer.print("HTTP/1.1 " + status + "\r\n");
        writer.print("Content-Type: text/html\r\n");
        writer.print("Content-Length: " + body.length() + "\r\n");
        writer.print("Connection: close\r\n");
        writer.print("\r\n");

        if (sendBody) {
            writer.print(body);
        }

        writer.flush();
    }
}
