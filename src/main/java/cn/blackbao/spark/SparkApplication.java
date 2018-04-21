package cn.blackbao.spark;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@ServletComponentScan
@SpringBootApplication
public class SparkApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SparkApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
//		SpringApplication.run(SparkApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		builder.sources(this.getClass());
		return super.configure(builder);
	}
	

}
