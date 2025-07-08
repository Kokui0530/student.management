package raisetech.student.management.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  //@GetMapping ページを表示するだけ（一覧、フォーム画面）
  //@PostMapping データを送信・保存・削除など変更したい

  @GetMapping("/studentList") //学生情報を取得、HTMLとの紐づけ
  public String getStudentList(Model model) { //Model->HTMLに渡すデータを入れる入れ物。
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentCoursesList();
    //↓ HTMLに入れるリストを指示
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }

  @GetMapping("/student/{id}")  //受講生情報の更新
  public String getStudent(@PathVariable String id, Model model) {
    StudentDetail studentDetail = service.searchStudent(id);
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }


  @GetMapping("/newStudent")  //新規登録画面
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent") //↓　Model->  StudentDetail　の　studentsDetailに値をいれる処理
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    //BindingResult result->入力チェック
    if (result.hasErrors()) {  //エラーがあったら、registerStudentに返す処理
      return "registerStudent";
    }
    //新規受講生情報を登録
    service.registerStudent(studentDetail);
    return "redirect:/studentList";
  }

  @PostMapping("/updateStudent") //更新内容を入力するところ
  public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    //BindingResult result->入力チェック
    if (result.hasErrors()) {  //エラーがあったら、registerStudentに返す処理
      return "updateStudent";
    }
    //新規受講生情報を登録
    service.updateStudent(studentDetail);
    return "redirect:/studentList";
  }
}