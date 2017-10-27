package fr.quintipio.javarestangulararchi.configuration;

import fr.quintipio.javarestangulararchi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Composant contenant les timer à éxécuter régulièrement
 */
@Component
public class ScheduledTask {


    private final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private UserService userService;


    /**
     * Efface tout les comptes non activé depuis trois jour, tout les jours à 00h01
     */
    @Scheduled(cron = "0 1 0 * * *")
    public void ScheduledEffacerCompteNonActive() {
        userService.effacerUtilisateurNonActive();
    }
}
