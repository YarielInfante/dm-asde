package com.dm.asde.controller;

import com.dm.asde.model.DmCase;
import com.dm.asde.repository.CaseRepository;
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
 * Created by yarielinfante on 4/10/17.
 */
@RestController
@RequestMapping("dmCases")
public class CaseController {

    @Autowired
    private CaseRepository caseRepository;


    @GetMapping
    @ResponseBody
    public ResponseEntity findCases(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) throws JsonProcessingException {

        Page<DmCase> cases = caseRepository.findAll(new PageRequest(page, size));

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(new ObjectMapper().writeValueAsString(cases));


    }

    @GetMapping("{caseId}")
    @ResponseBody
    public ResponseEntity findCases(@PathVariable long caseId, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page, @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) throws JsonProcessingException {

        DmCase dmCase = caseRepository.findOne(caseId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(new ObjectMapper().writeValueAsString(dmCase));


    }


}
