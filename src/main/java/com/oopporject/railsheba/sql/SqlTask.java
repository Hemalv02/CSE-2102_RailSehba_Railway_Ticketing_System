package com.oopporject.railsheba.sql;

import com.oopporject.railsheba.model.Entity;

public interface SqlTask {
    void InsertIntoDatabase(Entity entity);
    void UpdateIntoDatabase(Entity entity);
    void InsertFromDatabase(Entity entity);
}
