package com.leetcode

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by WangZiHe on 2020/9/25
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 * Github:https://github.com/yancheng199287
 */

/*给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

示例：

输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 0 -> 8
原因：342 + 465 = 807

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/add-two-numbers
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/
object AddTwoNum002 {


    fun addTwoNumbers(l1: ListNode, l2: ListNode): ListNode {

        return ListNode(5);
    }
}

class ListNode(var value: Int) {
    var next: ListNode? = null


    fun hasNext(): Boolean {
        return next != null
    }

    fun nextValue(): Int {
        return value
    }

    override fun toString(): String {
        return "ListNode(value=$value, next=${next.toString()})"
        // return "ListNode(value=$value)"
    }

    fun toString1(): String {
        return "ListNode(value=$value)"
    }

}

fun main() {
    val len = 3
    val nodeArray = initData(len)
    var l1 = nodeArray[0]
    var l2 = nodeArray[1]

    val listNodeArray1 = transformList(l1, len)
    val listNodeArray2 = transformList(l2, len)

    println(listNodeArray1.toString())
    println(listNodeArray2.toString())

    val resultList = transformList(addTwoNumbers(l1, l2)!!, len)
    println(resultList.toString())


}

fun initData(len: Int): Array<ListNode> {
    var l1 = ListNode(random())
    var l2 = ListNode(random())
    val nodeArray = arrayOf(l1, l2)
    var lastL1Node = l1
    var lastL2Node = l2
    for (index in 0 until len - 1) {
        val nextNode1 = ListNode(random())
        lastL1Node.next = nextNode1
        lastL1Node = nextNode1

        val nextNode2 = ListNode(random())
        lastL2Node.next = nextNode2
        lastL2Node = nextNode2;
    }
    return nodeArray
}

fun random(): Int {
    return (0..9).random()
}


fun transformList(listNode: ListNode, len: Int): List<Int> {
    val listNodeArray = ArrayList<Int>(len)
    var iteratorNode = listNode
    while (true) {
        val v = iteratorNode.nextValue()
        listNodeArray.add(v)
        if (iteratorNode.next != null) {
            iteratorNode = iteratorNode.next!!
        } else {
            break
        }
    }
    return listNodeArray
}

fun addTwoNumbers(l1: ListNode, l2: ListNode): ListNode? {
    var iteratorNode1 = l1
    var iteratorNode2 = l2

    var rootResultListNode: ListNode? = null

    var lastListNode: ListNode? = null


    var remain = 0
    while (true) {
        val v1 = iteratorNode1.nextValue()
        val v2 = iteratorNode2.nextValue()

        var oneDigit = 0
        var sum = v1 + v2 + remain

        if (sum < 10) {
            oneDigit = sum
            remain = 0
        } else if (sum == 10) {
            oneDigit = 0
            remain = 1
        } else if (sum > 10) {
            oneDigit = sum - 10
            remain = 1
        }

        val c = ListNode(oneDigit)
        if (rootResultListNode == null) {
            rootResultListNode = c
            lastListNode = rootResultListNode
        } else {

            lastListNode!!.next = c
            lastListNode = c
        }

        if (iteratorNode1.next != null) {
            iteratorNode1 = iteratorNode1.next!!
        } else {
            if (remain > 0) {
                lastListNode.next = ListNode(remain)
            }
            break
        }
        if (iteratorNode2.next != null) {
            iteratorNode2 = iteratorNode2.next!!
        } else {
            break
        }

    }
    return rootResultListNode
}
