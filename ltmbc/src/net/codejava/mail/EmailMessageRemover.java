/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.codejava.mail;
import java.util.Properties;
 
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
 
/**
 *
 * @author admin
 */
public class EmailMessageRemover {
     public void deleteMessages(String Host, String Port,
            String username, String password) {
        Properties prpt = new Properties();
 
        // server setting
        prpt.put("mail.imap.host", Host);
        prpt.put("mail.imap.port", Port); 
        // SSL setting
        prpt.setProperty("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        prpt.setProperty("mail.imap.socketFactory.fallback", "false");
        prpt.setProperty("mail.imap.socketFactory.port",String.valueOf(Port)); 
        Session ss = Session.getDefaultInstance(prpt);
        System.out.println(username);
        System.out.println(password);
        try {
            // connects to the message store
            Store str = ss.getStore("imap");
            System.out.println(username);
            System.out.println(password);
            str.connect(username, password);
            // opens the inbox folder
            Folder folderInbox = str.getFolder("INBOX");
            folderInbox.open(Folder.READ_WRITE);
            // fetches new messages from server
            Message[] arrayMessages = folderInbox.getMessages();
            for (int i = 0; i < arrayMessages.length; i++) {
                Message message = arrayMessages[i];
                String subject = message.getSubject();               
                    message.setFlag(Flags.Flag.DELETED, true);
                    System.out.println("Marked DELETE for message: " + (i+1));               
            }
           // expunges the folder to remove messages which are marked deleted
            boolean expunge = true;
            folderInbox.close(expunge);
            // another way:
            //folderInbox.expunge();
            //folderInbox.close(false);
            // disconnect
            str.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
    }    
    /**
     * Runs this program to delete e-mail messages on a Gmail account
     * via IMAP protocol.
     */
    public static void main(String[] args) {
		String host = "imap.gmail.com";
		String port = "993";
		String userName = "laptrinhmangbc@gmail.com";
		String password = "A1122334455";
		EmailMessageRemover remover = new EmailMessageRemover();

		// try to delete all messages contain this string its Subject field
		String subjectToDelete = "Newsletter";
		remover.deleteMessages(host, port, userName, password);

	}
}
