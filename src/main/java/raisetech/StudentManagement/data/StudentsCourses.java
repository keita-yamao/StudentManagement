package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講情報")
@Getter
@Setter
public class StudentsCourses {

  private int id;

  @Schema(description = "受講生ID", type = "String", example = "1")
  @Size(max = 10, message = "受講生IDは10桁までです")
  @NotEmpty(message = "受講生IDが入力されていません")
  @Pattern(regexp = "^(?!.*[ \u0020\u3000]).*$", message = "半角スペースと全角スペースは使用できません")
  private String studentId;

  @Schema(description = "コースID", type = "String", example = "0001")
  @Size(min = 5, max = 5, message = "コースIDは5文字で入力してください")
  @NotEmpty(message = "コースIDが入力されていません")
  @Pattern(regexp = "^(?!.*[ \u0020\u3000]).*$", message = "半角スペースと全角スペースは使用できません")
  private String courseId;

  @Schema(description = "受講開始日", type = "Date", example = "2023-09-01")
  @NotNull(message = "開始日付が未入力です")
  private Date startDate;

  @Schema(description = "修了日", type = "Date", example = "2024-09-01")
  @NotNull(message = "修了日付が未入力です")
  private Date expectedCompletionDate;
}
