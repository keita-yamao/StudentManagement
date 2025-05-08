package raisetech.StudentManagement.controller.dto;

import lombok.Getter;
import raisetech.StudentManagement.data.Student;

@Getter
public class ResponseDeleteStudent {

  private Student student;
  private String deleteMessage;

  public ResponseDeleteStudent(Student student) {
    this.student = student;
    this.deleteMessage = "削除処理が成功しました。";
  }
}
