package raisetech.student.management.data;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

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
