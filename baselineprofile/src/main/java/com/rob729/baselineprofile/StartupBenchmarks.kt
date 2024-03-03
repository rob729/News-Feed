package com.rob729.baselineprofile

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern
import kotlin.random.Random

/**
 * This test class benchmarks the speed of app startup.
 * Run this benchmark to verify how effective a Baseline Profile is.
 * It does this by comparing [CompilationMode.None], which represents the app with no Baseline
 * Profiles optimizations, and [CompilationMode.Partial], which uses Baseline Profiles.
 *
 * Run this benchmark to see startup measurements and captured system traces for verifying
 * the effectiveness of your Baseline Profiles. You can run it directly from Android
 * Studio as an instrumentation test, or run all benchmarks with this Gradle task:
 * ```
 * ./gradlew :baselineprofile:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.androidx.benchmark.enabledRules=Macrobenchmark
 * ```
 *
 * You should run the benchmarks on a physical device, not an Android emulator, because the
 * emulator doesn't represent real world performance and shares system resources with its host.
 *
 * For more information, see the [Macrobenchmark documentation](https://d.android.com/macrobenchmark#create-macrobenchmark)
 * and the [instrumentation arguments documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args).
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class StartupBenchmarks {

    @get:Rule
    val rule = MacrobenchmarkRule()

    @Test
    fun startupCompilationNone() =
        startupBenchmark(CompilationMode.None())

    @Test
    fun startupCompilationBaselineProfiles() =
        startupBenchmark(CompilationMode.Partial(BaselineProfileMode.Require))

    @Test
    fun scrollCompilationNone()  = scrollAndNavigate(CompilationMode.None())

    @Test
    fun scrollCompilationBaselineProfiles()  = scrollAndNavigate(CompilationMode.Partial(BaselineProfileMode.Require))

    @Test
    fun searchScrollAndNavigateCompilationModeNone()  = searchScrollAndNavigate(CompilationMode.None())

    @Test
    fun searchScrollAndNavigateCompilationBaselineProfiles() = searchScrollAndNavigate(CompilationMode.Partial(BaselineProfileMode.Require))

    private fun startupBenchmark(compilationMode: CompilationMode) {
        rule.measureRepeated(
            packageName = "com.rob729.newsfeed",
            metrics = listOf(StartupTimingMetric()),
            compilationMode = compilationMode,
            startupMode = StartupMode.COLD,
            iterations = 5,
            setupBlock = {
                pressHome()
            },
            measureBlock = {
                startActivityAndWait()
            }
        )
    }

    private fun scrollAndNavigate(mode: CompilationMode) {
        rule.measureRepeated(
            packageName = "com.rob729.newsfeed",
            metrics = listOf(FrameTimingMetric()),
            iterations = 5,
            startupMode = StartupMode.COLD,
            compilationMode = mode,
            setupBlock = {
                pressHome()
            }
        ) {
            startActivityAndWait()
            scrollAndNavigate()
        }
    }

    private fun searchScrollAndNavigate(mode: CompilationMode) {
        rule.measureRepeated(
            packageName = "com.rob729.newsfeed",
            metrics = listOf(FrameTimingMetric()),
            iterations = 5,
            startupMode = StartupMode.COLD,
            compilationMode = mode,
            setupBlock = {
                pressHome()
            }
        ) {
            startActivityAndWait()
            searchScrollAndNavigate()
        }
    }
}

fun MacrobenchmarkScope.scrollAndNavigate() {
    val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    if (uiDevice.findObject(UiSelector().textMatches("Allow")).exists()) {
        uiDevice.findObject(UiSelector().textMatches("Allow")).click()
    }

    val selector = By.res("news_list")
    if(!device.wait(Until.hasObject(selector), 3_500)) {
        TestCase.fail("Could not find news_list resource")
    }

    var list = device.findObject(selector)
    list.setGestureMargin(device.displayWidth / 5)
    repeat(3) {
        list.fling(Direction.DOWN)
    }
    list.fling(Direction.UP)

    val scrollUpButton = device.findObject(By.res("scroll_up_fab"))
    scrollUpButton?.click()

    val newsSourceButton = device.findObject(By.res("news_source_fab"))
    newsSourceButton.click()

    val selector1 = By.res("news_source_list")
    if(!device.wait(Until.hasObject(selector1), 3_500)) {
        TestCase.fail("Could not find news_source_list resource")
    }
    val newsSourceList = device.findObject(By.res("news_source_list"))

    val newsSourceChildIndex = Random.nextInt(1, newsSourceList.children.size)
    newsSourceList.children[newsSourceChildIndex].click()

    if(!device.wait(Until.hasObject(selector), 5_000)) {
        TestCase.fail("Could not find news_list resource")
    }
    list = device.findObject(selector)
    if (list.children.size > 0 && list.children[0] != null) {
        list.children[0].click()
        if(!device.wait(Until.hasObject(By.text(Pattern.compile("^.*com.*\$"))), 3_500)) {
            TestCase.fail("News details screen didn't opened")
        }
    }
}

fun MacrobenchmarkScope.searchScrollAndNavigate() {
    val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    if (uiDevice.findObject(UiSelector().textMatches("Allow")).exists()) {
        uiDevice.findObject(UiSelector().textMatches("Allow")).click()
    }

    val searchIconSelector = By.res("right_icon_1")
    if(!device.wait(Until.hasObject(searchIconSelector), 2_000)) {
        TestCase.fail("Could not find search icon resource")
    }
    val searchIcon = device.findObject(searchIconSelector)
    searchIcon.click()

    val searchInputTextFieldSelector = By.res("search_input_text_field")
    if(!device.wait(Until.hasObject(searchInputTextFieldSelector), 2_500)) {
        TestCase.fail("Search input text field not found on clicking search icon")
    }
    val searchInputTextField = device.findObject(searchInputTextFieldSelector)
    searchInputTextField.click()
    searchInputTextField.text = "Samsung"

    val searchResultNewsListSelector = By.res("search_result_news_list")
    if(!device.wait(Until.hasObject(searchResultNewsListSelector), 3_500)) {
        TestCase.fail("Search news list not found on clicking search icon")
    }
    val list = device.findObject(searchResultNewsListSelector)
    if (list.children.size > 0) {
        val searchResultItemIndex = Random.nextInt(list.children.size)
        list.children[searchResultItemIndex].click()
        if(!device.wait(Until.hasObject(By.text(Pattern.compile("^.*com.*\$"))), 3_500)) {
            TestCase.fail("News details screen didn't opened")
        }
    }
}
