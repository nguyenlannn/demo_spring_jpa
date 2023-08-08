package com.example.lan_demo.repository;

import com.example.lan_demo.dto.res.UserRes;
import com.example.lan_demo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);

    UserEntity findByEmail(String email);

    List<UserEntity> findByName(String name);

    @Query(value = "select count(u.id) from user u where u.name like concat('%',:name,'%')")
    Long getTotalRecord(@Param("name") String name);

    @Query(value = "select count(u.id) from user u")
    Long getTotalRecord1();

    @Query(value = "select * \n" +
            "from user \n" +
            "where user.name like concat('%',?1,'%') \n" +
            "limit ?3, ?2", nativeQuery = true)
    List<UserEntity> getPaging(String name,
                            Long limit,
                            Long offset);

    @Query(value = "select * \n" +
            "from user \n" +
            "limit ?2, ?1", nativeQuery = true)
    List<UserEntity> getPaging1(Long limit,
                               Long offset);

    @Query(value = "select u.id, u.email, d.id, d.userAgent from user u join device d on u.id = d.user.id")
    List<UserEntity> testJoin();

}
