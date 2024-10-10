package hello.sns.controller.auth;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfileUpdateForm {

    @NotEmpty(message = "이름을 필수로 입력해 주세요.")
    private String name;

    private String profileImageUrl;

    @NotNull
    @Max(value = 100, message = "100자 이내로 입력해 주세요.")
    private String bio;
}
