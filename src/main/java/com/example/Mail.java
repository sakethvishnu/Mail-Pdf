package com.example;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class Mail {

    public void sendEmail(String recipient) {

        Properties pro = new Properties();
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.starttls.enable","true");
        pro.put("mail.smtp.host","smtp.gmail.com");
        pro.put("mail.smtp.port","587");
        
        final String mail = "saketh4532@gmail.com";
        final String pass = "zwqulwtcgbzmvcie";
        Session session = Session.getInstance(pro , new Authenticator() {
            
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(mail, pass);
            }
        });

        Message message = prepareMessage(session,mail,recipient);

        try{
            Transport.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    Message prepareMessage(Session session, String mail ,String recipient){
        Message message = new MimeMessage(session);
        try{
            message.setFrom(new InternetAddress(mail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("PDF Test Mail");



            String attachFile = "D:/pdf_mail/src/Major project.pdf";
            BodyPart bodyPart = new MimeBodyPart();
            try {

                bodyPart.setContent("<h1>Test Email</h1>", "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(bodyPart);
                
                ByteArrayDataSource source = new ByteArrayDataSource(createPdf(), "application/pdf");
                DataSource dataSource = new FileDataSource(attachFile);
                DataHandler dataHandler = new DataHandler(source);

                bodyPart = new MimeBodyPart();
                bodyPart.setDataHandler(dataHandler);

                bodyPart.setFileName("Major Project.pdf");
                multipart.addBodyPart(bodyPart);

                message.setContent(multipart);

            } catch (MessagingException e) {
                e.printStackTrace();
            }

            return message;
        }catch(Exception e){System.out.println(e.getMessage());}
        return null;
    }

    public byte[] createPdf(){
        PdfWriter pdfWriter = null;
        PdfDocument pdfDocument = null;
        Document document = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            
            pdfWriter = new PdfWriter(out);
            pdfDocument = new PdfDocument(pdfWriter);

            // Adding page to the document.
            pdfDocument.addNewPage();
            pdfDocument.setDefaultPageSize(PageSize.A4);

            document = new Document(pdfDocument);

            document.add(new Paragraph("This is the test pdf file."));

            document.close();
            pdfDocument.close();
            pdfWriter.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}