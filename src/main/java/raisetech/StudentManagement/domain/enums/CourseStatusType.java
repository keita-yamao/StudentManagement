package raisetech.StudentManagement.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = Shape.OBJECT)
public enum CourseStatusType {
  PROVISIONAL(1, "仮申込"),
  OFFICIAL(2, "本申込"),
  ENROLLED(3, "受講中"),
  COMPLETED(4, "修了");

  private final int code;
  private final String label;

}
