package raisetech.StudentManagement.controller.dto;

import lombok.Getter;
import raisetech.StudentManagement.domain.StudentDetail;

@Getter
public class ResponseRegisterStudent {

  private StudentDetail studentDetail;
  private String registerMessage;

  public ResponseRegisterStudent(StudentDetail studentDetail) {
    this.studentDetail = studentDetail;
    this.registerMessage = "登録処理が成功しました。";
  }
}
