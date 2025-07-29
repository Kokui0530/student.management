package raisetech.student.management.controller.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

@ExtendWith(MockitoExtension.class)
class StudentConverterTest {

  private StudentConverter sut;

  @Test
  void StudentIdが一致する受講生詳細とコース情報が取れてくること(){
    sut = new StudentConverter();
    String id = "999";

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

}