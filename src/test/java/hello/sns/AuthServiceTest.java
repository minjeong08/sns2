package hello.sns;

import hello.sns.domain.User;
import hello.sns.repository.UserRepository;
import hello.sns.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void 정상_회원가입() {
        // given
        User user = new User();
        user.setLoginId("test");
        user.setPassword("test");

        // when
        User savedUser = authService.register(user.getLoginId(), user.getPassword());
        Long savedUserId = savedUser.getId();

        // then
        User foundUser = userRepository.findById(savedUserId).get();
        assertEquals(user.getLoginId(), foundUser.getLoginId());
        assertTrue(passwordEncoder.matches(user.getPassword(), foundUser.getPassword()));
    }

    @Test
    void 중복_회원가입() {
        // given
        User user1 = new User();
        user1.setLoginId("test");
        user1.setPassword("test");

        User user2 = new User();
        user2.setLoginId("test");
        user2.setPassword("test");

        // when
        authService.register(user1.getLoginId(), user1.getPassword());

        // then
        assertThatThrownBy(() -> authService.register(user2.getLoginId(), user2.getPassword()))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void 정상_로그인() {
        // given
        User user = new User();
        user.setLoginId("test");
        user.setPassword("test");

        // when
        authService.register(user.getLoginId(), user.getPassword());
        Optional<User> result = authService.login(user.getLoginId(), user.getPassword());

        // then
        assertTrue(result.isPresent());
        assertEquals(user.getLoginId(), result.get().getLoginId());
        assertTrue(passwordEncoder.matches(user.getPassword(), result.get().getPassword()));
    }
}