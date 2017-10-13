package com.example.daulet.lab3.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.daulet.lab3.models.Hero;

/**
 * Created by daulet on 10/13/17.
 */

@Database(entities = {Hero.class}, version = 1)
public abstract class RoomDb extends RoomDatabase {
    public abstract HeroDAO newsDao();
}

