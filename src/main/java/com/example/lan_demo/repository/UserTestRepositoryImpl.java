package com.example.lan_demo.repository;

import com.example.lan_demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public abstract class UserTestRepositoryImpl implements JpaRepository<UserEntity, Integer>, UserTestRepository {
    EntityManager em;

    @Query(value = "select e from user e where e.name = :name")
    public abstract List<UserEntity> findAllByName1(@Param("name") String name);

    @Query(value = "select e from user e where e.name = ?1")
    public abstract List<UserEntity> findAllByName2(String name);

    @Query(value = "select * from user e where e.name = ?1", nativeQuery = true)
    public abstract List<UserEntity> findAllByName3(String name);

    @Query(value = "select * from user e where e.name = :name", nativeQuery = true)
    public abstract List<UserEntity> findAllByName4(@Param("email") String name);

    public List<UserEntity> findAllByName5(String name){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);

        Root<UserEntity> book = cq.from(UserEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(cb.equal(book.get("name"), name));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
