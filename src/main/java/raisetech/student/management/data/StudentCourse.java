package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.validation.Update;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@EqualsAndHashCode

public class StudentCourse {

  @Min(value = 1, message = "IDは1以上", groups = Update.class)
  @Schema(description = "ID、自動採番", example = "2")
  private int id;

  @Min(value = 1, message = "IDは1以上", groups = Update.class)
  @Schema(description = "受講生ID" , example = "5")
  private int studentsId;

  @Schema(description = "コース名" , example = "Javaコース")
  @NotBlank(message = "必須項目です。")
  private String coursesName;

  @Schema(description = "受講開始日" , example = "2024.09.01")
  private LocalDateTime startDate;

  @Schema(description = "受講終了日" , example = "2025.09.01")
  private LocalDateTime endDate;

}
