package com.example.demo.data.converter;

import org.springframework.stereotype.Component;

import com.example.demo.boundaries.PostBoundary;
import com.example.demo.boundaries.ProductBoundary;
import com.example.demo.boundaries.UserBoundary;
import com.example.demo.data.PostEntity;
import com.example.demo.data.ProductEntity;
import com.example.demo.data.UserEntity;

@Component
public class PostConverter {

	public PostBoundary fromEntity(PostEntity entity) {
		PostBoundary pb = new PostBoundary();
		pb.setUser(fromEntityUser(entity.getUser()));
		pb.setLanguage(entity.getLanguage());
		pb.setProduct(fromEntityProduct(entity.getProduct()));
		pb.setPostingTimestamp(entity.getPostingTimestamp());
		pb.setPostContent(entity.getPostContent());
		return pb;
	}

	public PostEntity toEntity(PostBoundary boundary) {
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

	private UserEntity toEntityUser(UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		entity.setEmail(boundary.getEmail());
		return entity;
	}
}
