package raisetech.student.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void 事前準備() {
    sut = new StudentService(repository, converter);
    //テストメソッドの都度新しく作ってくれる、共通してやりたいこと
    //StudentService sut = new StudentService(repository,converter);　を毎回書かなくてもよくなる！
  }

  @Test  //searchStudentList
  void 受講生詳細の一覧検索_リポジトリとコンバータの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>(); //空のstudentListを生成
    List<StudentCourse> studentCourseList = new ArrayList<>();  //空のstudentCourseListを生成
    Mockito.when(repository.search()).thenReturn(studentList); //repositoryのsearchを呼び出したら、studentListを返すよって処理
    Mockito.when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    //repositoryのsearchStudentCourseListを呼び出したら、studentCourseListを返すよって処理

    sut.searchStudentList();

    verify(repository, times(1)).search(); //repositoryのsearchを1回ちゃんと呼び出せてるか確認
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test  //searchStudent
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること(){

    String id = "2";
    Student student = new Student();
    student.setId(id);
    List<StudentCourse> studentCourse = new ArrayList<>();

    Mockito.when(repository.searchStudent(id)).thenReturn(student);
    Mockito.when(repository.searchStudentCourse(id)).thenReturn(studentCourse);

    StudentDetail expected = new StudentDetail(student,studentCourse);
    StudentDetail actual =  sut.searchStudent(id);

    verify(repository,times(1)).searchStudent(id);
    verify(repository,times(1)).searchStudentCourse(id);

    assertEquals(expected , actual);
  }

  @Test  //registerStudent
  void 受講生詳細の登録_リポジトリの処理が適切に行えていること(){
    Student student = new Student();
    StudentCourse course1 = new StudentCourse(); //for文でループしている為、複数回テストさせる為コースを２つ
    StudentCourse course2 = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(course1,course2);

    StudentDetail studentDetail = new StudentDetail(student,studentCourseList);

    sut.registerStudent(studentDetail);

    verify(repository,times(1)).registerStudent(student);
    verify(repository,times(1)).registerStudentCourse(course1);

  }
  @Test //initStudentsCourse
  void 受講生詳細の登録_初期化処理が行われること(){
    String id = "100";
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();

    sut.initStudentsCourse(studentCourse,student.getId());

    assertEquals(id , studentCourse.getStudentsId());
    assertEquals(LocalDateTime.now().getHour(),studentCourse.getStartDate().getHour());
    assertEquals(LocalDateTime.now().plusYears(1).getYear(),studentCourse.getEndDate().getYear());

  }

  @Test //updateStudent
  void 受講生詳細の更新_リポジトリの処理が適切に行えていること(){
    Student student = new Student();
    StudentCourse course = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(course);

    StudentDetail studentDetail = new StudentDetail(student,studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository,times(1)).updateStudent(student);
    verify(repository,times(1)).updateStudentCourse(course);

  }
}