package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.exceptions.InvalidPassword;
import com.example.UserAuthenticationService.exceptions.UserAlreadyExistsException;
import com.example.UserAuthenticationService.exceptions.UserNotPresent;
import com.example.UserAuthenticationService.models.Role;
import com.example.UserAuthenticationService.models.Session;
import com.example.UserAuthenticationService.models.User;
import com.example.UserAuthenticationService.repos.SessionRepository;
import com.example.UserAuthenticationService.repos.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService implements IAuthService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SessionRepository sessionRepository;

        private SecretKey key = Jwts.SIG.HS256.key().build();
//    private SecretKey key = Keys.hmacShaKeyFor(
//            "namanisveryveryveryveryveryveryverycool"
//                    .getBytes(StandardCharsets.UTF_8));

    @Override
    public User signup(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmailId(email);
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException("emailId is already used");
        }
        User user = new User();
        user.setEmailId(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user = userRepository.save(user);

        return user;
    }

    @Override
    public User login(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmailId(email);

        if(userOptional.isEmpty()){
            throw new UserNotPresent("Please signup first");
        }

        if(!bCryptPasswordEncoder.matches(password,userOptional.get().getPassword())){
            throw new InvalidPassword("Password is incorrect");
        }

        return userOptional.get();
    }

    @Override
    public String loginForToken(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmailId(email);

        if(userOptional.isEmpty()){
            throw new UserNotPresent("Please signup first");
        }

        if(!bCryptPasswordEncoder.matches(password,userOptional.get().getPassword())){
            throw new InvalidPassword("Password is incorrect");
        }

        String token =  createJwtToken(userOptional.get().getId(),userOptional.get().getRoleList(),userOptional.get().getEmailId());

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date datePlus30Days = calendar.getTime();

        Session session = new Session();
        session.setToken(token);
        session.setUser(userOptional.get());
        session.setExpiryAt(datePlus30Days);

        sessionRepository.save(session);

        return token;
    }

    @Override
    public Boolean validate(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            Date expiryAt = claims.getPayload().getExpiration();
            Long userId = claims.getPayload().get("user_id", Long.class);

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private String createJwtToken(Long userId, List<Role> roles, String email) {
        Map<String, Object> dataInJwt = new HashMap<>();
        dataInJwt.put("user_id", userId);
        dataInJwt.put("roles", roles);
        dataInJwt.put("email", email);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date datePlus30Days = calendar.getTime();

        String token = Jwts.builder()
                .claims(dataInJwt)
                .expiration(datePlus30Days)
                .issuedAt(new Date())
                .signWith(key)
                .compact();

        return token;
    }
}
