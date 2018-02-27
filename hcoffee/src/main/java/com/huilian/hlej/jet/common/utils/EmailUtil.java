package com.huilian.hlej.jet.common.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
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
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;


public class EmailUtil {

    // 设置服务器
    private static String KEY_SMTP   = "mail.smtp.host";
    //private static String VALUE_SMTP = "smtp.exmail.qq.com";
    // 服务器验证
    private static String KEY_PROPS  = "mail.smtp.auth";
    // 发件人用户名、密码  
   /* private String        SEND_USER  = "bugzilla@touch-ez.com.cn";  //flyingfinancial_1@163.com
    private String        SEND_UNAME = "bugzilla@touch-ez.com.cn";  //flyingfinancial_1@163.com
    private String        SEND_PWD   = "bug123";  //bug123
*/    
    private static String VALUE_SMTP = "smtp.163.com";
    
    private String        SEND_USER  = "13923739261@163.com";  //flyingfinancial_1@163.com
    private String        SEND_UNAME = "13923739261@163.com";  //flyingfinancial_1@163.com
    private String        SEND_PWD   = "leekylee118";  //bug123
    
    // 建立会话
    private MimeMessage   message;
    private Session       s;
    
    private static final String CONTENT_TYPE_PLAIN= "PLAIN";

    /*
     * 初始化方法
     */
    public EmailUtil(){
        Properties props = System.getProperties();
        // smtp 设置
        props.setProperty(KEY_SMTP, VALUE_SMTP);
        props.put(KEY_PROPS, "true");
        // props.put("mail.smtp.auth", "true");
        s = Session.getDefaultInstance(props, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户密码 设置
                return new PasswordAuthentication(SEND_USER, SEND_PWD);
            }
        });
        s.setDebug(false);
        message = new MimeMessage(s);
    }

    private static EmailUtil instance = null;

    public static EmailUtil getInstance() {
        return instance == null ? new EmailUtil() : instance;
    }

    /**
     * 发送邮件
     * 
     * @param headName 邮件头文件名
     * @param sendHtml 邮件内容
     * @param receiveUser 收件人地址
     * @throws MessagingException 
     * @throws UnsupportedEncodingException 
     */
    public void doSendEmail(String headName, String mailContent, String receiveUser,
                            String contentType) throws MessagingException, UnsupportedEncodingException{
        
        Transport transport = null;
        try
        {
            // 发件人
        	String nick=javax.mail.internet.MimeUtility.encodeText("系统用户"); 
            InternetAddress from = new InternetAddress(SEND_UNAME,nick);
            message.setFrom(from);
            // 收件人
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);
            // 邮件标题
            message.setSubject(headName);
            String content = mailContent.toString();
            if(StringUtils.isEmpty(contentType)){
                contentType = CONTENT_TYPE_PLAIN;
            }
            // 邮件类型：PLAIN-简单类型邮件；HTML-HTML类型邮件
            if (StringUtils.equalsIgnoreCase(CONTENT_TYPE_PLAIN, contentType)) {
                // 邮件内容,也可以使纯文本"text/plain"
                message.setContent(content, "text/plain;charset=utf-8");
            } else {
                message.setContent(content, "text/html;charset=utf-8");
            }
            message.saveChanges();
            transport = s.getTransport("smtp");
            // smtp验证，就是你用来发邮件的邮箱用户名密码
           //transport.connect("smtp.263.net","chent@flyingfinancial.hk", "hl1234");
            transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
            // 发送
            transport.sendMessage(message, message.getAllRecipients());
        }
        finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void doSendEmail(String headName, String mailContent, String receiveUser) throws MessagingException, UnsupportedEncodingException{
        doSendEmail(headName, mailContent, receiveUser, null);
    }
    /**
    * 发送邮件
    * 
    * @param subject
    *            邮件主题
    * @param sendHtml
    *            邮件内容
    * @param receiveUser
    *            收件人地址
    * @param attachment
    *            附件
     * @throws MessagingException 
     * @throws UnsupportedEncodingException 
    */
   public void doSendHtmlEmail(String subject, String sendHtml, String receiveUser, File attachment,String sender) throws MessagingException, UnsupportedEncodingException {
       Transport transport = null;
       try {
           // 发件人
           if(StringUtils.isEmpty(sender)){
               sender = SEND_UNAME;
           }
           InternetAddress from = new InternetAddress(sender);
           message.setFrom(from);

           // 收件人
           InternetAddress to = new InternetAddress(receiveUser);
           message.setRecipient(Message.RecipientType.TO, to);

           // 邮件主题
           message.setSubject(subject);

           // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
           Multipart multipart = new MimeMultipart();
           
           // 添加邮件正文
           BodyPart contentPart = new MimeBodyPart();
           contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
           multipart.addBodyPart(contentPart);
           
           // 添加附件的内容
           if (attachment != null) {
               BodyPart attachmentBodyPart = new MimeBodyPart();
               DataSource source = new FileDataSource(attachment);
               attachmentBodyPart.setDataHandler(new DataHandler(source));
               
               // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
               // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
               //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
               //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
               
               //MimeUtility.encodeWord可以避免文件名乱码
               attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
               multipart.addBodyPart(attachmentBodyPart);
           }
           
           // 将multipart对象放到message中
           message.setContent(multipart);
           // 保存邮件
           message.saveChanges();
           transport = s.getTransport("smtp");
           // smtp验证，就是你用来发邮件的邮箱用户名密码
           //transport.connect("smtp.263.net","songl@flyingfinancial.hk", "hl1234");
           transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
           // 发送
           transport.sendMessage(message, message.getAllRecipients());
       }finally {
           if (transport != null) {
               try {
                   transport.close();
               } catch (MessagingException e) {
                   e.printStackTrace();
               }
           }
       }
   }
   
   
   
   public void doSendHtmlEmail(String subject, String sendHtml, String receiveUser, File attachment) throws MessagingException, UnsupportedEncodingException {
       doSendHtmlEmail(subject, sendHtml, receiveUser, attachment, null);
   }
   
   
   public static void main(String[] args) throws UnsupportedEncodingException{
	   EmailUtil  eu =  new EmailUtil();
	   try {
		eu.doSendEmail("你好啊", "en", "chent@flyingfinancial.hk");
	} catch (MessagingException e) {
		e.printStackTrace();
	}
   }
}
