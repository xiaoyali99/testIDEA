package com.linzc.security1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author linzc99
 * @create 2021-03-14 21:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    private Integer id;
    private String username;
    private String password;
}
