package hello.sns.controller.auth;

import hello.sns.domain.User;
import hello.sns.repository.UserRepository;
import hello.sns.service.ProfileService;
import hello.sns.service.UpdateProfileDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;

    @GetMapping("/{username}")
    public String profile(Model model, @PathVariable String username) {

        model.addAttribute("user", userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다.")));
        return "profile/user_profile";
    }

    @GetMapping("/edit")
    public String editProfile(Model model) {
        User user = getAuthUser();
        model.addAttribute("user", user);
        model.addAttribute("form", new ProfileUpdateForm());
        return "profile/edit_profile";
    }

    @PostMapping("/edit")
    public String edit(@Valid ProfileUpdateForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "profile/edit_profile";
        }

        User user = getAuthUser();

        UpdateProfileDto dto = new UpdateProfileDto();
        dto.setName(form.getName());
        dto.setProfileImageUrl(form.getProfileImageUrl());
        dto.setBio(form.getBio());

        profileService.updateProfile(user.getId(), dto);
        return "redirect:/profile/{user.getName()}";
    }

    @GetMapping("/edit/password")
    public String editPassword() {
        return "profile/edit_password";
    }
    
    @PostMapping("/edit/password")
    public String editPassword(String password) {
        User user = getAuthUser();

        profileService.updatePassword(user.getId(), password);
        return "redirect:/profile/edit/password";
    }

    private static User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
