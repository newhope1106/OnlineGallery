package cn.appleye.onlinegallery.list;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import cn.appleye.onlinegallery.R;

/**
 * Created by liuliaopu on 2016/6/28.
 */
public class ImageTileAdapter extends BaseAdapter {
    private static final String TAG = "ImageTileAdapter";
    private static int sNumberImagesOneRow = 4;

    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<Uri> mImageUriList = null;

    public ImageTileAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        sNumberImagesOneRow = context.getResources().getInteger(R.integer.number_images_one_row);
    }

    @Override
    public int getCount() {
        if (mImageUriList == null) {
            return 0;
        }

        if (sNumberImagesOneRow <= 0) {
            return mImageUriList.size();
        }

        return (mImageUriList.size()+1)/sNumberImagesOneRow;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
