package com.rob729.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import junit.framework.TestCase.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startUpCompilationModeNone()  = startup(CompilationMode.None())

    @Test
    fun startUpCompilationModePartial()  = startup(CompilationMode.Partial())

    @Test
    fun scrollCompilationModeNone()  = scrollAndNavigate(CompilationMode.None())

    @Test
    fun scrollCompilationModePartial()  = scrollAndNavigate(CompilationMode.Partial())


    fun startup(mode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.rob729.newsfeed",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode,
        setupBlock = {
            pressHome()
        }
    ) {
        startActivityAndWait()
    }

    fun scrollAndNavigate(mode: CompilationMode) = benchmarkRule.measureRepeated(
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

fun MacrobenchmarkScope.scrollAndNavigate() {
    val selector = By.res("news_list")
    if(!device.wait(Until.hasObject(selector), 2_500)) {
        fail("Could not find news_list resource")
    }

    var list = device.findObject(selector)
    list.setGestureMargin(device.displayWidth / 5)
    repeat(3) {
        list.fling(Direction.DOWN)
    }
    list.fling(Direction.UP)

    val scrollUpButton = device.findObject(By.res("scroll_up_fab"))
    scrollUpButton.click()

    val newsSourceButton = device.findObject(By.res("news_source_fab"))
    newsSourceButton.click()

    val selector1 = By.res("news_source_list")
    if(!device.wait(Until.hasObject(selector1), 1_500)) {
        fail("Could not find news_source_list resource")
    }
    val newsSourceList = device.findObject(By.res("news_source_list"))
    if(newsSourceList.children != null && newsSourceList.children.size > 7 && newsSourceList.children[0] != null) {
        newsSourceList.children[3].click()
    }

    if(!device.wait(Until.hasObject(selector), 2_500)) {
        fail("Could not find news_list resource")
    }
    list = device.findObject(selector)
    if (list.children != null && list.children.size > 0 && list.children[0] != null) {
        list.children[0].click()
        if(!device.wait(Until.hasObject(By.text(Pattern.compile("^.*com.*\$"))), 3_500)) {
            fail("News details screen didn't opened")
        }
    }
}