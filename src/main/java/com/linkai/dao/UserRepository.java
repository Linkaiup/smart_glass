package com.linkai.dao;

import com.linkai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 14:48
 * Remember to sow in the spring.
 * Description :
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * 根据账号查询用户
     *
     * @param account 用户账号
     * @return 用户实体类
     */
    User findByAccount(@Param("account") String account);
}
