package com.example.demo.validator;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.example.demo.utility.TimeEnum;

@Component
public class Validator {

	public Date validDate(String value) {
		TimeEnum timeEnum = TimeEnum.valueOf(value);
		switch (timeEnum) {
		case lastDay:
			return new Date(System.currentTimeMillis() - (TimeUnit.DAYS.toMillis(1))); // day before

		case lastWeek:
			return new Date(System.currentTimeMillis() - (TimeUnit.DAYS.toMillis(7)));// week before

		case lastMonth:
			return new Date(System.currentTimeMillis() - (TimeUnit.DAYS.toMillis(30))); // month before

		default:
			return null;
		}
	}
}
