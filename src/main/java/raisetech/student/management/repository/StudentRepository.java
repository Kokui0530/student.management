package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentAppStatus;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */

@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧(全件)
   */
  List<Student> search();

  /**
   * 受講生の検索を行います。
   *
   * @param id 　受講生ID
   * @return 受講生
   */
  Student searchStudent(int id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報(全件)
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生のコース情報
   */
  List<StudentCourse> searchStudentCourse(int studentId);

  /**
   * 受講生のコース申し込み状況の全件検索を行います。
   *
   * @return 受講生んｐコース申し込み状況(全件)
   */
  List<StudentAppStatus> searchStatusList();

  /**
   * 受講生コースIDに紐づく受講生コース申し込み状況を検索します。
   *
   * @param StudentCourseId　受講生コースID
   * @return 受講生コースIDに紐づく受講生コース申し込み状況を検索します。
   */
  StudentAppStatus searchStudentStatus(int StudentCourseId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);


  /**
   * 受講生コース申し込み状況を新規登録します。IDに関しては自動採番を行う。
   *
   * @param studentAppStatus 受講生コース申し込み状況
   */
  void registerStudentAppStatus(StudentAppStatus studentAppStatus);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生コース申し込み状況の申し込み状況を更新します。
   *
   * @param StudentAppStatus 受講生コース申し込み状況
   */
  void updateStudentAppStatus(StudentAppStatus StudentAppStatus);


  //statusごとの検索
  List<StudentAppStatus> searchStudentAppStatus(String status);



}