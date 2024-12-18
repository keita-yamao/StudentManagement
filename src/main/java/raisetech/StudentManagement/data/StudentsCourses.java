package raisetech.StudentManagement.data;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

  private int id;
  private String studentId;
  private String courseId;
  private String course;
  private Date startDate;
  private Date expectedCompletionDate;

}