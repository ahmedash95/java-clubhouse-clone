package com.egy.clubhouse_clone.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.egy.clubhouse_clone.ClubhouseCloneApplicationTests;
import com.egy.clubhouse_clone.entity.UserEntity;
import com.egy.clubhouse_clone.exceptions.user.EmailAlreadyTakenException;
import com.egy.clubhouse_clone.helper.UserHelper;
import com.egy.clubhouse_clone.repository.UserRepository;
import com.egy.clubhouse_clone.services.UserService;
import java.net.URI;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsersControllerTest extends ClubhouseCloneApplicationTests {

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

}
