package com.silverhair.care.filter;

import com.silverhair.care.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * JWT认证过滤器
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = extractTokenFromRequest(request);
        
        if (token != null) {
            try {
                Long userId = jwtUtil.getUserIdFromToken(token);
                Integer roleValue = jwtUtil.getRoleFromToken(token);
                
                // 将角色值转换为角色名称
                String roleName = convertRoleValueToName(roleValue);
                
                // 设置Spring Security认证信息
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, null, Collections.singletonList(authority));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Token无效或过期,清除认证信息
                SecurityContextHolder.clearContext();
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String convertRoleValueToName(Integer roleValue) {
        if (roleValue == null) {
            return "USER";
        }
        switch (roleValue) {
            case 1:
                return "ELDERLY";
            case 2:
                return "FAMILY";
            case 9:
                return "ADMIN";
            default:
                return "USER";
        }
    }
}
