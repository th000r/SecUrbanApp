package de.tudarmstadt.smartcitystudyapp.ui.welcome

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import dagger.hilt.android.AndroidEntryPoint
import de.tudarmstadt.smartcitystudyapp.MainActivity
import de.tudarmstadt.smartcitystudyapp.R
import de.tudarmstadt.smartcitystudyapp.featuremanager.FeatureManager
import de.tudarmstadt.smartcitystudyapp.models.TeamModel
import de.tudarmstadt.smartcitystudyapp.models.UserModel
import de.tudarmstadt.smartcitystudyapp.interfaces.TeamServiceInterface
import de.tudarmstadt.smartcitystudyapp.interfaces.UserServiceInterface
import de.tudarmstadt.smartcitystudyapp.interfaces.UsersAndTeamServiceInterface
import de.tudarmstadt.smartcitystudyapp.matomo.MatomoCategory
import de.tudarmstadt.smartcitystudyapp.matomo.MatomoTracker
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Class structure taken from tutorial at http://www.androidhive.info/2016/05/android-build-intro-slider-app/
 */
@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    @Inject
    lateinit var userServiceInterface: UserServiceInterface
    @Inject
    lateinit var teamServiceInterface: TeamServiceInterface
    @Inject
    lateinit var usersAndTeamServiceInterface: UsersAndTeamServiceInterface
    private var viewPager: ViewPager? = null
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private var dotsLayout: LinearLayout? = null
    private lateinit var dots: Array<TextView?>
    private lateinit var layouts: IntArray
    private var btnNext: Button? = null
    private var userId: String? = null
    private var userName: String? = null
    private var userCity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_welcome)

        //Matomo
        MatomoTracker.setParams(MatomoCategory.WELCOME, "/welcome")
        MatomoTracker.initFragment()

        viewPager = findViewById<View>(R.id.view_pager) as ViewPager
        dotsLayout = findViewById<View>(R.id.layoutDots) as LinearLayout
        btnNext = findViewById<View>(R.id.btn_next) as Button
        layouts = intArrayOf(
            R.layout.tutorial_slide_1,
            R.layout.tutorial_slide_2,
            R.layout.tutorial_slide_3
        )

        if (FeatureManager.readFeatures(applicationContext).preinvestigation == true) {
            val array = layouts.copyOf(layouts.size + 1)
            array[layouts.size] = R.layout.preinvestigation
            layouts = array
        }

        addBottomDots(0)
        changeStatusBarColor()
        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager!!.adapter = myViewPagerAdapter
        viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)

        btnNext!!.setOnClickListener {
            val current = getNextItem()
            if (current == layouts.size) {
                val userIdEntryField = findViewById<EditText>(R.id.user_id_entry_field)
                userId = userIdEntryField.text.toString()
                val userNameEntryField = findViewById<EditText>(R.id.user_name_entry_field)
                userName = userNameEntryField.text.toString()
                val userCityEntryField = findViewById<EditText>(R.id.city_entry_field)
                userCity = userCityEntryField.text.toString()
                launchHomeScreen()
            } else if (current < layouts.size) {
                viewPager!!.currentItem = current
            }
        }
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        dotsLayout!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226;")
            dots[i]!!.textSize = 35f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            dotsLayout!!.addView(dots[i])
        }
        if (dots.isNotEmpty()) dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    private fun getNextItem(): Int {
        return viewPager!!.currentItem + 1
    }

    private fun launchHomeScreen() {
        if (userId == null || userId!!.isEmpty() || userId!!.length != 6) {
            val toast = Toast.makeText(this, R.string.user_id_not_set_toast, Toast.LENGTH_SHORT)
            toast.show()
        } else if(userName == null || userName!!.isEmpty()) {
                val toast = Toast.makeText(this, R.string.user_name_not_set_toast, Toast.LENGTH_SHORT)
                toast.show()
        } else if(userCity == null || userCity!!.isEmpty()) {
            val toast = Toast.makeText(this, R.string.user_city_not_set_toast, Toast.LENGTH_SHORT)
            toast.show()
        } else {
            this.lifecycleScope.launch {
                usersAndTeamServiceInterface.addTeam(TeamModel(userCity!!.toLowerCase(), userCity!!.toLowerCase(), 0))
                usersAndTeamServiceInterface.addUser(UserModel(userId!!, userName!!, userCity!!, 0, userCity!!.toLowerCase()))
                //userService.setUser(User(userId!!, userName!!, 0, userCity!!.toLowerCase()))
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val toast = Toast.makeText(this, R.string.user_id_not_set_toast, Toast.LENGTH_SHORT)
        toast.show()
    }

    //  viewpager change listener
    private var viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            addBottomDots(position)
            if (position == layouts.size - 1) {
                btnNext!!.text = getString(R.string.start)
            } else {
                btnNext!!.text = getString(R.string.next)
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater!!.inflate(layouts[position], container, false)
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}