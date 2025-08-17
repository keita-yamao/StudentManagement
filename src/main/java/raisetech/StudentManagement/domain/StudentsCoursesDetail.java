package raisetech.StudentManagement.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.data.StudentsCourses;

@Schema(description = "受講状態情報")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentsCoursesDetail {

  @Valid
  private StudentsCourses studentsCourses;

  private CourseStatus courseStatus;
}
