package com.dm.asde.controller;

import com.dm.asde.model.DmCase;
import com.dm.asde.model.User;
import com.dm.asde.repository.CaseRepository;
import com.dm.asde.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yarielinfante on 4/1/17.
 */
@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("{userId}/cases")
    @ResponseBody
    public ResponseEntity findCases(@PathVariable long userId, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page, @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) throws JsonProcessingException {

        if (userRepository.exists(userId)) {

            Page<DmCase> cases = caseRepository.findByUser(new User(userId), new PageRequest(page, size));

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(new ObjectMapper().writeValueAsString(cases));

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
