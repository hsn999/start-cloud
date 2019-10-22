package com.start.framwork.jwt;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;


@Getter
@ToString
public class JwtAuthenticationConfig {

    @Value("${shuaicj.security.jwt.url:/login}")
    private String url;

    @Value("${shuaicj.security.jwt.header:Authorization}")
    private String header;

    @Value("${shuaicj.security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${shuaicj.security.jwt.expiration:#{2*60*60}}")
    private int expiration; // default 2 hours

    @Value("${shuaicj.security.jwt.secret}")
    private String secret;
}
