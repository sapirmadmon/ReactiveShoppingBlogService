package com.example.demo.dal;

import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.example.demo.data.PostEntity;

import reactor.core.publisher.Flux;

public interface PostDao extends ReactiveSortingRepository<PostEntity, String>{

	public Flux<PostEntity> findAllByUser_Email(
			@Param("email") String email, 
			Sort sort);

	public Flux<PostEntity> findAllByUser_EmailAndLanguage(
			@Param("email") String email, 
			@Param("language") String language,
			Sort sort);

	public Flux<PostEntity> findAllByUser_EmailAndPostingTimestampGreaterThanEqual(
			@Param("email") String email, 
			@Param("postingTimestamp") Date date, Sort by);

	public Flux<PostEntity> findAllByUser_EmailAndProduct_Id(
			@Param("email") String email, 
			@Param("id") String id,
			Sort by);
	
	
}
