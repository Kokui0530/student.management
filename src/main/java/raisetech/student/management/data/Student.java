package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.validation.Update;

@Schema(description = "受講生情報")
@Getter
@Setter
@EqualsAndHashCode //equals() メソッドをオーバーライド
public class Student {


 @Min(value = 1, message = "IDは1以上", groups = Update.class)
 @Schema(description = "ID、自動採番", example = "2")
  private int id;

 @Schema(description = "名前" , example = "山田太郎")
  @NotBlank(message = "必須項目です。")
  private String name;

 @Schema(description = "フリガナ" , example = "ヤマダタロウ")
  @NotBlank(message = "必須項目です。")
  private String furigana;

  @Schema(description = "ニックネーム" , example = "たろう")
  @NotBlank(message = "必須項目です。")
  private String nickname;

  @Schema(description = "メールアドレス" , example = "tarou@gmail.com")
  @NotBlank(message = "必須項目です。")
  @Email
  private String mail;

  @Schema(description = "地域" , example = "さいたま市")
  @NotBlank(message = "必須項目です。")
  private String region;

  @Schema(description = "年齢" , example = "35")
  private int age;

  @Schema(description = "性別" , example = "男性")
  @NotBlank(message = "必須項目です。")
  private String gender;

  @Schema(description = "備考" , example = "10月から受講開始")
  private String remark;

  @Schema(description = "削除フラグ" , example = "退会")
  private String isDeleted;


}
