package cn.appleye.onlinegallery.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import java.util.ArrayList;

import cn.appleye.onlinegallery.R;
import cn.appleye.onlinegallery.common.ImageLoaderManager;

/**
 * Created by liuliaopu on 2016/6/28.
 */
public class ImageTileAdapter extends BaseAdapter {
    private static final String TAG = "ImageTileAdapter";
    private static int sNumberImagesOneRow = 4;

    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<ImageEntry> mImageUriList = null;

    private ImageLoaderManager mImageLoader;

    public ImageTileAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        sNumberImagesOneRow = context.getResources().getInteger(R.integer.number_images_one_row);

        mImageLoader = ImageLoaderManager.getInstance(mContext.getApplicationContext());
    }

    public void changeList(ArrayList<ImageEntry> list) {
        mImageUriList = list;

        notifyDataSetChanged();
    }

    public void clearData() {
        if (mImageUriList != null) {
            mImageUriList.clear();
            notifyDataSetChanged();
        }
    }

    public ArrayList<ImageEntry> getImageList() {
        return mImageUriList;
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
    public ArrayList<ImageEntry> getItem(int position) {
        ArrayList<ImageEntry> entryList = new ArrayList<ImageEntry>();
        int max = Math.max((position+sNumberImagesOneRow), mImageUriList.size());
        for(int i=position; i<max; i++) {
            entryList.add(mImageUriList.get(i));
        }

        return entryList;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageTileRow imageTileRow = null;
        if(convertView == null) {
            imageTileRow = new ImageTileRow(mContext);
        } else{
            imageTileRow = (ImageTileRow)convertView;
        }

        ArrayList<ImageEntry> entryList = getItem(position);
        imageTileRow.configureRow(entryList);

        return imageTileRow;
    }

    private class ImageTileRow extends FrameLayout{

        public ImageTileRow(Context context) {
            super(context);
        }

        public void configureRow(ArrayList<ImageEntry> rowList) {
            int columnCount = (rowList.size() > sNumberImagesOneRow)? sNumberImagesOneRow:rowList.size();

            for (int columnCounter = 0; columnCounter < columnCount; columnCounter++) {
                addTileFromEntry(rowList.get(columnCounter), columnCounter);
            }
        }

        private void addTileFromEntry(ImageEntry entry, int childIndex) {
            final ImageTileView imageTileView;

            if(getChildCount() <= childIndex) {
                imageTileView = new ImageTileView(mContext);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                imageTileView.setLayoutParams(params);

                addView(imageTileView);
            } else {
                imageTileView = (ImageTileView)getChildAt(childIndex);
            }

            mImageLoader.loadPhoto(imageTileView, entry.uri);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom){
            final int count = getChildCount();

            // Just line up children horizontally.
            int childLeft = 0;
            for (int i = 0; i < count; i++) {
                final View child = getChildAt(i);

                // Note MeasuredWidth includes the padding.
                final int childWidth = child.getMeasuredWidth();
                child.layout(childLeft, 0, childLeft + childWidth, child.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    public void resumeImageLoader() {
        mImageLoader.resume();
    }

    public void pauseImageLoader() {
        mImageLoader.pause();
    }
}
