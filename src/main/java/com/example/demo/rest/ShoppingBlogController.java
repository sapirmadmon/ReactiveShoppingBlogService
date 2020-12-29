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
	public Flux<PostBoundary> getPostsByUser(@PathVariable("email") String email,
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

	@RequestMapping(path = "/blog/byProduct/{productId}", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<PostBoundary> getPostsByProduct(@PathVariable("productId") String productId,
			@RequestParam(name = "filterType", required = false, defaultValue = "") FilterType type,
			@RequestParam(name = "filterValue", required = false) String value,
			@RequestParam(name = "sortBy", required = false, defaultValue = "postingTimestamp") String sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") OrderEnum order) {

		switch (type.name()) {
		case "byLanguage":
			return this.shoppingBlogService.getPostsByLanguage(productId, value, sortBy, order.equals(OrderEnum.ASC));

		case "byCreation":
			return this.shoppingBlogService.getPostsByCreation(productId, value, sortBy, order.equals(OrderEnum.ASC));

		default:
			return this.shoppingBlogService.getAllPosts(productId, sortBy, order.equals(OrderEnum.ASC));
		}
	}
	
	@RequestMapping(path="/blog", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<PostBoundary> getAllPosts(
			@RequestParam(name = "filterType", required = false) String filterType,
			@RequestParam(name = "filterValue", required = false) String filterValue,
			@RequestParam(name = "sortBy", required = false, defaultValue = "postingTimestamp") String sortAttr,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") OrderEnum order
			){
		switch (filterType) {
		case ControllerTypes.BY_CREATION:
			return this.shoppingBlogService.getAllPostsByCreation(filterValue, sortAttr, order.equals(OrderEnum.ASC));
			
		case ControllerTypes.BY_COUNT:
			return this.shoppingBlogService.getAllPostsByCount(filterValue, "postingTimestamp", order.equals(OrderEnum.DESC));

		default:	
			return null;
		}
		
	}
	
	@RequestMapping(path="/blog", method = RequestMethod.DELETE)
	public Mono<Void> deleteAll(){
		return this.shoppingBlogService.deleteAll();
		
	}
}
