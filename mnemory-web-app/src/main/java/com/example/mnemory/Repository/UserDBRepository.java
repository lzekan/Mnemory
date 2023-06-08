package com.example.mnemory.Repository;

import com.example.mnemory.Entity.UserDb;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDbRepository extends CrudRepository<UserDb, Long> {

    @Query(
            value = "select * from user_db u where u.id= :idUser", nativeQuery = true
    )
    UserDb findUserDbById(@Param("idUser") long idUser);

    @Query(
            value = "select * from user_db u where u.username = :username", nativeQuery = true
    )
    UserDb findUserDbByUsername(@Param("username") String username);

    @Query(
            value = "select * from user_db u where u.email = :email", nativeQuery = true
    )
    UserDb findUserDbByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(
        value = "update user_db set username = :username, password = :password, email = :email" +
                " where id = :id", nativeQuery = true
    )
    void updateUserById(@Param("id") int id, @Param("username") String username,
                        @Param("password") String password, @Param("email") String email);
}

