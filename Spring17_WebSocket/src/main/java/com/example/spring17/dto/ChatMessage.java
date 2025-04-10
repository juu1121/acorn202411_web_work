package com.example.spring17.dto;

import lombok.Data;

@Data
public class ChatMessage {
	public String userName;
	public String text;
	public String toUserName;
}
