package com.example.mnemory.Repository;

import com.example.mnemory.Entity.UserDb;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemplateRepository extends CrudRepository<UserDb, Long> {

    @Query(
            value = "select * from template t where t.wordsno = :wordsno", nativeQuery=true
    )
    List<String> getTemplateByLength(@Param("wordsno") int length);
}
