package com.example.mnemory.Repository;

import com.example.mnemory.Entity.UserDb;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDbRepository extends CrudRepository<UserDb, Long> {

    @Query(
            value = "select * from userdb u where u.id_user= :idUser", nativeQuery = true
    )
    UserDb findUserDbById(@Param("idUser") long idUser);

    @Query(
            value = "select * from userdb u where u.username = :username", nativeQuery = true
    )
    UserDb findUserDbByUsername(@Param("username") String username);

    @Query(
            value = "select * from userdb u where u.email = :email", nativeQuery = true
    )
    UserDb findUserDbByEmail(@Param("email") String email);
}

