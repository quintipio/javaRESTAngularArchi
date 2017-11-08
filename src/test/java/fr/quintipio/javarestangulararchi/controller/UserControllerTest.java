package fr.quintipio.javarestangulararchi.controller;

import fr.quintipio.javarestangulararchi.JavarestangulararchiApplication;
import fr.quintipio.javarestangulararchi.model.Profil;
import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.service.ProfilService;
import fr.quintipio.javarestangulararchi.service.UserService;
import fr.quintipio.javarestangulararchi.util.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavarestangulararchiApplication.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfilService profilService;


    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllUtilisateurs() throws Exception {
        mockMvc.perform(get("/admin/utilisateur")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private Utilisateur getUtilisateurById(Integer id, String sso) throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/utilisateur/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sso",is(sso)))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        return TestUtil.convertJsonAsObject(jsonResult,Utilisateur.class);
    }

    @Test
    public void getOneUtilisateur() throws Exception {
        getUtilisateurById(1,"admin.admin");
    }

    @Test
    public void searchUtilisateur() throws Exception {
        mockMvc.perform(get("/admin/utilisateur/search")
                .contentType(MediaType.APPLICATION_JSON).param("sso","admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void checkSso() throws Exception  {
        MvcResult result = mockMvc.perform(get("/admin/utilisateur/checkSso")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id","null")
                .param("sso","admin.admin"))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertTrue(result.getResponse().getContentAsString().equalsIgnoreCase("true"));

        result = mockMvc.perform(get("/admin/utilisateur/checkSso")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id","1")
                .param("sso","admin.admin"))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertTrue(result.getResponse().getContentAsString().equalsIgnoreCase("false"));
    }

    @Test
    public void testActivation() throws Exception {

        //désactivation
        mockMvc.perform(get("/admin/utilisateur/desactiver/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //check
        Utilisateur adm = userService.findById(1L);
        Assert.assertFalse(adm.getActif());

        //activation
        mockMvc.perform(get("/admin/utilisateur/activer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //check
        adm = userService.findById(1L);
        Assert.assertTrue(adm.getActif());
    }

    @Test
    public void manageUser() throws Exception {
        //création
        Set<Profil> setprofil = new HashSet<>();
        setprofil.add(profilService.findById(1L));

        Utilisateur user = new Utilisateur();
        user.setSso("test");
        user.setEmail("test@test.fr");
        user.setNom("TEST");
        user.setPrenom("Test");
        user.setLangue("fr");
        user.setActif(true);
        user.setMotDePasse("testMdp");
        user.setUserProfiles(setprofil);

        mockMvc.perform(post("/admin/utilisateur")
                .contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonString(user)))
                .andExpect(status().isOk());

        //checkCréation
        Utilisateur userTmp = userService.findById(2L);
        Assert.assertTrue(userTmp.getSso().equalsIgnoreCase("test"));

        //update
        userTmp.setSso("NewTestSso");
        mockMvc.perform(put("/admin/utilisateur")
                .contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonString(userTmp)))
                .andExpect(status().isOk());

        //checkUpdate
        userTmp = userService.findById(2L);
        Assert.assertTrue(userTmp.getSso().equalsIgnoreCase("NewTestSso"));

        //changeMdp
        String oldMdp = userTmp.getMotDePasse();
        mockMvc.perform(get("/admin/utilisateur/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id",userTmp.getId().toString())
                .param("mdp","newMdp"))
                .andExpect(status().isOk());

        userTmp = userService.findById(2L);
        Assert.assertTrue(!userTmp.getMotDePasse().equalsIgnoreCase(oldMdp));

        //delete
        mockMvc.perform(delete("/admin/utilisateur/"+userTmp.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //checkDelete
        mockMvc.perform(get("/admin/utilisateur")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }


}
