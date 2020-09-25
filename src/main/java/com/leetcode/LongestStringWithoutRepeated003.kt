package com.leetcode

import org.apache.commons.lang3.time.StopWatch
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.MutableSet
import kotlin.collections.set
import kotlin.math.max


/**
 * Created by WangZiHe on 2020/9/25
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 * Github:https://github.com/yancheng199287
 */

/*给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。

示例 1:

输入: "abcabcbb"
输出: 3
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
示例 2:

输入: "bbbbb"
输出: 1
解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
示例 3:

输入: "pwwkew"
输出: 3
解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/
object LongestStringWithoutRepeated003 {


    // 我这个憨憨写的，直接求出字符串了 审题失败，勉强过关
    fun findChildString1(str: String, startIndex: Int = 0): String {
        var len = str.length
        var ifSame = false
        var lastChar = ' '

        // 上一个字符串，不重复的，缓存比较
        var lastString = ""
        var resultString = ""
        var currentSB = StringBuilder()
        for (index in startIndex until len) {
            val char = str[index]
            //上次已经找到相同 ，这次是来找相邻不同的字符   aaabbc   第一个第二个a相同  要找到b的索引
            if (ifSame) {
                //如果不同，就找到b
                if (lastChar == char) {
                    continue
                } else {
                    //找到新的不重复字符
                    lastChar = char
                    //回归正常
                    ifSame = false
                }
            }

            // println("currentSB:${currentSB.toString()}")
            //如果当前子字符串，发现右边有个重复的，则
            if (currentSB.contains(char)) {
                ifSame = true
                lastChar = char
                //这里因为找到一个重复的字符，保留上一次未重复的子字符串
                lastString = currentSB.toString()
                currentSB.clear()
                currentSB.append(lastString[lastString.lastIndex])
                if (lastString.length > resultString.length) {
                    resultString = lastString
                }
            } else {
                currentSB.append(char)
            }
        }
        return resultString
    }


    fun findChildString2(str: String): Int {
        val hashMap = HashMap<Char, Int>()
        var leftIndex = 0
        var maxLen = 0
        for (rightIndex in str.indices) {
            val currentChar = str[rightIndex]
            if (hashMap.containsKey(currentChar)) {
                leftIndex = max(leftIndex, hashMap[currentChar]!!.plus(1))    //更新左边界L
            }
            hashMap[currentChar] = rightIndex
            maxLen = max(maxLen, rightIndex - leftIndex + 1) //更新最大长度记录
        }
        return maxLen
    }


    // 对方法2 使用map进行了优化升级，巧妙使用 下标唯一性对应重复的字符，避开了使用map 提升了性能
    // 这个是取巧的 不适用于非ASCII码字符！！！ 谨慎使用
    fun lengthOfLongestSubstring3(s: String): Int {
/*
        a 转换十进制97    97 映射字符数组下标  取 a的索引 ，下一次重复出现的a可以直接拿到上次索引值，如果比前面大，就赋值给左指针，右边指针则取每次减去左指针，获取最新长度和之前的长度进行比较取值
*/
        // 记录字符上一次出现的位置  128个ASCII码
        val last = IntArray(128)
        for (i in 0..127) {
            last[i] = -1
        }
        val n = s.length
        var res = 0
        var start = 0 // 窗口开始位置
        for (rightIndex in 0 until n) {
            // 将字母元素转换成十进制
            val index = s[rightIndex].toInt()

            // 通过十进制的下标找到对应的值，默认是-1，这里+1  则是0
            start = max(start, last[index] + 1)

            // 当前字符串下标减去start+1，类似右边的索引减去左边的索引，跟上面hash非常相似
            res = max(res, rightIndex - start + 1)

            // 将当前的索引值存在 元素字符下标， 因为字符可能存在相同的哦，下次可以直接取出来找到这个字符下标位置
            last[index] = rightIndex
        }
        return res
    }

    // 官网推荐算法
    fun lengthOfLongestSubstring4(s: String): Int {
        // 哈希集合，记录每个字符是否出现过
        val occ: MutableSet<Char> = HashSet()
        val n = s.length
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        var rk = -1
        var ans = 0
        for (i in 0 until n) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s[i - 1])
            }
            while (rk + 1 < n && !occ.contains(s[rk + 1])) {
                // 不断地移动右指针
                occ.add(s[rk + 1])
                ++rk
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1)
        }
        return ans
    }


}


fun main() {
    // val str = randomChar(15)
    // val str = "aabbc"
    val str = "abcabcbb"
    // val str = "pwwkew"
    // val str="bbbbb"
    println("目标查找字符串： $str")
    val watch = StopWatch.createStarted()
    val r = LongestStringWithoutRepeated003.findChildString1(str, 0)
    watch.stop()
    println("耗时：${watch.time} result: ${r}")
}


fun randomChar(len: Int): String {
    val letter = "abcdefghijklmnopqrstuvwxyz"
    val sb = StringBuilder()
    for (i in 0 until len) {
        sb.append(letter[(letter.indices).random()])
    }
    return sb.toString()
}