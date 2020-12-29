package com.example.demo.logic;

import com.example.demo.boundaries.PostBoundary;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ShoppingBlogService {

	public Mono<PostBoundary> createPost(PostBoundary post);

	public Flux<PostBoundary> getAll(String email, String sortAttr, boolean sortOrder);

	public Flux<PostBoundary> getByLanguage(String email, String filterValue, String sortAttr, boolean sortOrder);

	public Flux<PostBoundary> getByCreation(String email, String filterValue, String sortAttr, boolean sortOrder);

	public Flux<PostBoundary> getByProduct(String email, String filterValue, String sortAttr, boolean sortOrder);

	public Flux<PostBoundary> getPostsByLanguage(String productId, String filterValue, String sortAttr, boolean sortOrder);

	public Flux<PostBoundary> getPostsByCreation(String productId, String filterValue, String sortAttr, boolean sortOrder);

	public Flux<PostBoundary> getAllPosts(String productId, String sortAttr, boolean sortOrder);

	public Flux<PostBoundary> getAllPostsByCreation(String filterValue, String sortAttr, boolean sortOrder);

	public Flux<PostBoundary> getAllPostsByCount(String filterValue, String sortAttr, boolean sortOrder);

	public Mono<Void> deleteAll();

}
