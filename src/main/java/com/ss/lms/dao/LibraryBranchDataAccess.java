package com.ss.lms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ss.lms.entity.LibraryBranch;

@Component
public interface LibraryBranchDataAccess extends CrudRepository<LibraryBranch, Integer> {
	
//	LibraryBranch findById(int branchId);
//	
//	ArrayList<LibraryBranch> findAll();
//	
//	void update(LibraryBranch libraryBranch);
//	
}
