package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生の全ての情報")
@Getter
@Setter
@EqualsAndHashCode

public class StudentInfo {
  private Student student;
  private StudentCourse studentCourse;
  private StudentAppStatus studentAppStatus;

}
