package com.dm.asde.util;

import com.dm.asde.model.dto.TokenModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by yinfante on 10/19/16.
 */
@Component
public class JwtManager {


    @Value("${jwt.secret}")
    private String secret;


    private static final int ARRAY_LENGTH = 2;
    private final static int USER_ID_INDEX = 0;
    private final static int EMAIL_INDEX = 1;
    private final static int USER_TYPE_INDEX = 1;

    public String generateToken(String email, long userId, int userType) {

        String subject = userId + ":" + email + ":" + userType;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String parseToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public TokenModel parseTokenToModel(String token) {

        String parseToken = parseToken(token);

        String[] split = parseToken.split(":");

        if (split.length != ARRAY_LENGTH)
            return null;

        Long userId = Long.valueOf(split[USER_ID_INDEX]);
        String email = split[EMAIL_INDEX];
        int userType = Integer.valueOf(split[USER_TYPE_INDEX]);

        return new TokenModel(userId, email, userType);
    }

    public boolean validToken(String jws) {
        return parseToken(jws) != null;
    }


}
