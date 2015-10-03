package com.ssttevee.attendance.data.model;

import com.google.appengine.api.datastore.*;
import com.ssttevee.attendance.data.DataBranchImpl;

import java.lang.reflect.Field;

public class Model
{
    protected Class cls = this.getClass();
    protected Long id;

    public boolean get()
    {
        try
        {
            if (id == null)
                return false;

            Entity entity = DataBranchImpl.datastore.get(KeyFactory.createKey(cls.getSimpleName(), id));

            fromEntity(entity);

            return true;
        }
        catch (EntityNotFoundException e)
        {
            return false;
        }
    }

    public boolean insert()
    {
        Entity entity = toEntity();

        Transaction txn = DataBranchImpl.datastore.beginTransaction();
        Key key = DataBranchImpl.datastore.put(entity);
        txn.commit();

        if(txn.isActive())
        {
            txn.rollback();
            return false;
        }

        this.id = key.getId();

        return true;
    }
    public boolean patch()
    {
        if (id == null)
            return false;

        Entity entity = toEntity(id);

        if(!get())
            return false;

        fromEntity(entity);

        Transaction txn = DataBranchImpl.datastore.beginTransaction();
        DataBranchImpl.datastore.put(toEntity(id));
        txn.commit();

        if(txn.isActive())
        {
            txn.rollback();
            return false;
        }

        return true;
    }
    public boolean put()
    {
        Field[] fields = this.cls.getDeclaredFields();

        Transaction txn = DataBranchImpl.datastore.beginTransaction();
        DataBranchImpl.datastore.put(toEntity(id));
        txn.commit();

        if(txn.isActive())
        {
            txn.rollback();
            return false;
        }

        return true;
    }
    public boolean delete()
    {
        if (id == null)
            return false;

        Transaction txn = DataBranchImpl.datastore.beginTransaction();
        DataBranchImpl.datastore.delete(KeyFactory.createKey(cls.getSimpleName(), id));
        txn.commit();

        if(txn.isActive())
        {
            txn.rollback();
            return false;
        }

        return true;
    }

    public Long getId()
    {
        return id;
    }

    protected void fromEntity(Entity entity)
    {
        try
        {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields)
            {
                Object val = entity.getProperty(field.getName());
                if(val == null) continue;

                if (field.getType() == Short.class && val.getClass() == Long.class)
                    field.set(this, ((Long) val).shortValue());
                else if (field.getType() == Integer.class && val.getClass() == Long.class)
                    field.set(this, ((Long) val).intValue());
                else
                    field.set(this, val);
            }

            id = entity.getKey().getId();
        }
        catch (IllegalAccessException e1)
        {
            e1.printStackTrace();
        }
    }

    protected Entity toEntity()
    {
        return toEntity(null);
    }

    protected Entity toEntity(Long id)
    {
        Field[] fields = cls.getDeclaredFields();
        Entity entity;

        if(id != null)
            entity = new Entity(cls.getSimpleName(), id);
        else
            entity = new Entity(cls.getSimpleName());

        try
        {
            for (Field field : fields)
            {
                entity.setProperty(field.getName(), field.get(this));
            }
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return entity;
    }
}
