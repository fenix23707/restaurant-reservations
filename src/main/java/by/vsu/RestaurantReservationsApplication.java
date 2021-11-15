package by.vsu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("by.vsu.dao")
public class RestaurantReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantReservationsApplication.class, args);
	}

}
