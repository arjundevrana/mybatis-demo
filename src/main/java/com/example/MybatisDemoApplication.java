package com.example;


import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class MybatisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisDemoApplication.class, args);

	}
	@Bean
	CommandLineRunner demo(UserMapper userMapper){
		return args->{
			List<User> users = Arrays.asList(new User("Arjun Singh" , "delhi",007L,1991),
			new User("Gagan Deep" , "delhi",8l,1997),
					new User("Amit" , "delhi",9l,1990)

			);
			System.out.print("------------------All users-----------------------");
			users.forEach(user -> {
				userMapper.insert(user);
				System.out.println(user.toString());
			});
			userMapper.findAll().forEach(System.out::println);
			System.out.print("------------------Search result-------------------");
			userMapper.search(0L,"Arjun Singh",null,0).forEach(System.out::println);

		};
	}

}
@Mapper
interface UserMapper{
	@Options(useGeneratedKeys = true)
	@Insert("insert into user(name,address,id,dob) values (#{name},#{address},#{id},#{dob})")
	void insert(User user);
	@Select("select * from USER")
	Collection<User> findAll();
	@Delete("delete from USER where id = #{id}")
	void deleteById(long id);
	Collection<User> search( @Param("id") long id,@Param("name") String name,
							@Param("address") String address,@Param("dob") int dob
							);
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
	private String name,address;
	private Long id;
	private int dob;
}
