package hello.sns.service;

import hello.sns.domain.User;
import hello.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final UserRepository userRepository;

    public void updatePassword(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        user.setPassword(password);
    }

    public void updateProfile(Long userId, UpdateProfileDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        user.setName(dto.getName());
        user.setProfileImageUrl(dto.getProfileImageUrl());
        user.setBio(dto.getBio());
    }
}
