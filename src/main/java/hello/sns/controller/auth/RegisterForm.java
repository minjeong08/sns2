package hello.sns.controller.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterForm {

    @NotNull(message = "아이디를 입력해 주세요.")
    private String loginId;

    @NotNull(message = "비밀번호를 입력해 주세요.")
    private String password;

    @NotNull(message = "비밀번호를 확인해 주세요.")
    private String checkPassword;
}
