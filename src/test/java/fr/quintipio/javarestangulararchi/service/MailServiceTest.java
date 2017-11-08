package fr.quintipio.javarestangulararchi.service;

import fr.quintipio.javarestangulararchi.JavarestangulararchiApplication;
import fr.quintipio.javarestangulararchi.model.Utilisateur;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.thymeleaf.spring4.SpringTemplateEngine;


import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavarestangulararchiApplication.class)
@WebAppConfiguration
public class MailServiceTest {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Spy
    private JavaMailSenderImpl javaMailSender;

    private MailService mailService;

    @Captor
    private ArgumentCaptor messageCaptor;

    private String mailFrom = "test@localhost";

    private String mailTo = "john.doe@example.com";

    @Before
    public void setup() {
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        //mailService = new MailService(javaMailSender, templateEngine);
    }

    @Test
    public void testEnvoiEmail() throws Exception {
        mailService.sendEmail(mailFrom,mailTo, "testSubject","testContent", false, false);
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("testSubject");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(mailTo);
        assertThat(message.getFrom()[0].toString()).isEqualTo(mailFrom);
        assertThat(message.getContent()).isInstanceOf(String.class);
        assertThat(message.getContent().toString()).isEqualTo("testContent");
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/plain; charset=UTF-8");
    }

    @Test
    public void testEnvoiEmailTemplate() throws Exception {
        Utilisateur user = new Utilisateur();
        user.setNom("TEST");
        user.setPrenom("Test");
        user.setLangue("fr");
        user.setSso("test");
        user.setEmail(mailTo);

        mailService.sendEmailFromTemplate(user,"testEmail","test");
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(user.getEmail());
        assertThat(message.getContent().toString()).isNotEmpty();
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }
}
