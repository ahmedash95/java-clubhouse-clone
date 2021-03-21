package com.egy.clubhouse_clone.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.egy.clubhouse_clone.entity.UserEntity;
import com.egy.clubhouse_clone.helper.UserHelper;
import com.egy.clubhouse_clone.services.UserService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private JacksonTester<UserEntity> json;

    @Test
    @WithMockUser(username = "ahmed@email.com", password = "pwd", roles = "USER")
    public void test_user_can_follow_other_users() throws Exception {
        // create logged in user
        userService.createUser(UserHelper.getUserEntity());

        UserEntity userEntity = UserHelper.getUserEntity();
        userEntity.setEmail("user2@email.com");
        UserEntity user = userService.createUser(userEntity);

        mockMvc.perform(post(String.format("/users/follow/%d", user.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "ahmed@email.com", password = "pwd", roles = "USER")
    public void test_user_can_not_follow_him_self() throws Exception {
        // create logged in user
        userService.createUser(UserHelper.getUserEntity());

        mockMvc.perform(post(String.format("/users/follow/%d", 1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }


}
