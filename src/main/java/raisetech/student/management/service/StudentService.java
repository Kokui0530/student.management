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
    return repository.search();
  }

  public List<StudentsCourses> searchStudentCoursesList() {
    return coursesRepository.search();
  }


  //データを挿入
  public void insertStudents (Student student){
    repository.insert(student);
  }
}


