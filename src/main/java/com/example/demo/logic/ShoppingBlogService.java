package com.example.demo.logic;

import com.example.demo.boundaries.PostBoundary;


import reactor.core.publisher.Mono;

public interface ShoppingBlogService {

	public Mono<PostBoundary> createPost (PostBoundary post);

}
