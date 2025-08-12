package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.data.StudentInfo;
import raisetech.student.management.service.StudentService;

/**
 受講生の検索や登録、更新などを行うREST APIとして実行されるcontrollerです
 *
 */

@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細一覧検索です 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧（全件）
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @ApiResponse(responseCode = "200", description = "登録が完了しました。")
  @GetMapping("/studentList")
  public List<StudentInfo> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細検索です。 IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Operation(summary = "ID検索", description = "受講生IDをもとに受講生情報を検索します。")
  @ApiResponse(responseCode = "200", description = "登録が完了しました。")

  @GetMapping("/student/{id}")
  public List<StudentInfo> getStudent(
      @PathVariable @Min(1) int id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細検索です。申し込み情報に紐づくコース情報、受講生詳細を取得します。
   *
   * @param status 申し込み情報
   * @return 受講生詳細、コース情報、申し込み情報
   */
  @Operation(summary = "申し込み情報検索", description = "受講生申し込み情報をもとに対象の受講生詳細を検索します。")
  @GetMapping("/student")
  public List<StudentInfo> getStudentInfo(@RequestParam String status) {
    return service.searchStudentAppStatus(status);
  }

  /**
   * 受講生詳細の登録を行います
   *
   * @param studentInfo 受講生詳細
   * @return 実行結果
   */


  @Operation(summary = "受講生登録", description = "受講生を登録します。")
  @ApiResponse(responseCode = "200", description = "登録が完了しました。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentInfo> registerStudent(@RequestBody @Valid StudentInfo studentInfo) {
    StudentInfo responseStudentInfo = service.registerStudent(studentInfo);
    return ResponseEntity.ok(responseStudentInfo);
  }

  /**
   * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います(論理削除)
   *
   * @param studentInfoList 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生詳細更新", description = "受講生の詳細情報を更新します。")
  @ApiResponse(responseCode = "200", description = "登録が完了しました。")
  @PutMapping("/updateStudent") //更新内容を入力するところ
  public ResponseEntity<String> updateStudent(
      @RequestBody @Valid List<StudentInfo> studentInfoList) {
    service.updateStudent(studentInfoList);
    return ResponseEntity.ok("更新が成功しました");
  }

  @GetMapping("/exception")
  public ResponseEntity<String> exceptionApi() {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("このAPIは現在利用できません。古いURLとなっています。");
  }

}