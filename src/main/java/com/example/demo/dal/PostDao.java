package com.example.demo.dal;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.example.demo.data.PostEntity;



public interface PostDao extends ReactiveSortingRepository<PostEntity, String>{

}
