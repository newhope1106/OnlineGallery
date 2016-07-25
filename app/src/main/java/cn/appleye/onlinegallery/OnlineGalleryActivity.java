package cn.appleye.onlinegallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class OnlineGalleryActivity extends Activity {
    private View mSearchImageOnlineView;

    /* 两次返回键之间的间隔 */
    private long mExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_gallery);

        mSearchImageOnlineView = findViewById(R.id.search_image_online_btn);
        mSearchImageOnlineView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(OnlineGalleryActivity.this, GallerySearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_online_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 连续两个返回键退出
     * */
    public void onBackPressed() {
        exit();
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 1000) {
            Toast.makeText(this,
                    getString(R.string.keyback_hint), Toast.LENGTH_SHORT)
                    .show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
