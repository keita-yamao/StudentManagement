package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.dto.ResponseDeleteStudent;
import raisetech.StudentManagement.controller.dto.ResponseRegisterStudent;
import raisetech.StudentManagement.controller.dto.ResponseUpdateStudent;
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

  /**
   * コンストラクタ
   *
   * @param service 　受講生サービス
   */
  @Autowired
  private StudentController(StudentService service) {
    this.service = service;
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

  /**
   * 単一の受講生詳細情報の検索
   *
   * @param studentId 受講生ID
   * @return 受講生詳細情報(1件)
   */
  @GetMapping("/students/{studentId}")
  public StudentDetail getStudentDetailById(@PathVariable String studentId) {
    return service.searchStudentDetailById(studentId);
  }

  /**
   * 受講生詳細情報の絞り込み検索
   *
   * @param minAge    検索年齢の最小値
   * @param maxAge    検索年齢の最大値
   * @param isDeleted 論理削除の真偽値
   * @param courseId  コースID
   * @return 受講生詳細情報一覧(条件に一致するもの)
   */
  @GetMapping("/students")
  public List<StudentDetail> getFilteredStudentDetailList(
      @RequestParam(required = false) Integer minAge,
      @RequestParam(required = false) Integer maxAge,
      @RequestParam(required = false) Boolean isDeleted,
      @RequestParam(required = false) String courseId) {
    return service.searchFilterStudentDetailList(minAge, maxAge, isDeleted, courseId);
  }

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
  public ResponseEntity<ResponseRegisterStudent> registerStudent(
      @RequestBody StudentDetail studentDetail) {
    //登録処理
    service.addStudent(studentDetail);
    //DTOオブジェクトに登録処理情報を格納
    ResponseRegisterStudent responseRegisterStudent = new ResponseRegisterStudent(studentDetail);
    return ResponseEntity.ok(responseRegisterStudent);
  }

  /**
   * 受講生情報の更新処理
   *
   * @param studentDetail 更新する受講生情報の入ったオブジェクト
   * @return 更新処理が完了したメッセージの文字列
   */
  @PostMapping("/updateStudent")
  public ResponseEntity<ResponseUpdateStudent> updateStudent(
      @RequestBody StudentDetail studentDetail) {
    //更新処理
    service.updateStudent(studentDetail);
    //DTOオブジェクトに更新処理情報を格納
    ResponseUpdateStudent responseUpdateStudent = new ResponseUpdateStudent(studentDetail);
    return ResponseEntity.ok(responseUpdateStudent);
  }

  /**
   * 受講生一覧の削除処理
   *
   * @param studentId 受講生ID
   * @return 削除処理が完了した受講生情報と削除メッセージのDTOを返す
   */
  @PostMapping("/deleteStudent")
  public ResponseEntity<ResponseDeleteStudent> deleteStudent(@RequestBody String studentId) {
    //studentIdから登録データをオブジェクトに格納
    Student student = service.searchStudentById(studentId);
    //削除処理
    service.deleteStudent(student);
    //DTOオブジェクトに削除処理情報を格納
    ResponseDeleteStudent responseDeleteStudent = new ResponseDeleteStudent(student);
    return ResponseEntity.ok(responseDeleteStudent);
  }
}
