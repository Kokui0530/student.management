package raisetech.student.management;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "受講生管理システム" ,
description = "受講生情報と、受講生コース情報を管理するシステムです"))
@SpringBootApplication
public class Application {


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
