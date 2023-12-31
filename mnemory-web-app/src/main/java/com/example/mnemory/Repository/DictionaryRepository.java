package com.example.mnemory.Repository;


import com.example.mnemory.Entity.UserDb;
import com.example.mnemory.Entity.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DictionaryRepository extends CrudRepository<UserDb, Long> {
    @Query(
            value="select wordname, defaultword, wordtype from dictionary d where left (wordname, 1) = :firstletter", nativeQuery = true
    )
    List<String> getWordsByFirstLetter(@Param("firstletter") String firstletter);

    @Query(
            value="select preference_example from preference p where left (preference_example, 1) = :firstletter and preference_type = :preferencetype", nativeQuery = true
    )
    List<String> getPersonalizedWordsByFirstLetter(@Param("firstletter") String firstletter, @Param("preferencetype") String preferencetype);
}
