package raisetech.student.management.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

@Getter
@Setter
@NoArgsConstructor //引数を使わないコンストラクター
@AllArgsConstructor  //全項目を設定するコンストラクター
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourseList;
}
