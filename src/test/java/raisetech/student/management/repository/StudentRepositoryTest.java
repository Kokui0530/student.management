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
    String id = "1";
    Student student = new Student();
    student.setId(id);
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
    Student actual = sut.searchStudent(id);

    assertThat(actual).isEqualTo(student);
  }

  @Test //updateStudentCourse
  void 受講生コース情報の更新が行えること(){
    String studentId = "1";
    StudentCourse expected = new StudentCourse();
    expected.setId("1");
    expected.setStudentsId(studentId);
    expected.setCoursesName("Javaコース");
    expected.setStartDate(LocalDateTime.of(2024,4,1,1,0));
    expected.setEndDate(LocalDateTime.of(2024,6,30,15,0));

    //実行
    sut.updateStudentCourse(expected);

    StudentCourse actual = sut.searchStudentCourse(studentId).get(0);

    assertThat(expected.getStudentsId()).isEqualTo(actual.getStudentsId());
    assertThat(expected.getCoursesName()).isEqualTo(actual.getCoursesName());
    //assertThat(actual).isEqualTo(student)　だとダメな理由
    //コース情報はLocalDateTime.nowを使っていて、ミリ単位で変わってくるから、中身が完全に一致しない
    //だから、それぞれの要素を比較すればOK
  }

  @Test  //異常系
  void IDに紐づく受講生検索で存在しないIDの場合はNullを返すこと(){
    Student actual = sut.searchStudent("5555");
    assertThat(actual).isNull();
  }


  @Test  //searchStudentCourse
  void IDに紐づく受講生コース情報の検索で存在しないIDの場合空のリストを返す(){
     String studentId = "5555";
    List<StudentCourse> actual = sut.searchStudentCourse(studentId);
    assertThat(actual).hasSize(0);
    //ID 1 に対して紐づくコース情報は2件
  }



}
