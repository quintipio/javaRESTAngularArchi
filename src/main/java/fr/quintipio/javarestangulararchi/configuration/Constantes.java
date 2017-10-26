package fr.quintipio.javarestangulararchi.configuration;

public class Constantes {

    /**
     * Adresse mail d'envoi de l'appli
     */
    public static final String MAIL_ENVOI = "ne-pas-repondre@modeleArchi.fr";

    /*******************
     * REGEX DE CONTROLE
     *******************/
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    /*******************
     * SECURITE
     *******************/
    public static final String CLIEN_ID = "archiAngularRest";
    public static final String GRANT_TYPE = "password";
    public static final String SCOPE_READ = "read";
    public static final String SCOPE_WRITE = "write";
    public static final String RESOURCES_IDS = "archiAngularRest";
    static final String SIGNING_KEY = "MaYzkSjmkzPC57L";
    static final String SECURITY_REALM = "Spring Boot Angular Archi example";

    /*****************
     * LANGUES
     *****************/
    public static final String PARAM_URL_LANGUE = "lang";
    public static final String PARAM_LOC_TRAD = "classpath:messages";

    /*****************
     * MAILS
     *****************/
    public static final String PARAM_LOC_MAIL = "mails/";
    public static final String TEMPLATE_ACTIVATION = "activerCompteMail";
    public static final String TEMPLATE_RESET_PASSWORD = "passwordResetEmail";

    /*****************
     * DROITS
     *****************/
    public static final String DROIT_USER = "STANDARD_USER";
    public static final String DROIT_ADMIN = "ADMIN_USER";

    /*****************
     * URL
     *****************/
    public static final String URL_PUBLIC = "/public";
    public static final String URL_USER = "/standard";
    public static final String URL_ADMIN = "/admin";




}
