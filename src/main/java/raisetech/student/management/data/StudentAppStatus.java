package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.validation.Update;

@Schema(description = "受講生申し込み情報")
@Getter
@Setter
@EqualsAndHashCode
public class StudentAppStatus {

  @Min(value = 1, message = "IDは1以上", groups = Update.class)
  @Schema(description = "ID、自動採番", example = "2")
  private int id;

  @Min(value = 1, message = "IDは1以上", groups = Update.class)
  @Schema(description = "受講生コースID" , example = "8")
  private int studentCourseId;

  @Schema(description = "ステータス" , example = "本申し込み")
  @NotBlank(message = "必須項目です。")
  private String status;
  //仮申し込み、本申し込み、受講中、受講終了　の４つ



}
