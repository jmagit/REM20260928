package com.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemosApplicationTests {

	@Test
//	@Tag("smoke")
	@Smoke
	void contextLoads() {
	}

}
