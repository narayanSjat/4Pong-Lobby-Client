/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jason Xu
 */
import java.net.*;
import java.io.*;

public class ChatClient
{  private Socket socket              = null;
   private DataInputStream  console   = null;
   private DataOutputStream streamOut = null;
   //private DataInputStream streamIn = null;
   public ChatClient(String serverName, int serverPort)
   {  System.out.println("Establishing connection. Please wait ...");
      try
      {  socket = new Socket(serverName, serverPort);
         System.out.println("Connected: " + socket);
         console   = new DataInputStream(System.in);
         streamOut = new DataOutputStream(socket.getOutputStream());
         //streamIn = new DataInputStream(socket.getInputStream());
         //start();
      }
      catch(UnknownHostException uhe)
      {  System.out.println("Host unknown: " + uhe.getMessage());
      }
      catch(IOException ioe)
      {  System.out.println("Unexpected exception: " + ioe.getMessage());
      }
      String line = "";
      String in = "";
      while (!line.equals(".bye"))
      {  try
         {  line = console.readLine();
            streamOut.writeUTF(line);
            streamOut.flush();
            
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println("Message received from the server : " +message);
            
            
         }
         catch(IOException ioe)
         {  System.out.println("Sending error: " + ioe.getMessage());
         }
      }
   }
   public void start() throws IOException
   {  console   = new DataInputStream(System.in);
      streamOut = new DataOutputStream(socket.getOutputStream());
   }
   public void stop()
   {  try
      {  if (console   != null)  console.close();
         if (streamOut != null)  streamOut.close();
         if (socket    != null)  socket.close();
      }
      catch(IOException ioe)
      {  System.out.println("Error closing ...");
      }
   }
   public static void main(String args[])
   {  
        ChatClient client = null;
        String serverName = "";
        int port = 5000;
        client = new ChatClient(serverName, port);
   }
}