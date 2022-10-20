package com.example.wsbp.service.user;

import com.example.wsbp.domain.models.user.IUserRepository;
import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.infrastructure.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository authUserRepos) {
        this.userRepository = authUserRepos;
    }

    @Override
    public void registerUser(final User user) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(user.getPass().getBytes());
        byte[] sha256_result = md.digest();
        StringBuilder builder = new StringBuilder(2 * sha256_result.length);
        for (byte b : sha256_result) {
            builder.append(String.format("%02x", b & 0xff));
        }
        user.setPass(builder.toString());
        userRepository.registerUser(user);
    }


    @Override
    public void removeUser(final String studentNumber) {
        userRepository.removeUser(studentNumber);
    }

    @Override
    public User loginUser(final String studentNumber, final String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(pass.getBytes());
        byte[] sha256_result = md.digest();
        StringBuilder builder = new StringBuilder(2 * sha256_result.length);
        for (byte b : sha256_result) {
            builder.append(String.format("%02x", b & 0xff));
        }
        return userRepository.loginUser(studentNumber, builder.toString());
    }
}
