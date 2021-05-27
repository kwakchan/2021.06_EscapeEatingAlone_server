package com.wpjm.escapeeatingalone.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wpjm.escapeeatingalone.Model.Menu
import com.wpjm.escapeeatingalone.Model.MenuDetailModel
import com.wpjm.escapeeatingalone.R
import com.wpjm.escapeeatingalone.databinding.ActivityMenuDetailBinding
import javax.security.auth.callback.Callback

class MenuDetailActivity : AppCompatActivity() {
    private var mBinding: ActivityMenuDetailBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)
        mBinding = ActivityMenuDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var menuDetailList= arrayListOf(
            MenuDetailModel(R.drawable.rice, "경성대부경대 우쭈쭈", "경성대 쭈꾸미집","한식"),
            MenuDetailModel(R.drawable.rice, "미진축산", "경성대 삼겹살집", "한식"),
            MenuDetailModel(R.drawable.rice, "영진돼지국밥", "경성대 돼지국밥", "한식"),
            MenuDetailModel(R.drawable.rice, "차이나타운", "경성대 중국집", "중식"),
            MenuDetailModel(R.drawable.rice, "겐로쿠", "경성대 우동", "일식"),
            MenuDetailModel(R.drawable.rice, "노랑통닭", "경성대 치킨", "치킨"),
            MenuDetailModel(R.drawable.rice, "미스터피자", "경성대 피자", "피자"),
            MenuDetailModel(R.drawable.rice, "멕시카나", "경성대 치킨", "치킨"),
            MenuDetailModel(R.drawable.rice, "보리밭", "경성대 밥골", "한식"),
            MenuDetailModel(R.drawable.rice, "청년다방", "경성대 분식", "분식"),
            MenuDetailModel(R.drawable.rice, "맥도날드", "경성대 맥도널드", "패스트푸드"),
            MenuDetailModel(R.drawable.rice, "스타벅스", "경성대 스타벅스", "카페"),
            MenuDetailModel(R.drawable.rice, "동대문엽기떡볶이", "경성대 엽떡", "패스트푸드")
        )

        var filtedMenuDetailList = menuDetailList.filter {
            it.type.equals(intent.getStringExtra("MenuType").toString())
        }

        binding.meniDetailActivityRecyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.meniDetailActivityRecyclerView.setHasFixedSize(true)
        binding.meniDetailActivityRecyclerView.adapter=MenuDetailAdapter(filtedMenuDetailList as ArrayList<MenuDetailModel>)
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }



}