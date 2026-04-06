package com.taskapi.shared.application.port.out.security;

import com.taskapi.shared.domain.model.JwtSkeleton;

public interface IJwtConsumer {
    
    public String generateToken(JwtSkeleton tokenSkeleton);

    public String extractEmail(String token);

    public boolean isTokenValid(String token);

    public JwtSkeleton extractSkeleton(String token);

}
