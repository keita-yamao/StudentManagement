package raisetech.StudentManagement;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class studentsCourses {

  private String studentId;
  private String courseId;
  private String course;
  private Date startDate;
  private Date expectedCompletionDate;

}