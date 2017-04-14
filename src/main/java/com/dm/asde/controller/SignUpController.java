package com.dm.asde.controller;

import com.dm.asde.exception.DmAPIException;
import com.dm.asde.exception.PasswordDoesNotMeetTheRequirementsExpection;
import com.dm.asde.model.User;
import com.dm.asde.model.dto.SignUpDTO;
import com.dm.asde.repository.UserRepository;
import com.dm.asde.util.JwtManager;
import com.dm.asde.util.PasswordStorage;
import com.dm.asde.exception.InvalidEmailException;
import com.dm.asde.exception.NonUniqueUserNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yarielinfante on 3/26/17.
 */
@RestController
@RequestMapping("/signup")
public class SignUpController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordStorage passwordStorage;

    @Autowired
    private JwtManager jwtManager;

    private final static String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


    @PostMapping
    @Transactional
    public ResponseEntity signUp(@RequestBody SignUpDTO signUpDTO) throws DmAPIException, PasswordStorage.CannotPerformOperationException {

        try {
            if (!patternCorrect(signUpDTO.getEmail(), EMAIL_REGEX)) {
                throw new InvalidEmailException(signUpDTO);
            }


            User userFound = userRepository.findByEmail(signUpDTO.getEmail());

            if (userFound != null) {
                throw new NonUniqueUserNameException(signUpDTO);
            }

            if (isPasswordInvalid(signUpDTO.getPassword()) || signUpDTO.getEmail().equals(signUpDTO.getPassword())) {
                throw new PasswordDoesNotMeetTheRequirementsExpection();
            }

            String hash = passwordStorage.createHash(signUpDTO.getPassword());

            User user = new User();
            user.setName(signUpDTO.getName());
            user.setLastName(signUpDTO.getLastName());
            user.setEmail(signUpDTO.getEmail());
            user.setPassword(hash);

            userRepository.save(user);

            String generatedToken = jwtManager.generateToken(user.getEmail(), user.getId(), signUpDTO.getUserType());

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).header("token", generatedToken).body(user);

        } catch (Throwable e) {
            throw new DmAPIException(e, signUpDTO);
        }
    }


    private boolean patternCorrect(String text, final String regex) {

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }


    private boolean isPasswordInvalid(String password) {
        // Mínimo 6 caracteres
        // No permitir el username como password

        // TODO 6 characters for now
        return !((password.length() >= 6)
//                && (password.matches(".*[a-z]+.*"))
//                && (password.matches(".*[0-9]+.*"))
        );
    }

}
