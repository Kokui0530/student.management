package raisetech.student.management.exception;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.domain.StudentDetail;

@RestController
public class Test {

  @GetMapping("/studentListTest")
  public List<StudentDetail> getStudentList() throws TestException{
    System.out.println("エラーが発生したよ！"); //ログ出力
    throw new TestException("エラーが発生しました。"); //ユーザー側に表示
  }
}
