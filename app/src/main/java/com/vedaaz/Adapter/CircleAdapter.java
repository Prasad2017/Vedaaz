package com.vedaaz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.vedaaz.R;


public class CircleAdapter extends PagerAdapter {

    private Context context;
    private int[] images;
    private String[] titles;
    private String[] descs;
    private LayoutInflater layoutInflater;


    public CircleAdapter(Context context, int[] images, String[] titles, String[] descs) {
        this.context = context;
        this.images = images;
        this.titles = titles;
        this.descs = descs;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.itemlist, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        TextView title = itemView.findViewById(R.id.title);
        TextView desc = itemView.findViewById(R.id.desc);
        imageView.setImageResource(images[position]);
        title.setText(titles[position]);
        desc.setText(descs[position]);
        container.addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
