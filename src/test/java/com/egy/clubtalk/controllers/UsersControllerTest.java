package com.egy.clubtalk.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.egy.clubtalk.ClubTalkApplicationTest;
import com.egy.clubtalk.dao.UserDAO;
import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.exceptions.user.EmailAlreadyTakenException;
import com.egy.clubtalk.helper.UserHelper;
import com.egy.clubtalk.repository.UserRepository;
import com.egy.clubtalk.services.UserService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
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
public class UsersControllerTest extends ClubTalkApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JacksonTester<UserEntity> json;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void test_user_can_register_new_account() throws Exception {
        UserEntity user = UserHelper.getUserEntity();

        mockMvc.perform(
                post(new URI("/register/"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json.write(user).getJson())
        ).andExpect(status().isCreated());
    }

    @Test
    public void test_user_can_not_register_with_taken_email() throws Exception {
        UserEntity user = UserHelper.getUserEntity();
        userService.createUser(user);

        exception.expect(EmailAlreadyTakenException.class);
        mockMvc.perform(
                post(new URI("/register/"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json.write(user).getJson())
        ).andExpect(status().isUnprocessableEntity());

        Assert.assertEquals(1, userRepository.findAll().size());
    }


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


    @Test
    @WithMockUser(username = "ahmed@email.com", password = "pwd", roles = "USER")
    public void test_can_see_followings_of_the_user() throws Exception {
        // create logged in user
        UserEntity user = userService.createUser(UserHelper.getUserEntity());

        // follow other users
        UserEntity user1 = userService.createUser(UserHelper.getUserEntity("test@email.com"));
        UserEntity user2 = userService.createUser(UserHelper.getUserEntity("test2@email.com"));
        UserEntity user3 = userService.createUser(UserHelper.getUserEntity("test3@email.com"));

        userService.follow(user.getEmail(), user1.getId());
        userService.follow(user.getEmail(), user2.getId());

        ResultActions resultAction = mockMvc.perform(get(String.format("/users/%d/following", user.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        MvcResult result = resultAction.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Assert.assertTrue(contentAsString.contains(user1.getEmail()));
        Assert.assertTrue(contentAsString.contains(user2.getEmail()));
        Assert.assertFalse(contentAsString.contains(user3.getEmail()));
    }
    @Test
    @WithMockUser(username = "ahmed@email.com", password = "pwd", roles = "USER")
    public void test_can_see_followers_of_the_user() throws Exception {
        // create logged in user
        UserEntity user = userService.createUser(UserHelper.getUserEntity());

        // follow other users
        UserEntity user1 = userService.createUser(UserHelper.getUserEntity("test@email.com"));
        UserEntity user2 = userService.createUser(UserHelper.getUserEntity("test2@email.com"));
        UserEntity user3 = userService.createUser(UserHelper.getUserEntity("test3@email.com"));

        userService.follow(user1.getEmail(), user.getId());
        userService.follow(user2.getEmail(), user.getId());

        ResultActions resultAction = mockMvc.perform(get(String.format("/users/%d/followers", user.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        MvcResult result = resultAction.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Assert.assertTrue(contentAsString.contains(user1.getEmail()));
        Assert.assertTrue(contentAsString.contains(user2.getEmail()));
        Assert.assertFalse(contentAsString.contains(user3.getEmail()));
    }

}
