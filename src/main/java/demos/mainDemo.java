package demos;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.*;
import java.util.Properties;


public class mainDemo{


    public static void main(String[] args){


//
//        String host="outlook.office365.com";
//
//
//        final String user="kestutis.zaltauskas@Bakkavor.com";//change accordingly
//        final String password="Bendinskas21";//change accordingly
//
//        String to="akazxx@gmail.com";//change accordingly
//        String second="akazxx@live.com";//change accordingly
//
//        //Get the session object
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "outlook.office365.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getDefaultInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(user,password);
//                    }
//                });
//
//        //Compose the message
//        try {
//            MimeMessage message = new MimeMessage(session);
//
//
//
//
//            message.setFrom(new InternetAddress(user));
//            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
//            message.addRecipient(Message.RecipientType.TO,new InternetAddress(second));
//            message.setSubject("asdasd");
//
//            BodyPart messageBody = new MimeBodyPart();
//            messageBody.setText("This is simple program of sending email using JavaMail API");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(messageBody);
//            messageBody = new MimeBodyPart();
//            String file = "C:\\Users\\akazx\\OneDrive\\Desktop\\GoodsIn\\output.pdf";
//            DataSource dataSource = new FileDataSource(file);
//            messageBody.setDataHandler(new DataHandler(dataSource));
//            messageBody.setFileName("somefile.pdf");
//            multipart.addBodyPart(messageBody);
//            message.setContent(multipart);
//
//            Transport.send(message);
//
//            System.out.println("message sent successfully...");
//
//        } catch (MessagingException e) {e.printStackTrace();}
//


    }

}
