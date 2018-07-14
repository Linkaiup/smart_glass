package com.linkai.dao;

import com.linkai.model.PersonToContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author K Lin
 * Date: 2018/6/23.
 * Time: 15:06
 * Remember to sow in the spring.
 * Description :
 */
@Repository
public interface PersonToContactRepository extends JpaRepository<PersonToContact,Integer> {
}
