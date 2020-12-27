package com.example.demo.logic;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.boundaries.PostBoundary;
import com.example.demo.dal.PostDao;
import com.example.demo.data.converter.PostConverter;
import com.example.demo.utility.TimeEnum;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ShoppingBlogServiceWithDb implements ShoppingBlogService {

	private PostDao posts;
	private PostConverter converter;
	
	
	@Autowired
	public ShoppingBlogServiceWithDb(PostDao posts) {
		super();
		this.posts = posts;
	}

	
	@Autowired
	public void setConverter(PostConverter converter) {
		this.converter = converter;
	}



	@Override
	public Mono<PostBoundary> createPost(PostBoundary post) {
		
	
		return Mono.just(post)
				.map(boundary->{
					boundary.setPostingTimestamp(new Date());
					return boundary;
				})
				.map(boundary -> this.converter.toEntity(boundary))
				.flatMap(entity -> this.posts.save(entity))
				.map(this.converter::fromEntity);
	
	}
	
	
	
	@Override
	public Flux<PostBoundary> getAll(String email, String sortBy, boolean sortOrder) {

		return this.posts.findAllByUser_Email(email, Sort.by(sortOrder?Direction.ASC:Direction.DESC, sortBy))
				.map(this.converter::fromEntity);
	}


	@Override
	public Flux<PostBoundary> getByLanguage(String email, String value, String sortBy, boolean sortOrder) {
		
		return this.posts.findAllByUser_EmailAndLanguage(email, value, Sort.by(sortOrder?Direction.ASC:Direction.DESC, sortBy))
				.map(this.converter::fromEntity);
	}


	@Override
	public Flux<PostBoundary> getByCreation(String email, String value, String sortBy, boolean sortOrder) {

		Date date; 
		
		if(value.equalsIgnoreCase(TimeEnum.lastDay.name())) {
			System.err.println(value);
			date = new Date(System.currentTimeMillis() - (24*60*60*1000)); //24 hours before
		}
		
		else if(value.equalsIgnoreCase(TimeEnum.lastWeek.name())) {
			System.err.println(value);
			date = new Date(System.currentTimeMillis() - (7*24*60*60*1000)); 
		}
		
		else {
			System.err.println(value);
			date = new Date(System.currentTimeMillis() - (30*24*60*60*1000)); 
		}
		
		return this.posts.findAllByUser_EmailAndPostingTimestampGreaterThanEqual(email, date, Sort.by(sortOrder?Direction.ASC:Direction.DESC, sortBy))
				.map(this.converter::fromEntity);
	}


	@Override
	public Flux<PostBoundary> getByProduct(String email, String value, String sortBy, boolean sortOrder) {
		return this.posts.findAllByUser_EmailAndProduct_Id(email, value, Sort.by(sortOrder?Direction.ASC:Direction.DESC, sortBy))
				.map(this.converter::fromEntity);
	}
	

	



}
