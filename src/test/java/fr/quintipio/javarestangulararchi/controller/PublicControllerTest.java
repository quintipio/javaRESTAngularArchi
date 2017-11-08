package fr.quintipio.javarestangulararchi.controller;

import fr.quintipio.javarestangulararchi.JavarestangulararchiApplication;
import fr.quintipio.javarestangulararchi.model.Profil;
import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.service.ProfilService;
import fr.quintipio.javarestangulararchi.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavarestangulararchiApplication.class)
@WebAppConfiguration
public class PublicControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfilService profilService;

    private Utilisateur user;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Set<Profil> setprofil = new HashSet<>();
        setprofil.add(profilService.findById(1L));

        user = new Utilisateur();
        user.setSso("test");
        user.setEmail("test@test.fr");
        user.setNom("TEST");
        user.setPrenom("Test");
        user.setLangue("fr");
        user.setActif(false);
        user.setMotDePasse("testMdp");
        user.setLinkResetPassword(null);
        user.setLinkActivation(null);
        user.setUserProfiles(setprofil);
        user.setCreatedDate(new Date());
        user.setLastModifiedDate(null);

        user = userService.saveUser(user);
    }

    @After
    public void end() throws Exception {
        userService.deleteUser(user.getId());
    }

    @Test
    public void pageAccueilTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/public/accueil?lang=fr")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertTrue("Bienvenue sur la page d'accueil publique".equals(result.getResponse().getContentAsString()));
    }

    @Test
    public void testActivation() throws Exception {
        //préparation pour activation
        Utilisateur userTest = userService.findById(user.getId());
        userTest.setActif(false);
        userTest.setLinkActivation("test");
        userService.saveUser(userTest);

        //test activation
        mockMvc.perform(get("/public/activerCompte")
                .param("link","test"))
                .andExpect(status().isOk());

        //check activation
        userTest = userService.findById(user.getId());
        Assert.assertTrue(userTest.getActif() && user.getLinkActivation() == null);
    }

    @Test
    public void testSendReinitMdp() throws Exception {
        mockMvc.perform(get("/public/demandeReinitMdp")
                .param("sso","test"))
                .andExpect(status().isOk());

        Utilisateur userTest = userService.findById(user.getId());
        Assert.assertTrue(user.getLinkResetPassword() != null);
    }

    @Test
    public void testReinitMdp() throws Exception {
        //préparation pour reset
        Utilisateur userTest = userService.findById(user.getId());
        userTest.setLinkResetPassword("test");
        userTest.setActif(true);
        userTest = userService.saveUser(userTest);

        String memOldPass = userTest.getMotDePasse();

        //test activation
        mockMvc.perform(get("/public/reinitMdp")
                .param("link","test")
                .param("newmdp","nouveaumdp"))
                .andExpect(status().isOk());

        userTest = userService.findById(user.getId());
        Assert.assertTrue(userTest.getLinkResetPassword() == null && !userTest.getMotDePasse().equals(memOldPass));
    }
}
