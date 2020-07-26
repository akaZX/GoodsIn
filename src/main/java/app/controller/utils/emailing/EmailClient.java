package app.controller.utils.emailing;

import app.controller.sql.dao.SuppEmailsDao;
import app.pojos.SuppEmails;
import app.pojos.SupplierOrders;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

public class EmailClient {


    public static boolean sendEmailWithAttachement(String email, String psw, String filePath, String fileName, SupplierOrders supplierOrders) {

        //creates session object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication(email.trim(), psw.trim());
                    }
                });

        //Composing the message
        try {

            MimeMessage message = new MimeMessage(session);


            message.setFrom(new InternetAddress(email));


            List<SuppEmails> emails = new SuppEmailsDao().getAll(supplierOrders.getSuppCode());

            if (emails.size() > 0) {
                emails.forEach(suppEmails -> {
                    try {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(suppEmails.getEmail()));
                    }
                    catch (MessagingException e) {
                        e.printStackTrace();
                    }
                });

                message.setSubject("QA report for: " + supplierOrders.getPoNumber());

                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setText("");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(bodyPart);
                bodyPart = new MimeBodyPart();

                DataSource dataSource = new FileDataSource(filePath);
                bodyPart.setDataHandler(new DataHandler(dataSource));
                bodyPart.setFileName(fileName);
                multipart.addBodyPart(bodyPart);
                message.setContent(multipart);

                Transport.send(message);
//                System.out.println("EMAIL SENT TO: " + supplierOrders.getPoNumber());
                return true;
            }

            return false;
        }
        catch (MessagingException e) {
//            e.printStackTrace();
//            System.out.println("Failed to send email");
            return false;
        }
    }


}
