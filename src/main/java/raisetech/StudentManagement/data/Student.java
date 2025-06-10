package raisetech.StudentManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  @Size(max = 10, message = "受講生IDは10桁までです")
  @NotEmpty(message = "受講生IDが入力されていません")
  @Pattern(regexp = "^(?!.*[ \u0020\u3000]).*$", message = "半角スペースと全角スペースは使用できません")
  private String studentId;

  @Size(max = 60, message = "名前は60文字までです")
  @NotEmpty(message = "名前が入力されていません")
  private String name;

  @Size(max = 60, message = "フリガナは60文字までです")
  @NotEmpty(message = "フリガナが入力されていません")
  private String furigana;

  @Size(max = 60, message = "ニックネームは60文字までです")
  @NotEmpty(message = "ニックネームが入力されていません")
  private String nickname;

  @Email(message = "無効な形式のメールアドレスです")
  @NotNull(message = "メールアドレスが入力されていません")
  private String email;

  @Size(max = 161, message = "住所は161文字までです")
  private String address;

  @Min(value = 0, message = "最少年齢は0才からです")
  @Max(value = 120, message = "最大年齢は120才までです")
  private int age;

  @Size(max = 20, message = "20文字までです")
  private String gender;

  @Size(max = 255, message = "最大文字数255文字まで")
  private String remark;

  private boolean isDeleted;


  public void delete() {
    this.isDeleted = true;
  }

  public void undelete() {
    this.isDeleted = false;
  }
}
