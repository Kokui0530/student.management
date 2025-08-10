package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentAppStatus;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentInfo;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。 受講生の検索や登録、更新処理を行います。
 */

@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter){
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    List<StudentAppStatus> studentAppStatusList = repository.searchStatusList();
    //受講生情報とコース情報を紐づけ
    List<StudentDetail>studentDetails = converter.convertStudentDetails(studentList,studentCourseList);
    //コース情報に申し込み状況を紐づけ
    return converter.convertCourseStatus(studentDetails,studentAppStatusList);
  }

  /**
   * 受講生詳細検索です。
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して
   * 更に受講生コース情報に紐づく受講生申し込み情報を設定します。
   *
   * @param id 　受講生ID
   * @return 受講生詳細
   */

  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourseList = repository.searchStudentCourse(student.getId());
    //コースIDを取得して、コースに対して申し込み状況を紐づけしてコースにセットする
    for (StudentCourse studentCourse : studentCourseList){
      StudentAppStatus studentAppStatus = repository.searchStudentStatus(studentCourse.getId());
      studentCourse.setStatus(studentAppStatus);
    }
    return new StudentDetail(student, studentCourseList);
  }

  /**
   * 申し込み情報ごとの検索です。
   * 申し込み情報からコースIDを取得して後、そのコース情報に紐づく受講生情報を取得します。
   *
   * @param status 申し込み情報
   * @return 申し込み情報に当てはまる、受講生コース情報と受講生詳細
   */
  public List<StudentInfo> searchStudentAppStatus(String status) {
    List<StudentAppStatus> studentAppStatusList = repository.searchStudentAppStatus(status);
    List<StudentInfo> studentInfoList = new ArrayList<>();

    studentAppStatusList.forEach(studentAppStatus -> {
      int studentCourseId = studentAppStatus.getStudentCourseId();
      StudentCourse studentCourse = repository.searchStudentCourseById(studentCourseId);
      Student student = repository.searchStudent(studentCourse.getStudentsId());
      StudentInfo info = new StudentInfo();
      info.setStudent(student);
      info.setStudentCourse(studentCourse);
      info.setStudentAppStatus(studentAppStatus);
      studentInfoList.add(info);
    });
    return studentInfoList;

  }

  /**
   * 受講生詳細の登録を行います。
   * 受講生と受講生コース情報と申し込み情報を個別に登録し、受講生コース情報には受講生情報を紐づける値やコース開始日、コース終了日を設定します。
   * 受講生申し込み情報には受講生コース情報を紐づける値を設定します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録を付与した受講生詳細
   */

  @Transactional //登録、更新、削除する項目につける、必須！　基本はServiceでつける。
  //Transactional -> 複数の処理を一つのまとまりとして扱う仕組みのこと
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);
    for (StudentCourse studentCourse : studentDetail.getStudentCourseList()) {
      initStudentsCourse(studentCourse, student.getId());
      repository.registerStudentCourse(studentCourse);
      linkCourseToStatus(studentCourse.getStatus(), studentCourse.getId());
      repository.registerStudentAppStatus(studentCourse.getStatus());
      }
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定します。
   *
   * @param studentCourses 受講生コース情報
   * @param id       受講生
   */
  void initStudentsCourse(StudentCourse studentCourses, int id) {
    LocalDateTime now = LocalDateTime.now();

    studentCourses.setStudentsId(id);
    studentCourses.setStartDate(now);
    studentCourses.setEndDate(now.plusYears(1));
  }

  /***
   * 受講生申し込み情報を登録する際の受講生コースIDを設定します。
   *
   * @param studentAppStatus 受講生申し込み情報
   * @param id　受講生
   */
  void linkCourseToStatus(StudentAppStatus studentAppStatus , int id) {
    studentAppStatus.setStudentCourseId(id);
  }

  /**
   * 受講生詳細の更新を行います。 受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourse studentCourse : studentDetail.getStudentCourseList()) {
      repository.updateStudentCourse(studentCourse);
      repository.updateStudentAppStatus(studentCourse.getStatus());
    }
  }
}
