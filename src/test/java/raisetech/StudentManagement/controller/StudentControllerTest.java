package raisetech.StudentManagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import factory.TestDataFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import raisetech.StudentManagement.controller.dto.ResponseDeleteStudent;
import raisetech.StudentManagement.controller.dto.ResponseRegisterStudent;
import raisetech.StudentManagement.controller.dto.ResponseUpdateStudent;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private StudentService studentService;

  /*
   *getStudentDetailListメソッドのテスト
   * 1.service.searchStudentDetailListメソッドの呼び出しとJSON形式で想定された型で返ってくるか
   **/
  @Test
  void 受講生詳細情報一覧検索_サービス処理の呼び出しと返り値の検証() throws Exception {
    //テストデータのセット
    List<StudentDetail> studentDetails = TestDataFactory.createSampleStudentDetails();

    //サービスのスタブ化
    when(studentService.searchStudentDetailList()).thenReturn(studentDetails);

    //実行
    mockMvc.perform(
            MockMvcRequestBuilders.get("/studentList"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .json(objectMapper.writeValueAsString(studentDetails)));

    //検証
    Mockito.verify(studentService, Mockito.times(1)).searchStudentDetailList();
  }

  /*
   *getStudentDetailByIdメソッドのテスト
   *service.searchStudentDetailById(studentId)メソッドの呼び出し検証
   */
  @Test
  void 単一受講生詳細情報検索_サービス処理の呼び出しの検証() throws Exception {
    //テストデータのセット
    String studentId = "1";

    //実行結果の取得
    mockMvc.perform(MockMvcRequestBuilders.get("/students/" + studentId))
        .andExpect(MockMvcResultMatchers.status().isOk());

    //検証
    Mockito.verify(studentService, Mockito.times(1)).searchStudentDetailById(studentId);
  }

  /*
   *getFilteredStudentDetailListメソッドのテスト
   * service.searchFilterStudentDetailListメソッドの呼び出し検証
   */
  @Test
  void 受講生詳細情報の絞り込み検索_サービス処理の呼び出しの検証() throws Exception {
    //テストデータのセット
    Integer minAge = 0;
    Integer maxAge = 120;
    Boolean isDeleted = false;
    String courseId = "00001";
    List<StudentDetail> studentDetails = TestDataFactory.createSampleStudentDetails();

    //スタブ化
    when(studentService.searchFilterStudentDetailList(minAge, maxAge, isDeleted,
        courseId)).thenReturn(studentDetails);

    //実行
    mockMvc.perform(MockMvcRequestBuilders.get("/students")
            .param("minAge", String.valueOf(minAge))
            .param("maxAge", String.valueOf(maxAge))
            .param("isDeleted", String.valueOf(isDeleted))
            .param("courseId", courseId)
        )
        .andExpect(MockMvcResultMatchers.status().isOk());

    //検証
    Mockito.verify(studentService, Mockito.times(1))
        .searchFilterStudentDetailList(minAge, maxAge, isDeleted, courseId);

  }

  /*
   *getCourseListメソッドのテスト
   *service.searchCourseList()メソッドの呼び出し検証
   */
  @Test
  void コース一覧の全件検索_サービス処理の呼び出し検証() throws Exception {
    //実行結果の取得
    mockMvc.perform(MockMvcRequestBuilders.get("/courseList"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    //検証
    Mockito.verify(studentService, Mockito.times(1)).searchCourseList();
  }

  /*
   *registerStudentメソッドのテスト
   * service.addStudent(studentDetail)メソッドの呼び出しと返り値の検証
   */
  @Test
  void 新規受講生情報の登録処理_サービス処理の呼び出しと返り値検証() throws Exception {
    //テストデータのセット
    StudentDetail studentDetail = TestDataFactory.createSampleStudentDetail();
    String json = objectMapper.writeValueAsString(studentDetail);
    ResponseRegisterStudent responseRegisterStudent = new ResponseRegisterStudent(studentDetail);
    //スタブ化
    when(studentService.addStudent(any(StudentDetail.class))).thenReturn(studentDetail);

    //実行
    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(responseRegisterStudent)));

    //検証
    Mockito.verify(studentService, Mockito.times(1)).addStudent(any(StudentDetail.class));

  }

  /*
   * updateStudentメソッドのテスト
   * service.updateStudent(studentDetail)メソッドの呼び出しと返り値の検証
   * */
  @Test
  void 受講生情報の更新処理_サービス処理の呼び出しと返り値検証() throws Exception {
    //テストデータのセット
    StudentDetail studentDetail = TestDataFactory.createSampleStudentDetail();
    String json = objectMapper.writeValueAsString(studentDetail);
    ResponseUpdateStudent responseUpdateStudent = new ResponseUpdateStudent(studentDetail);

    //実行
    mockMvc.perform(MockMvcRequestBuilders.post("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(responseUpdateStudent)));

    //検証
    Mockito.verify(studentService, Mockito.times(1)).updateStudent(any(StudentDetail.class));
  }

  /*
   * deleteStudentメソッドのテスト
   * service.searchStudentById(studentId)とservice.deleteStudent(student)の呼び出しと返り値の検証
   * */
  @Test
  void 講生情報の論理削除処理_サービス処理の呼び出しと返り値検証() throws Exception {
    //テストデータのセット
    String studentId = "1";
    Student student = TestDataFactory.createSampleStudent();
    ResponseDeleteStudent responseDeleteStudent = new ResponseDeleteStudent(student);

    //スタブ化
    when(studentService.searchStudentById(anyString())).thenReturn(student);

    //実行
    mockMvc.perform(MockMvcRequestBuilders.post("/deleteStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(studentId))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(responseDeleteStudent)));

    //検証
    Mockito.verify(studentService, Mockito.times(1)).searchStudentById(anyString());
    Mockito.verify(studentService, Mockito.times(1)).deleteStudent(any(Student.class));
  }

  /*
   * throwExceptionメソッドのテスト
   */
  @Test
  void TestExceptionにアクセスして代入メッセージと404エラーが返ってくるか検証() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/throwException"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.content().string("例外処理テスト"));
  }

}
