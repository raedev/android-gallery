package com.androidx.gallery.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.gallery.GalleryManager;
import com.androidx.gallery.R;
import com.androidx.gallery.adapter.PhotoItemAdapter;
import com.androidx.gallery.db.GalleryDataSource;
import com.androidx.gallery.entity.Photo;
import com.androidx.gallery.internal.GalleryLog;
import com.androidx.gallery.provider.PhotoProvider;
import com.androidx.matisse.internal.ui.widget.MediaGridInset;

import java.util.List;

/**
 * 照片流
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class PhotoFlowFragment extends Fragment {

    protected PhotoItemAdapter mAdapter;
    private PhotoProvider mPhotoProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GalleryDataSource dataSource = GalleryManager.getInstance().getDataSource();
        mPhotoProvider = dataSource.getPhotoProvider();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView layout = new RecyclerView(requireContext());
        layout.setId(R.id.recyclerview);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        mAdapter = new PhotoItemAdapter(gridLayoutManager);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
        // 分割线
        int spacing = getResources().getDimensionPixelSize(com.androidx.matisse.R.dimen.media_grid_spacing);
        recyclerView.addItemDecoration(new MediaGridInset(gridLayoutManager.getSpanCount(), spacing, false));

        mAdapter.setEnableCheck(true);

        mPhotoProvider.onBindActivity(requireActivity());

    }

    public void onPhotoLoad() {
        List<Photo> photos = mPhotoProvider.queryPhotos(0, Integer.MAX_VALUE, null, null, null);
        mAdapter.setDataList(photos);
        mAdapter.notifyDataSetChanged();

        GalleryLog.d("全部照片数量：" + mPhotoProvider.queryCount());
        for (Photo photo : photos) {
            GalleryLog.d("照片：" + photo.getName() + photo.getFilePath());
        }
    }
}
