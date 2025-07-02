package raisetech.student.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.repository.StudentRepository;
import raisetech.student.management.repository.StudentsCoursesRepository;

@Service
public class StudentService {

  private StudentRepository repository;
  private StudentsCoursesRepository coursesRepository;

  @Autowired
  public StudentService(StudentRepository repository,
      StudentsCoursesRepository coursesRepository) {
    this.repository = repository;
    this.coursesRepository = coursesRepository;
  }

  public List<Student> searchStudentList() {
    //年齢が３０代の人のみを抽出する
    //抽出したリストをコントローラーに返す
    return repository.search().stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() <= 39)
        .toList();
  }

  public List<StudentsCourses> searchStudentCoursesList() {
    //絞り込み検索で「Javaコース」のコースのみを抽出する
    //抽出したリストをコントローラーに渡す。
    return coursesRepository.search().stream()
        .filter(studentsCourses -> studentsCourses.getCoursesName().equals("Javaコース"))
        .toList();
  }

}
