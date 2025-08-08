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
import raisetech.student.management.data.StudentAppStatus;
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
    List<StudentAppStatus> studentAppStatusList = new ArrayList<>();
    List<StudentDetail>studentDetails=new ArrayList<>();

    Mockito.when(repository.search()).thenReturn(studentList); //repositoryのsearchを呼び出したら、studentListを返すよって処理
    Mockito.when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    Mockito.when(repository.searchStatus()).thenReturn(studentAppStatusList);
    Mockito.when(converter.convertStudentDetails(studentList,studentCourseList)).thenReturn(studentDetails);

    List<StudentDetail> actual = sut.searchStudentList();

    verify(repository, times(1)).search(); //repositoryのsearchを1回ちゃんと呼び出せてるか確認
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
    verify(converter,times(1)).convertCourseStatus(studentDetails,studentAppStatusList);

    assertEquals(actual,studentDetails);
  }

  @Test  //searchStudent
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること(){

    int id = 2;
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(id);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentAppStatus studentAppStatus = new StudentAppStatus();

    Mockito.when(repository.searchStudent(id)).thenReturn(student);
    Mockito.when(repository.searchStudentCourse(id)).thenReturn(studentCourseList);
    Mockito.when(repository.searchStudentStatus(id)).thenReturn(studentAppStatus);

    StudentDetail expected = new StudentDetail(student,studentCourseList);
    StudentDetail actual =  sut.searchStudent(id);

    verify(repository,times(1)).searchStudent(id);
    verify(repository,times(1)).searchStudentCourse(id);
    verify(repository,times(1)).searchStudentStatus(id);

    assertEquals(expected , actual);
  }

  @Test  //registerStudent
  void 受講生詳細の登録_リポジトリの処理が適切に行えていること(){
    int id = 888;
    Student student = new Student();
    student.setId(id);
    StudentAppStatus studentAppStatus1 = new StudentAppStatus();
    StudentAppStatus studentAppStatus2 = new StudentAppStatus();
    StudentCourse course1 = new StudentCourse();
    course1.setStatus(studentAppStatus1);
    course1.setId(999);
    StudentCourse course2 = new StudentCourse();
    course2.setStatus(studentAppStatus2);
    course2.setId(777);
    List<StudentCourse> studentCourseList = List.of(course1,course2);

    StudentDetail studentDetail = new StudentDetail(student,studentCourseList);

    sut.registerStudent(studentDetail);

    verify(repository,times(1)).registerStudent(student);
    verify(repository,times(1)).registerStudentCourse(course1);
    verify(repository,times(1)).registerStudentCourse(course2);
    verify(repository,times(1)).registerStudentAppStatus(studentAppStatus1);
    verify(repository,times(1)).registerStudentAppStatus(studentAppStatus2);

  }
  @Test //initStudentsCourse
  void 受講生詳細の登録_初期化処理が行われること(){
    int id = 100;
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();

    sut.initStudentsCourse(studentCourse,student.getId());

    assertEquals(id , studentCourse.getStudentsId());
    assertEquals(LocalDateTime.now().getHour(),studentCourse.getStartDate().getHour());
    assertEquals(LocalDateTime.now().plusYears(1).getYear(),studentCourse.getEndDate().getYear());

  }

  @Test //linkCourseToStatus
  void コースIDが申し込み状況に正しく設定されること() {
    int id = 100;
    StudentAppStatus studentAppStatus = new StudentAppStatus();
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(id);

    sut.linkCourseToStatus(studentAppStatus, studentCourse.getId());

    assertEquals(id, studentAppStatus.getStudentCourseId());

  }

  @Test //updateStudent
  void 受講生詳細の更新_リポジトリの処理が適切に行えていること(){
    Student student = new Student();
    StudentCourse course1 = new StudentCourse();
    StudentAppStatus studentAppStatus = new StudentAppStatus();
    studentAppStatus.setStatus("本申し込み");
    course1.setId(99);
    course1.setStatus(studentAppStatus);
    StudentCourse course2 = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(course1,course2);

    StudentDetail studentDetail = new StudentDetail(student,studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository,times(1)).updateStudent(student);
    verify(repository,times(1)).updateStudentCourse(course1);
    verify(repository,times(1)).updateStudentCourse(course2);
    verify(repository,times(1)).updateStudentAppStatus(course1.getStatus());
    verify(repository,times(1)).updateStudentAppStatus(course2.getStatus());
  }
}