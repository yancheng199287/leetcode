package com.leetcode

import com.leetcode.SumTwoNum.twoSum1
import com.leetcode.SumTwoNum.twoSum2
import com.leetcode.SumTwoNum.twoSum3
import com.leetcode.SumTwoNum.twoSum4
import com.leetcode.SumTwoNum.twoSum5
import org.apache.commons.lang3.time.StopWatch
import java.util.HashMap
import java.util.concurrent.TimeUnit


/**
 * Created by WangZiHe on 2020/9/24
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 * Github:https://github.com/yancheng199287
 */

/*给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。

示例:
给定 nums = [2, 7, 11, 15], target = 9
因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/two-sum
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*/

/*方法1 使用遍历

方法2 先排序再遍历  待优化，根据目标值在数组 1/4  1/2 1/4 各取样对比，找到合适的区间然后再去循环，充分利用排序的特性

方法3 双指针

方法四 map找差值*/
object SumTwoNum {


    //数据越大效果越好  四种算法按优先级   3<1<2<4   4是map最差，主要在hash慢

    val result = IntArray(2)

    /**
     * 方法1 使用遍历
     * */
    @JvmStatic
    fun twoSum1(nums: IntArray, target: Int): IntArray {
        val len = nums.size
        for (m in 0 until len) {
            for (n in 1 + m until len) {
                val left = nums[m]
                val right = nums[n]
                val sum = left + right
                // println("$left + $right = ${sum} ")
                if (sum == target) {
                    // println("恭喜，中奖啦，结果：[$m,$n]")
                    return intArrayOf(m, n)
                }
            }
        }
        return result
    }


    /**
     *  方法2 先排序再遍历，后面比target大值可以直接忽略
     * **/
    @JvmStatic
    fun twoSum2(nums: IntArray, target: Int): IntArray {
        nums.sort()
        val len = nums.size
        for (m in 0 until len) {
            val left = nums[m]
            if (left >= target) {
                return result;
            }
            for (n in 1 + m until len) {
                val left = nums[m]
                val right = nums[n]
                val sum = left + right
                // println("$left + $right = ${sum} ")
                if (sum == target) {
                    // println("恭喜，中奖啦，结果：[$m,$n]")
                    return intArrayOf(m, n)
                }
            }
        }
        return result
    }


    /**
     *  方法2 先排序再遍历，后面比target大值可以直接忽略
     * **/
    @JvmStatic
    fun twoSum3(nums: IntArray, target: Int): IntArray {
        nums.sort()
        val len = nums.size
        var m = 0;
        var n = len - 1
        while (m < n) {
            when {
                nums[m] + nums[n] == target -> {
                    result[0] = m
                    result[1] = n
                    return result
                }
                nums[m] + nums[n] < target -> {
                    m++
                }
                else -> {
                    n--
                }
            }
        }
        return result
    }


    @JvmStatic
    fun twoSum4(nums: IntArray, target: Int): IntArray {
        val map: HashMap<Int, Int> = HashMap<Int, Int>((nums.size / 0.75).toInt())
        val len = nums.size
        for (m in 0 until len) {
            map[nums[m]] = m
        }

        for (m in 0 until len) {
            val s = target - nums[m]
            if (map.containsKey(s)) {
                result[0] = map.get(s)!!
                result[1] = m
                return result
            }
        }
        return result
    }


    @JvmStatic
    fun twoSum5(nums: IntArray, target: Int, map: HashMap<Int, Int>): IntArray {
        val len = nums.size


        for (m in 0 until len) {
            val s = target - nums[m]
            if (map.containsKey(s)) {
                result[0] = map.get(s)!!
                result[1] = m
                return result
            }
        }
        return result
    }
}


//  在中间  3 4 12
//在左边 4

fun main() {
    val len = 20 * 10000
    var nums = IntArray(len)
    val target = 65557898
    for (i in 0 until len) {
        nums[i] = (0..len).random()
    }
    nums[len / 2 - 10000] = 55566660
    nums[len / 2 + 10000] = 9991238

    //  nums = intArrayOf(10, 20, 30, 40, 50, 60, 701, 200, 800)

    // println(nums.contentToString())

    val watch = StopWatch.createStarted()
    val result1 = twoSum1(nums, target)
    // println(nums.contentToString())
    watch.stop()
    println("耗时： ${watch.time}毫秒  index:${result1.contentToString()}  result1:  ${nums[result1[0]]} + ${nums[result1[1]]} =? $target")

    watch.reset()
    watch.start()
    val result2 = twoSum2(nums, target)
    // println(nums.contentToString())
    println("耗时： ${watch.time}毫秒 index:${result2.contentToString()} result2:  ${nums[result2[0]]} + ${nums[result2[1]]} =? $target")

    watch.reset()
    watch.start()
    val result3 = twoSum3(nums, target)
    // println(nums.contentToString())
    println("耗时： ${watch.time}毫秒 index:${result3.contentToString()} result3:  ${nums[result3[0]]} + ${nums[result3[1]]} =? $target")


    val map: HashMap<Int, Int> = HashMap<Int, Int>((nums.size / 0.75).toInt())
    for (m in 0 until len) {
        map[nums[m]] = m
    }
    watch.reset()
    watch.start()
    val result4 = twoSum5(nums, target, map)
    watch.stop()
    // println(nums.contentToString())
    println("耗时： ${watch.getTime()}毫秒 ${watch.getTime(TimeUnit.MICROSECONDS)}微秒 index:${result4.contentToString()} result4:  ${nums[result4[0]]} + ${nums[result4[1]]} =? $target")

}







