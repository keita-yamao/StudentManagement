package raisetech.StudentManagement.domein;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.StudentsCourses;

@Schema(description = "受講コース情報")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetail {

  private Course course;

  @Valid
  private StudentsCourses studentsCourses;

}
