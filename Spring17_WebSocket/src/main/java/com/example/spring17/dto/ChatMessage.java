package com.example.spring17.dto;

import lombok.Data;

@Data
public class ChatMessage {
	private String userName;
	private String text;
	private String toUserName;
	private String saveFileName;
}
