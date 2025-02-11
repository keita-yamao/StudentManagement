package raisetech.StudentManagement.domein;

import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.StudentsCourses;

@Getter
@Setter
public class CourseDetail {

  private Course course;
  private StudentsCourses studentsCourses;

}
