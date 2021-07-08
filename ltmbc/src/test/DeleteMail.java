/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import com.sun.mail.imap.protocol.FLAGS;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
/**
 *
 * @author admin
 */
public class DeleteMail {
    public static void main(String args[]) throws Exception {

 String user= "laptrinhmangbc@gmail.com";//change accordingly
 String password="A1122334455";//change accordingly

 //1) get the session object
 Properties properties = System.getProperties();
 Session session = Session.getDefaultInstance(properties);

 //2) create the store object and connect to the current host 
 Store store = session.getStore("pop3");
 store.connect(user,password);

 //3) create the folder object and open it
 Folder folder = store.getFolder("inbox");

 if (!folder.exists()) {
 System.out.println("inbox not found");
 System.exit(0);
 }

 folder.open(Folder.READ_WRITE);

 //4) Get all the messages and print it (optional)
 Message[] msg = folder.getMessages();

 //System.out.println((messages.length+1)+" message found");
 for (int i = 0; i < msg.length; i++) {
   System.out.println("--------- " + (i + 1) + "------------");
   String from = InternetAddress.toString(msg[i].getFrom());
 
   if (from != null) {
     System.out.println("From: " + from);
   }

   String replyTo = InternetAddress.toString(
   msg[i].getReplyTo());
   if (replyTo != null) {
    System.out.println("Reply-to: " + replyTo);
   }

   String to = InternetAddress.toString(
   msg[i].getRecipients(Message.RecipientType.TO));
  
   if (to != null) {
    System.out.println("To: " + to);
   }

   String subject = msg[i].getSubject();
   if (subject != null) {
    System.out.println("Subject: " + subject);
   }
   Date sent = msg[i].getSentDate();
   if (sent != null) {
    System.out.println("Sent: " + sent);
   }
   System.out.println("Message : ");
   System.out.println(msg[i].getContent());

 }//end of for loop

  //5) get the message number to delete (optional)
  System.out.println("Enter message number to delete :");
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  String no = br.readLine();
  //6) delete the message using setFlag method
  msg[Integer.parseInt(no) - 1].setFlag(FLAGS.Flag.DELETED, true);
  
  System.out.println("Msg Delete .....");

  folder.close(true);
  store.close();
  }
} 