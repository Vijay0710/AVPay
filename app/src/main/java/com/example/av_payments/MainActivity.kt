package com.example.av_payments


import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import android.os.Bundle
import android.service.autofill.UserData
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var customerList: ArrayList<UserModelClass>? = null
    lateinit var toggle: ActionBarDrawerToggle
    private var itemAdapter: ItemStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val drawerLayout : DrawerLayout = findViewById(R.id.my_drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)

        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_add_user -> Toast.makeText(
                    applicationContext,
                    "Clicked Add user",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_remove_user -> Toast.makeText(
                    applicationContext,
                    "Clicked Remove user",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_update_details -> Toast.makeText(
                    applicationContext,
                    "Clicked update details",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_share -> Toast.makeText(
                    applicationContext,
                    "Clicked Share",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_rate_us -> Toast.makeText(
                    applicationContext,
                    "Clicked rate us",
                    Toast.LENGTH_SHORT
                ).show()
            }

            true
        }

        val db = DatabaseHandler(this)
        if(db.GetRowCount()<1){
            var customerList = ArrayList<UserModelClass>()
            val customer1 = UserModelClass(
            1,
            "Iron Man",
            "IronMan@gmail.com",
            25000,
            DbBitmapUtility.getBytes(DbBitmapUtility.drawableToBitmap(resources.getDrawable(R.drawable.iron_main)))
        )

        val user2 = UserModelClass(
            2,
            "Captain America",
            "CaptainAmerica@gmail.com",
            125,
            DbBitmapUtility.getBytes(DbBitmapUtility.drawableToBitmap(resources.getDrawable(R.drawable.captain_america)))
        )

        val user3 = UserModelClass(
            3,
            "Black Panther",
            "BlackPanther@gmail.com",
            125,
            DbBitmapUtility.getBytes(DbBitmapUtility.drawableToBitmap(resources.getDrawable(R.drawable.black_panther)))
        )

        val user4 = UserModelClass(
            4,
            "Nayanthara",
            "Nayanthara@gmail.com",
            250,
            DbBitmapUtility.getBytes(DbBitmapUtility.drawableToBitmap(resources.getDrawable(R.drawable.nayanthara)))
        )

        val user5 = UserModelClass(
            5,
            "Ant Man",
            "AntMan@gmail.com",
            45500,
            DbBitmapUtility.getBytes(DbBitmapUtility.drawableToBitmap(resources.getDrawable(R.drawable.ant_man)))
        )

        val user6 = UserModelClass(
            6,
            "Sergio",
            "Sergio01@gmail.com",
            75500,
            DbBitmapUtility.getBytes(DbBitmapUtility.drawableToBitmap(resources.getDrawable(R.drawable.sergio)))
        )

        val user7 = UserModelClass(
            7,
            "Natasha",
            "natasharomanoff@gmail.com",
            25500,
            DbBitmapUtility.getBytes(DbBitmapUtility.drawableToBitmap(resources.getDrawable(R.drawable.natasha)))
        )

        val user8 = UserModelClass(
            8,
            "Mark Ruffalow",
            "hulf@gmail.com",
            25500,
            DbBitmapUtility.getBytes(DbBitmapUtility.drawableToBitmap(resources.getDrawable(R.drawable.hulk)))
        )

        val user9 = UserModelClass(
            9,
            "Tokyo",
            "Tokyo01@gmail.com",
            30500,
            DbBitmapUtility.getBytes(DbBitmapUtility.drawableToBitmap(resources.getDrawable(R.drawable.tokyo)))
        )

        val user10 = UserModelClass(
            10,
            "Tom Hiddleston",
            "lokigodofMischief@gmail.com",
            55500,
            DbBitmapUtility.getBytes(DbBitmapUtility.drawableToBitmap(resources.getDrawable(R.drawable.loki)))
        )
        customerList.add(customer1)
        customerList.add(user2)
        customerList.add(user3)
        customerList.add(user4)
        customerList.add(user5)
        customerList.add(user6)
        customerList.add(user7)
        customerList.add(user8)
        customerList.add(user9)
        customerList.add(user10)
        val status = db.addCustomer(customerList)
        if (status <= -1) {
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }
        }
        customerList = db.viewUserList()
        setupUserListRecyclerView()
    }
    private fun setupUserListRecyclerView() {
        var rvUserList = findViewById<RecyclerView>(R.id.rvUserStatus)

        rvUserList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        itemAdapter = ItemStatusAdapter(this, customerList!!)

        rvUserList.adapter = itemAdapter


    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if(toggle.onOptionsItemSelected(item)){
                return true
            }

            return super.onOptionsItemSelected(item)
        }
}

