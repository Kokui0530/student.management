package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
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
  void 受講生のリストと受講生コース情報のリストを渡して受講生詳細のリストが作成出来る事() {
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

}