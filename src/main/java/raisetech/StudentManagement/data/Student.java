package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生情報")
@Getter
@Setter
public class Student {

  @Schema(description = "受講生ID", type = "String", example = "1")
  @Size(max = 10, message = "受講生IDは10桁までです")
  @NotEmpty(message = "受講生IDが入力されていません")
  @Pattern(regexp = "^(?!.*[ \u0020\u3000]).*$", message = "半角スペースと全角スペースは使用できません")
  private String studentId;

  @Schema(description = "フルネーム", type = "String", example = "山本太郎")
  @Size(max = 60, message = "名前は60文字までです")
  @NotEmpty(message = "名前が入力されていません")
  private String name;

  @Schema(description = "フリガナ", type = "String", example = "ヤマモトタロウ")
  @Size(max = 60, message = "フリガナは60文字までです")
  @NotEmpty(message = "フリガナが入力されていません")
  private String furigana;

  @Schema(description = "ニックネーム", type = "String", example = "タロ")
  @Size(max = 60, message = "ニックネームは60文字までです")
  @NotEmpty(message = "ニックネームが入力されていません")
  private String nickname;

  @Schema(description = "メールアドレス", type = "String", example = "taro@exampl.com")
  @Email(message = "無効な形式のメールアドレスです")
  @NotNull(message = "メールアドレスが入力されていません")
  private String email;

  @Schema(description = "住所", type = "String", example = "東京")
  @Size(max = 161, message = "住所は161文字までです")
  private String address;

  @Schema(description = "年齢", type = "int", example = "26")
  @Min(value = 0, message = "最少年齢は0才からです")
  @Max(value = 120, message = "最大年齢は120才までです")
  private int age;

  @Schema(description = "性別", type = "String", example = "男性")
  @Size(max = 20, message = "20文字までです")
  private String gender;

  @Schema(description = "備考", type = "String", example = "255文字までなんでも記入OK")
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
