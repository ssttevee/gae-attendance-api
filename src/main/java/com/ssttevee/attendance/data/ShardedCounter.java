package com.ssttevee.attendance.data;

import com.google.appengine.api.datastore.*;

import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShardedCounter
{
    private static final int NUM_SHARDS = 5;
    private final Random generator = new Random();

    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final Logger LOG = Logger.getLogger(ShardedCounter.class.getName());

    private String shardPrefix;

    public ShardedCounter(String shardPrefix)
    {
        this.shardPrefix = shardPrefix;
    }

    public void increment()
    {
        update(1L);
    }

    public void decrement()
    {
        update(-1L);
    }

    public long getCount()
    {
        long sum = 0;

        for (int i = 0; i < NUM_SHARDS; i++)
        {
            Key shardKey = KeyFactory.createKey("ShardedCounter", shardPrefix + i);

            try
            {
                sum += (Long) datastore.get(shardKey).getProperty("count");
            } catch (EntityNotFoundException e)
            {
                // ignore this
            }
        }

        return sum;
    }

    private void update(Long change)
    {
        Key shardKey = KeyFactory.createKey("ShardedCounter", shardPrefix + generator.nextInt(NUM_SHARDS));

        Transaction tx = datastore.beginTransaction();
        try
        {
            com.google.appengine.api.datastore.Entity shard;
            try
            {
                shard = datastore.get(tx, shardKey);
                long count = (Long) shard.getProperty("count");
                shard.setUnindexedProperty("count", count + change);
            } catch (EntityNotFoundException e)
            {
                shard = new com.google.appengine.api.datastore.Entity(shardKey);
                shard.setUnindexedProperty("count", change);
            }
            datastore.put(tx, shard);
            tx.commit();
        } catch (ConcurrentModificationException e)
        {
            LOG.log(Level.WARNING, "You may need more shards. Consider adding more shards.");
            LOG.log(Level.WARNING, e.toString(), e);
        } catch (Exception e)
        {
            LOG.log(Level.WARNING, e.toString(), e);
        } finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
        }
    }
}
