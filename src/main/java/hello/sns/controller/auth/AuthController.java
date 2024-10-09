package hello.sns.controller.auth;

import hello.sns.domain.User;
import hello.sns.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "auth/register";
        }

        if (!form.getPassword().equals(form.getCheckPassword())) {
            result.rejectValue("checkPassword", "checkPassword", "비밀번호가 일치하지 않습니다.");
            return "auth/register";
        }

        authService.register(form.getLoginId(), form.getPassword());
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm form, BindingResult result, HttpSession session, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "auth/login";
        }

        Optional<User> userOpt = authService.login(form.getLoginId(), form.getPassword());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user,  // principal (사용자 정보)
                    null,  // credentials (비밀번호는 이미 인증되었으므로 null로 설정)
                    List.of()  // 권한 설정 (필요하면 권한을 설정할 수 있습니다.)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            SecurityContext context = SecurityContextHolder.getContext();
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            return "redirect:/home";
        } else {
            result.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "auth/login";
        }
    }
}
