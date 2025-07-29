package raisetech.student.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.data.Student;
import raisetech.student.management.service.StudentService;

@WebMvcTest(StudentController.class) //テスト対象のクラスを入れる
class StudentControllerTest {  //これでテスト用のスプリングブートが立ち上がる

  @Autowired
  private MockMvc mockMvc;  //お作法、

  @MockitoBean
  private StudentService service;  //サービスとかとはモック化のやり方が違う

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  //入力チェックに必要なvalidatorが取れる

  @Test //@GetMapping("/studentList")
  void 受講生詳細一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(get("/studentList"))  //getメソッドの検証
        .andExpect(status().isOk());
        //.andExpect(content().json("[]")); どんな内容で返ってくるか検証
         //json　レスポンスの中身は〇〇になってるよってエラーのBodyの所を見ると書いてある

    verify(service, times(1)).searchStudentList();
  }

  @Test //@GetMapping("/student/{id}")
  void 受講生詳細検索_idに紐づく受講生情報を取得できていること() throws Exception {
    String id = "99";
    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test//@PostMapping("/registerStudent")
  void 受講生詳細の登録が実行出来て空で返ってくること() throws Exception {
    //リクエストデータは適切に構築して入力チェックの検証も兼ねている
    //本来であれば登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない
    mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                "student":{
                    "name" : "林田耕太",
                    "furigana" : "ハヤシダコウタ",
                    "nickname" : "はやしだ",
                    "mail" : "hayasida@gmail.com",
                    "region" : "草加市",
                    "gender" : "男",
                    "remark" : ""
                    },
                    "studentCourseList" : [
                    {
                    "coursesName" : "javaコース"
                    }
                    ]
                    }
                """
        ))
        .andExpect(status().isOk());
    verify(service, times(1)).registerStudent(any());

  }


  @Test //@PutMapping("/updateStudent")
  void 受講生詳細の更新または論理削除が実行できること() throws Exception{
mockMvc.perform(put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
    """
        {
                "student": {
                    "id": "2",
                    "name": "佐藤健",
                    "furigana": "サトウケン",
                    "nickname": "けんけん",
                    "mail": "sato.ken@example.com",
                    "region": "東京都新宿区",
                    "age": 35,
                    "gender": "男",
                    "remark": "",
                    "deleted": false
                },
                "studentCourseList": [
                    {
                        "id": "4",
                        "studentsId": "2",
                        "coursesName": "Javaコース",
                        "start": "2025-06-10T00:00:00",
                        "end": "2025-09-10T00:00:00"
                    }
                ]
            }
        """
))
    .andExpect(status().isOk());
verify(service,times(1)).updateStudent(any());

  }

  @Test
  void 受講生詳細の例外APIが実行できてステータスが400で返ってくること()throws Exception{
    mockMvc.perform(get("/exception"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("このAPIは現在利用できません。古いURLとなっています。"));
  }

  @Test //入力チェック　エラーが出ないチェック
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しない事(){
    Student student = new Student();
    student.setId("1");
    student.setName("林田耕太");
    student.setFurigana("ハヤシダコウタ");
    student.setNickname("はやしだ");
    student.setMail("hayasida@gmail.com");
    student.setRegion("草加市");
    student.setGender("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
    //エラーが何個発生してるかをisEqualToに記載(今回はエラーが出ない事の検証なので０)

  }


  @Test //入力チェック　エラーになるかチェック
  void 受講生詳細の受講生IDに数字以外を用いた時に入力チェックに掛かること(){
    Student student = new Student();
    student.setId("テストです");
    student.setName("林田耕太");
    student.setFurigana("ハヤシダコウタ");
    student.setNickname("はやしだ");
    student.setMail("hayasida@gmail.com");
    student.setRegion("草加市");
    student.setGender("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("数値のみを入力してください。");
    //extracting → violationsは複数のものを持ってるので対象のフィールド名を指定して内容を書く
  }

}