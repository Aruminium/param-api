package com.example.wsbp.service.user;

import com.example.wsbp.AdminProperties;
import com.example.wsbp.domain.models.user.IUserRepository;
import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.infrastructure.service.user.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AdminService implements IAdminService {

    private final IUserRepository userRepository;

    @Autowired
    public AdminService(IUserRepository userRepository) throws NoSuchAlgorithmException {
        this.userRepository = userRepository;
        registerUser();
    }

    @Override
    public void registerUser() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(AdminProperties.PASSWORD.getBytes());
        byte[] sha256_result = md.digest();
        StringBuilder builder = new StringBuilder(2 * sha256_result.length);
        for (byte b : sha256_result) {
            builder.append(String.format("%02x", b & 0xff));
        }
        User adminUser = new User(AdminProperties.STUDENT_NUMBER, "管理者", builder.toString());
        try {
            userRepository.registerUser(adminUser);
        } catch (Exception ignored) {

        }
    }
}
