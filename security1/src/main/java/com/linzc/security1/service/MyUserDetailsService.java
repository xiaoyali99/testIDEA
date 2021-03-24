package com.linzc.security1.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linzc.security1.entity.Users;
import com.linzc.security1.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linzc99
 * @create 2021-03-14 17:00
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Users user = usersMapper.selectOne(wrapper);
        if (user == null) {
            throw new UsernameNotFoundException("找不到此用户");
        }
        List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_role");
        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), roles);
    }
}
