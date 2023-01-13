package com.hujiayucc.demo

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    /** 参与者列表 */
    val array = arrayOf("晚风依旧",
        "b1ackmarket","DJY","长江233666","Mutx163",
        "腾哈哈","木年璟惜","Horatio","chenhufei","jkjk6862",
        "千","永燃之心","人间蒸发ing","触动那一刻","难忆安然","2005050510",
        "科技微学","苏浅陌","玖牧泽","nmmmea","FEC_Sky","憨憨在藤希","小55555",
        "同关如晨","辰迷星海","donghaiswat","Focalors","Xiao636","宵宫","滑稽world",
        "晚枫\uD83C\uDF41","江书韵","mouse","千灯.","9898730","AllKO","djcyjc","liwezee",
        "Jerry Zhou","鱼耆","碳水化合物饮料","墨_灬","羊小橙n","雾起群山隐"
    )
    val list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        Thread {
            var i = 0
            for (name in array) {
                list.add(name)
                if (textView.text.length < 1)
                    textView.text = "参与者名单：\n$name"
                else
                    textView.text = "${textView.text}、$name"
                i++
                if (i == array.size) textView.text = "${textView.text}\n共计：${i}人"
            }
        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.start -> {
                var i = 0
                Thread {
                    while (i < 9) {
                        if (i != 8) Thread.sleep(5000)
                        val name = list.get(Radom(list.size))
                        winning(name,i)
                        remove(name)
                        i++
                    }
                }.start()
            }
            else -> {}
        }
        return true
    }

    /**
     * 中奖者
     * @param name 中奖者名字
     * @param rank 中奖名次
     */
    fun winning(name: String, rank: Int) {
        when (rank) {
            0 -> {
                runOnUiThread { textView.text = "${textView.text}\n\n一等奖：$name" }
            }
            1,2 -> {
                runOnUiThread { textView.text = "${textView.text}\n二等奖：$name" }
            }
            3,4,5,6,7 -> {
                runOnUiThread { textView.text = "${textView.text}\n三等奖：$name" }
            }
            else -> {
                runOnUiThread { textView.text = "${textView.text}\n\n开奖完成" }
                copyText(textView.text)
            }
        }
    }

    /**
     *  获取随机数
     *  @param bound 数字范围
     *  @return 0-bound 之间的随机数
     */
    fun Radom(bound: Int): Int {
        val random = Random()
        return random.nextInt(bound)
    }

    /** 防止重复中奖 */
    fun remove(name: String) {
        while (list.indexOf(name) != -1) {
            list.remove(name)
        }
    }

    /**
     * 复制文本
     */
    fun copyText(text: CharSequence) {
        val cmb: ClipboardManager = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cmb.setPrimaryClip(ClipData.newPlainText(null, text))
    }
}