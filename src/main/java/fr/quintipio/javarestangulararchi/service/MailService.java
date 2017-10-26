package fr.quintipio.javarestangulararchi.service;

import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.configuration.Constantes;
import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Outil pour envoyer les mails
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private static final String USER = "user";

    public static final String BASE_URL = "baseurl";

    @Value("${quintipio.baseurl}")
    private String propertyUrlApp;

    /**
     * Envoi un mail avec les informations en paramètre
     * @param from l'émetteur
     * @param to le destinataire
     * @param subject le sujet
     * @param content le contenu
     * @param isMultipart
     * @param isHtml
     * @return true si envoyé sinon false
     */
    @Async
    public boolean sendEmail(String from, String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Envoi email [multipart '{}' and html '{}'] to '{}' subject '{}' content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Email envoyé à ", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Erreur lors de l'enoi du mail à ", to, e);
            } else {
                log.warn("L'email n'a pu être envoyé à l'utilisateur  ", to, e.getMessage());
            }
            return false;
        }
        return true;
    }

    /**
     * Envoi un mail à partir d'un template
     * @param user l'utilisateur concerné par le mail
     * @param templateName me nom du template à prendre
     * @param sujet le sujet du mail
     * @return true si ok
     */
    @Async
    public boolean sendEmailFromTemplate(Utilisateur user, String templateName, String sujet) {
        Context context;
        if(user.getLangue() != null) {
            Locale locale = Locale.forLanguageTag(user.getLangue());
            context = new Context(locale);
        } else {
            context = new Context();
        }
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, propertyUrlApp);
        String content = templateEngine.process(templateName, context);
        return sendEmail(Constantes.MAIL_ENVOI,user.getEmail(), sujet, content, false, true);
    }
}
