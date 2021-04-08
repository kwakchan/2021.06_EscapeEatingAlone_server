package com.wpjm.escapeeatingalone.Activity

import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.wpjm.escapeeatingalone.R
import com.wpjm.escapeeatingalone.databinding.ActivityMainBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.Source
import com.wpjm.escapeeatingalone.Model.MemberInfo
import com.wpjm.escapeeatingalone.Model.Menu
import java.lang.reflect.Member

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    private val user = FirebaseAuth.getInstance().currentUser
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name=""
        db.collection("users").document(user!!.getUid()).get()
            .addOnSuccessListener { result ->
                    name=result["name"] as String
                    binding.naviView.menu.findItem(R.id.profile).setTitle(name)


            }

        // 기본 시작 주기
        if (user == null) { // 파이어베이스 유저가 존재하지 않으면
            gotoActivity(SignupActivity::class.java)
        } else { // 파이어베이스 유저가 존재하면
            val docRef: DocumentReference = db.collection("users").document(user.getUid())
            docRef.get().addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null){
                        if (document!!.exists()) { // 개인정보가 존재하면

                            // cloud firestore로부터 이름 읽어오기
                            Log.e("name", "${document.data}")
                          //  binding.mainActivityTextviewName.setText(document.id)
                        } else { // 개인정보가 존재하지 않으면
                            gotoActivity(MemberInitActivity::class.java)
                        }
                    }
                } else {
                    Log.e("msg", "에러")
                }
            }

        }

        binding.btnNavi.setOnClickListener{
            binding.layoutDrawer.openDrawer(GravityCompat.START)

        }
        binding.naviView.setNavigationItemSelectedListener(this)


        binding.mainActivityButtonFindmenu.setOnClickListener(View.OnClickListener {
            gotoActivity(MenuActivity::class.java)
        })

        // 가게명으로 찾기 눌렀을 때
        binding.mainActivityButtonFindname.setOnClickListener(View.OnClickListener {
            gotoActivity(MapNaverActivity::class.java)
        })

        // 자유게시판 눌렀을 때
        binding.mainActivityButtonFreeboard.setOnClickListener(View.OnClickListener {
            gotoActivity(BoardActivity::class.java)
        })

        // 채팅하기 버튼 눌렀을 때
        binding.mainActivityButtonChatting.setOnClickListener(View.OnClickListener {
            gotoActivity(ChatActivity::class.java)
        })

    }

    // Intent function
    private fun gotoActivity(c: Class<*>) {
        var intent9 = Intent(this, c)
        startActivity(intent9)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.menu -> gotoActivity(MenuActivity::class.java)
            R.id.restaurant -> gotoActivity(MapNaverActivity::class.java)
            R.id.chatting -> gotoActivity(ChatActivity::class.java)
            R.id.community -> gotoActivity(BoardActivity::class.java)
            R.id.logout ->{
                FirebaseAuth.getInstance().signOut()
                val intent2 = Intent(this, LoginActivity::class.java)
                startActivity(intent2)
                finish()
            }

        }
        binding.layoutDrawer.closeDrawers()
        return false
    }

    override fun onBackPressed() {
        if (binding.layoutDrawer.isDrawerOpen(GravityCompat.START)){
            binding.layoutDrawer.closeDrawers()
        }

        super.onBackPressed()
    }

    // 해쉬값 구하기
    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }

}