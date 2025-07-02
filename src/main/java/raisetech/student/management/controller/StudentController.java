package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentList") //学生情報を取得
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/studentscoursesList") //学生情報を取得
  public List<StudentsCourses> getStudentCoursesList() {
    return service.searchStudentCoursesList();
  }

}
