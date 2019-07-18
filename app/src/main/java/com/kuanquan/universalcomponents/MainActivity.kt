package com.kuanquan.universalcomponents

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.base.library.utils.LogUtil
import com.kuanquan.mine.MineActivity
import com.kuanquan.mine.MyReceivingAddressActivity
import com.kuanquan.universalcomponents.dialog.BottomDialog
import com.kuanquan.universalcomponents.kotlinTest.adapter.AdapterActivity
import com.kuanquan.universalcomponents.kotlinTest.TestActivity
import com.kuanquan.universalcomponents.main.HomeActivity
import com.kuanquan.universalcomponents.main.SearchActivity
import com.kuanquan.universalcomponents.rx.RxActivity
import com.kuanquan.universalcomponents.slide.GuideActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * https://blog.csdn.net/qq_26287435/article/details/82015218   kotlin 点击事件写法
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 获取控件的写法
        val btn: Button = findViewById(R.id.jump_rx)
        val adapter = findViewById<Button>(R.id.jump_adapter)
        btn.setOnClickListener(this)
        adapter.setOnClickListener(this)
        // 点击事件的写法
//        btn.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(v: View?) {
//
//            }
//        })

        // 省略 findViewById 的写法，可以直接那 TextView 的 id 直接使用
        // import kotlinx.android.synthetic.main.activity_home.*  整体导入布局即可
        text_view.text = "哈哈"

        jump_test.setOnClickListener(this)
        jump_home.setOnClickListener(this)
        button.setOnClickListener(this)
        dialog.setOnClickListener(this)
        slide.setOnClickListener(this)
        address.setOnClickListener(this)
        mine.setOnClickListener(this)
    }

    // 这里的 Unit 可以省略不写
    override fun onClick(v: View?): Unit {
        when (v?.id) {
            R.id.jump_rx -> {
                val intent = Intent(this, RxActivity::class.java)
                startActivity(intent)
            }
            R.id.jump_adapter -> {
                LogUtil.e("主页的点击事件")
                val intent = Intent(this, AdapterActivity::class.java)
                startActivity(intent)
            }

            R.id.jump_test -> {
                val intent = Intent(this, TestActivity::class.java)
                intent.putExtra("aa","哈哈大笑")
                startActivity(intent)
            }
            R.id.jump_home -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            R.id.button -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.dialog -> {
//                val intent = Intent(this, CouponActivity::class.java)
//                startActivity(intent)
                BottomDialog(this).builder().show()
            }
            R.id.slide -> {
                val intent = Intent(this, GuideActivity::class.java)
                startActivity(intent)
            }
            R.id.address -> {
                val intent = Intent(this, MyReceivingAddressActivity::class.java)
                startActivity(intent)
            }
            R.id.mine -> {
                val intent = Intent(this, MineActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
