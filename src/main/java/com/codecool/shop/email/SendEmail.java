package com.codecool.shop.email;

import com.codecool.shop.model.customer.Customer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by prezi on 2016. 12. 08..
 */
public class SendEmail {
    private static SMTPConnection smtp;
    private static SendEmail INSTANCE;

    private SendEmail(){
        smtp = SMTPConnection.getInstance();
    }

    public static SendEmail getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new SendEmail();
        }
        return INSTANCE;
    }

    private Session makeSession(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtp.getHost());
        props.put("mail.smtp.port", smtp.getPort());

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(smtp.getUser(), smtp.getPassword());
                    }
                });
        return session;
    }

    public void sendRegistrationEmail(Customer customer){
        Session session = makeSession();

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(smtp.getUser()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(customer.getEmail()));
            message.setSubject("Registration");
            message.setText("Dear " + customer.getName() + ","
                    + "\n\n Welcome to our shop."
                    + "\n Your Unique Identification number is " + customer.getCustomerUUID()
                    + "\n Your billing address is " + customer.getBillingAddress()
                    + "\n Your phone number is " + customer.getPhoneNumber());

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
