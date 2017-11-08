package fr.quintipio.javarestangulararchi.controller;

import fr.quintipio.javarestangulararchi.JavarestangulararchiApplication;
import fr.quintipio.javarestangulararchi.model.Profil;
import fr.quintipio.javarestangulararchi.util.TestUtil;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavarestangulararchiApplication.class)
@WebAppConfiguration
public class ProfilControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String libelleNewprofil = "testProfil";

    private String libelleProfilModif = "testProfilNew";


    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllProfils() throws Exception {
         mockMvc.perform(get("/admin/profil")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getOneProfil() throws Exception {
        getProfilById(1,"STANDARD_USER");
    }

    private Profil getProfilById(Integer id,String libelleProfilToCheck) throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/profil/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libelle",is(libelleProfilToCheck)))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        return TestUtil.convertJsonAsObject(jsonResult,Profil.class);
    }

    @Test
    public void searchProfil() throws Exception {
        mockMvc.perform(get("/admin/profil/search")
                        .contentType(MediaType.APPLICATION_JSON).param("libelle","ADMIN"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void manageProfil() throws Exception {
        Profil newProfil = new Profil();
        newProfil.setLibelle(libelleNewprofil);
        //création
        mockMvc.perform(post("/admin/profil")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(newProfil)))
                .andExpect(status().isOk());

        //check Création
        Profil profil = getProfilById(3,libelleNewprofil);

        //update
        profil.setLibelle(libelleProfilModif);
        mockMvc.perform(put("/admin/profil")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(profil)))
                .andExpect(status().isOk());
        profil = getProfilById(3,libelleProfilModif);

        //delete
        mockMvc.perform(delete("/admin/profil/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/admin/profil")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
