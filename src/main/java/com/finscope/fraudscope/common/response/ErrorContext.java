package com.finscope.fraudscope.common.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorContext<T> {
	private String path;

	private Date createTime;

	private String hostName;

	private T message;

}
