package com.bdqn.spz.spring.redis.test;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bdqn.spz.spring.redis.redis.ShardedJedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class Test {

    public void ShardedJedisClientTest() {
        ShardedJedisClient shardedJedisClient = new ShardedJedisClient();
        for (int i = 0; i < 100; i++) {
            System.out.println(shardedJedisClient.set("name" + i, "spz" + i));
            System.out.println(shardedJedisClient.get("name" + i));
        }
    }

    public void JedisPoolTest() {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-redis.xml");
        JedisPool pool = context.getBean("jedisPool", JedisPool.class);
        Jedis jedis = pool.getResource();
        for (int i = 0; i < 100; i++) {
            System.out.println(jedis.set("name" + i, "spz" + i));
            System.out.println(jedis.get("name" + i));
        }
    }

    public void JedisClusterTest() throws IOException {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-redis.xml");
        JedisCluster jedisCluster=null;
        try {
             jedisCluster = context.getBean("jedisCluster", JedisCluster.class);
            for (int i = 0; i < 100; i++) {
                System.out.println(jedisCluster.set("name" + i, "spz" + i));
                System.out.println(jedisCluster.get("name" + i));
            }
        }catch (Exception e) {
           e.printStackTrace();
        } finally {
            jedisCluster.close();
        }
    }

    public static void main(String[] args) throws IOException {

        new Test().JedisClusterTest();
    }
}
