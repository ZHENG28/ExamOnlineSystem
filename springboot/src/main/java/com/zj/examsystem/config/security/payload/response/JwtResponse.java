package com.zj.examsystem.config.security.payload.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtResponse
{
    @NonNull
    private String token;

    private String type = "Bearer";

    @NonNull
    private String account;

    @NonNull
    private List<String> roles;
}
