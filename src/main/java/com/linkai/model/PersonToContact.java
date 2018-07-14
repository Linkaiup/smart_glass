package com.linkai.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 15:05
 * Remember to sow in the spring.
 * Description :
 */
@Data
@Entity
@Table(name = "contact")
@NoArgsConstructor
public class PersonToContact {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /**
     * 联系电话
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * 姓名
     */
    @Column(name = "name", nullable = false)
    private String name;
}
