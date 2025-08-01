package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "コース情報")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

  @Schema(description = "コースID", type = "String", example = "00001")
  private String courseId;
  @Schema(description = "コース名", type = "String", example = "Javaコース")
  private String course;
}
