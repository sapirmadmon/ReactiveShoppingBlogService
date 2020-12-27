package com.example.demo.logic;

import com.example.demo.boundaries.PostBoundary;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ShoppingBlogService {

	public Mono<PostBoundary> createPost (PostBoundary post);

	public Flux<PostBoundary> getAll(String email, String sortBy, boolean sortOrder);

	public Flux<PostBoundary> getByLanguage(String email, String value, String sortBy, boolean sortOrder);

	public Flux<PostBoundary> getByCreation(String email, String value, String sortBy, boolean sortOrder);

	public Flux<PostBoundary> getByProduct(String email, String value, String sortBy, boolean sortOrder);

}
