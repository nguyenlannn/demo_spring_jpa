package com.example.lan_demo.repository;

import com.example.lan_demo.dto.Rss.SelectAllUserRss;
import com.example.lan_demo.dto.Rss.SelectDeviceRss;
import com.example.lan_demo.entity.DeviceEntity;
import com.example.lan_demo.entity.UserEntity;
import com.example.lan_demo.enums.UserEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);

    UserEntity findByEmail(String email);

    @Query(value = "SELECT u FROM user u")
//JPQL SORT
    List<UserEntity> findByUser(Sort sort);


// native query
//    @Query(value = "select count(u.id) from user u " +
//            "where if(?1 !='',u.name like concat('%',?1,'%'),2) " +
//            "and if(?2 !='',u.email like concat('%',?2,'%'),1) " +
//            "and if(?3 != null,u.id= ?3,1) " +
//            "and if(?4 != null,u.is_active= ?4,1)", nativeQuery = true)
//    Long getTotalRecord(String name,
//                        String email,
//                        Integer id,
//                        UserEnum isActive1);
//
//    @Query(value = "select * from user u " +
//        "where if(?1!='',u.name like concat('%',?1,'%'),1) " +
//        "and if((?2!=''),(u.email like concat('%',?2,'%')),1) " +
//        "and if((?3 is not null),(u.id= ?3),1) " +
//        "and if((?4 is not null),(u.is_active= ?4),1) " +
//        "limit ?6, ?5", nativeQuery = true)
//    List<UserEntity> getPaging(String name,String email,Integer id,UserEnum isActive1, Long limit, Long offset);

    //jpql
    @Query(value = "select count(u.id) from user u " +
            "where (:name is null or u.name like concat('%',:name,'%')) " +
            "and (:email is null or u.email like concat('%',:email,'%')) " +
            "and (:id is null or u.id= :id) " +
            "and (:isActive is null or u.isActive= :isActive)")
    Long getTotalRecord(@Param("name") String name,
                        @Param("email") String email,
                        @Param("id") Integer id,
                        @Param("isActive") UserEnum isActive);

    @Query(value = "select * from user u " +
            "where if(?1!='',u.name like concat('%',?1,'%'),1) " +
            "and if((?2!=''),(u.email like concat('%',?2,'%')),1) " +
            "and if((?3 is not null),(u.id= ?3),1) " +
            "and if((?4 is not null),(u.is_active= ?4),1) " +
            "order by u.email asc, u.name desc " +
            "limit ?6, ?5", nativeQuery = true)
        //không hỗ trợ sort
    List<UserEntity> getPaging(String name, String email, Integer id, UserEnum isActive1, Long limit, Long offset);

    @Query(value = "select u.id, u.email, d.id, d.userAgent from user u join device d on u.id = d.user.id")
    List<UserEntity> testJoin();

//    @Query(value = "select u from user u order by u.id asc ")// JPQL
//    List<UserEntity> findAllUser(Pageable pageable);

//    @Query(value = "select * from user ",//câu query 1
//            countQuery = "select count(*) from user",
//            nativeQuery = true)
//    Page<UserEntity> selectAllUser(Pageable pageable);

    //câu query 2
    @Query(value = "select d.id as id, d.is_active as isActive, d.user_agent as userAgent, d.device_verification as deviceVerification, d.user_id as userId " +
            "from device d",
            nativeQuery = true)
    List<SelectDeviceRss> selectDevice();

    @Query(value = "select u.id as id, " +
                        "u.email as email, " +
                        "u.name as name, " +
                        "d.id as id, " +
                        "d.is_active as isActive, " +
                        "d.user_agent as userAgent, " +
                        "d.device_verification as deviceVerification, " +
                        "d.user_id as userId " +
                        "from user u left join device d on u.id=d.user_id ",
            countQuery = "select count(*) from user",
            nativeQuery = true)
    Page<SelectAllUserRss> selectAllUser(Pageable pageable);
}



