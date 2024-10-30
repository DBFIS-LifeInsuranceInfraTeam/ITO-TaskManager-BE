package com.ITOPW.itopw.service;

import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // PasswordEncoder 주입

    @Override
    public User registerUser(User user) {
        // 추가적인 유효성 검사나 로직을 여기에 추가할 수 있습니다.
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUserId(String id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

//    @Override
//    public boolean authenticateUser(String id, String password) {
//        Optional<User> user = userRepository.findById(id); // ID로 사용자 조회
//        // 사용자 없음
//        // 비밀번호 비교
//        return user.filter(value -> passwordEncoder.matches(password, value.getPassword())).isPresent();
//    }

    @Override
    public Optional<User> authenticateUser(String userId, String password) {
        Optional<User> userOptional = userRepository.findByUserId(userId); // ID로 사용자 조회

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // 비밀번호가 일치하는지 확인

            if (passwordEncoder.matches(password, user.getPassword())) {
                return userOptional; // 비밀번호가 일치하면 사용자 객체 반환
            }
        }
        return Optional.empty(); // 인증 실패 시 빈 Optional 반환
    }

    @Override
    public List<User> getUsersByProjectId(String projectId) {
        return userRepository.findByProjectId(projectId);
    }
}
