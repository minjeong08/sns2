package hello.sns.service;

import hello.sns.domain.User;
import hello.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String loginId, String password) {
        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setLoginId(loginId);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public Optional<User> login(String loginId, String rawPassword) {
        Optional<User> userOpt = userRepository.findByLoginId(loginId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
