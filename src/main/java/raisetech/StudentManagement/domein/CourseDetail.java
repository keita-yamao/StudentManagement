package raisetech.StudentManagement.domein;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.StudentsCourses;

@Getter
@Setter
public class CourseDetail {

  private Course course;

  @Valid
  private StudentsCourses studentsCourses;

}
