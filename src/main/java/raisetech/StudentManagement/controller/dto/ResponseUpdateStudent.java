package raisetech.StudentManagement.controller.dto;

import lombok.Getter;
import raisetech.StudentManagement.domain.StudentDetail;

@Getter
public class ResponseUpdateStudent {

  private StudentDetail studentDetail;
  private String updateMessage;

  public ResponseUpdateStudent(StudentDetail studentDetail) {
    this.studentDetail = studentDetail;
    this.updateMessage = "更新処理が成功しました。";
  }
}
