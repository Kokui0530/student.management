package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test  //search
  void 受講生の全件検索が行えること(){
    List<Student> actual = sut.search();
   assertThat(actual.size()).isEqualTo(5); //5件データが取れてくる
  }

  @Test  //searchStudent
  void IDに紐づく受講生詳細の検索(){
    Student actual = sut.searchStudent("1");
    assertThat(actual.getId()).isEqualTo("1"); //IDが同じ受講生が取れてくる
  }

  @Test //searchStudentCourseList
  void 受講生コース情報の全件検索が行えること(){
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(10); //10件データが取れてくる
  }

  @Test  //searchStudentCourse
  void IDに紐づく受講生コース情報の検索(){
    Student student = new Student();
    student.setId("1");
    List<StudentCourse> actual = sut.searchStudentCourse(student.getId());
    assertThat(actual.size()).isEqualTo(2);
    //ID 1 に対して紐づくコース情報は2件
  }


  @Test //registerStudent
  void 受講生の登録が行えること(){
    Student student = new Student();
    student.setName("山田太郎");
    student.setFurigana("やまだたろう");
    student.setNickname("タロー");
    student.setMail("taro@gmail.com");
    student.setRegion("山形");
    student.setAge(39);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);
    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test //registerStudentCourse
  void 受講生コース情報の登録が行えること(){
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentsId("1");
    studentCourse.setCoursesName("Javaコース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));

    sut.registerStudentCourse(studentCourse);
    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(11);

  }

  @Test //updateStudent
  void 受講生詳細の更新が行えること(){
    Student student = new Student();
    student.setName("山田太郎");
    student.setFurigana("やまだたろう");
    student.setNickname("タロー");
    student.setMail("taro@gmail.com");
    student.setRegion("山形");
    student.setAge(39);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.updateStudent(student);
    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(5);

  }

  @Test //updateStudentCourse
  void 受講生コース情報の更新が行えること(){
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentsId("1");
    studentCourse.setCoursesName("Javaコース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));

    sut.updateStudentCourse(studentCourse);
    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(10);

  }

}
