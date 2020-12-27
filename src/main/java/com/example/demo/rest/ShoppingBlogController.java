package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.boundaries.PostBoundary;
import com.example.demo.data.OrderEnum;
import com.example.demo.logic.ShoppingBlogService;
import com.example.demo.utility.ControllerTypes;
import com.example.demo.utility.TimeEnum;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ShoppingBlogController {

	private ShoppingBlogService shoppingBlogService;

	@Autowired
	public void setShoppingBlogService(ShoppingBlogService shoppingBlogService) {
		this.shoppingBlogService = shoppingBlogService;
	}

	@RequestMapping(path = "/blog", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<PostBoundary> createMessage(@RequestBody PostBoundary post) {
		return this.shoppingBlogService.createPost(post);
	}

//	@RequestMapping(path = "/blog/byUser/{email}", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	public Flux<PostBoundary> getPostsByEmail(@PathVariable("email") String email,
//			@RequestParam(name = "sortBy", required = false, defaultValue = "postingTimestamp") String sortBy,
//			@RequestParam(name = "order", required = false, defaultValue = "ASC") OrderEnum order) {
//		return this.shoppingBlogService.getAll(email, sortBy, order.equals(OrderEnum.ASC));
//	}

	@RequestMapping(path = "/blog/byUser/{email}", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<PostBoundary> getPosts(@PathVariable("email") String email,
			@RequestParam(name = "filterType", required = false, defaultValue = "") String type,
			@RequestParam(name = "filterValue", required = false) String value,
			@RequestParam(name = "sortBy", required = false, defaultValue = "postingTimestamp") String sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") OrderEnum order) {

		
		switch (type) {
		case ControllerTypes.BY_LANGUAGE:
			
			return this.shoppingBlogService.getByLanguage(email, value, sortBy, order.equals(OrderEnum.ASC));
			
		case ControllerTypes.BY_CREATION:
			return this.shoppingBlogService.getByCreation(email, value, sortBy, order.equals(OrderEnum.ASC));
		
		case ControllerTypes.BY_PRODUCT:
			return this.shoppingBlogService.getByProduct(email, value, sortBy, order.equals(OrderEnum.ASC));
			
		default:
			return this.shoppingBlogService.getAll(email, sortBy, order.equals(OrderEnum.ASC));
		}
	}

}
