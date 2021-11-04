package com.example.domain;

import java.util.Date;

import lombok.Data;

@Data
public class SearchVO {
	
	private String keyword;
	private int count;
	private Date regDate;
	
}
