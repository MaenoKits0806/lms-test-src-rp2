package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.sss.lms.controller.LoginController;
import jp.co.sss.lms.ct.util.WebDriverUtils;
import jp.co.sss.lms.service.InfoService;
import jp.co.sss.lms.service.LoginService;
import jp.co.sss.lms.util.LoginUserUtil;
import jp.co.sss.lms.util.MessageUtil;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

	public MockMvc mockMvc;

	public MockHttpServletRequestBuilder getRequest;

	// 既に完成されたクラスは、ReflectionTestUtilsで設定出来る。
	@Autowired
	private MessageUtil messageUtil;

	// Mock、Spyの対象となるクラスを宣言
	@Mock
	private InfoService infoService;
	@Mock
	private LoginUserUtil loginUserUtil;
	@Mock
	private LoginService loginService;

	// @Mock、@Spyで宣言したクラスをテスト対象クラスに注入
	@InjectMocks
	private LoginController loginController;

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/**
	 * setupメソッド
	 * 
	 * Tips: BeforeEachアノテーションを付与するとテストメソッドを実行する前に必ず実行される（必要のない場合、省略可）
	 *       テストクラス全体で事前に設定すべき処理を記載することでソースの記載量を削減できる。
	 * */
	@BeforeEach
	public void setup() {
		// Spring MVCにテスト対象のコントローラを設定する 
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();

		// 既に完成されているクラスに対して、@BeforEachにReflectionTestUtilsを設定することで、各テストメソッドで呼び出す処理を省略出来る。
		ReflectionTestUtils.setField(loginController, "messageUtil", messageUtil);
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		String suffix = null;

		//自分自身をインスタンス化して渡す
		Case09 instance = new Case09();

		// 指定のURLの画面を開く
		goTo("http://localhost:8080/lms/");
		scrollTo(0);

		suffix = "01_ログイン前(登録済ユーザー)";

		getEvidence(instance, suffix);

		assertEquals("ログイン | LMS", getTitle());
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws InterruptedException {
		String suffix = null;

		//自分自身をインスタンス化して渡す
		Case09 instance = new Case09();
		// ユーザー名とパスワードを入力

		WebElement username = WebDriverUtils.getUserName();
		WebElement password = WebDriverUtils.getPassword();

		username.sendKeys("StudentAA01");
		password.sendKeys("StudentAA01A");

		// ログインボタンをクリック
		WebElement loginBtn = WebDriverUtils.getLoginBtn();

		loginBtn.click();

		// 5秒待つ 
		Thread.sleep(5000);

		suffix = "02_ログイン後(登録済ユーザー)";

		getEvidence(instance, suffix);

		assertEquals("コース詳細 | LMS", getTitle());
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() throws InterruptedException {
		// TODO ここに追加

		String suffix = null;

		//自分自身をインスタンス化して渡す
		Case09 instance = new Case09();

		//scrollTo(250);
		scrollTo(0);

		// ようこそ受験生○○さん情報取得

		String examinee = "ようこそ受講生ＡＡ１さん";

		WebElement welcomeexaminee = WebDriverUtils.getWelcomeExaminee(examinee);
		welcomeexaminee.click();

		scrollTo(600);

		// 5秒待つ 
		Thread.sleep(5000);

		suffix = "01_ユーザー詳細画面遷移";

		getEvidence(instance, suffix);

		assertEquals("ユーザー詳細", getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() throws InterruptedException {
		// TODO ここに追加

		String suffix = null;

		//自分自身をインスタンス化して渡す
		Case09 instance = new Case09();

		String reportdate = "2022年10月2日(日)";
		String reportname = "週報【デモ】";
		WebElement applicablereportcorrectionBtn = WebDriverUtils.getApplicableReportCorrectionBtn(reportdate,
				reportname);
		applicablereportcorrectionBtn.click();

		//scrollTo(250);
		//scrollTo(0);

		// 5秒待つ 
		Thread.sleep(5000);

		suffix = "01_レポート詳細画面遷移";

		getEvidence(instance, suffix);

		assertEquals("レポート登録 | LMS | LMS", getTitle());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() throws InterruptedException {
		// TODO ここに追加

		String suffix = null;

		//自分自身をインスタンス化して渡す
		Case09 instance = new Case09();

		//scrollTo(250);
		//scrollTo(0);

		// 学習項目を入力
		WebElement learningtopic = getLearningTopic();
		learningtopic.clear();
		learningtopic.sendKeys("ITリテラシー①7");

		//		WebElement comprehensionlevel = getComprehensionLevel();
		//		comprehensionlevel.clear();
		//		comprehensionlevel.sendKeys("ITリテラシー①7");

		WebElement goalachievement = getGoalAchievement();
		goalachievement.clear();
		goalachievement.sendKeys("52");

		WebElement impressions = getImpressions();
		impressions.clear();
		impressions.sendKeys("週報のサンプルです。2");

		WebElement reviewoftheweek = getReviewOfTheWeek();
		reviewoftheweek.clear();
		reviewoftheweek.sendKeys(
				"日報・週報などの入力項目は管理者権限で作成・変更することが可能です。「週報」の入力項目は管理画面のレポート作成機能を用いて設定し、登録されたレポートのフォーマットはデータベースの「m_daily_report」テーブルと「m_daily_report_detail」テーブルに登録されています2。");

		// 5秒待つ 
		Thread.sleep(5000);

		suffix = "01_報告内容を提出する(学習項目が未入力)";

		getEvidence(instance, suffix);

		assertEquals("レポート登録 | LMS", getTitle());
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// TODO ここに追加
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// TODO ここに追加
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// TODO ここに追加
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// TODO ここに追加
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// TODO ここに追加
	}

}
