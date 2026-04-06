package com.taskapi.security.infraestructure.adapter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.taskapi.shared.domain.ICurrentUser;
import com.taskapi.shared.domain.exceptions.BussinesRuleException;
import com.taskapi.shared.domain.model.JwtSkeleton;

@Component
public class CurrentUserAdapter implements ICurrentUser {

    @Override  
    public Long getId() {  
        return getSkeleton().getId();  
    }  
  
    @Override  
    public String getEmail() {  
        return getSkeleton().getEmail();  
    }  
  
    private JwtSkeleton getSkeleton() {  
        var auth = SecurityContextHolder.getContext().getAuthentication();  
  
        if (auth == null || !auth.isAuthenticated()) {  
            throw new BussinesRuleException("No authenticated user");  
        }  
  
        // El principal es el JwtSkeleton que seteaste en el filter  
        return (JwtSkeleton) auth.getPrincipal();  
    }
}
