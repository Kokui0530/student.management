package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;


/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うConverterです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報をマッピングする。 受講生コース情報は受講生に対して複数存在するので、 ループを回して受講生詳細情報を組み立てる
   *
   * @param studentList       　受講生一覧
   * @param studentCourseList 　受講生コース情報リスト
   * @return 受講生詳細情報のリスト
   */


  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> studentCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
          .filter(studentCourses -> student.getId().equals(studentCourses.getStudentsId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
