package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるコントローラークラス
 */
@RestController
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;

  /**
   * コンストラクタ
   *
   * @param service   　受講生サービス
   * @param converter 　受講生コンバーター
   */
  @Autowired
  private StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  /**
   * 受講生詳細情報一覧の全件検索
   *
   * @return 受講生詳細情報一覧(全件)
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentDetailList() {
    return service.searchStudentDetailList();
  }

  //todo:受講生詳細情報一覧をstudentIdを指定して検索するメソッドを作成する。

  //todo:受講生詳細情報一覧をフィルタリング(削除済み・年齢・受講コース)して表示するメソッドを作成する。

  /**
   * コース一覧の全件検索
   *
   * @return コース一覧(全件)
   */
  @GetMapping("/courseList")
  public List<Course> getCourseList() {
    return service.searchCourseList();
  }

  /**
   * 新規受講生情報の登録処理
   *
   * @param studentDetail 登録する受講生詳細情報の入ったオブジェクト
   * @return 登録された受講生情報の入ったオブジェクトを返す
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.addStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生情報の更新処理
   *
   * @param studentDetail 更新する受講生情報の入ったオブジェクト
   * @return 更新処理が完了したメッセージの文字列
   */
  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  /**
   * 受講生一覧の削除処理
   *
   * @param studentId 受講生ID
   * @return 削除処理が完了したメッセージの文字列
   */
  @PostMapping("/deleteStudent")
  public ResponseEntity<String> deleteStudent(@RequestBody String studentId) {
    //studentIdから登録データをオブジェクトに格納
    Student student = service.searchStudentById(studentId);
    //削除処理
    service.deleteStudent(student);
    return ResponseEntity.ok("削除処理が成功しました。");
  }
}
