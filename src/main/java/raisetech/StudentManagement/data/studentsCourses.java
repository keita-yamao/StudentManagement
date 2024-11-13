package raisetech.StudentManagement.data;

import java.sql.Date;
import lombok.Getter;

@Getter
public class studentsCourses {

  private String studentId;
  private String courseId;
  private String course;
  private Date startDate;
  private Date expectedCompletionDate;

}