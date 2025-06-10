package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

  private int id;

  @Size(max = 10, message = "受講生IDは10桁までです")
  @NotEmpty(message = "受講生IDが入力されていません")
  @Pattern(regexp = "^(?!.*[ \u0020\u3000]).*$", message = "半角スペースと全角スペースは使用できません")
  private String studentId;

  @Size(min = 5, max = 5, message = "コースIDは5文字で入力してください")
  @NotEmpty(message = "コースIDが入力されていません")
  @Pattern(regexp = "^(?!.*[ \u0020\u3000]).*$", message = "半角スペースと全角スペースは使用できません")
  private String courseId;

  @NotNull(message = "開始日付が未入力です")
  private Date startDate;

  @NotNull(message = "修了日付が未入力です")
  private Date expectedCompletionDate;
}
