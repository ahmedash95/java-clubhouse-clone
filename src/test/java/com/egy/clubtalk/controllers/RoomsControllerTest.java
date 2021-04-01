package com.egy.clubtalk.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.egy.clubtalk.dao.RoomDAO;
import com.egy.clubtalk.entity.RoomEntity;
import com.egy.clubtalk.entity.RoomInvitation;
import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.helper.UserHelper;
import com.egy.clubtalk.repository.RoomRepository;
import com.egy.clubtalk.repository.UserRepository;
import com.egy.clubtalk.services.RoomsService;
import com.egy.clubtalk.services.UserService;
import org.junit.Assert;
import org.junit.Test;
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
public class RoomsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomsService roomsService;

    @Autowired
    private JacksonTester<RoomEntity> json;

    @Autowired
    private JacksonTester<RoomInvitation> roomInvitiationJson;

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

    @Test
    @WithMockUser(username = "ahmed@email.com", password = "pwd", roles = "USER")
    public void test_user_can_invite_another_user_to_room() throws Exception {
        userService.createUser(UserHelper.getUserEntity());

        UserEntity user = userService.createUser(UserHelper.getUserEntity("user2@email.com"));
        RoomEntity room = new RoomEntity();
        room.setName("My first room!");
        room.setOwner(userService.getById(1L));

        room = roomsService.createRoom(room);

        RoomInvitation invitation = new RoomInvitation();
        invitation.setUserId(user.getId());

        ResultActions resultActions = mockMvc.perform(post(String.format("/rooms/%d/invite", room.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(roomInvitiationJson.write(invitation).getJson())
        ).andExpect(status().isCreated());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Assert.assertTrue(contentAsString.contains("User has been invited to room."));
    }

    @Test
    @WithMockUser(username = "ahmed@email.com", password = "pwd", roles = "USER")
    public void test_user_can_be_invited_to_room_once() throws Exception {
        userService.createUser(UserHelper.getUserEntity());

        UserEntity user = userService.createUser(UserHelper.getUserEntity("user2@email.com"));
        RoomEntity room = new RoomEntity();
        room.setName("My first room!");
        room.setOwner(userService.getById(1L));

        room = roomsService.createRoom(room);

        roomsService.inviteToRoom(room.getId(), userService.getByEmail("ahmed@email.com"), new RoomInvitation(user.getId()));


        RoomInvitation invitation = new RoomInvitation();
        invitation.setUserId(user.getId());

        ResultActions resultActions = mockMvc.perform(post(String.format("/rooms/%d/invite", room.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(roomInvitiationJson.write(invitation).getJson())
        ).andExpect(status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Assert.assertTrue(contentAsString.contains("User is already invited."));
    }

}
