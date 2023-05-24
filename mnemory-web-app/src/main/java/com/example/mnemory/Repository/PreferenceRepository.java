package com.example.mnemory.Repository;

import com.example.mnemory.Entity.UserDb;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceRepository extends CrudRepository<UserDb, Long> {
    @Query(
            value = "select * from preference p where p.preference_type = :type", nativeQuery=true
    )
    List<String> getPreferenceExampleByType(@Param("type") String type);

    @Query(
            value = "select distinct preference_type from preference p", nativeQuery = true
    )
    List<String> getAllPreferences();

    @Transactional
    @Modifying
    @Query(
            value = "insert into haspreferences values (:idUser, :idPreference)", nativeQuery = true
    )
    void addPreferenceToUser(@Param("idUser") int idUser, @Param("idPreference") int idPreference);

    @Transactional
    @Modifying
    @Query(
            value = "delete from haspreferences where iduser = :idUser and idpreference = :idPreference", nativeQuery = true
    )
    void removePreferenceFromUser(@Param("idUser") int idUser, @Param("idPreference") int idPreference);

    @Query(
            value = "select * from haspreferences where iduser = :idUser and idpreference = :idPreference", nativeQuery = true
    )
    String checkIfUserAlreadyHasPreference(@Param("idUser") int idUser, @Param("idPreference") int idPreference);
}
