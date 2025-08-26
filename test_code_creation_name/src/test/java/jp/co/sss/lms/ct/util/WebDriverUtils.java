package jp.co.sss.lms.ct.util;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

/**
 * Webドライバーユーティリティ
 * @author holy
 */
public class WebDriverUtils {

	/** Webドライバ */
	public static WebDriver webDriver;

	/**
	 * インスタンス取得
	 * @return Webドライバ
	 */
	public static void createDriver() {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		webDriver = new ChromeDriver();
	}

	/**
	 * インスタンス終了
	 */
	public static void closeDriver() {
		webDriver.quit();
	}

	/**
	 * 画面遷移
	 * @param url
	 */
	public static void goTo(String url) {
		webDriver.get(url);
		pageLoadTimeout(5);
	}

	/**
	 * ページロードタイムアウト設定
	 * @param second
	 */
	public static void pageLoadTimeout(int second) {
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(second));
	}

	/**
	 * 要素の可視性タイムアウト設定
	 * @param locater
	 * @param second
	 */
	public static void visibilityTimeout(By locater, int second) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(second));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
	}

	/**
	 * 指定ピクセル分だけスクロール
	 * @param pixel
	 */
	public static void scrollBy(int pixel) {
		//((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + pixel + ");");
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + pixel + ");");
	}

	/**
	 * 指定位置までスクロール
	 * @param pixel
	 */
	public static void scrollTo(int pixel) {
		((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0," + pixel + ");");
	}

	/**
	 * エビデンス取得
	 * @param instance
	 */
	public static void getEvidence(Object instance) {
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			String className = instance.getClass().getSimpleName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			Files.move(tempFile, new File("evidence\\" + className + "_" + methodName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * エビデンス取得（サフィックスあり）
	 * @param instance
	 * @param suffix
	 */
	public static void getEvidence(Object instance, String suffix) {
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			String className = instance.getClass().getSimpleName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			Files.move(tempFile, new File("evidence\\" + className + "_" + methodName + "_" + suffix + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * タイトル取得
	 * @return 
	 */
	public static String getTitle() {
		String title = webDriver.getTitle();
		return title;
	}

	/**
	 * ログインID取得
	 */
	public static WebElement getUserName() {
		WebElement username = webDriver.findElement(By.id("loginId"));
		return username;
	}

	/**
	 * パスワード取得
	 */
	public static WebElement getPassword() {
		WebElement password = webDriver.findElement(By.id("password"));
		return password;
	}

	/**
	 * ログインボタン取得
	 */
	public static WebElement getLoginBtn() {
		WebElement loginBtn = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		return loginBtn;
	}

	/**
	 * 機能タブ取得
	 */
	public static WebElement getFunctionTab() {
		WebElement functionTab = webDriver.findElement(By.cssSelector(".dropdown-toggle"));
		return functionTab;
	}

	/**
	 * ヘルプリンク取得
	 */
	public static WebElement getHelpLink() {
		WebElement helpLink = webDriver.findElement(By.linkText("ヘルプ"));
		return helpLink;
	}

	/**
	 * よくある質問リンク取得
	 */
	public static WebElement getFAQ() {
		WebElement FAQ = webDriver.findElement(By.linkText("よくある質問"));
		return FAQ;
	}

	/**
	 * キーワード検索取得
	 */
	public static WebElement getKeyword() {
		WebElement keyword = webDriver.findElement(By.cssSelector(".form-control"));
		return keyword;
	}

	/**
	 * 検索ボタン取得
	 */
	public static WebElement getSearchBtn() {
		WebElement searchBtn = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		return searchBtn;
	}

	/**
	 * クリアボタン取得
	 */
	public static void getClearBtn() {

		webDriver.findElement(By.xpath("//input[@value='クリア']")).click();
	}

	/**
	 * カテゴリー検索の「【研修関係】」情報取得
	 */
	public static WebElement getTrainingRelated() {
		WebElement trainingRelated = webDriver.findElement(By.partialLinkText("【研修関係】"));
		return trainingRelated;
	}

	/**
	 * 「キャンセル料・途中退校について」情報取得
	 */
	public static WebElement getSearchResultFAQ() {
		WebElement searchresultFAQ = webDriver.findElement(By.xpath("//span[text()='キャンセル料・途中退校について']"));
		return searchresultFAQ;
	}

	/**
	 * 「未提出について」情報取得 ★使用していない
	 */
	public static WebElement getNotSubmitted() {
		//WebElement notSubmitted = webDriver.findElement(By.xpath("//span[text()='未提出']"));
		//return notSubmitted;
		List<WebElement> elements = webDriver.findElements(By.xpath("//span[text()='未提出']"));
		if (elements.size() > 1) {
			WebElement notSubmitted = elements.get(2); // 0が最初の要素、1が2番目の要素
			// secondElementに対して操作を行う
			return notSubmitted;
		}

		return null;
	}

	/**
	 * 「未提出について」情報取得
	 */
	public static WebElement getDetailBtn() {
		//WebElement notSubmitted = webDriver.findElement(By.xpath("//span[text()='未提出']"));
		//WebElement detailBtn = webDriver
		//		.findElement(By.xpath("//input[@value='詳細']"));
		List<WebElement> elements = webDriver.findElements(By.xpath("//*[@class='btn btn-default']"));
		if (elements.size() > 1) {
			WebElement detailBtn = elements.get(3); // 0が最初の要素、1が2番目の要素
			// secondElementに対して操作を行う
			return detailBtn;
		}

		return null;
	}

	/**
	 * 「日報【デモ】を提出する」情報取得
	 */
	public static WebElement getDailyReportBtn() {
		WebElement dailyreportBtn = webDriver
				.findElement(By.xpath("//input[@value='日報【デモ】を提出する']"));
		//WebElement dailyreportBtn = webDriver
		//		.findElement(By.xpath("//input[@value='提出済み日報【デモ】を確認する']"));

		return dailyreportBtn;
	}

	/**
	 * 報告内容情報取得
	 */
	public static WebElement getReportContent() {
		WebElement reportContent = webDriver.findElement(By.id("content_0"));
		return reportContent;
	}

	/**
	 * 報告内容提出ボタン取得
	 */
	public static WebElement getSubmitBtn() {
		WebElement submitBtn = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		return submitBtn;
	}

	/**
	 * 次へボタン取得
	 */
	public static WebElement getNextBtn() {
		WebElement nextBtn = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		return nextBtn;
	}

	/**
	 * セキュリティ規約エラーメッセージ取得
	 */
	public static String getSecurityConsentErorrMessage() {

		WebElement element = webDriver.findElement(By.xpath("//div[text()='セキュリティ規約への同意は必須です。']"));
		String securityconsentErorrMessage = element.getText(); // テキストを取得
		return securityconsentErorrMessage;
	}

	/**
	 * ラジオボタン取得(非選択)
	 */
	public static WebElement getRadioNegativeBtn() {
		//WebElement radioBtn = webDriver.findElement(By.xpath("//input[value()='1']"));
		WebElement radionegativeBtn = webDriver.findElement(By.name("securityFlg"));

		//radionegativeBtn.click();
		Boolean isSelected = radionegativeBtn.isSelected();
		if (isSelected) {
			return null;
		}
		return radionegativeBtn;
	}

	/**
	 * ラジオボタン取得(選択)
	 */
	public static WebElement getRadioAcitveBtn() {
		//WebElement radioBtn = webDriver.findElement(By.xpath("//input[value()='1']"));
		WebElement radioacitveBtn = webDriver.findElement(By.name("securityFlg"));

		radioacitveBtn.click();
		Boolean isSelected = radioacitveBtn.isSelected();
		if (isSelected) {
			return radioacitveBtn;
		}

		return null;
	}

	/**
	 * 変更ボタン情報取得
	 */
	public static WebElement getChangeBtn() {
		List<WebElement> elements = webDriver.findElements(By.xpath("//*[@class='btn btn-primary']"));
		if (elements.size() > 1) {
			WebElement changeBtn = elements.get(1); // 0が最初の要素、1が2番目の要素
			// secondElementに対して操作を行う
			return changeBtn;
		}

		return null;
	}

	/**
	 * モーダル表示の変更ボタン情報取得
	 */
	public static WebElement getChangeModalBtn() {
		//		List<WebElement> elements = webDriver.findElements(By.xpath("//*[@class='btn btn-primary']"));
		//		if (elements.size() > 1) {
		//			WebElement changeBtn = elements.get(1); // 0が最初の要素、1が2番目の要素
		//			// secondElementに対して操作を行う
		//			return changeBtn;
		//		}
		//
		//		return null;

		WebElement changemodalBtn = webDriver.findElement(By.id("upd-btn"));
		return changemodalBtn;

	}

	/**
	 * 現在のパスワード取得
	 */
	public static WebElement getCurrentPassword() {
		WebElement currentpassword = webDriver.findElement(By.id("currentPassword"));
		return currentpassword;
	}

	/**
	 * 新しいパスワード取得
	 */
	public static WebElement getNewPassword() {
		WebElement newpassword = webDriver.findElement(By.id("password"));
		return newpassword;
	}

	/**
	 * 確認パスワード取得
	 */
	public static WebElement getCheckPassword() {
		WebElement checkpassword = webDriver.findElement(By.id("passwordConfirm"));
		return checkpassword;
	}

	/**
	 * 長いパスワードエラーメッセージ取得
	 */
	public static String getLongPasswordErorrMessage() {

		WebElement element = webDriver.findElement(By.xpath("//span[text()='パスワードの長さが最大値(20)を超えています。']"));
		String longpasswordErorrMessage = element.getText(); // テキストを取得
		return longpasswordErorrMessage;
	}

	/**
	 * ポリシーに合わないパスワードエラーメッセージ取得
	 */
	public static String getNotMatchPolicyErorrMessage() {

		WebElement element = webDriver.findElement(
				By.xpath("//span[text()='「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。']"));
		String notmatchpolicyErorrMessage = element.getText(); // テキストを取得
		return notmatchpolicyErorrMessage;
	}

	/**
	 * 一致しない確認パスワードエラーメッセージ取得
	 */
	public static String getNotMatchCheckErorrMessage() {

		WebElement element = webDriver.findElement(
				By.xpath("//span[text()='パスワードは英大文字、英小文字、数字の3文字種を混合させた8文字以上を入力してください。']"));
		String notmatchCheckErorrMessage = element.getText(); // テキストを取得
		return notmatchCheckErorrMessage;
	}

}
