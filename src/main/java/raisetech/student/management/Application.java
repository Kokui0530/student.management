package raisetech.student.management;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	Map<String , String>studentMap = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
//これはmap-feature ブランチ
	
	@GetMapping("/studentInfo") //学生情報を取得
	public  String getStudentInfo(){
		String name = studentMap.get("name");
		String age = studentMap.get("age");

		return name + " " + age + "歳";
	}
	@PostMapping("/studentInfo") //学生情報を登録
	public String setStudentInfo(@RequestParam String name,@RequestParam String age){
		studentMap.put("name" , name);
		studentMap.put("age" , age);
		return "登録完了";
	}

	@PostMapping("/studentName") //学生の名前だけ登録
	public String updateStudentName(@RequestParam String name){
		studentMap.put("name" , name);
		return "更新完了";
	}

}
//GET 取得する、リクエストの結果を受け取る
//POST 情報を与える、渡す