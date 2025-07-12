package raisetech.student.management.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {


  private String id;

  @NotBlank
  private String name;

  @NotBlank
  private String furigana;

  @NotBlank
  private String nickname;

  @NotBlank
  @Email
  private String mail;

  @NotBlank
  private String region;

  private int age;

  @NotBlank
  private String gender;

  private String remark;
  private boolean isDeleted;

}
