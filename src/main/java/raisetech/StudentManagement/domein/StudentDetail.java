package raisetech.StudentManagement.domein;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<CourseDetail> courseDetail;

  public StudentDetail() {
  }

  public StudentDetail(Student student, List<CourseDetail> courseDetails) {
    this.student = student;
    this.courseDetail = courseDetails;
  }
}
