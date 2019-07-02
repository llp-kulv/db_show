package com.lingrui.db_show.ui.activity;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lingrui.db_show.BaseActivity;
import com.lingrui.db_show.R;
import com.lingrui.db_show.ui.MainType;
import com.lingrui.db_show.ui.fragment.FirstFragment;
import com.lingrui.db_show.ui.fragment.OtherFragment;
import com.lingrui.db_show.ui.fragment.SecondFragment;
import com.lingrui.db_show.ui.fragment.ThirdFragment;
import com.lingrui.db_show.util.Flog;
import com.lingrui.db_show.util.ViewUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main_page)
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainPageActivity";

    @ViewInject(R.id.main_top_layout)
    private ViewGroup main_top_layout;

    @ViewInject(R.id.main_content_layout)
    private ViewGroup main_content_layout;

    private TextView all_surplus;

    private TextView all_out;

    private TextView all_in;

    private TextView other;

    protected Fragment mCurFragment;
    protected String currentPageStr = null;

    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;
    private ThirdFragment mThirdFragment;
    private OtherFragment mOtherFragment;

    public void createView() {
        initTopView();
        preLoadMainFragment();
        viewClick(MainType.FIRST);
    }

    /**
     * 性能优化：预加载主界面的几个fragment
     */
    private void preLoadMainFragment() {
        mFirstFragment = FirstFragment.newInstance();
        mSecondFragment = SecondFragment.newInstance();
        mThirdFragment = ThirdFragment.newInstance();
        mOtherFragment = OtherFragment.newInstance();
    }

    private void initTopView() {
        View topView = LayoutInflater.from(this).inflate(R.layout.main_top_view, null);
        main_top_layout.addView(topView);

        topView.findViewById(R.id.surplus_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClick(MainType.FIRST);
            }
        });
        topView.findViewById(R.id.out_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClick(MainType.SECOND);
            }
        });
        topView.findViewById(R.id.in_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClick(MainType.THIRD);
            }
        });
        topView.findViewById(R.id.other_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClick(MainType.FOURTH);
            }
        });

        all_surplus = topView.findViewById(R.id.all_surplus);
        all_in = topView.findViewById(R.id.all_in);
        all_out = topView.findViewById(R.id.all_out);
        other = topView.findViewById(R.id.other);
    }

    private void viewClick(MainType type) {
        Flog.d(TAG, "viewClick type:" + type);

        switch (type) {
            case FIRST:
                mCurFragment = addFragment(R.id.main_content_layout,
                        MainType.FIRST.getValue(), currentPageStr, null,
                        mFirstFragment);
                currentPageStr = MainType.FIRST.getValue();
                break;
            case SECOND:
                mCurFragment = addFragment(R.id.main_content_layout,
                        MainType.SECOND.getValue(), currentPageStr, null,
                        mSecondFragment);
                currentPageStr = MainType.SECOND.getValue();
                break;
            case THIRD:
                mCurFragment = addFragment(R.id.main_content_layout,
                        MainType.THIRD.getValue(), currentPageStr, null,
                        mThirdFragment);
                currentPageStr = MainType.THIRD.getValue();
                break;
            case FOURTH:
                mCurFragment = addFragment(R.id.main_content_layout,
                        MainType.FOURTH.getValue(), currentPageStr, null,
                        mOtherFragment);
                currentPageStr = MainType.FOURTH.getValue();
                break;
            default:
                break;
        }

        changeTextColor();
    }

    private void changeTextColor() {
        if (currentPageStr == MainType.FIRST.getValue()) {
            all_surplus.setTextColor(ViewUtils.getColor(R.color.blue));
            all_out.setTextColor(ViewUtils.getColor(R.color.black));
            all_in.setTextColor(ViewUtils.getColor(R.color.black));
            other.setTextColor(ViewUtils.getColor(R.color.black));
        } else if (currentPageStr == MainType.SECOND.getValue()) {
            all_out.setTextColor(ViewUtils.getColor(R.color.blue));
            all_surplus.setTextColor(ViewUtils.getColor(R.color.black));
            all_in.setTextColor(ViewUtils.getColor(R.color.black));
            other.setTextColor(ViewUtils.getColor(R.color.black));
        } else if (currentPageStr == MainType.THIRD.getValue()) {
            all_in.setTextColor(ViewUtils.getColor(R.color.blue));
            all_surplus.setTextColor(ViewUtils.getColor(R.color.black));
            all_out.setTextColor(ViewUtils.getColor(R.color.black));
            other.setTextColor(ViewUtils.getColor(R.color.black));
        } else if (currentPageStr == MainType.FOURTH.getValue()) {
            other.setTextColor(ViewUtils.getColor(R.color.blue));
            all_surplus.setTextColor(ViewUtils.getColor(R.color.black));
            all_in.setTextColor(ViewUtils.getColor(R.color.black));
            all_out.setTextColor(ViewUtils.getColor(R.color.black));
        }
    }

    public void change() {
        mFirstFragment.onChange();
        mSecondFragment.onChange();
        mThirdFragment.onChange();
        mThirdFragment.onChange();
    }

}
