package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "受講状態")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseStatus {

  private int id;

  @Schema(description = "受講情報のID", type = "int", example = "1")
  private int studentsCoursesId;

  @Schema(description = "状態", type = "String", example = "1")
  private String status;

  public CourseStatus(int studentsCoursesId, String status) {
    this.studentsCoursesId = studentsCoursesId;
    this.status = status;
  }
}
