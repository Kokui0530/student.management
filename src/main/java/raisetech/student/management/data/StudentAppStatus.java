package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生申し込み情報")
@Getter
@Setter
@EqualsAndHashCode
public class StudentAppStatus {

  @Pattern(regexp = "^\\d+$" , message = "数値のみを入力してください。")
  @Schema(description = "ID、自動採番", example = "2")
  private int id;

  @Schema(description = "受講生ID" , example = "8")
  private String studentsId;

  @Schema(description = "ステータス" , example = "本申し込み")
  @NotBlank(message = "必須項目です。")
  private String status;
  //仮申し込み、本申し込み、受講中、受講終了　の４つ


}
