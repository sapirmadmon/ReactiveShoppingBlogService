package com.example.demo.rest;

import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.boundaries.PostBoundary;
import com.example.demo.logic.ShoppingBlogService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ShoppingBlogController {

	private ShoppingBlogService shoppingBlogService;

	@Autowired
	public void setShoppingBlogService(ShoppingBlogService shoppingBlogService) {
		this.shoppingBlogService = shoppingBlogService;
	}
	
	
	@RequestMapping(
			path="/blog",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<PostBoundary> createMessage (@RequestBody PostBoundary post){
		return this.shoppingBlogService.createPost(post);
	}
	

//	@RequestMapping(path="/blog/byUser/{email}",
//			method = RequestMethod.GET,
//			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	public Flux<PostBoundary> getPostsByEmail (
//			@PathVariable("email") String email,
//			@RequestParam(name="sortBy", required = false, defaultValue = "id") String sortBy,
//			@RequestParam(name="order", required = false, defaultValue = "ASC") OrderEnum order){
//		return this.messageService
//			.getAll(name, sortBy, order.equals(OrderEnum.ASC));
//	}
//	
	
}
