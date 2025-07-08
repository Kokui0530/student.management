package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
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


  @Transactional //登録、更新、削除する項目につける、必須！　基本はServiceでつける。
  //Transactional -> 複数の処理を一つのまとまりとして扱う仕組みのこと
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    //複数の可能性がある為、ループさせて該当するコース分登録させる
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      //自動採番されたIDをstudentsCoursesのIDにセット
      studentsCourses.setStudentsId(studentDetail.getStudent().getId());
      //日時を自動登録させる
      studentsCourses.setStart(LocalDateTime.now());
      studentsCourses.setEnd(LocalDateTime.now().plusYears(1));

      repository.registerStudentsCourses(studentsCourses);
    }


  }
}


