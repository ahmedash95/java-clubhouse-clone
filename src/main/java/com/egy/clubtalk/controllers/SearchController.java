package com.egy.clubtalk.controllers;

import com.egy.clubtalk.entity.SearchResultEntity;
import com.egy.clubtalk.entity.SearchResultType;
import com.egy.clubtalk.entity.UserEntity;
import com.egy.clubtalk.services.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    UserService userService;

    @GetMapping("/find")
    public ResponseEntity<Object> find(@RequestParam("query") String query) {
        List<SearchResultEntity> results = new ArrayList<>();
        List<UserEntity> users = userService.search(query);
        users.stream().map(SearchResultEntity::fromUser).forEach(results::add);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
