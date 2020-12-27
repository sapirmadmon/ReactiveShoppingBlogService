package com.example.demo.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.boundaries.PostBoundary;
import com.example.demo.boundaries.ProductBoundary;
import com.example.demo.boundaries.UserBoundary;
import com.example.demo.dal.PostDao;
import com.example.demo.data.PostEntity;
import com.example.demo.data.ProductEntity;
import com.example.demo.data.UserEntity;
import com.example.demo.data.converter.PostConverter;

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


	@Override
	public Mono<PostBoundary> createPost(PostBoundary post) {
		
	
		return Mono.just(post)
				.map(boundary -> this.toEntity(boundary))
				.flatMap(entity -> this.posts.save(entity))
				.map(this::fromEntity);
		/*	return Mono.just(post) // Mono<PostBoundary>
				
				.map(boundary->{
					boundary.setCreationTimestamp(new Date());
					return boundary;
				}) // Mono<MessageBoundary>
				
				.map(boundary->this.toEntity(boundary)) // Mono<PostBoundary>
				
				.map(entity->
					this.posts
					.save(entity)) // Mono<PostBoundary>
				
				.map(this::fromEntity); // Mono<PostBoundary>*/
	}
	
	
	private PostBoundary fromEntity (PostEntity entity) {
		PostBoundary pb = new PostBoundary();
		pb.setUser(fromEntityUser(entity.getUser()));
		pb.setLanguage(entity.getLanguage());
		pb.setProduct(fromEntityProduct(entity.getProduct()));
		pb.setPostingTimestamp(entity.getPostingTimestamp());
		pb.setPostContent(entity.getPostContent());
		return pb;
	}

	private  PostEntity toEntity (PostBoundary boundary) {
		PostEntity entity = new PostEntity();
		entity.setUser(toEntityUser(boundary.getUser()));
		entity.setLanguage(boundary.getLanguage());
		entity.setProduct(toEntityProduct(boundary.getProduct()));
		entity.setPostingTimestamp(boundary.getPostingTimestamp());
		entity.setPostContent(boundary.getPostContent());
		
		return entity;
	}
	
	private ProductBoundary fromEntityProduct(ProductEntity entity) {
		ProductBoundary boundary = new ProductBoundary();
		boundary.setId(entity.getId());
		return boundary;
	}
	
	private ProductEntity toEntityProduct(ProductBoundary boundary) {
		ProductEntity entity = new ProductEntity();
		entity.setId(boundary.getId());
		return entity;

	}
	
	private UserBoundary fromEntityUser(UserEntity entity) {
		UserBoundary boundary = new UserBoundary();
		boundary.setEmail(entity.getEmail());
		return boundary;
	}
	
	private UserEntity toEntityUser (UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		entity.setEmail(boundary.getEmail());
		return entity;
	}

}
