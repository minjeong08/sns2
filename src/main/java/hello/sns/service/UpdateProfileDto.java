package hello.sns.service;

import lombok.Data;

@Data
public class UpdateProfileDto {

    private String name;
    private String profileImageUrl;
    private String bio;
}
