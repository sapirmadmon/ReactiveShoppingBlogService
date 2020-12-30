package com.example.demo.logic;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.boundaries.PostBoundary;
import com.example.demo.dal.PostDao;
import com.example.demo.data.converter.PostConverter;
import com.example.demo.exceptions.BadTypeDateFormatException;
import com.example.demo.exceptions.BadTypeFilterValueException;
import com.example.demo.validator.Validator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ShoppingBlogServiceWithDb implements ShoppingBlogService {

	private PostDao posts;
	private PostConverter converter;
	private Validator validator;

	@Autowired
	public ShoppingBlogServiceWithDb(PostDao posts) {
		super();
		this.posts = posts;
	}

	@Autowired
	public void setConverter(PostConverter converter) {
		this.converter = converter;
	}

	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	public Mono<PostBoundary> createPost(PostBoundary post) {

		return Mono.just(post).map(boundary -> {
			
			boundary.setPostingTimestamp(new Date());
			return boundary;
		}).map(boundary -> this.converter.toEntity(boundary)).flatMap(entity -> this.posts.save(entity))
				.map(this.converter::fromEntity);

	}

	@Override
	public Flux<PostBoundary> getAll(String email, String sortBy, boolean sortOrder) {

		return this.posts.findAllByUser_Email(email, Sort.by(sortOrder ? Direction.ASC : Direction.DESC, sortBy))
				.map(this.converter::fromEntity);
	}

	@Override
	public Flux<PostBoundary> getByLanguage(String email, String value, String sortBy, boolean sortOrder) {

		return this.posts.findAllByUser_EmailAndLanguage(email, value,
				Sort.by(sortOrder ? Direction.ASC : Direction.DESC, sortBy)).map(this.converter::fromEntity);
	}

	@Override
	public Flux<PostBoundary> getByCreation(String email, String value, String sortBy, boolean sortOrder) {
		Date date = this.validator.validDate(value);
		if (date == null) {
			throw new BadTypeDateFormatException("Date type should be 'lastDay'/'lastWeek'/'lastMonth'");
		}

		return this.posts.findAllByUser_EmailAndPostingTimestampGreaterThanEqual(email, date,
				Sort.by(sortOrder ? Direction.ASC : Direction.DESC, sortBy)).map(this.converter::fromEntity);
	}

	@Override
	public Flux<PostBoundary> getByProduct(String email, String value, String sortBy, boolean sortOrder) {
		return this.posts.findAllByUser_EmailAndProduct_Id(email, value,
				Sort.by(sortOrder ? Direction.ASC : Direction.DESC, sortBy)).map(this.converter::fromEntity);
	}

	@Override
	public Flux<PostBoundary> getPostsByLanguage(String productId, String value, String sortBy, boolean sortOrder) {
		return this.posts.findAllByProduct_IdAndLanguage(productId, value,
				Sort.by(sortOrder ? Direction.ASC : Direction.DESC, sortBy)).map(this.converter::fromEntity);
	}

	@Override
	public Flux<PostBoundary> getPostsByCreation(String productId, String value, String sortBy, boolean sortOrder) {
		Date date = this.validator.validDate(value);
		if (date == null) {
			throw new BadTypeDateFormatException("Date type should be 'lastDay'/'lastWeek'/'lastMonth'");
		}

		return this.posts.findAllByProduct_IdAndPostingTimestampGreaterThanEqual(productId, date,
				Sort.by(sortOrder ? Direction.ASC : Direction.DESC, sortBy)).map(this.converter::fromEntity);
	}

	@Override
	public Flux<PostBoundary> getAllPosts(String productId, String sortBy, boolean sortOrder) {
		return this.posts.findAllByProduct_Id(productId, Sort.by(sortOrder ? Direction.ASC : Direction.DESC, sortBy))
				.map(this.converter::fromEntity);
	}

	@Override
	public Flux<PostBoundary> getAllPostsByCreation(String filterValue, String sortAttr, boolean sortOrder) {
		Date date = this.validator.validDate(filterValue);
		if (date == null) {
			throw new BadTypeDateFormatException("Date type should be 'lastDay'/'lastWeek'/'lastMonth'");
		}

		return this.posts.findAllByPostingTimestampGreaterThanEqual(date,
				Sort.by(sortOrder ? Direction.ASC : Direction.DESC, sortAttr)).map(this.converter::fromEntity);
	}

	@Override
	public Flux<PostBoundary> getAllPostsByCount(String filterValue, String sortAttr, boolean sortOrder) {
		if(!((filterValue != null) && (filterValue.matches("\\d*")))) {
			throw new BadTypeFilterValueException("Filter value should be only digits");
		}
		return this.posts.findAll(Sort.by(Direction.DESC, sortAttr)).limitRequest(Long.parseLong(filterValue))
				.map(this.converter::fromEntity);
	}

	@Override
	public Mono<Void> deleteAll() {
		return this.posts.deleteAll();
	}

}
