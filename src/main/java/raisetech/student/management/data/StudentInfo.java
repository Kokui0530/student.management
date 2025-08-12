package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生詳細　1受講生：1コース：1申し込み状況")
@Getter
@Setter
@EqualsAndHashCode

public class StudentInfo {

  @Schema(description = "受講生詳細")
  @Valid
  private Student student;

  @Schema(description = "受講生コース詳細")
  @Valid
  private StudentCourse studentCourse;

  @Schema(description = "受講生コース申し込み状況")
  @Valid
  private StudentAppStatus studentAppStatus;

}
