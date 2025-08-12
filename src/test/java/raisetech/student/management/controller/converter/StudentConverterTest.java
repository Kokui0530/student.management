package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentAppStatus;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentInfo;
import raisetech.student.management.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before(){

    sut = new  StudentConverter();
  }

  @Test
  void StudentIdが一致する受講生詳細とコース情報が取れてくること(){
    int id = 999;

    Student student = new Student();
    student.setId(id);
    List<Student> studentList = List.of(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentsId(id);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourseList);

    List<StudentDetail> expected = new ArrayList<>();
    expected.add(studentDetail);

    //実行
    List<StudentDetail> actual = sut.convertStudentDetails(studentList,studentCourseList);

    //検証
    assertEquals(actual , expected);

  }

  @Test
  void コースIDに紐づく申し込み情報が取れてくること(){
    int id = 999;
    StudentAppStatus status = new StudentAppStatus();
    status.setStudentCourseId(id);
    List<StudentAppStatus> studentAppStatusList = List.of(status);

    Student student = new Student();
    student.setId(id);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentsId(id);
    studentCourse.setId(id);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourseList);

    List<StudentDetail> studentDetails = new ArrayList<>();
    studentDetails.add(studentDetail);

    //実行
    List<StudentInfo> actual = sut.convertStudentInfo(studentDetails,studentAppStatusList);

    //statusが同じか確認
    StudentAppStatus result = actual.get(0).getStudentAppStatus();
    assertEquals(status,result);

  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡して受講生詳細のリストが作成出来る事() {
    Student student = createStudent();

    StudentAppStatus studentAppStatus = new StudentAppStatus();
    studentAppStatus.setId(1);
    studentAppStatus.setStudentCourseId(1);
    studentAppStatus.setStatus("本申し込み");

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentsId(1);
    studentCourse.setCoursesName("Javaコース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList,studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡した時に紐づかない受講生コース情報は除外される事() {
    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentsId(2);
    studentCourse.setCoursesName("Javaコース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList,studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEmpty(); //studentIdが紐づかない場合はコースは空
  }

  @Test
  void 受講生コース情報を渡した時に紐づかない申し込み情報はNullになる事() {
    Student student = createStudent();

    StudentAppStatus studentAppStatus = new StudentAppStatus();
    studentAppStatus.setId(999);
    studentAppStatus.setStudentCourseId(989);
    studentAppStatus.setStatus("本申し込み");

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentsId(1);
    studentCourse.setCoursesName("Javaコース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));

    List<StudentAppStatus> studentAppStatusList = List.of(studentAppStatus);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student,studentCourseList);
    List<StudentDetail> studentDetails = List.of(studentDetail);

    List<StudentInfo> actual = sut.convertStudentInfo(studentDetails,studentAppStatusList);

    assertThat(actual.get(0).getStudentCourse().getId()).isEqualTo(studentCourse.getId());
    assertThat(actual.get(0).getStudentAppStatus()).isNull();//コースIdが紐づかない場合はstatusはnull
  }



  private Student createStudent() {
    Student student = new Student();
    student.setId(1);
    student.setName("林田耕太");
    student.setFurigana("ハヤシダコウタ");
    student.setNickname("はやしだ");
    student.setMail("hayasida@gmail.com");
    student.setRegion("草加市");
    student.setAge(41);
    student.setGender("男");
    student.setRemark("");
    student.setDeleted(false);
    return student;
  }
}