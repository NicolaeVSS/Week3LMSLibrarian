package com.ss.lms;

import static org.hamcrest.CoreMatchers.any;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ss.lms.dao.LibraryBranchDataAccess;
import com.ss.lms.entity.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class LmsApplicationTests {

	@MockBean
	private LibraryBranchDataAccess libraryBranchDao = Mockito.mock(LibraryBranchDataAccess.class);

	@Test
	public void contextLoads() {
	}
	
//	@Test
//	public void testReadBranch() {
//		LibraryBranch libraryBranch = new LibraryBranch(1, "TestName", "TestAddress");
//		when(libraryBranchDao.save(any(LibraryBranch.class))).then(returnFirstArg());
//		
//	}

}
