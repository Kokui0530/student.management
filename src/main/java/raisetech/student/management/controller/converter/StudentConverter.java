package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentAppStatus;
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

  //受講生情報とコース情報を紐づけ
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> studentCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
          .filter(studentCourses -> student.getId() == studentCourses.getStudentsId())
          .collect(Collectors.toList());

      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
  public List<StudentDetail> convertCourseStatusList (List<StudentDetail> studentDetails,
      List<StudentAppStatus> studentAppStatusList){
  //コース情報と申し込み情報の紐づけ
    Map<Integer,StudentAppStatus> studentAppStatusMap = new HashMap<>();
    for(StudentAppStatus studentAppstatus : studentAppStatusList){
      studentAppStatusMap.put(studentAppstatus.getStudentCourseId(),studentAppstatus);
    }

    for (StudentDetail studentDetail : studentDetails){
      for (StudentCourse studentCourse : studentDetail.getStudentCourseList()){
        StudentAppStatus studentAppStatus = studentAppStatusMap.get(studentCourse.getId());
        studentCourse.setStatus(studentAppStatus);
      }
    }
  return studentDetails;
  }
  }

