package com.example.todoapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;


@Configuration
@EnableCouchbaseRepositories(basePackages = "com.example.todoapp")
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {


    @Override
    public String getConnectionString() {
        return "couchbase";
    }

    @Override
    public String getUserName() {
        return "Administrator";
    }

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getBucketName() {
        return "todo";
    }


}