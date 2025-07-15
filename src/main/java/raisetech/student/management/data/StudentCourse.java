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

  @Schema(description = "ID、自動採番", example = "2")
  private String id;

  @Schema(description = "受講生ID" , example = "5")
  private String studentsId;

  @Schema(description = "コース名" , example = "Javaコース")
  @NotBlank(message = "必須項目です。")
  private String coursesName;

  @Schema(description = "受講開始日" , example = "2024.09.01")
  private LocalDateTime start;

  @Schema(description = "受講終了日" , example = "2025.09.01")
  private LocalDateTime end;

}
