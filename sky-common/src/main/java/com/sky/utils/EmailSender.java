import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    // 注入 JavaMailSender 对象
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String message) throws MessagingException {
        // 创建 MimeMessage 对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // 设置收件人、主题和内容
        mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(message);

        // 发送邮件
        javaMailSender.send(mimeMessage);
    }

    // 将 JavaMailSender 对象注入到 EmailSender 类中
    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
}