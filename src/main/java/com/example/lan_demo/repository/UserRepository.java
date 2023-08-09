package com.example.lan_demo.repository;

import com.example.lan_demo.entity.UserEntity;
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

    @Query(value = "select count(u.id) from user u " +
            "where (:name !='' or u.name like concat('%',:name,'%') or :name IS NULL) " +
            "and (:email !='' or u.email like concat('%',:email,'%') or :email is null) "+
            "and (:id !=''or u.id= :id or :id is null) "+
            "and (:isActive1 !='' or u.isActive= :isActive1 or :isActive1 is null)")
    Long getTotalRecord(@Param("name") String name ,
                        @Param("email") String email,
                        @Param("id") Integer id,
                        @Param("isActive1") String isActive1);

    @Query(value = "select * from user u" +
            "where if(?1!='',u.name like concat('%',?1,'%'),1) " +
            "and if((?4!=''),(u.email like concat('%',?4,'%')),1) " +
            "and if((?5!=''),(u.id= ?5),1) " +
            "and if((?6!=''),(u.isActive= ?6),1) " +
            "limit ?3, ?2", nativeQuery = true)
    List<UserEntity> getPaging(String name, Long limit, Long offset, String email, Integer id, String isActive1);

    @Query(value = "select u.id, u.email, d.id, d.userAgent from user u join device d on u.id = d.user.id")
    List<UserEntity> testJoin();
}
