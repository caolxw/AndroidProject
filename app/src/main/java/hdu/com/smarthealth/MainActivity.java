package hdu.com.smarthealth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import hdu.com.smarthealth.fragment.ChatFragment;
import hdu.com.smarthealth.fragment.MainFragment;
import hdu.com.smarthealth.fragment.MyFragment;

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
        //Radiodi的点击事件监听
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
