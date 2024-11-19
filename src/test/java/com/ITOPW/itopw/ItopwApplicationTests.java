package com.ITOPW.itopw;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.springframework.mail.javamail.JavaMailSender;


@SpringBootTest
class ItopwApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void contextLoads() {
		// 기존의 컨텍스트 로드 테스트
	}

	@Test
	void testDataSourceConnection() {
		try (Connection connection = dataSource.getConnection()) {
			assertNotNull(connection);
			System.out.println("연결 성공! 데이터베이스에 연결되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("연결 실패: " + e.getMessage());
		}
	}
	@Autowired
	private JavaMailSender mailSender;
	@Test
	void sendEmail() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("xoyeon9612@gmail.com");
		message.setSubject("작업 마감일 안내");
		message.setText("dd" + " 작업이 마감 하루 남았습니다.");

		try {
			mailSender.send(message);
		} catch (Exception e) {
			System.out.println("메일 전송 실패: " + e.getMessage());
		}
	}



}
