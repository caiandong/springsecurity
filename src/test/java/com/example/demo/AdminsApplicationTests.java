package com.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminsApplicationTests {
	
	void RecursionGet(Consumer<String> C,String dir){
		if(dir==null||dir.isEmpty()) 
			return;
		File file = new File(dir);
		for(File childfile:file.listFiles()) {
			String filename=dir+"/"+childfile.getName();
			if(childfile.isFile())
				C.accept(filename);
			else
				RecursionGet(C, filename);
		}	
	}

	@Test
	public void contextLoads() {
		List<String> list = new ArrayList<String>();
		RecursionGet(string->System.out.println(list), "/home/kmp/home");
		//System.out.println(list);
	}

}
