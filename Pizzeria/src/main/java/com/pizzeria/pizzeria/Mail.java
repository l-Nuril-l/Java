package com.pizzeria.pizzeria;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {
    public static void send(String to, String subject, String text)
    {
        String from  = "noreply.miyuli@gmail.com";
        String password = "@Ar8?x_#jPyJYcj";

        String host = "smtp.gmail.com";

        Properties prop = new Properties();

        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.connectiontimeout", "5000");
        prop.put("mail.smtp.timeout", "5000");

        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        prop.put("mail.smtp.port", "587");

        //prop.put("mail.smtp.port", "465"); //SSL
        //prop.put("mail.smtp.socketFactory.port", "465");
        //prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }
    }
}
