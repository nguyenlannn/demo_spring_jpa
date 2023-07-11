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

    abstract List<UserEntity> findByEmail(String email);

    @Query(value = "select e from user e where e.email = :email")
    public abstract List<UserEntity> findByEmail1(@Param("email") String email);

    @Query(value = "select e from user e where e.email = ?1")
    public abstract List<UserEntity> findByEmail3(String email);

    @Query(value = "select * from user e where e.email = ?1", nativeQuery = true)
    public abstract List<UserEntity> findByEmail2(String email);

    @Query(value = "select * from user e where e.email = :email", nativeQuery = true)
    public abstract List<UserEntity> findByEmail4(@Param("email") String email);

    public List<UserEntity> findByEmail5(String email){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);

        Root<UserEntity> book = cq.from(UserEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (email != null) {
            predicates.add(cb.equal(book.get("email"), email));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
