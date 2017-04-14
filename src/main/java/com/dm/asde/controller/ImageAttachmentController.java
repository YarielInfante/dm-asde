package com.dm.asde.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dm.asde.exception.CaseNotFoundException;
import com.dm.asde.exception.DmAPIException;
import com.dm.asde.exception.InvalidUploadException;
import com.dm.asde.model.CaseAttachment;
import com.dm.asde.model.DmCase;
import com.dm.asde.repository.CaseAttachmentRepository;
import com.dm.asde.repository.CaseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by yarielinfante on 3/30/17.
 */

@RestController
@RequestMapping("/image")
public class ImageAttachmentController {

    Logger log = LogManager.getLogger(ImageAttachmentController.class);
    @Autowired
    private CaseAttachmentRepository caseAttachmentRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private Cloudinary cloudinary;


    @PostMapping("/upload")
    @Transactional
    public ResponseEntity handleFileUpload(@RequestParam("case_id") String caseId, @RequestParam("file") MultipartFile file) throws IOException, DmAPIException {

        log.info("uploading case attachment... ");

        if (file == null & file.isEmpty())
            throw new InvalidUploadException();


        DmCase caseFound = caseRepository.findOne(new Long(caseId));

        if (caseFound == null)
            throw new CaseNotFoundException();

        log.info("case found :" + caseFound);

        Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

        log.info("file uploaded :\n" + result);

        CaseAttachment caseAttachment = new CaseAttachment();
        caseAttachment.setDmCase(caseFound);
        caseAttachment.setType(identifyResourceType(result.get("resource_type").toString()));
        caseAttachment.setUrl(result.get("secure_url").toString());

        caseAttachmentRepository.save(caseAttachment);

        log.info("attachment for case :" + caseId + "\n" + caseAttachment);

        return ResponseEntity.ok().body(caseAttachment);

    }

    private int identifyResourceType(String resourceType) {

        if ("image".equalsIgnoreCase(resourceType)) {
            return 0;
        } else if ("video".equalsIgnoreCase(resourceType)) {
            return 1;
        } else {
            return 2;
        }
    }
}
