package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter

public class StudentCourse {
  private String id;
  private String studentsId;

  @NotBlank
  private String coursesName;
  private LocalDateTime start;
  private LocalDateTime end;

}
