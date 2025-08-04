package raisetech.student.management.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentAppStatus;
import raisetech.student.management.data.StudentCourse;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor //引数を使わないコンストラクター
@AllArgsConstructor  //全項目を設定するコンストラクター
@EqualsAndHashCode //equals() メソッドをオーバーライド
public class StudentDetail {

  @Schema(description = "受講生詳細情報")
  @Valid
  private Student student;

  @Schema(description = "受講生コース情報")
  @Valid
  private List<StudentCourse> studentCourseList;

  @Schema(description = "申し込み状況")
  @Valid
  private List<StudentAppStatus> StudentAppStatus;
}
