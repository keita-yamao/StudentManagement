package raisetech.StudentManagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.util.ArrayList;
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
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.CourseDetail;
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
    List<StudentDetail> studentDetails = getSampleStudentDetails();

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
    List<StudentDetail> studentDetails = getSampleStudentDetails();

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
    StudentDetail studentDetail = getSampleStudentDetail();
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
    StudentDetail studentDetail = getSampleStudentDetail();
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
    //受講生情報
    Student student1 = new Student();
    student1.setStudentId("1");
    student1.setName("山本太郎");
    student1.setFurigana("ヤマモトタロウ");
    student1.setNickname("タロ");
    student1.setEmail("taro@exampl.com");
    student1.setAddress("東京");
    student1.setAge(25);
    student1.setGender("男性");
    student1.undelete();//削除フラグfalse

    Student student = student1;
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

  /*
   * テスト用データ
   * */
  /*受講生詳細情報*/
  private static StudentDetail getSampleStudentDetail() {
    //受講生情報
    Student student = new Student(
        "1",
        "山本太郎",
        "ヤマモトタロウ",
        "タロ",
        "taro@exampl.com",
        "東京",
        25,
        "男性",
        null,
        false
    );

    //コース情報
    Course course = new Course("00001", "Javaコース");

    //受講情報
    StudentsCourses studentsCourses = StudentsCourses.builder()
        .id(1)
        .studentId("1")
        .courseId("00001")
        .startDate(Date.valueOf("2023-09-01"))
        .expectedCompletionDate(Date.valueOf("2024-09-01"))
        .build();

    //リターンオブジェクトの作成
    CourseDetail courseDetail = new CourseDetail(course, studentsCourses);
    List<CourseDetail> courseDetails = new ArrayList<>();
    courseDetails.add(courseDetail);
    StudentDetail studentDetail = new StudentDetail(student, courseDetails);

    return studentDetail;
  }

  /*受講生詳細情報一覧*/
  private static List<StudentDetail> getSampleStudentDetails() {
    //受講生情報
    Student student1 = new Student(
        "1",
        "山本太郎",
        "ヤマモトタロウ",
        "タロ",
        "taro@exampl.com",
        "東京",
        25,
        "男性",
        null,
        false
    );

    //受講生2
    Student student2 = new Student(
        "2",
        "鈴木一郎",
        "スズキイチロウ",
        "イチ",
        "ichiro@exampl.com",
        "大阪",
        30,
        "男性",
        null,
        false
    );//削除フラグfalse

    //コース情報
    Course course = new Course("00001", "Javaコース");

    //受講情報１
    StudentsCourses studentsCourses1 = StudentsCourses.builder()
        .id(1)
        .studentId("1")
        .courseId("00001")
        .startDate(Date.valueOf("2023-09-01"))
        .expectedCompletionDate(Date.valueOf("2024-09-01"))
        .build();

    //受講情報2
    StudentsCourses studentsCourses2 = StudentsCourses.builder()
        .id(2)
        .studentId("2")
        .courseId("00002")
        .startDate(Date.valueOf("2024-01-01"))
        .expectedCompletionDate(Date.valueOf("2025-01-01"))
        .build();

    //リターンオブジェクトの作成
    //受講生１
    CourseDetail courseDetail1 = new CourseDetail(course, studentsCourses1);
    List<CourseDetail> courseDetails1 = new ArrayList<>();
    courseDetails1.add(courseDetail1);
    StudentDetail studentDetail1 = new StudentDetail(student1, courseDetails1);
    //受講生2
    CourseDetail courseDetail2 = new CourseDetail(course, studentsCourses2);
    List<CourseDetail> courseDetails2 = new ArrayList<>();
    courseDetails2.add(courseDetail2);
    StudentDetail studentDetail2 = new StudentDetail(student2, courseDetails2);

    //リターンオブジェクトの作成
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentDetails.add(studentDetail1);
    studentDetails.add(studentDetail2);
    return studentDetails;
  }

}
