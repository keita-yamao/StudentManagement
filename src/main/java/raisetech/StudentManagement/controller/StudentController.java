package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.domain.enums.CourseStatusType;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるコントローラークラス
 */
@Validated
@RestController
public class StudentController {

  private final StudentService service;

  /**
   * コンストラクタ
   *
   * @param service 　受講生サービス
   */
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細情報一覧の全件検索
   *
   * @return 受講生詳細情報一覧(全件)
   */
  @Operation(summary = "受講生詳細情報の一覧検索", description = "受講生詳細情報を一覧を検索します")
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
  @Operation(summary = "受講生詳細情報の単一検索", description = "受講生詳細情報を受講生IDを1件指定して検索します")
  @GetMapping("/students/{studentId}")
  public StudentDetail getStudentDetailById(
      @PathVariable @Size(min = 1, max = 10, message = "入力できる値は10文字までです") @Pattern(regexp = "^(?!.*[ \u0020\u3000]).*$", message = "半角スペースと全角スペースは使用できません") String studentId) {
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
  @Operation(summary = "受講生詳細情報の絞り込み検索", description = "受講生詳細情報を年齢・削除済み・コースIDを指定して検索します。")
  @GetMapping("/students")
  public List<StudentDetail> getFilteredStudentDetailList(
      @RequestParam(required = false) @Size(max = 60, message = "名前は60文字までです") String name,
      @RequestParam(required = false) @Size(max = 60, message = "フリガナは60文字までです") String furigana,
      @RequestParam(required = false) @Min(value = 0, message = "年齢の最小値は0です") Integer minAge,
      @RequestParam(required = false) @Max(value = 120, message = "年齢の最大値は120です") Integer maxAge,
      @RequestParam(required = false) Boolean isDeleted,
      @RequestParam(required = false) @Size(min = 5, max = 5, message = "コースIDは5文字で入力してください") String courseId) {
    return service.searchFilterStudentDetailList(name, furigana, minAge, maxAge, isDeleted,
        courseId);
  }

  /**
   * コース一覧の全件検索
   *
   * @return コース一覧(全件)
   */
  @Operation(summary = "コース一覧検索", description = "コース情報を一覧検索します。")
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
  @Operation(summary = "受講生詳細情報の登録", description = "受講生詳細情報の登録処理を行います。")
  @PostMapping("/registerStudent")
  public ResponseEntity<ResponseRegisterStudent> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    //登録処理
    service.addStudent(studentDetail);
    //DTOオブジェクトに登録処理情報を格納
    ResponseRegisterStudent responseRegisterStudent = new ResponseRegisterStudent(studentDetail);
    return ResponseEntity.ok(responseRegisterStudent);
  }

  /**
   * 受講生情報の更新処理
   *
   * @param studentDetail 更新する受講生詳細情報の入ったオブジェクト
   * @return 更新処理が完了したメッセージの文字列
   */
  @Operation(summary = "更新処理", description = "受講生詳細情報の更新処理を行います。")
  @PostMapping("/updateStudent")
  public ResponseEntity<ResponseUpdateStudent> updateStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
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
  @Operation(summary = "受講生の削除", description = "受講生IDを指定して受講生情報の論理削除処理を行います。")
  @PostMapping("/deleteStudent")
  public ResponseEntity<ResponseDeleteStudent> deleteStudent(
      @RequestBody @Size(min = 1, max = 10, message = "入力できる受講生IDは10文字までです") @Pattern(regexp = "^(?!.*[ \u0020\u3000]).*$", message = "半角スペースと全角スペースは使用できません") String studentId) {
    //studentIdから登録データをオブジェクトに格納
    Student student = service.searchStudentById(studentId);
    //削除処理
    service.deleteStudent(student);
    //DTOオブジェクトに削除処理情報を格納
    ResponseDeleteStudent responseDeleteStudent = new ResponseDeleteStudent(student);
    return ResponseEntity.ok(responseDeleteStudent);
  }

  /**
   * 受講状態の設定ステータス一覧の検索
   *
   * @return 受講状態のステータス一覧を返す
   */
  @GetMapping("/courseStatusList")
  public CourseStatusType[] getCourseStatusTypes() {
    return CourseStatusType.values();
  }

  /**
   * 例外処理テスト用
   *
   * @return
   * @throws TestException
   */
  @GetMapping("/throwException")
  public Student throwException() throws TestException {
    throw new TestException("例外処理テスト");
  }
}
