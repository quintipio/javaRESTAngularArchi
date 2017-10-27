package fr.quintipio.javarestangulararchi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Surchage de message Source
 */
@Component
public class MessageByLocaleServiceImpl implements MessageByLocaleService {

    @Autowired
    private MessageSource messageSource;

    /**
     * Récupère le texte dans les fichiers properties en fonction de la langue
     * @param id l'id du texte à récupérer
     * @return le texte dans la langue voulue
     */
    @Override
    public String getMessage(String id) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id,null,locale);
    }
}
