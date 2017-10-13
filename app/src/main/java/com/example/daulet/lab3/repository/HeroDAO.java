package com.example.daulet.lab3.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.daulet.lab3.models.Hero;

import java.util.List;

/**
 * Created by daulet on 10/13/17.
 */

@Dao
public interface HeroDAO {
    @Query("SELECT * FROM heroes")
    List<Hero> getAll();

    @Insert
    void insert(Hero news);

    @Delete
    void delete(Hero news);

    @Query("SELECT * FROM heroes")
    List<Hero> getAllNews();
}
