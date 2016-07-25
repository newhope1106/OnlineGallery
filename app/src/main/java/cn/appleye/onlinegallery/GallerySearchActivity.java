package cn.appleye.onlinegallery;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.appleye.onlinegallery.list.ImageEntry;
import cn.appleye.onlinegallery.list.ImageTileAdapter;
import cn.appleye.onlinegallery.utils.BaiduSearcher;

public class GallerySearchActivity extends Activity {
    private static final String TAG = "GallerySearchActivity";

    private ListView mListView;
    private Button mSearchBtn;
    private EditText mSearchTextView;
    private ImageTileAdapter mAdapter;

    private int mPageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_search);

        mListView = (ListView) findViewById(R.id.search_list);
        mSearchBtn = (Button) findViewById(R.id.search_btn);
        mSearchTextView = (EditText) findViewById(R.id.search_view);

        mSearchBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startSearch();
            }
        });

        setupListView();
    }

    private void setupListView() {
        mAdapter = new ImageTileAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter != null) {
            mAdapter.resumeImageLoader();
        }
    }

    private void startSearch() {
        String queryWord = mSearchTextView.getText().toString();

        if (!TextUtils.isEmpty(queryWord)) {
            mAdapter.clearData();

            new SearchTask().execute(queryWord);
        }
    }

    private int getPageNumber() {
        return mPageNumber;
    }

    private void increatePageNumber() {
        mPageNumber++;
    }

    @Override
    public void onStop() {
        if (mAdapter != null) {
            mAdapter.pauseImageLoader();
        }

        super.onStop();
    }

    private class SearchTask extends AsyncTask<String, Void, ArrayList<ImageEntry>> {
        @Override
        protected ArrayList<ImageEntry> doInBackground(String... params) {

            if (params == null || params.length<1) {
                return null;
            }

            String keyword = params[0];
            Log.d(TAG, "keyword = " + keyword);
            List<String> imageUrls = BaiduSearcher.loadData(getPageNumber(), keyword);

            ArrayList<ImageEntry> imageUris = new ArrayList<ImageEntry>();

            if (imageUrls != null) {
                for (String url : imageUrls) {
                    Log.d(TAG, "[onPageSelected]url = " + url);
                    ImageEntry entry = new ImageEntry();
                    entry.uri = Uri.parse(url);
                    imageUris.add(entry);
                }
            }
            return imageUris;
        }

        protected void onPostExecute(ArrayList<ImageEntry> result) {
            ArrayList<ImageEntry> imageList = mAdapter.getImageList();

            if (imageList == null) {
                imageList = result;
            } else{
                imageList.addAll(result);
            }
            mAdapter.changeList(imageList);
        }
    }
}
