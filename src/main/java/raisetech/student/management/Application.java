package raisetech.student.management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private StudentRepository repository;
	@Autowired
	private StudentsCoursesRepository repository1;


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentList") //学生情報を取得
	public List<Student> getStudentList() {
		return repository.search();
	}
	@GetMapping("/studentscoursesList") //学生情報を取得
	public List<StudentsCourses> getStudent_coursesList() {
		return repository1.search();
	}

}


//GET 取得する、リクエストの結果を受け取る
//POST 情報を与える、渡す