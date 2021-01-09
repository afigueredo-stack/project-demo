package com.afigueredo.demo;

import java.util.List;

import com.afigueredo.demo.api.entities.Produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String sql = "SELECT * FROM produto";

		List<Produto> produtos = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Produto.class));

		System.out.println("## Conexao no Oracle realizada com sucesso ##");
		System.out.println("## Listando produtos... ##");
		produtos.forEach(System.out::println);

	}

}
