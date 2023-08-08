package com.example.lan_demo.repository;

import com.example.lan_demo.entity.UserEntity;
import com.example.lan_demo.produce.TestProduceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTestRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "select e from user e where e.name = :name", nativeQuery = false)// kiểu câu query thường- map vơi entity
    List<UserEntity> findByName1(@Param("name") String x);

    @Query(value = "select e.* from user e where e.name = :name", nativeQuery = true)// native query -map với bảng trong csdl
    List<UserEntity> findByName2(@Param("name") String x);

    @Query(value = "select e.id as id, e.email as email  from user e where e.name = :name", nativeQuery = true)//native query - sử dụng dữ liệu tự định nghĩa để hứng
    List<TestProduceDto> findByName3(@Param("name") String x);

    List<UserEntity> findByName(String x);

//    @Query(value = "select e from user e where e.name = ?1")
//    public abstract List<UserEntity> findAllByName2(String name);
//
//    @Query(value = "select * from user e where e.name = ?1", nativeQuery = true)
//    public abstract List<UserEntity> findAllByName3(String name);
//
//    @Query(value = "select * from user e where e.name = :name", nativeQuery = true)
//    public abstract List<UserEntity> findAllByName4(@Param("email") String name);
//
//    public List<UserEntity> findAllByName5(String name) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
//
//        Root<UserEntity> book = cq.from(UserEntity.class);
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (name != null) {
//            predicates.add(cb.equal(book.get("name"), name));
//        }
//
//        cq.where(predicates.toArray(new Predicate[0]));
//
//        return em.createQuery(cq).getResultList();
//    }
}
