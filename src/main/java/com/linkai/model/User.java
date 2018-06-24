package com.linkai.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 14:57
 * Remember to sow in the spring.
 * Description :
 */
@Data
@Entity
@Table(name = "user")
public class User {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /**
     * 账号
     */
    @Column(name = "account", nullable = false)
    private String account;

    /**
     * 密码
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 姓名
     */
    @Column(name = "name", nullable = false)
    private String name;
}
