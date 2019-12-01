package com.igor.repository;


import com.igor.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    List<Model> findModelsByDescriptionContainingOrTitleContainingIgnoreCase(String searchP1, String searchP2);

    @Modifying
    @Query("delete from Model m where m.id=:id")
    void deleteModel(@Param("id") Integer id);


}
