package com.raedev.gallery.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 1;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return new Fragment();
            }
        };
        viewPager.setAdapter(adapter);

        findViewById(R.id.btn_photo).setOnClickListener(v -> {
            viewPager.setCurrentItem(0);
        });
        findViewById(R.id.btn_albums).setOnClickListener(v -> {
            viewPager.setCurrentItem(1);
        });

//        Matisse.from(this)
//                .choose(MimeType.ofImage(), false)
//                .countable(true)
//                .capture(true)
//                .captureStrategy(
//                        new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider", "test"))
//                .maxSelectable(9)
//                .thumbnailScale(0.85f)
//                .imageEngine(new GlideEngine())
//                .setOnSelectedListener((uriList, pathList) -> {
//                    Log.e("onSelected", "onSelected: pathList=" + pathList);
//                })
//                .showSingleMediaType(true)
//                .originalEnable(true)
//                .maxOriginalSize(10)
//                .autoHideToolbarOnSingleTap(true)
//                .setOnCheckedListener(isChecked -> {
//                    Log.e("isChecked", "onCheck: isChecked=" + isChecked);
//                })
//                .forResult(10);
//
//        finish();

    }
}