# Android 实现导航栏
*导航栏是每一个app都需要的一部分，本片文章会介绍导航栏的一种实现方法。*  
需要提前了解的知识有：   

 + Fragment  
 + RadioButton
 + TabLayout
 + PagerAdapter

## RadioGroup + RadioButton
### 1.一些底部选项资源文件
图片资源文件：*tab_menu_chat.xml*

```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@mipmap/tab_chat_checked" android:state_checked="true" />
    <item android:drawable="@mipmap/tab_chat_uncheck" />
</selector>
```
&emsp;剩下的其他图片是一样的写法。  

文字资源文件：*tab_menu_text.xml*

```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:color="#EF8555" android:state_checked="true" />
    <item android:color="#000000" />
</selector>
```
### 2.编写activity布局
&emsp;&emsp;因为每一个RadioButton的属性都差不多，所以可以把相同属性提取出来放在 *style.xml* 当中。

```
<!-- Tab Base Style -->
    <style name="tab_menu_item">
        <item name="android:layout_width">0dp</item>
        <item name="android:layout_weight">1</item>
        <item name="android:layout_height">match_parent</item>
        <item name="android:button">@null</item>
        <item name="android:gravity">center</item>
        <item name="android:paddingTop">3dp</item>
        <item name="android:textColor">@drawable/tab_menu_text</item>
        <item name="android:textSize">12sp</item>
    </style>
```
*activity_main.xml*  

&emsp;&emsp;注意通过style="@style/tab_menu_item"引用相同的属性。

```
    <RadioGroup
        android:id="@+id/rg_tab_bar"
        android:layout_width="match_parent"
        android:layout_height="55dp"
        android:layout_alignParentBottom="true"
        android:orientation="horizontal">

        <RadioButton
            android:id="@+id/rb_chat"
            style="@style/tab_menu_item"
            android:drawableTop="@drawable/tab_menu_chat"
            android:text="社区" />

        <RadioButton
            android:id="@+id/rb_main"
            style="@style/tab_menu_item"
            android:drawableTop="@drawable/tab_menu_main"
            android:text="首页" />

        <RadioButton
            android:id="@+id/rb_my"
            style="@style/tab_menu_item"
            android:drawableTop="@drawable/tab_menu_my"
            android:text="我的" />

    </RadioGroup>
```
```
    <FrameLayout
        android:id="@+id/ly_content"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@id/rg_tab_bar">
    </FrameLayout>
```
### 3.创建Fragment的简单布局与类
&emsp;&emsp;一般来说，有几个RadioButton就需要几个Fragment的布局，这里就只用一个Fragment做例子。  
主页的fragment布局： *fargment_main.xml*

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="首页"/>
</LinearLayout>
```

主页的fragment类：*MainFragment.java*

```

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }
}
```
### 4.编写MainActivity.java
&emsp;&emsp;解释都在代码的注释当中。

```
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rg_tab_bar;
    private RadioButton rb_main;

    //Fragment Object
    private Fragment fg_main,fg_chat,fg_my;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fManager = getSupportFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        //Radio的点击事件监听
        rg_tab_bar.setOnCheckedChangeListener(this);

        //获取首页单选按钮，并设置为选中状态
        rb_main = (RadioButton) findViewById(R.id.rb_main);
        rb_main.setChecked(true);

    }

    //Radio 点击事件设置
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);

        switch (checkedId){
            case R.id.rb_chat:
                if (fg_chat == null){
                    //第一次需要加载Fragment
                    fg_chat = new ChatFragment();
                    fTransaction.add(R.id.ly_content,fg_chat);
                } else{
                    fTransaction.show(fg_chat);
                }
                break;
            case R.id.rb_main:
                if (fg_main == null){
                    fg_main = new MainFragment();
                    fTransaction.add(R.id.ly_content,fg_main);
                }else{
                    fTransaction.show(fg_main);
                }
                break;
            case R.id.rb_my:
                if (fg_my == null){
                    fg_my = new MyFragment();
                    fTransaction.add(R.id.ly_content,fg_my);
                }else{
                    fTransaction.show(fg_my);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有的Fragment
    private void hideAllFragment(FragmentTransaction fTransaction) {
        if(fg_chat != null)fTransaction.hide(fg_chat);
        if(fg_main != null)fTransaction.hide(fg_main);
        if(fg_my != null)fTransaction.hide(fg_my);
    }
}

```
### 5.点击运行查看效果
![avatar](/images/1.png)
## TabLayout滑动切换页面
&emsp;&emsp;我把滑动切换放在了主界面的顶部，和底部导航栏相结合。

### 1.相关资源文件的准备
&emsp;&emsp;这个部分和之前没有不同，不再次贴代码了。

###2.编写主布局
&emsp;&emsp;注意要添加依赖：
```
implementation 'com.android.support:design:28.0.0'
```
*fragment_mian.xml*

```
    <android.support.design.widget.TabLayout
        android:id="@+id/tl_tab_main"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center_vertical"
        android:paddingTop="10dp"
        style="@style/MyTabLayoutStyle"
        app:tabGravity="fill"
        app:tabMaxWidth="0dp"
        app:tabIndicatorFullWidth="false"
        app:tabMode="fixed">

    </android.support.design.widget.TabLayout>
```
这里也把一些属性抽取了出来。 *style.xml*

```
    <style name="MyTabLayoutStyle" parent="Base.Widget.Design.TabLayout">
        <item name="tabIndicatorHeight">2dp</item>
        <item name="android:textSize">20sp</item>
        <item name="tabSelectedTextColor">#000</item>
        <item name="tabTextColor">#c0c0c0</item>
        <item name="tabIndicatorColor">#71c4b0</item>
    </style>
```
###3.编写fragment布局和类
&emsp;&emsp;这个部分和之前没有不同，不再次贴代码了。

###4.编写页面适配器
*MainPagerAdapter.java*

```
public class MainPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = {"推荐","话题","关注"};
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 1){
            return new TopicFragment();
        }
        if (i == 2){
            return new InterestFragment();
        }
        if(i == 0){
            return new RecommendFragment();
        }
        //刚刚进入时选择 话题 Fragment
        return new RecommendFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
```
###5.重写MainFragment.java

```
public class MainFragment extends Fragment{

    TabLayout mTablayout;
    ViewPager mViewPager;
    private TabLayout.Tab tab_topic,tab_interest,tab_recommend;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        initViews(view);
        initEvents(view);
        return view;
    }

    private void initEvents(View view) {
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTablayout.getTabAt(0)) {
                    mViewPager.setCurrentItem(0);
                } else if (tab == mTablayout.getTabAt(1)) {
                    mViewPager.setCurrentItem(1);
                }else if(tab == mTablayout.getTabAt(2)){
                    mViewPager.setCurrentItem(2);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViews(View view) {
        mTablayout = (TabLayout) view.findViewById(R.id.tl_tab_main);
        mViewPager = (ViewPager) view.findViewById(R.id.vpager_main);

        mViewPager.setAdapter(new MainPagerAdapter(getChildFragmentManager()));
        mTablayout.setupWithViewPager(mViewPager);

        //初始化tab
        tab_recommend = mTablayout.getTabAt(0); //推荐
        tab_topic = mTablayout.getTabAt(1);     //话题
        tab_interest = mTablayout.getTabAt(2);  //关注

        //初始化
        mViewPager.setCurrentItem(0);
    }
}
```
###6.点击运行查看效果
![avatar](/images/2.png)
## 最后贴一下整个代码的结构
![avatar](/images/3.png)
