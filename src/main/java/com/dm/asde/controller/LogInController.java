package com.dm.asde.controller;

import com.dm.asde.exception.DmAPIException;
import com.dm.asde.exception.InvalidUserException;
import com.dm.asde.model.User;
import com.dm.asde.repository.UserRepository;
import com.dm.asde.util.JwtManager;
import com.dm.asde.util.PasswordStorage;
import com.dm.asde.model.dto.LogInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yarielinfante on 3/26/17.
 */
@RestController
@RequestMapping("/login")
public class LogInController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordStorage passwordStorage;

    @Autowired
    private JwtManager jwtManager;

    @PostMapping
    public ResponseEntity login(@RequestBody LogInDTO logInDTO) throws DmAPIException, PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {

        try {
            User userFound = userRepository.findByEmail(logInDTO.getEmail());


            if (userFound == null)
                throw new InvalidUserException();

            boolean success = passwordStorage.verifyPassword(logInDTO.getPassword(), userFound.getPassword());

            if (success) {
                String generatedToken = jwtManager.generateToken(userFound.getEmail(), userFound.getId(), userFound.getUserType());

                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).header("token", generatedToken).body(userFound);

            } else {
                throw new InvalidUserException(logInDTO);
            }

        } catch (Throwable e) {
            throw new DmAPIException(e, logInDTO);
        }
    }
}
