package com.example.spring10.repository;

import java.util.List;

import com.example.spring10.dto.FileDto;

public interface FileDao {
	public List<FileDto> getList();
	public int insert(FileDto dto);
	public FileDto getData(long num);
}
