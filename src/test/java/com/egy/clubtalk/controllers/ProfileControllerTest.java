package com.egy.clubtalk.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.egy.clubtalk.ClubhouseCloneApplicationTests;
import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.helper.UserHelper;
import com.egy.clubtalk.services.UserService;
import java.net.URI;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProfileControllerTest extends ClubhouseCloneApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private JacksonTester<UserEntity> json;

    @Test
    @WithMockUser(username = "ahmed@email.com", password = "pwd", roles = "USER")
    public void test_user_can_update_profile() throws Exception {
        UserEntity user = UserHelper.getUserEntity();
        userService.createUser(user);
        user.setBio("Hello from tests.");

        ResultActions resultAction = mockMvc.perform(
                post(new URI("/profile/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json.write(user).getJson())
        ).andExpect(status().isOk());

        MvcResult result = resultAction.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Assert.assertTrue(contentAsString.contains("Hello from tests."));
    }

    @Test
    @WithMockUser(username = "ahmed@email.com", password = "pwd", roles = "USER")
    public void test_user_can_fetch_profile() throws Exception {
        UserEntity user = userService.createUser(UserHelper.getUserEntity());

        ResultActions resultAction = mockMvc.perform(
                get(new URI("/profile/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        MvcResult result = resultAction.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Assert.assertTrue(contentAsString.contains(user.getFirstName()));
        Assert.assertTrue(contentAsString.contains(user.getEmail()));
    }
}
