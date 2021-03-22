package com.egy.clubtalk.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.egy.clubtalk.entity.RoomEntity;
import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.helper.UserHelper;
import com.egy.clubtalk.repository.RoomRepository;
import com.egy.clubtalk.repository.UserRepository;
import com.egy.clubtalk.services.UserService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RoomsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JacksonTester<RoomEntity> json;

    @Test
    @WithMockUser(username = "ahmed@email.com", password = "pwd", roles = "USER")
    public void test_user_can_create_rooms() throws Exception {
        // create logged in user
        UserEntity user = userService.createUser(UserHelper.getUserEntity());

        RoomEntity room = new RoomEntity();
        room.setName("My first room!");
        room.setOwner(user);

        mockMvc.perform(post("/rooms/")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.write(room).getJson())
        ).andExpect(status().isCreated());

        Assert.assertEquals(1, roomRepository.findAll().size());
        Assert.assertEquals("My first room!", roomRepository.findAll().get(0).getName());
    }

}
